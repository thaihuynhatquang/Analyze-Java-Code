/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Analyze.AnalyzeFile;
import Analyze.NodeInfo;
import Coefficient.Constants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Tung Duong
 */
public class MainPanel extends JPanel implements MouseListener, MouseMotionListener{
    private HashMap<String, Box> listBox;
    private static MainPanel main;
    private static ArrayList<DrawBox> listDrawBox=new ArrayList<>();
    private static String temp;
    public static MainPanel getMainPanel(){
        if(main==null){
            main=new MainPanel();
            return main;
        }
        return main;
    }

    public static ArrayList<DrawBox> getListDrawBox() {
        return listDrawBox;
    }
    
    MainPanel() {
        listBox=new HashMap<>();
//        setLayout(null);
//        setListBox(folderPath);
//        setListDrawBox();
//        addContent();
//        addMouseListener(this );
//        addMouseMotionListener(this);
        
    }
    
    public void init(String folderPath){
        
        setLayout(null);
        setListBox(folderPath);
        setListDrawBox();
        addContent();
        addMouseListener(this );
        addMouseMotionListener(this);
    }
    
    public void setListDrawBox(){
        listBox.values().forEach((index) -> {
            listDrawBox.add(new DrawBox(index)); });
    }
    
    public void toSortAuto(){
        setListBox(temp);
        int count=0;
        for(Box index: listBox.values()){
            listDrawBox.get(count++).updateDrawBox(index);
        }
        addContent();
    }
    
    public void updateListDrawBox(boolean isZoomIn){

        if(isZoomIn){
            for(Box index: listBox.values()){
            index.getRec().x*=Constants.getFactor();
            index.getRec().y*=Constants.getFactor();
            index.getRec().width*=Constants.getFactor();
            index.getRec().height*=Constants.getFactor();
            }}
        else{
             for(Box index: listBox.values()){
            index.getRec().x/=Constants.getFactor();
            index.getRec().y/=Constants.getFactor();
            index.getRec().width/=Constants.getFactor();
            index.getRec().height/=Constants.getFactor();
            }       
        }           

        int count=0;
        for(Box index: listBox.values()){
            listDrawBox.get(count++).updateDrawBox(index);
        }


    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
        g.setColor(Color.RED);
        DrawRelation newDrawRelation=new DrawRelation(listBox);
        newDrawRelation.draw(g);
    }
    public HashMap<String, Box> getListBox() {
        return listBox;
    }
    @Override
    public Dimension getPreferredSize() {
        int maxX=0;
        int maxY=0;
        for(Box index: listBox.values()){
            int x=index.getRec().x+index.getRec().width;
            int y=index.getRec().y+index.getRec().height;
            if(maxX<x) maxX=x;
            if(maxY<y) maxY=y;
        }
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        if(dim.getWidth() < maxX+100 && dim.getHeight() < maxY+100) return new Dimension(maxX+100,maxY+100);
        else return dim;
    }
    
    public void addContent(){
        for(DrawBox index: listDrawBox){
            add(index);
        }
    }
    public void setListBox(String folderPath) {  
        try {
            temp = folderPath;
            AnalyzeFile newAnalyzeFile = new AnalyzeFile(folderPath);
            // initializing the listBox
        
            for(NodeInfo index: newAnalyzeFile.getListNode().values()){
                listBox.put(index.getNameOfNode(), new Box(index));

            }
            // set Location of each Box: 2 rows
            // Problems remaining: Print all classes ,which is independent , on the left
            int count=0;
            int evenDistance=0;
            Point evenPoint=new Point();
            Point oddPoint=new Point();
            int distanceBoxes=Constants.getDistanceBoxes();
            int columnWidth=Constants.getColumnWidth();
            for(Box index: listBox.values()){
                if(0!=count%2){
                    count++;
                    continue;
                }
                else if(0==count){
                    index.setLocationOfRec(0,0);
                    count++;
                }
                else  {
                    index.setLocationOfRec(evenPoint.x+distanceBoxes+columnWidth, evenPoint.y);
                    evenPoint=new Point(evenPoint.x+distanceBoxes+columnWidth, evenPoint.y);
                    count++;

                }

                int tmpHeight=index.getRec().height;
                if(evenDistance<tmpHeight)
                    evenDistance=tmpHeight;
            }
            count=0;

            for(Box index: listBox.values()){

                if(1==count){
                    index.setLocationOfRec(0,evenDistance+distanceBoxes);
                    count++;
                    oddPoint=new Point(0,evenDistance+distanceBoxes);

                }
                else if(0!=count%2) {
                    index.setLocationOfRec(oddPoint.x+columnWidth+distanceBoxes, oddPoint.y);
                    oddPoint=new Point(oddPoint.x+columnWidth+distanceBoxes, oddPoint.y);
                    count++;

                }
                else{
                    count++;

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
            if(e.getClickCount() == 2){
                Constants.zoomIn();
                updateListDrawBox(true);
                
            //    setComponentZOrder(this, 0); 
//                MainPanel.getMainPanel().addContent();
                getTopLevelAncestor().repaint();
                for(DrawBox index: MainPanel.getListDrawBox()){
                    index.setBounds(index.getBox().getRec());
                    index.repaint();
                    index.removeAll();
                    index.revalidate();
                    index.addContent();
                }                
            }
        }
        if(SwingUtilities.isRightMouseButton(e)){
            if(e.getClickCount() == 2){
                Constants.zoomOut();
                updateListDrawBox(false);
            //    setComponentZOrder(this, 0);
                getTopLevelAncestor().repaint();
                for(DrawBox index : MainPanel.getListDrawBox()){
                    index.setBounds(index.getBox().getRec());
                    index.repaint();
                    index.removeAll();
                    index.revalidate();
                    index.addContent();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
   
}
