package sinple.ast;
import  sinple.MemoryTable;


public class AssignmentStatement implements Statement , Expr {
    
    VarExpr var;
    Expr    expr;
    
    public AssignmentStatement(String name , Expr e ) {
        this(new VarExpr(name) , e);
    }
    
    /** Creates a new instance of AssignmentStatement */
    public AssignmentStatement(VarExpr var , Expr expr ) {
        this.var = var;
        this.expr = expr;
    }
    
    public void interpret(MemoryTable memory) {
        var.assign(memory , expr );        
    }
    
    public Object evaluate(MemoryTable memory) {
        return var.assign(memory , expr);        
    }
    
    public Integer evaluateAsInt(MemoryTable memory) {
        var.assign( memory , expr );
        return var.evaluateAsInt( memory );        
    }
    
    public String evaluateAsString(MemoryTable memory) {
        var.assign( memory , expr );
        return var.evaluateAsString( memory );         
    }
    
}
