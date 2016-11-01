/*
 * Homework 6 by Sergey Anisimov
 */

import java.io.*;

public class GBJava1Homework6 {
    static String fileName = "C:\\Data\\file.txt";
    static File file = new File(fileName);
    public static void main(String[] args) {
        writeFile(); 
        readFile(); 
        System.out.println("=====================================================================================================================");
        writeFile(); 
    }
    static void writeFile(){
        String text = "Some words for write file";
        try {
            if(!file.exists()){
                file.createNewFile();
            
            FileWriter out = new FileWriter(file.getAbsoluteFile(), true); 
            out.write(text);
            out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    static void readFile(){
        try(FileReader reader = new FileReader(fileName)){
            char[] buffer = new char[(int)file.length()];
            reader.read(buffer);
        } catch (IOException excptn) {System.out.println(excptn.getMessage());}
        //return buffer;
    }
    
    static void printFile(){
        
    }
    
    
}
