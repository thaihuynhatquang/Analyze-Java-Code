package AnalysisSourceCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class AnalyzeFile {
    private  HashMap<String, NodeInfo> listNode = new HashMap<>();

    public  AnalyzeFile(String filePath){
        
        readAllFileInFolder(filePath);
        
        
    }

    public HashMap<String, NodeInfo> getListNode() {
        return listNode;
    }
    
    public String deleteContentsInCurlyBraces(String sourceCode){
        
        StringBuilder tmpStr=new StringBuilder(sourceCode);
        Stack tmpIndex=new Stack();
        String patternStr = "(?<=\\))(\\s*\\{)";  
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(sourceCode);
        int start=0;
        while(m.find(start)){
            int temp=m.end();
            int temp2=0;
            tmpIndex.push(m.end()-1-(sourceCode.length()-tmpStr.length()));
            do{
                for(int i=(int) tmpIndex.peek()+1;i<tmpStr.length();i++){
                    if('{'==tmpStr.charAt(i)){

                       tmpIndex.push(i);
                        continue;
                    }   
                    if('}'==tmpStr.charAt(i)){

                        temp2+=i+1-(int) tmpIndex.peek()-1;
                        tmpStr.replace((int) tmpIndex.pop(), i+1,";");
                        break;
                     }
                    }
            }while(!tmpIndex.isEmpty());
            start=temp+temp2-1;
            if(start>=sourceCode.length())
               break;
        }
        return tmpStr.toString();
    }
    public String handle(String sourceCode){
        //Delete all comment in source code
        sourceCode = sourceCode.replaceAll("(['\"])(?:\\.|"
                + "(?!\1).)*\1|/\\*(?:.|[\\n\\r])*?\\*/|(?://.*)","");
        //Delete all value of variables
        sourceCode = sourceCode.replaceAll("=\\s?.*(?=;)","");
        //Delete content in " "
        sourceCode = sourceCode.replaceAll("\".*\"","");
        //Delete all white-space
        sourceCode = sourceCode.replaceAll("\\s{2,}"," ");
        //Fixed sourceCode to easier for solving
        sourceCode = sourceCode.replaceAll("(?!\\w+)\\s(?=\\()","");
        sourceCode = sourceCode.replaceAll("\\](?=\\w)","\\] ");
        sourceCode = sourceCode.replaceAll(">(?=\\w)","> ");
        //Fixed sourceCode
        sourceCode = sourceCode.replaceAll("(?!\\w+)\\s(?=\\()","");
        sourceCode = sourceCode.replaceAll(">(?=\\w)","> ");
        sourceCode = sourceCode.replaceAll(",\\s(?=\\w)",",");
        sourceCode = deleteContentsInCurlyBraces(sourceCode);
        return sourceCode;
    }
    
    public String readFile(String filePath){
        //convert source code to string
        String sourceCode = new String();
       
        try {
            sourceCode = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sourceCode = handle(sourceCode);
//        System.out.println(sourceCode);
        return sourceCode;
    }
    
    public void readAllFileInFolder(String folderPath){
        File folder = new File(folderPath);
        File[] listFiles = folder.listFiles();

        //Get information of all classes
        for (File file : listFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                if(fileName.matches("^.*java$")){
                    String content = readFile(file.getPath());
                    NodeInfo temp = new NodeInfo();
                    temp.setNameOfNode(content);
                    temp.setNameOfVariables(content);
                    temp.setNameOfMethods(content);
                    listNode.put(temp.getNameOfNode(), temp);
                }
            }
        }
        
        //Set relationship of all classes
        for (File file : listFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                if(fileName.matches("^.*java$")){
                    String content = readFile(file.getPath());
                    NodeInfo temp = new NodeInfo();
                    temp.setNameOfNode(content);
                    String s = temp.getNameOfNode();
                    listNode.get(s).setRelationship(content);
                }
            }
        }
    }
}
