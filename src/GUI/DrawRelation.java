package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */

public class DrawRelation {
    private HashMap<String, Box> listBox;
    AffineTransform tx = new AffineTransform();
    Polygon arrowHead = new Polygon();  

    private HashMap<Number,String> alreadyDrawn=new HashMap<>();
    public DrawRelation(HashMap<String, Box> listBox) {
        this.listBox = listBox;
        setArrowHead();
    }

    public void setArrowHead() {
        arrowHead.addPoint( 0,10);
        arrowHead.addPoint( -5, 0);
        arrowHead.addPoint( 5,0);
    }
    
    public void draw(Graphics g){
        drawRelationShip(g);
    }
    public void drawRelationShip(Graphics g){
        // draw inheritance && implementation
        for(Box index: listBox.values()){
             // draw inheritance
            String father=index.getData().getSuperNode();
            if(listBox.containsKey(father)){
                Box fatherBox=listBox.get(father);
                drawInheritance(g,index,fatherBox);
            }
             // draw  implementation
            for(String tmpInterface: index.getData().getListInterfaces()){
                if(listBox.containsKey(tmpInterface)){
                Box interfaceBox=listBox.get(tmpInterface);
                drawImplementation(g,index,interfaceBox);
            }
            }
//             draw  association
            
            for(Box tmp:listBox.values()){
                if(index.hasA(tmp)){
                    if(tmp.equals(index))
                        draw2WayAssociation(g,index,tmp);
                    else if(tmp.hasA(index))
                        draw2WayAssociation(g,index,tmp);
                    else draw1WayAssociation(g,index,tmp);
                }
            }    
        }   
    }
    
    public void draw2WayAssociation(Graphics g,Box child,Box father){
        try {
            if(child!=father){
            Line2D.Double line = new Line2D.Double(child.getRec().x+child.getRec().width/2,child.getRec().y+child.getRec().height/2,father.getRec().x+father.getRec().width/2,father.getRec().y+father.getRec().height/2);
            Graphics2D g2=(Graphics2D)g.create();
            g2.draw(line);  
            }
            else{
                int extraDistance=20;
                Rectangle rec=child.getRec();
                g.setColor(Color.red);
                g.drawOval(rec.x+rec.width/2, rec.y-extraDistance, extraDistance, extraDistance);
            }
        } catch (Exception e) {
            return;
        }
    }
    public void draw1WayAssociation(Graphics g,Box child,Box father){
        try {
            Line2D.Double line = new Line2D.Double(child.getRec().x+child.getRec().width/2,child.getRec().y+child.getRec().height/2,father.getRec().x+father.getRec().width/2,father.getRec().y+father.getRec().height/2);
            Graphics2D g2=(Graphics2D)g.create();
            Point2D.Double point=getIntersectionPoint(line, father.getRec());
            Line2D.Double myLine=new Line2D.Double(line.x1,line.y1,point.getX(),point.getY());
            g2.draw(myLine);  
            drawVectorHead(g,myLine);   
        } catch (Exception e) {
            return;
        }
        
       
    }
    public void drawImplementation(Graphics g,Box child,Box father){
        try {
            Line2D.Double line = new Line2D.Double(child.getRec().x+child.getRec().width/2,child.getRec().y+child.getRec().height/2,father.getRec().x+father.getRec().width/2,father.getRec().y+father.getRec().height/2);
            Graphics2D g2=(Graphics2D)g.create();
            Point2D.Double point=getIntersectionPoint(line, father.getRec());
            Line2D.Double myLine=new Line2D.Double(line.x1,line.y1,point.getX(),point.getY());
            float[] dash3 = {4f, 0f, 2f};
            BasicStroke bs3 = new BasicStroke(1, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_ROUND, 1.0f, dash3, 2f);
            g2.setStroke(bs3);
            g2.draw(myLine);
            g2.dispose();
            drawArrowHead(g,myLine);
        } catch (Exception e) {
            return;
        }
        
    }
    public void drawInheritance(Graphics g,Box child,Box father){
        try {
            Line2D.Double line = new Line2D.Double(child.getRec().x+child.getRec().width/2,
                                                child.getRec().y+child.getRec().height/2,
                                                father.getRec().x+father.getRec().width/2,
                                                father.getRec().y+father.getRec().height/2);
            Graphics2D g2=(Graphics2D)g;
            Point2D.Double point=getIntersectionPoint(line, father.getRec());
            Line2D.Double myLine=new Line2D.Double(line.x1,line.y1,point.getX(),point.getY());
            g2.draw(myLine);
            drawArrowHead(g,myLine);
        } catch (Exception e) {
            return;
        }
        
        
    }
    
    private void drawVectorHead(Graphics g,Line2D.Double line) {  
        Graphics2D g2 = (Graphics2D) g.create();
        AffineTransform saveXform = g2.getTransform();
        tx.setToIdentity();
        double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
        tx.translate(line.x2, line.y2);
        tx.rotate((angle-Math.PI/2d)); 
        g2.transform(tx);   
        g2.drawLine(-5, 0, 0, 10);
        g2.drawLine(5, 0, 0, 10);
        g2.drawLine(0, 10, 0, 0);
        g2.setTransform(saveXform);
        g2.dispose();
}
    private void drawArrowHead(Graphics g,Line2D.Double line) {  
        Graphics2D g2 = (Graphics2D) g.create();
        AffineTransform saveXform = g2.getTransform();
        tx.setToIdentity();
        double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
        tx.translate(line.x2, line.y2);
        tx.rotate((angle-Math.PI/2d)); 
        g2.transform(tx);   
        g2.draw(arrowHead);
        g2.setTransform(saveXform);
        g2.dispose();
}
    public boolean Contain(Line2D.Double line,Point2D.Double point){
        final double acceptableDistance=0.01;
        double lengthLine=Math.sqrt((line.y1-line.y2)*(line.y1-line.y2)+(line.x1-line.x2)*(line.x1-line.x2));
        double subLength1=Math.sqrt((line.y1-point.y)*(line.y1-point.y)+(line.x1-point.x)*(line.x1-point.x));
        double subLength2=Math.sqrt((line.y2-point.y)*(line.y2-point.y)+(line.x2-point.x)*(line.x2-point.x));
        
        return Math.abs(lengthLine-subLength1-subLength2)<acceptableDistance;
    }
    public Point2D.Double getIntersectionPoint(Line2D.Double line, Rectangle2D rectangle) {
        final int extraDistance=10;
        Point2D.Double[] p = new Point2D.Double[4];
        // above line
        Line2D.Double aboveLine=new Line2D.Double(rectangle.getX(),
            rectangle.getY(),
            rectangle.getX() + rectangle.getWidth(),
            rectangle.getY());
        p[0] = getIntersectionPoint(line,aboveLine);
        if(null!=p[0])
            if(Contain(line,p[0])&& Contain(aboveLine,p[0]) )
                return new Point2D.Double(p[0].x, p[0].y-extraDistance);
        // Bottom line
        Line2D.Double bottomLine=new Line2D.Double(
            rectangle.getX(),
            rectangle.getY() + rectangle.getHeight(),
            rectangle.getX() + rectangle.getWidth(),
            rectangle.getY() + rectangle.getHeight());
        p[1] = getIntersectionPoint(line,bottomLine );
        
        if(null!=p[1])
            if(Contain(line,p[1]) &&Contain(bottomLine,p[1]) )
                return new Point2D.Double(p[1].x, p[1].y+extraDistance);
        // Left side
        Line2D.Double leftSide=new Line2D.Double(
            rectangle.getX(),
            rectangle.getY(),
            rectangle.getX(),
            rectangle.getY() + rectangle.getHeight());
        p[2] = getIntersectionPoint(line,leftSide);
        
        if(null!=p[2])
            if(Contain(line,p[2])&&Contain(leftSide,p[2]))
             return new Point2D.Double(p[2].x-extraDistance, p[2].y);
        // Right side
        Line2D.Double rightSide=new Line2D.Double(
            rectangle.getX() + rectangle.getWidth(),
            rectangle.getY(),
            rectangle.getX() + rectangle.getWidth(),
            rectangle.getY() + rectangle.getHeight());
        
        p[3] = getIntersectionPoint(line,rightSide);
        if(null!=p[3])
            if(Contain(line,p[3]) &&Contain(rightSide,p[3]))
                return new Point2D.Double(p[3].x+extraDistance, p[3].y);
        return null;

        }

    public Point2D.Double getIntersectionPoint(Line2D.Double lineA, Line2D lineB) {

        double x1 = lineA.getX1();
        double y1 = lineA.getY1();
        double x2 = lineA.getX2();
        double y2 = lineA.getY2();

        double x3 = lineB.getX1();
        double y3 = lineB.getY1();
        double x4 = lineB.getX2();
        double y4 = lineB.getY2();

        Point2D.Double p = null;
        
        double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        
        if (d != 0) {
            double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
            double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

            p = new Point2D.Double(xi, yi);
        }
        return p;
    }
}

