/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author Administrator
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelSwap extends JPanel implements ActionListener {
    JPanel firstPanel = new JPanel();
    JPanel secondPanel = new JPanel();

    public PanelSwap() {
        super(new BorderLayout());
        firstPanel.setBackground(Color.RED);

        secondPanel.setBackground(Color.YELLOW);

        JButton swap1 = new JButton("SwapToYellow");
        swap1.addActionListener(this);

        JButton swap2 = new JButton("SwapToRed");
        swap2.addActionListener(this);

        firstPanel.add(swap1);
        secondPanel.add(swap2);

        add(firstPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        for (Component component : getComponents())
            if (firstPanel == component) {
                remove(firstPanel);
                add(secondPanel);

            } else {
                remove(secondPanel);
                add(firstPanel);
            }

        repaint();
        revalidate();
    }
}
