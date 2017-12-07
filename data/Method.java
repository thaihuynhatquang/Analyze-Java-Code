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
public class Method {
    private String accessModifier;
    private String type;
    private String name;

    public Method(String type, String name) {
        this.accessModifier = "~";        
        this.type = type;
        this.name = name;
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
    
    public String getMethod(){
        return "\t" + this.accessModifier + " " + this.type + " " + this.name;
    }
}
