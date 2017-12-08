package Analyze;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
import java.util.ArrayList;

public class NodeInfo {
    private String typeOfNode = new String();
    private String nameOfNode = new String();
    private  String superNode = new String();
    private ArrayList <String> listInterfaces = new ArrayList<>();
    private ArrayList <Constructor> constructors = new ArrayList<>();
    private ArrayList <Variable> variables = new ArrayList<>();    
    private ArrayList <Method> methods = new ArrayList<>();  

    public void printDetails(){
        System.out.println("\n" + this.getTypeOfNode() + ": " + this.getNameOfNode());
        if(listInterfaces.size() > 0){
            System.out.println("    Interfaces: ");
            for(int i = 0; i < listInterfaces.size(); i++){
                System.out.println("\t" + listInterfaces.get(i));
            }
        }

        if(superNode.length()>0) {
            System.out.println("    Super Class: " );
                System.out.println("\t" + superNode);
        }
        
        if(constructors.size() > 0){
            System.out.println("    Constructors: ");
            constructors.forEach((index) -> {
                System.out.println(index.getConstructor());
            });
        }
        
        if(variables.size() > 0){
            System.out.println("    Variables: ");
            variables.forEach((index) -> {
                System.out.println(index.getVariable());
            });
        }
            
        if(methods.size() > 0){
            System.out.println("    Methods: ");
                methods.forEach((index) -> {
                    System.out.println(index.getMethod());
            });
        }
    }
    
    public void setNameOfVariables(String content) {
        String patternStr = "(?<accessModifier>\\w+)?(?:\\s)?(\\w+)?(?:\\s)?"
                + "(\\w+)?\\s(?<type>\\w+\\s*(?:(?:(?:<\\w+(?:,\\s*\\w+)*>)|(?:\\[\\w*\\]))"
                + ")*)\\s(?<name>\\w+(?:,\\s*\\w+)*)(?:\\s)?;";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        while(!m.hitEnd()){
            if(m.find()){
                String accessModifier = "";
                if(!m.group("type").equals("package")){
                    if("public".equals(m.group(1)) || "private".equals(m.group(1)) || "protected".equals(m.group(1))){
                        accessModifier = m.group(1);
                    }else if("public".equals(m.group(2)) || "private".equals(m.group(2)) || "protected".equals(m.group(2))){
                        accessModifier = m.group(2);
                    }else if("public".equals(m.group(3)) || "private".equals(m.group(3)) || "protected".equals(m.group(3))){
                        accessModifier = m.group(3);
                    }
                    String[] list = m.group("name").split(",");
                    for(String str : list){  
                        Variable temp = new Variable(m.group("type"), str);
                        temp.setAccessModifier(accessModifier);
                        this.variables.add(temp);
                    }
                }
            }
        }
    }
    
    public void setNameOfMethods(String content) {  
        String patternStr = "(\\w+)?(?:\\s)?(\\w+)?(?:\\s)?(\\w+)?\\s"
                + "(?<type>\\w+\\s*(?:(?:(?:<\\w+(?:,\\s*\\w+)*>)|(?:\\[\\w*\\])))*)\\s"
                + "(?<fullname>(?<name>\\w+)\\([^\\\\)]*\\))\\{\\s;";  
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        while(m.find()){
            if(!m.group("name").equals(this.getNameOfNode())){
                Method temp = new Method(m.group("type"), m.group("fullname"));
                String s1 = m.group(1);
                String s2 = m.group(2);
                String s3 = m.group(3);
                if("public".equals(s1) || "private".equals(s1) || "protected".equals(s1)){
                    temp.setAccessModifier(m.group(1));
                }else if("public".equals(s2) || "private".equals(s2) || "protected".equals(s2)){
                    temp.setAccessModifier(m.group(2));
                }else if("public".equals(s3) || "private".equals(s3) || "protected".equals(s3)){
                    temp.setAccessModifier(m.group(3));
                }else temp.setAccessModifier("default");
                
                this.methods.add(temp);
            }
        }
    }
    
    public void setNameOfConstructor(String content){
        String patternStr = "(?<accessModifier>\\w+\\s*(?:(?:(?:<\\w+(?:,\\s*\\w+)*>)|(?:\\"
                + "[\\w*\\])))*)?\\s(?<fullname>(?<name>\\w+)\\([^\\\\)]*\\))"
                + "\\{\\s;";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        while(m.find()){
            if(m.group("name").equals(this.getNameOfNode())){
                Constructor temp2 = new Constructor(m.group("fullname"));
                if(m.group("accessModifier") != null) temp2.setAccessModifier(m.group("accessModifier"));
                else temp2.setAccessModifier("default");
                
                this.constructors.add(temp2);
            }
        }
    }
    
    public void getRelationship(NodeInfo node, String content){
        String patternStr = "(?<type>(?:class\\s)|(?:interface\\s))(?<name>\\w+"
                + ")(?:(?:(?:\\sextends\\s+(?<extends1>\\w+))?(?:\\simplements"
                + "\\s(?<implements1>\\w+(?:,\\s*\\w+)*)))|(?:(?:\\simplements"
                + "\\s(?<implements2>\\w+(?:,\\s*\\w+)*))?(?:\\sextends\\s+(?"
                + "<extends2>\\w+))))?\\s*(\\{)";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        if(m.find()){
            if(m.group("extends2") != null){
                node.superNode = m.group("extends2");
            }
            else if(m.group("extends1") != null){
                node.superNode = m.group("extends1");
            }
            
            if(m.group("implements1") != null){
                String[] list = m.group("implements1").split(",");
                for(String str : list){  
                    node.listInterfaces.add(str);
                }  
            }
            else if(m.group("implements2") != null){
                String[] list = m.group("implements2").split(",");
                for(String str : list){  
                    node.listInterfaces.add(str);
                }  
            }
        }
    }

    public void setTypeOfNode(String typeOfNode) {
        this.typeOfNode = typeOfNode;
    }

    public void setNameOfNode(String nameOfNode) {
        this.nameOfNode = nameOfNode;
    }

    public String getNameOfNode() {
        return nameOfNode;
    }

    public String getTypeOfNode() {

        return typeOfNode;
    }

    public String getSuperNode() {
        return superNode;
    }

    public ArrayList<String> getListInterfaces() {
        return listInterfaces;
    }

    public ArrayList<Constructor> getConstructors() {
        return constructors;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public ArrayList<Method> getMethods() {
        return methods;
    }
}
