package sinple;

public class Main {
    
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Usage: simple <filename>");
            System.exit(1);
        }
        Parser parser;
        try {
            parser = new Parser(args[0]);
            MemoryTable mem = new MemoryTable();
            parser.parse().interpret(mem);
        }
       catch(Exception e) {
         e.printStackTrace();
         Error.signalRuntimeError("Unknown error\n" + e.getMessage());
       }


        
    }
    
}
