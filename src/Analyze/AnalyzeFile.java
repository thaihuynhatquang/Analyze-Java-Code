package Analyze;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class AnalyzeFile {
    public static HashMap<String, NodeInfo> listNode = new HashMap<>();
    
    public AnalyzeFile(){};
    
    public AnalyzeFile(String filePath){
        readAllFilesInFolder(filePath); 
    }
    public static HashMap<String, NodeInfo> getListNode() {
        return listNode;
    }
    
    public void readAllFilesInFolder(String folderPath){
        File folder = new File(folderPath);
        File[] listFiles = folder.listFiles();
        for (File file : listFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                if(fileName.matches("^.*java$")){
                    readFile(file.getPath());
                }
            }
        }
    }
    
    public void readFile(String filePath){
        String str = new String();
        FileInputStream inp = null;
        try {
            inp = new FileInputStream(filePath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnalyzeFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scanner scanner = new Scanner(inp);
        while(scanner.hasNextLine()){
            str += scanner.nextLine() + "\n";
        }
        scanner.close();
        str = handleSourceCode(str);
         //Delete \n
        str = str.replaceAll("\\n", " ");
        StringBuffer content = new StringBuffer(str);
        getContentOfClass(content);
    }
    
    public String handleSourceCode(String sourceCode){
        //Delete all comment, content in " ", in ' '
        sourceCode = sourceCode.replaceAll("(?:(?:=\\s+)?(['\\\"])(?:\\.|(?!\\1)"
                + ".)*\\1|\\/\\*(?:.|[\\n\\r])*?\\*\\/|(?:\\/.*))","");
        //Delete "import" and "package"
        sourceCode = sourceCode.replaceAll("(import.*;)|(package.*;)","");
        sourceCode = sourceCode.replaceAll("(?!\\w)\\s\\{","{");
        //Delete throws exception
        sourceCode = sourceCode.replaceAll("throws\\s+\\w+","");
        //Delete value of variable
        sourceCode = sourceCode.replaceAll("=.*," , ",");
        sourceCode = sourceCode.replaceAll("=.*;" , ";");
        //Delete all white-space
        sourceCode = sourceCode.replaceAll("\\s{2,}"," ");
        //Delete decleration Override
        sourceCode = sourceCode.replaceAll("@Override"," ");
        //Fixed sourceCode to easier for solving
        sourceCode = sourceCode.replaceAll("(?!\\w+)\\s(?=\\()","");
        sourceCode = sourceCode.replaceAll("\\](?=\\w)","\\] ");
        sourceCode = sourceCode.replaceAll(">(?=\\w)","> ");
        sourceCode = sourceCode.replaceAll(",\\s(?=\\w)",",");
        sourceCode = sourceCode.replaceAll("(?!\\w)\\s,",",");
        return sourceCode;
    }
    
    public void getContentOfClass(StringBuffer content){
        String patternStr = "(?<type>(?:class)|(?:interface))\\s(?<name>\\w+"
                + ")(?:(?:(?:\\sextends\\s+(?<extends1>\\w+))?(?:\\simplements"
                + "\\s(?<implements1>\\w+(?:,\\s*\\w+)*)))|(?:(?:\\simplements"
                + "\\s(?<implements2>\\w+(?:,\\s*\\w+)*))?(?:\\sextends\\s+(?"
                + "<extends2>\\w+))))?\\s*(\\{)";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        
        while(m.find()){
            String className = m.group();
            NodeInfo node = new NodeInfo();
            node.setNameOfNode(m.group("name"));
            node.setTypeOfNode(m.group("type"));
            
            int check = 1;
            int indexStart = m.start(7);
            int index = indexStart;
            do{
                index++;
                if(content.charAt(index) == '{') check++;
                if(content.charAt(index) == '}') check--;
            }while(check != 0);
            int indexEnd = index;
            
            String subString = content.substring(indexStart+1, indexEnd-1);
            StringBuffer temp = new StringBuffer(subString);
            subString = deleteContentOfMethods(temp);
            //Delete all white-space
            subString = subString.replaceAll("\\s{2,}"," ");
            node.setNameOfVariables(subString);
            node.setNameOfConstructor(subString);
            node.setNameOfMethods(subString);
            subString = className + subString;
            node.getRelationship(node, subString);
            listNode.put(node.getNameOfNode(), node);
            
        }
    }
    
    public String deleteContentOfMethods(StringBuffer content){
        String patternStr = "(?<=\\)|\\w)(\\{)";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(content);
        while(m.find()){
            int check = 1;
            int indexStart = m.start(1);
            int index = indexStart;
            do{
                index++;
                if(content.charAt(index) == '{') check++;
                if(content.charAt(index) == '}') check--;
                if(check == 0) content.setCharAt(index, ';');
                else content.setCharAt(index, ' ');
            }while(check != 0);
        }
        return content.toString();
    }
}