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
    JMenu menu = new JMenu("Action");
    JMenuItem saveMenuItem = new JMenuItem("Save as image");
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    JMenuItem sortMenuItem = new JMenuItem("Auto Sorting");
    
    public void addMenuItem(){

      saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
      saveMenuItem.setMnemonic(KeyEvent.VK_S);
      saveMenuItem.setActionCommand("Save");

      sortMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
      sortMenuItem.setMnemonic(KeyEvent.VK_A);
      sortMenuItem.setActionCommand("Auto Sorting");
      
      
      exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
      exitMenuItem.setMnemonic(KeyEvent.VK_X);
      exitMenuItem.setActionCommand("Exit");
      
      MenuItemListener menuItemListener = new MenuItemListener();
      saveMenuItem.addActionListener(menuItemListener);
      sortMenuItem.addActionListener(menuItemListener);
      exitMenuItem.addActionListener(menuItemListener);
      
      
      //Add item to Menu Bar
      menu.add(saveMenuItem);
      menu.add(sortMenuItem);
      menu.add(exitMenuItem);
}
    
    public MenuBar() {
        addMenuItem();
        menu.setMnemonic(KeyEvent.VK_A);
        //Add Menu Bar to JFrame
        this.add(menu);
    }
    
    class MenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {   
            if(e.getSource() == exitMenuItem){
                System.out.println(e.getActionCommand() + " JMenuItem clicked.");
                System.exit(0);
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
