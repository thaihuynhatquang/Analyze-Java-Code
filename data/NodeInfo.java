package AnalysisSourceCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
import java.util.ArrayList;

public class NodeInfo {
    private String typeOfNode;
    private String nameOfNode;
    private  String superNode=new String();
    private  ArrayList <String> listInterfaces = new ArrayList<>();
    private final ArrayList <Constructor> constructors = new ArrayList<>();
    private final ArrayList <Variable> variables = new ArrayList<>();    
    private final ArrayList <Method> methods = new ArrayList<>();  
    
    public String getNameOfNode() {
        return nameOfNode;
    }

    public String getTypeOfNode() {
        
        return typeOfNode;
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

    public String getSuperNode() {
        return superNode;
    }

    public ArrayList<String> getListInterfaces() {
        return listInterfaces;
    }
    
    
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
            System.out.println("    Contrusctors: ");
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
    
    public void setNameOfNode(String content){
        String patternStr = "(?<type>(?:class\\s)|(?:interface\\s))(?<name>\\w+)"
                + "(?:(?:(?:\\sextends\\s+(?<extends1>\\w+))?(?:\\simplements"
                + "\\s(?<implements1>\\w+(?:,\\s*\\w+)*)))|(?:(?:\\simplements"
                + "\\s(?<implements2>\\w+(?:,\\s*\\w+)*))?(?:\\sextends\\s+"
                + "(?<extends2>\\w+))))?\\s*\\{";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        if(m.find()){
            this.typeOfNode = m.group("type");
            this.nameOfNode = m.group("name");
        }
    }
    
    public void setNameOfVariables(String content) {
        String patternStr = "(?:\\s*)(?<accessModifier>\\w+)?(?:\\s+)?(\\w+)?"
                + "(?:\\s+)?(\\w+)?\\s+(?<type>\\w+\\s*(?:(?:(?:<\\w*>)|"
                + "(?:\\[\\w*\\])))*)\\s+(?<name>\\w+)(?:\\s+)?;";  
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        while(!m.hitEnd()){
            if(m.find()){
                String s1 = m.group(1);
                String s2 = m.group(2);
                String s3 = m.group(3);
                if(!m.group("type").equals("package")){
                    Variable temp = new Variable(m.group("type"), m.group("name"));
                    if("public".equals(s1) || "private".equals(s1) || "protected".equals(s1)){
                        temp.setAccessModifier(m.group(1));
                    }else if("public".equals(s2) || "private".equals(s2) || "protected".equals(s2)){
                        temp.setAccessModifier(m.group(2));
                    }else if("public".equals(s3) || "private".equals(s3) || "protected".equals(s3)){
                        temp.setAccessModifier(m.group(3));
                    }
                    this.variables.add(temp);
                }
            }
        }
    }
    
    public void setNameOfMethods(String content) {  
        String patternStr = "(?:\\s+)?(?<accessModifier>\\w+)?(?:\\s+)?(\\w+)?("
                + "?:\\s+)?(\\w+)?\\s+(?<type>\\w+\\s*(?:(?:(?:<\\w*>)|(?:\\[\\"
                + "w*\\])))*)\\s+(?<fullname>(?<name>\\w+)\\([^\\\\)]*\\))\\s*;";  
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        while(!m.hitEnd()){
            if(m.find()){
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
                }
                
                if(m.group("name").equals(this.getNameOfNode())){
                    Constructor temp2 = new Constructor(m.group("fullname"));
                    this.constructors.add(temp2);
                }else{
                    this.methods.add(temp);
                }
            }
        }
    }
    
    public void setRelationship(String content){
        String patternStr = "(?<type>(?:class\\s)|(?:interface\\s))(?<name>\\w+)"
                + "(?:(?:(?:\\sextends\\s+(?<extends1>\\w+))?(?:\\simplements"
                + "\\s(?<implements1>\\w+(?:,\\s*\\w+)*)))|(?:(?:\\simplements"
                + "\\s(?<implements2>\\w+(?:,\\s*\\w+)*))?(?:\\sextends\\s+"
                + "(?<extends2>\\w+))))?\\s*\\{";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        if(m.find()){
            if(m.group("extends2") != null){
////                NodeInfo temp = AnalyzeFile.listNode.get(m.group("extends2"));
////                this.superNode.add(temp);
                this.superNode=m.group("extends2");
                
            }
            else if(m.group("extends1") != null){
//                NodeInfo temp = AnalyzeFile.listNode.get(m.group("extends1"));
//                this.superNode.add(temp);
                this.superNode=m.group("extends1");
            }
            
            if(m.group("implements1") != null){
                String[] list = m.group("implements1").split(",");
                for(String index : list){  
//                    NodeInfo temp = AnalyzeFile.listNode.get(index);
//                    this.listInterfaces.add(temp);
                    this.listInterfaces.add(index);
                }  
            }
            else if(m.group("implements2") != null){
                String[] list = m.group("implements2").split(",");
                for(String index : list){  
//                    NodeInfo temp = AnalyzeFile.listNode.get(index);
//                    this.listInterfaces.add(temp);
                    this.listInterfaces.add(index);
                }  
            }
        }
    }
}
