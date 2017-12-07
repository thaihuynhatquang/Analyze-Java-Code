/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Analyze.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class MainPanel extends JPanel {
    private HashMap<String, Box> listBox;
    private static MainPanel main;
   
    public static MainPanel getMainPanel(){
        if(main==null){
            main=new MainPanel();
            return main;
        }
        return main;
    }
    
    private MainPanel() {
        listBox=new HashMap<>();
        setLayout(null);
        setListBox();
        addContent();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height  );
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
        return new Dimension(maxX+100,maxY+100);
       
    }
    
    public void addContent(){
                listBox.values().forEach((index) -> {
            add(new DrawBox(index)); });
    }
    public void setListBox() {
        AnalyzeFile newAnalyzeFile = new AnalyzeFile();
        try {
            newAnalyzeFile = new AnalyzeFile("data");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
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
        
        for(Box index: listBox.values()){
            if(0!=count%2){
                count++;
                continue;
            }
            else if(0==count){
                index.setLocationOfRec(10,10);
                count++;
            }
            else  {
                index.setLocationOfRec(evenPoint.x+250+50 + 10, 10 + evenPoint.y);
                evenPoint=new Point(evenPoint.x+250+50, evenPoint.y);
                count++;
                
            }
            
            int tmpHeight=index.getRec().height;
            if(evenDistance<tmpHeight)
                evenDistance=tmpHeight;
        }
        count=0;
        
        for(Box index: listBox.values()){
            if(1==count){
                index.setLocationOfRec(10, evenDistance + 50);
                count++;
                oddPoint=new Point(10,evenDistance+50);        
            }
            else if(0!=count%2) {
                index.setLocationOfRec(oddPoint.x+250+50, oddPoint.y);
                oddPoint=new Point(oddPoint.x+250+50, oddPoint.y);
                count++;         
            }
            else{
                count++;        
            }
        }
    }   
}