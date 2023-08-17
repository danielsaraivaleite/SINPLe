package sinple;


public class Symbol {
  public Object obj;
  public int type;

  public Symbol(int type, Object value) {
      obj = value;
	  this.type = type;	  
  }
  
  public String getString() {
    return (String)obj;
  }
  
  public Integer getInt() {
    return (Integer)obj;
  }

}
