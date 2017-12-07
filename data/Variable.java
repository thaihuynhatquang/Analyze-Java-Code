package AnalysisSourceCode;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Variable {
    private String accessModifier;
    private String type;
    private String name;
    
    public Variable(String type, String name) {
        this.accessModifier = "~";        
        this.name = name;
        this.type=type;
        
    }

    public void setAccessModifier(String accessModifier) {
        
        switch(accessModifier){
            case "public": 
                this.accessModifier="+";
                break;
            case "protected":
                this.accessModifier="#";
                break;
            case "private":
                this.accessModifier="-";
                break;
//            case "default":
//                this.accessModifier="~";
//                break;
//            case "package":
//                this.accessModifier="~";
//                break;    
            case "derived":
                this.accessModifier="/";
                break;    
                    
        }
    }
    
    public String getVariable(){
        return "\t" + this.accessModifier + " " + this.type + " " + this.name;
    }
}
