package GUI;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Administrator
 */

public class MainWindow{
    public static void main(String[] args) {
         try{
//            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "QDH TEAM");
            com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setTheme("Green-Medium-Font", "", "QDH TEAM");
//            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        } 
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e){ 
            System.out.println(e);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Run here.
//                MainWindow run=new MainWindow();
                StartScreen run = new StartScreen();
            }
        });
    }
}
