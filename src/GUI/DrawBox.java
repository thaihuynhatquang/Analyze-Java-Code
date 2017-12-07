/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Analyze.NodeInfo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class DrawBox extends JPanel implements MouseListener,MouseMotionListener {
    private Box box;
    Point ChoosedPoint;
    
    DrawBox(Box box){
        super();
        this.box=box;
        setBounds(box.getRec());
        addMouseListener(this);
        addMouseMotionListener(this);
        addContent(); 
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.yellow);
        Rectangle rec=box.getRec();
        int x =0;
        int y = 0;
        int width = rec.width;
        int height = rec.height;
        g.drawRect(x, y, width, height);
        g.fillRect(x, y, width, height);
        for(int i = 0; i < height / 20; i++) {
            if(i == 0||i==1) {
                g.setColor(Color.green);
                g.fillRect(x, y + i * 20, width, 20);
                g.setColor(Color.black);
            }
            g.drawRect(x, y + i * 20, width, 20);;
        }
       
    }
    private void addMouse(JLabel label){
        label.addMouseListener(this);
        label.addMouseMotionListener(this);
    }
    private void addContent(){
        setLayout(new GridLayout(box.getRec().height/20,1));
        setOpaque(false);
        NodeInfo data=box.getData();
        // type Of Node
        JLabel type=new JLabel(data.getTypeOfNode(),JLabel.CENTER);
        addMouse(type);
        add(type);
        
        // Name of Node

        JLabel name=new JLabel(data.getNameOfNode(),JLabel.CENTER);
        addMouse(name);
        name.setToolTipText(data.getNameOfNode());
        add(name);
        // attribute
        
        for(int i=0;i<data.getVariables().size();i++){
            String var= "   "  + data.getVariables().get(i).getVariable();
            JLabel varLabel=new JLabel(var);
            addMouse(varLabel);
            varLabel.setToolTipText(var);
            add(varLabel);
        }
        // methods and constructors
        for(int i=0;i<data.getConstructors().size();i++){
            String Cons = "   " + data.getConstructors().get(i).getConstructor();
            JLabel ConsLabel=new JLabel(Cons);
            addMouse(ConsLabel);
            ConsLabel.setToolTipText(Cons);
            add(ConsLabel);
        }
        for(int i=0;i<data.getMethods().size();i++){
            String method = "   " + data.getMethods().get(i).getMethod();
            JLabel methodLabel=new JLabel(method);
            addMouse(methodLabel);
            methodLabel.setToolTipText(method);
            add(methodLabel);
        }   
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        MainPanel.getMainPanel().setComponentZOrder(this, 0);
        getTopLevelAncestor().repaint();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        MainPanel.getMainPanel().setComponentZOrder(this, 0);
        ChoosedPoint=e.getPoint();
        getTopLevelAncestor().repaint();
        repaint();
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
    public void mouseDragged(MouseEvent e) {
        int x=box.getRec().x+e.getX()-ChoosedPoint.x;
        int y=box.getRec().y+e.getY()-ChoosedPoint.y;
        box.setLocationOfRec(x, y);
        setBounds(box.getRec());
        getTopLevelAncestor().repaint();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
