package sinple.ast;
import  sinple.MemoryTable;


public class IfStatement implements Statement {
    
    Statement thenPart , elsePart;
    Expr      testExpr;
    
    /** Creates a new instance of IfStatement */
    public IfStatement( Expr testExpr , Statement thenPart , Statement elsePart ) {
        this.testExpr = testExpr;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }
    
    public void interpret( MemoryTable memory ) {
        if(elsePart == null ) {// simple if
            if( testExpr.evaluateAsInt(memory).intValue() != 0 ) thenPart.interpret( new MemoryTable(memory)  );            
        }
        else { // an if-then-else statement
            if(testExpr.evaluateAsInt(memory) != 0 ) thenPart.interpret(new MemoryTable(memory));   
            else elsePart.interpret( new MemoryTable(memory) );   
        }       
    }
    
}
