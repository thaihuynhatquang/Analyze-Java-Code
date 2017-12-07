/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Analyze.NodeInfo;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class DrawBox extends JPanel {
    private Box box;
    DrawBox(Box box){
        this.box=box;
        setBounds(box.getRec());
        setLayout(new GridLayout(box.getRec().height/20,1));
        setOpaque(false);
        addContent();
        
    
    }
    private void addContent(){
        NodeInfo data = box.getData();
        // type Of Node
        String typeOfNode = "<" + data.getTypeOfNode();
        double length_1 = (data.getTypeOfNode().length() + 2)*7;
        for(double i = 0; i <= (box.getRec().width - length_1)/2; i+=3){
            typeOfNode = " " + typeOfNode;
        }
        
        typeOfNode = typeOfNode+ ">";        
        JLabel typeOfNodeLabel = new JLabel(typeOfNode);
        typeOfNodeLabel.setToolTipText(data.getTypeOfNode());
        add(typeOfNodeLabel);
        
        // Name of Node
        String nameOfNode = data.getNameOfNode();
        double length_2 = data.getNameOfNode().length()*7;
        for(double i = 0; i <= (box.getRec().width - length_2)/2; i+=3){
            nameOfNode = " " + nameOfNode;
        }
        JLabel nameOfNodeLabel=new JLabel(nameOfNode);
        nameOfNodeLabel.setToolTipText(data.getNameOfNode());
        add(nameOfNodeLabel);
        
        // attribute        
        for(int i=0;i<data.getVariables().size();i++){
            String var = "   ";
            var = var + data.getVariables().get(i).getVariable();
            JLabel varLabel = new JLabel(var);
            varLabel.setToolTipText(var);
            add(varLabel);
        }
        
        // methods and constructors
        for(int i=0;i<data.getConstructors().size();i++){
            String Cons = "   ";
            Cons = Cons + data.getConstructors().get(i).getConstructor();
            JLabel ConsLabel=new JLabel(Cons);
            ConsLabel.setToolTipText(Cons);
            add(ConsLabel);
        }
        for(int i=0;i<data.getMethods().size();i++){
            String method = "   ";
            method = method + data.getMethods().get(i).getMethod();
            JLabel methodLabel=new JLabel(method);
            methodLabel.setToolTipText(method);
            add(methodLabel);
        }
    }
}
