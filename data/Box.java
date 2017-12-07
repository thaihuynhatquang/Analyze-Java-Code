/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalysisSourceCode;

import java.awt.Rectangle;
import java.util.ArrayList;




/**
 *
 * @author Tung Duong
 */
public class Box {
    private Rectangle rec;
    private NodeInfo data;
    
    
    public Box(NodeInfo data){
        this.data=data;
        rec=new Rectangle();
        this.rec.setSize(250, setHeight());
        
    
    }
    
    public void setLocationOfRec(int x,int y){
        rec.setLocation(x, y);
    }
    public Rectangle getRec() {
        return rec;
    }
    public int setHeight(){
        int Height=0;
        Height=data.getConstructors().size()+data.getMethods().size()+data.getVariables().size()+2;
        return Height*20;
    }

    public NodeInfo getData() {
        return data;
    }
    
}
