package sinple;
import java.io.*;
import javax.swing.*;


public class Error extends Exception {

    public Error( String message ) {
        signalError(message);
    }
    
    public static void signalPrepError(String fileName, int line , String message) {
        System.err.println("File: " + fileName);
        System.err.println("Preprocessing error at line " + (line+1) + " : " + getLine(fileName , line));
        signalError(message);
    }    
    
    public static void signalLexError(String fileName, int line , String message) {
        System.err.println("File: " + fileName);
        System.err.println("Lexical error at line " + (line+1) + " : " + getLine(fileName , line));
        signalError(message);
    }
    
    public static void signalError( String message ) {
        System.err.println(message);
        System.exit(1);        
    }
    
    public static void signalRuntimeError( String message ) {
        int op = JOptionPane.showConfirmDialog(null , message + "\nStop execution?" , 
                "Runtime Exception" , JOptionPane.YES_NO_OPTION , JOptionPane.ERROR_MESSAGE );
        if( op == JOptionPane.YES_OPTION ) {
            signalError("\n\n ** Execution aborted by user");             
        }
    } 
    
    public static void signalSintaxError(String fileName , int line , String message ) {
        System.err.println("File: " + fileName);
        System.err.println("Sintax error at line " + (line+1) + " : " + getLine(fileName , line));
        signalError(message);            
    }
    
    private static String getLine(String fileName , int line) {
        try {
            LineNumberReader reader = new LineNumberReader(new FileReader(fileName));
            while( reader.getLineNumber() < line ) reader.readLine();
            return reader.readLine();            
        }
        catch( Exception e ) {
            System.err.println("Unknown error. The file" + fileName + "could not be read");
            System.exit(1);
        }
        return "";
    }
    
    
}
