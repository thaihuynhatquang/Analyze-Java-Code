package GUI;

import Analyze.AnalyzeFile;
import Analyze.NodeInfo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
 * @author Administrator
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
    }
    
    public void init(String folderPath){
        if(temp != folderPath){
            main.listBox.clear();
            listDrawBox.clear();
            AnalyzeFile.listNode.clear();
            temp = folderPath;
        }else System.out.println("HI");
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
            index.getRec().x*=ConstantAtribute.getFactor();
            index.getRec().y*=ConstantAtribute.getFactor();
            index.getRec().width*=ConstantAtribute.getFactor();
            index.getRec().height*=ConstantAtribute.getFactor();
            }}
        else{
             for(Box index: listBox.values()){
            index.getRec().x/=ConstantAtribute.getFactor();
            index.getRec().y/=ConstantAtribute.getFactor();
            index.getRec().width/=ConstantAtribute.getFactor();
            index.getRec().height/=ConstantAtribute.getFactor();
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
        g.setColor(Color.WHITE);
        DrawRelation newDrawRelation = new DrawRelation(listBox);
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
        if(dim.getWidth() < maxX+200 && dim.getHeight() < maxY+200) 
            return new Dimension(maxX+100,maxY+100);
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
            int count=0;
            int evenDistance=0;
            Point evenPoint=new Point();
            Point oddPoint=new Point();
            int distanceBoxes=ConstantAtribute.getDistanceBoxes();
            int columnWidth=ConstantAtribute.getColumnWidth();
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
    
    public void zoomIn(){
        ConstantAtribute.zoomIn();
        updateListDrawBox(true);

        getTopLevelAncestor().repaint();
        for(DrawBox index: MainPanel.getListDrawBox()){
            index.setBounds(index.getBox().getRec());
            index.repaint();
            index.removeAll();
            index.revalidate();
            index.addContent();
        }        
    }
    
    public void zoomOut(){
        ConstantAtribute.zoomOut();
        updateListDrawBox(false);
        getTopLevelAncestor().repaint();
        for(DrawBox index : MainPanel.getListDrawBox()){
            index.setBounds(index.getBox().getRec());
            index.repaint();
            index.removeAll();
            index.revalidate();
            index.addContent();
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
            if(e.getClickCount() == 1){
                zoomIn();       
            }
        }
        if(SwingUtilities.isRightMouseButton(e)){
            if(e.getClickCount() == 1){
                zoomOut();
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
