package sinple.ast;
import  sinple.MemoryTable;
import  java.util.*;
import  java.io.*;
import  sinple.Error;


public class ReadStatement implements Statement {
    
    Vector vars;
    
    /** Creates a new instance of ReadStatement */
    public ReadStatement( Vector v )  {
        vars = v;
    }
    
    public void interpret( MemoryTable memory ) {
        int i = 0;
        try {
            while( i < vars.size() ) {
                 String strInput = "";
                 BufferedReader dataIn = new BufferedReader(new InputStreamReader(System.in));
                 strInput = dataIn.readLine();
                 StringTokenizer st = new StringTokenizer(strInput);
                 while(st.hasMoreTokens() && i < vars.size() ) {
                     (new VarExpr( (String)vars.get( i ) )).assign(memory , st.nextToken());
                     i++;
                 }
            }           
        }
        catch(Exception e) { Error.signalRuntimeError("Error reading from keyboard."); }
    }
    
}
