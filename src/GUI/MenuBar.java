/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import GUI.MainPanel.*;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author Administrator
 */
public class MenuBar extends JMenuBar{
    JMenu fileMenu = new JMenu("File");
    JMenuItem openMenuItem = new JMenuItem("Open folder");
    JMenuItem saveMenuItem = new JMenuItem("Save as image");
    JMenuItem clearMenuItem = new JMenuItem("Clear");
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    
    public void addMenuItem(){
      
      openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
      openMenuItem.setMnemonic(KeyEvent.VK_O);
      openMenuItem.setActionCommand("Open");

      
      saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
      saveMenuItem.setMnemonic(KeyEvent.VK_S);
      saveMenuItem.setActionCommand("Save");

      
      clearMenuItem.setMnemonic(KeyEvent.VK_C);
      clearMenuItem.setActionCommand("Clear");
      
      
      exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
      exitMenuItem.setMnemonic(KeyEvent.VK_X);
      exitMenuItem.setActionCommand("Exit");
      
      MenuItemListener menuItemListener = new MenuItemListener();
      clearMenuItem.addActionListener(menuItemListener);
      openMenuItem.addActionListener(menuItemListener);
      saveMenuItem.addActionListener(menuItemListener);
      exitMenuItem.addActionListener(menuItemListener);
      
      //Add item to Menu Bar
      fileMenu.add(openMenuItem);
      fileMenu.add(clearMenuItem);
      fileMenu.add(saveMenuItem);
      fileMenu.add(exitMenuItem);
}
    
    public MenuBar() {
        addMenuItem();
        fileMenu.setMnemonic(KeyEvent.VK_F);
        //Add Menu Bar to JFrame
        this.add(fileMenu);
    }
    
    class MenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {   
            if(e.getSource() == exitMenuItem){
                System.out.println(e.getActionCommand() + " JMenuItem clicked.");
                System.exit(0);
            }else if(e.getSource() == openMenuItem){
                System.out.println(e.getActionCommand() + " JMenuItem clicked.");
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Open");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                // disable the "All files" option.
                chooser.setAcceptAllFileFilterUsed(false);
                // only folder can be displayed
                chooser.setFileFilter(new FileNameExtensionFilter("Properties file", "properties"));
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    GUI.MainPanel.folderPath = chooser.getSelectedFile().getPath();
                    System.out.println("getSelectedFolder() : " +  chooser.getSelectedFile());
                } else {
                    System.out.println("Open command cancelled by user." + "\n");
                }
            }else if(e.getSource() == saveMenuItem){
                System.out.println(e.getActionCommand() + " JMenuItem clicked.");
                try{
                    Component mainPanel;
                        ImageIO.write(ExportImage.getScreenShot(MainPanel.getMainPanel()),"jpg",new File("exportImage.jpg"));
                    }
                    catch(IOException ex){
                        Logger.getLogger(MainWindow.class.getName());
                    }
            }
            else
            System.out.println(e.getActionCommand() + " JMenuItem clicked.");
        }    
    }
}
