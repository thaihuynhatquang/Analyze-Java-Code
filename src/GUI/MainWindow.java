/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author Administrator
 */
public class MainWindow extends JFrame {
    public MainWindow(){
        JFrame myFrame=new JFrame(" Class Diagram ");
        myFrame.setSize(800,600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        myFrame.setLocation(dim.width/2 - myFrame.getSize().width/2, 
                dim.height/2 - myFrame.getSize().height/2);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);

        MainPanel mainPanel = new MainPanel();
        //Hàm này đang test cái class Panel swap nè, đừng quan tâm ._.
//        PanelSwap panelSwap = new PanelSwap();
        JScrollPane scroll = new JScrollPane(mainPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        myFrame.add(scroll);
        
        MenuBar mb = new MenuBar();
        myFrame.setJMenuBar(mb);
//        myFrame.pack(); 

    }
    public static void main(String[] args) {
         try{ 
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "Nhật Quang");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } 
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e){ 
            System.out.println(e);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Run here.
                MainWindow run=new MainWindow();
            }
        });
    }
}
