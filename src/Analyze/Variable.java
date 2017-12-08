package Analyze;

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
        this.type = type;
    }

    public void setAccessModifier(String accessModifier) {
        switch(accessModifier){
            case "public": 
                this.accessModifier = "+";
                break;
            case "protected":
                this.accessModifier = "#";
                break;
            case "private":
                this.accessModifier = "-";
                break;
//            case "default":
//                this.accessModifier = "~";
//                break;
//            case "package":
//                this.accessModifier = "~";
//                break;    
            case "derived":
                this.accessModifier = "/";
                break;
        }
    }
    
    public String getType() {
        return type;
    }
    
    public String getVariable(){
        return "\t" + this.accessModifier + " " + this.type + " " + this.name;
    }
}
