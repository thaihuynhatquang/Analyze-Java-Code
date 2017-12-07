/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalysisSourceCode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Tung Duong
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
        NodeInfo data=box.getData();
        // type Of Node
        StringBuffer typeOfNode=new StringBuffer();
        
        for(int i=0;i<(box.getRec().width-typeOfNode.length()*7)/2*20;i++){
            typeOfNode.append(' ');
        }
        typeOfNode.append(data.getTypeOfNode());
        
        JLabel typeOfNodeLabel=new JLabel(typeOfNode.toString());
        
        typeOfNodeLabel.setToolTipText(data.getTypeOfNode());
        add(typeOfNodeLabel);
        
        // Name of Node
        StringBuffer nameOfNode=new StringBuffer();
        for(int i=0;i<(box.getRec().width-nameOfNode.length()*8)/2*20;i++){
            nameOfNode.append(' ');
        }
        nameOfNode.append(data.getNameOfNode());
        JLabel nameOfNodeLabel=new JLabel(nameOfNode.toString());
        nameOfNodeLabel.setToolTipText(data.getNameOfNode());
        add(nameOfNodeLabel);
        // attribute
        
        for(int i=0;i<data.getVariables().size();i++){
            String var=data.getVariables().get(i).getVariable();
            JLabel varLabel=new JLabel(var);
            varLabel.setToolTipText(var);
            add(varLabel);
        }
        // methods and constructors
        for(int i=0;i<data.getConstructors().size();i++){
            String Cons=data.getConstructors().get(i).getConstructor();
            JLabel ConsLabel=new JLabel(Cons);
            ConsLabel.setToolTipText(Cons);
            add(ConsLabel);
        }
        for(int i=0;i<data.getMethods().size();i++){
            String method=data.getMethods().get(i).getMethod();
            JLabel methodLabel=new JLabel(method);
            methodLabel.setToolTipText(method);
            add(methodLabel);
        }
            
        
    }
}
