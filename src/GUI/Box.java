package GUI;

import Analyze.NodeInfo;
import Analyze.Variable;
import java.awt.Rectangle;

/**
 *
 * @author Administrator
 */

public class Box {
    private Rectangle rec;
    private NodeInfo data;
    
    
    public Box(NodeInfo data){
        this.data=data;
        rec=new Rectangle();
        this.rec.setSize(ConstantAtribute.getColumnWidth(), setHeight());
        
    
    }
    public boolean hasA(Box box){
        for(Variable var: data.getVariables()){
                String varType =var.getType();
                if(varType.contains(box.getData().getNameOfNode()))
                    return true;
        }
        return false;
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
        return Height*ConstantAtribute.getRowHeight();
    }

    public NodeInfo getData() {
        return data;
    }
    
}