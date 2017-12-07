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
public class Constructor{
    // problems: error when set Access Modifier for a default constructer
    private String accessModifier;
    private String name;

    public Constructor(String name) {
        this.accessModifier = "public";
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
    
    public String getConstructor(){
        return "\t" + this.accessModifier + " " + this.name;
    }
}
