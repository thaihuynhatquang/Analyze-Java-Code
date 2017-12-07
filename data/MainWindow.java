/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalysisSourceCode;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Tung Duong
 */
public class MainWindow extends JFrame {
    public MainWindow(){
        JFrame myFrame=new JFrame(" Class Diagram ");
//        myFrame.setSize(800,600);
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        myFrame.setLocation(dim.width/2 - myFrame.getSize().width/2, dim.height/2 - myFrame.getSize().height/2);
        
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        myFrame.setVisible(true);
        

        MainPanel mainPanel=new MainPanel();
        mainPanel.setLayout(null);
        JScrollPane scroll=new JScrollPane(mainPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        myFrame.add(scroll);

        myFrame.pack(); 
        
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Run here.
                MainWindow run=new MainWindow();
                
                
            }
        });
    }
}
