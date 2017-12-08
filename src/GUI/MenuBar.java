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
import java.awt.Component;
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
    JMenuItem sortMenuItem = new JMenuItem("Auto Sorting");
    
    public void addMenuItem(){
      
      openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
      openMenuItem.setMnemonic(KeyEvent.VK_O);
      openMenuItem.setActionCommand("Open");

      
      saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
      saveMenuItem.setMnemonic(KeyEvent.VK_S);
      saveMenuItem.setActionCommand("Save");

      sortMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
      sortMenuItem.setMnemonic(KeyEvent.VK_A);
      sortMenuItem.setActionCommand("Auto Sorting");
      
      
      clearMenuItem.setMnemonic(KeyEvent.VK_C);
      clearMenuItem.setActionCommand("Clear");
      
      
      exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
      exitMenuItem.setMnemonic(KeyEvent.VK_X);
      exitMenuItem.setActionCommand("Exit");
      
      MenuItemListener menuItemListener = new MenuItemListener();
      clearMenuItem.addActionListener(menuItemListener);
      openMenuItem.addActionListener(menuItemListener);
      saveMenuItem.addActionListener(menuItemListener);
      sortMenuItem.addActionListener(menuItemListener);
      exitMenuItem.addActionListener(menuItemListener);
      
      
      //Add item to Menu Bar
      fileMenu.add(openMenuItem);
      fileMenu.add(clearMenuItem);
      fileMenu.add(saveMenuItem);
      fileMenu.add(sortMenuItem);
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
                //Capture Image
                System.out.println(e.getActionCommand() + " JMenuItem clicked.");
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Save Image");
                //set chooser filter
                chooser.addChoosableFileFilter(new FileNameExtensionFilter(".jpg", "jpg"));
                chooser.addChoosableFileFilter(new FileNameExtensionFilter(".png", "png"));
                // disable the "All files" option.
                chooser.setAcceptAllFileFilterUsed(false);
                // set default type
                chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);
                // set default file
//                chooser.setSelectedFile(defaultFile);
                int returnVal = chooser.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getSelectedFolder() : " +  chooser.getSelectedFile().getAbsolutePath());
                    try{
                        String name = chooser.getSelectedFile().getCanonicalPath();
                        String ext = ((FileNameExtensionFilter) chooser.getFileFilter()).getExtensions()[0];
                        File file = new File(name + "." + ext);
                        JOptionPane.showMessageDialog(null, "Image saved in "+ name + "." + ext);
                        ImageIO.write(getScreenShot(MainPanel.getMainPanel()),ext,file);
                        
                    }catch(IOException ex){
                        Logger.getLogger(MainWindow.class.getName());
                    }
                }else {
                    System.out.println("Save command cancelled by user." + "\n");
                }
            }
            else if(e.getSource() == sortMenuItem){
                System.out.println(e.getActionCommand() + " JMenuItem clicked.");
                MainPanel.getMainPanel().toSortAuto();
                MainPanel.getMainPanel().repaint();
                for(DrawBox index : MainPanel.getListDrawBox()){
                    index.setBounds(index.getBox().getRec());
                }
            }
            else
            System.out.println(e.getActionCommand() + " JMenuItem clicked.");
        }    
    }
    
    public BufferedImage getScreenShot(Component component){
        BufferedImage image = new BufferedImage(component.getWidth(),
                component.getHeight(),BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        return image ;
    } 
}
