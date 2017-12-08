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
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Default", "", "QDH TEAM");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
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
