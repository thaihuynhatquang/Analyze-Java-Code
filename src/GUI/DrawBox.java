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
        Color color = new Color(27,94,32);
        g.setColor(color);
        Rectangle rec=box.getRec();
        int x =0;
        int y = 0;
        int width = rec.width;
        int height = rec.height;
        g.drawRect(x, y, width, height);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        int tmpHeight=2*Constants.getRowHeight();
        y=y+tmpHeight;
        g.drawLine(x, y, x+Constants.getColumnWidth(), y);
        if(box.getData().getVariables().size()>0){
            tmpHeight=box.getData().getVariables().size()*Constants.getRowHeight();
            y=y+tmpHeight;
            g.drawLine(x, y, x+Constants.getColumnWidth(), y);
        }
    }
    private void addMouse(JLabel label){
        label.addMouseListener(this);
        label.addMouseMotionListener(this);
    }
    
    void addContent(){
        Color color = new Color(232,245,233);
        setLayout(new GridLayout(box.getRec().height/Constants.getRowHeight(),1));
        setOpaque(false);
        NodeInfo data=box.getData();
        int contentSize=Constants.getContentSize();
        // type Of Node
        JLabel type=new JLabel("< "+data.getTypeOfNode()+" >",JLabel.CENTER);
        type.setForeground(color);
        type.setFont(new Font(type.getFont().getName(),Font.ITALIC,contentSize));
        type.setToolTipText(data.getTypeOfNode());
        addMouse(type);
        add(type);
        // Name of Node

        JLabel name=new JLabel(data.getNameOfNode(),JLabel.CENTER);
        name.setForeground(color);
        name.setFont(new Font(name.getFont().getName(),Font.BOLD,contentSize));
        name.setToolTipText(data.getNameOfNode());
        addMouse(name);
        add(name);
        
        
        
        
        // attribute
        
        for(int i=0;i<data.getVariables().size();i++){
            String var="  "+data.getVariables().get(i).getVariable();
            JLabel varLabel=new JLabel(var);
            varLabel.setForeground(color);
            varLabel.setFont(new Font(varLabel.getFont().getName(),Font.PLAIN,contentSize));
            addMouse(varLabel);
            varLabel.setToolTipText(var);
            add(varLabel);
        }
        // methods and constructors
        for(int i=0;i<data.getConstructors().size();i++){
            String cons ="  "+data.getConstructors().get(i).getConstructor();
            JLabel consLabel = new JLabel(cons );
            consLabel.setForeground(color);
            consLabel.setFont(new Font(consLabel.getFont().getName(),Font.PLAIN,contentSize));
            addMouse(consLabel);
            consLabel.setToolTipText(cons );
            add(consLabel);
        }
        for(int i=0;i<data.getMethods().size();i++){
            String method="  "+data.getMethods().get(i).getMethod();
            JLabel methodLabel=new JLabel(method);
            methodLabel.setForeground(color);
            methodLabel.setFont(new Font(methodLabel.getFont().getName(),Font.PLAIN,contentSize));
            addMouse(methodLabel);
            methodLabel.setToolTipText(method);
            add(methodLabel);
        }       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // double click left mouse to zoom in
        if(SwingUtilities.isLeftMouseButton(e)){
            if(e.getClickCount() == 2){
                Constants.zoomIn();
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
        // double click right mouse to zoom out
        if(SwingUtilities.isRightMouseButton(e)){
            if(e.getClickCount() == 2){
                Constants.zoomOut();
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
