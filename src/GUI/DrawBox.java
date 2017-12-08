package GUI;

import Analyze.NodeInfo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Administrator
 */

public class DrawBox extends JPanel implements MouseListener,MouseMotionListener {
    private Box box;
    Point ChoosedPoint;

    public Box getBox() {
        return box;
    }
    
    DrawBox(Box box){
        super();
        this.box=box;
        setBounds(box.getRec());
        addContent(); 
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public void updateDrawBox(Box box){
        this.box=box;
    }
@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        Rectangle rec=box.getRec();
        int x =0;
        int y = 0;
        int width = rec.width;
        int height = rec.height;
        g.drawRect(x, y, width, height);
        g.fillRect(x, y, width, height);
        
       
    }
    private void addMouse(JLabel label){
        label.addMouseListener(this);
        label.addMouseMotionListener(this);
    }
    
    void addContent(){
        setLayout(new GridLayout(box.getRec().height/ConstantAtribute.getRowHeight(),1));
        setOpaque(false);
        NodeInfo data=box.getData();
        int contentSize=ConstantAtribute.getContentSize();
        
        // Name of Node

        JLabel name=new JLabel(data.getNameOfNode(),JLabel.CENTER);
        name.setFont(new Font(name.getFont().getName(),Font.BOLD,contentSize));
        name.setToolTipText(data.getNameOfNode());
        addMouse(name);
        add(name);
        
        // type Of Node
        JLabel type=new JLabel("< "+data.getTypeOfNode()+" >",JLabel.CENTER);
        type.setFont(new Font(type.getFont().getName(),Font.ITALIC,contentSize));
        type.setToolTipText(data.getTypeOfNode());
        addMouse(type);
        add(type);
        
        
        // attribute
        
        for(int i=0;i<data.getVariables().size();i++){
            String var="  "+data.getVariables().get(i).getVariable();
            JLabel varLabel=new JLabel(var);
            varLabel.setFont(new Font(varLabel.getFont().getName(),Font.PLAIN,contentSize));
            addMouse(varLabel);
            varLabel.setToolTipText(var);
            add(varLabel);
        }
        // methods and constructors
        for(int i=0;i<data.getConstructors().size();i++){
            String Cons="  "+data.getConstructors().get(i).getConstructor();
            JLabel ConsLabel=new JLabel(Cons);
            ConsLabel.setFont(new Font(ConsLabel.getFont().getName(),Font.PLAIN,contentSize));
            addMouse(ConsLabel);
            ConsLabel.setToolTipText(Cons);
            add(ConsLabel);
        }
        for(int i=0;i<data.getMethods().size();i++){
            String method="  "+data.getMethods().get(i).getMethod();
            JLabel methodLabel=new JLabel(method);
            methodLabel.setFont(new Font(methodLabel.getFont().getName(),Font.PLAIN,contentSize));
            addMouse(methodLabel);
            methodLabel.setToolTipText(method);
            add(methodLabel);
        }       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
            if(e.getClickCount() == 2){
                ConstantAtribute.zoomIn();
                MainPanel.getMainPanel().updateListDrawBox(true);
                MainPanel.getMainPanel().setComponentZOrder(this, 0); 
                MainPanel.getMainPanel().addContent();
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
                ConstantAtribute.zoomOut();
                MainPanel.getMainPanel().updateListDrawBox(false);
                MainPanel.getMainPanel().setComponentZOrder(this, 0);
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
