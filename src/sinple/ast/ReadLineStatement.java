package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;
import  java.io.*;
import  sinple.Error;


public class ReadLineStatement implements Statement {
    
    Vector vars;
    
    /** Creates a new instance of ReadStatement */
    public ReadLineStatement( Vector v )  {
        vars = v;
    }
    
    public void interpret( MemoryTable memory ) {
        for(int i=0 ; i< vars.size() ; i++)  {
              try {
                 String strInput = "";
                 BufferedReader dataIn = new BufferedReader(new InputStreamReader(System.in));
                 strInput = dataIn.readLine();
                (new VarExpr( (String)vars.get( i ) )).assign(memory , strInput);
              }              
              catch (Exception e) { Error.signalRuntimeError("Error reading from keyboard."); }

        }      
    }
    
}
