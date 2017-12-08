/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Analyze.AnalyzeFile;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Administrator
 */
public class StartScreen extends JFrame{
    public java.io.File folder;
    private JPanel contentPanel;
    private JLabel information;
    private JPanel buttonBar;
    private JButton button1;
    private JButton okButton;
    private JButton cancelButton;
    
    public StartScreen(){
        initComponents();
        setVisible(true);
        setResizable(false);
        information.setText("Press \"CHOOSE LOCATION OF ...\" to show your diagram of source code");
    }
    
    private void initComponents() {
        contentPanel = new JPanel();
        information = new JLabel();
        buttonBar = new JPanel();
        button1 = new JButton();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== contentPanel ========
        contentPanel.setBorder(new javax.swing.border.CompoundBorder(
            new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                "Analyze Your Source Code", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Dialog", java.awt.Font.BOLD, 20),
                java.awt.Color.BLACK), contentPanel.getBorder())); 
        contentPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

        GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup()
                .addGroup(contentPanelLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(information, GroupLayout.DEFAULT_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(110, Short.MAX_VALUE))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.CENTER, contentPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(information, GroupLayout.DEFAULT_SIZE, 55, GroupLayout.PREFERRED_SIZE))
        );
        contentPane.add(contentPanel, BorderLayout.CENTER);

        //======== buttonBar ========
        {
            buttonBar.setBorder(new EmptyBorder(0, 0, 0, 0));
            buttonBar.setLayout(new GridBagLayout());
            ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 60, 85};
            ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0};

            //---- button1 ----
            button1.setText("CHOOSE LOCATION OF YOUR SOURCE CODE");
            button1.addActionListener(e -> {
			clickChooseFolderPath(e);
		});
            buttonBar.add(button1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- okButton ----
            okButton.setText("OK");
            okButton.addActionListener(e -> clickOk(e));
            buttonBar.add(okButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- cancelButton ----
            cancelButton.setText("Cancel");
            cancelButton.addActionListener(e -> clickCancel(e));
            buttonBar.add(cancelButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        }
        contentPane.add(buttonBar, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
    }
    
    private void clickCancel(ActionEvent e) {
        System.out.println(e.getActionCommand() + " was clicked.");
        System.exit(0);
    }
    
    private void clickChooseFolderPath(ActionEvent e) {
        System.out.println(e.getActionCommand() + " was clicked.");
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);
        // only folder can be displayed
        chooser.setFileFilter(new FileNameExtensionFilter("Properties file", "properties"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            folder = chooser.getSelectedFile();
            information.setText(folder.getAbsolutePath());
//                    GUI.MainPanel.folderPath = chooser.getSelectedFile().getPath();;
            System.out.println("getSelectedFolder() : " +  chooser.getSelectedFile());
        }
    }
    
    private void clickOk(ActionEvent e) {
        // TODO add your code here
        if(folder == null){
            JOptionPane.showMessageDialog(null,"Choose your location of source code","ERROR!",1);
        }else {
            AnalyzeFile analyzeFile = new AnalyzeFile(folder.getAbsolutePath());
            if(analyzeFile.getListNode().size() == 0){
                JOptionPane.showMessageDialog(null,"Folder hasn't any java file","Error!!",1);
            }else {
                setVisible(false);
                System.out.println(e.getActionCommand() + " was clicked.");
                JFrame myFrame=new JFrame(" Class Diagram ");
                myFrame.setSize(800,600);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                myFrame.setLocation(dim.width/2 - myFrame.getSize().width/2, dim.height/2 - myFrame.getSize().height/2);
                myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                myFrame.setVisible(true);
                MainPanel mainPanel = MainPanel.getMainPanel();
                mainPanel.init(folder.getPath());
                JScrollPane scroll = new JScrollPane(mainPanel,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                myFrame.add(scroll);
                MenuBar mb = new MenuBar();
                myFrame.setJMenuBar(mb);
                System.out.println("HIHI");
            }
        }
    }   
}
