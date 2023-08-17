package symple.lexer;

import symple.*;
import symple.Error;


%%

%public
%class Lexer
%unicode
%line
%column
%standalone
%function nextToken
%type Symbol



%{
  private StringBuffer string = new StringBuffer();
  private java.util.Stack fileNames = new java.util.Stack();

  private Symbol symbol(int type) {
    return new Symbol(type , null );
  }

  private Symbol symbol(int type, Object value) {

    return new Symbol(type , value );
  }   

  public int getLine() {
    return yyline;
  }
  

  public String getCurrentFile() {
    return (String)fileNames.peek();
  }

  private String parseFileName() {
     String s = yytext().replaceAll( "\"" , "\'" );
     return s.substring(s.indexOf("\'")+1 , s.lastIndexOf("\'") );
  }

  public Lexer( String filename ) {     
     try {
        this.zzReader = new java.io.FileReader(filename);
        fileNames.push(filename);
     }
     catch(Exception e) {
        Error.signalError("Could not open file " + filename );
     }
  }


%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | 
          {DocumentationComment} | {PerlStyleComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*" "*"+ [^/*] ~"*/"
PerlStyleComment = "#" {InputCharacter}* {LineTerminator}?


/* identifiers */
VarIdentifier = "$"[:jletter:][:jletterdigit:]*
FunctionIdentifier = [:jletter:][:jletterdigit:]*


/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*


/* string and character literals */
InterpoledStringCharacter = [^\r\n\"\\]
SimpleStringCharacter = [^\r\n\'\\]
FileCharacter = [^\r\n\'] | [\b]

%state INTERPOLEDSTRING, SIMPLESTRING , INCLUDEPREPROCESSOR

%%


<YYINITIAL> {

  /* keywords */
  "if"                           { return symbol(Sym.IF); }
  "else"                         { return symbol(Sym.ELSE); }
  "include"                      { yybegin(INCLUDEPREPROCESSOR);  }
  "foreach"                      { return symbol(Sym.FOREACH); }  
  "as"                           { return symbol(Sym.AS); }   
  "while"                        { return symbol(Sym.WHILE); }  
  "for"                          { return symbol(Sym.FOR); }     
  "do"                           { return symbol(Sym.DO); }  
  "function"                     { return symbol(Sym.FUNCTION); }   
  "return"                       { return symbol(Sym.RETURN); }   
  "array"                        { return symbol(Sym.ARRAY); }
  "echoln"                       { return symbol(Sym.PRINTLN); }  
  "echo"                         { return symbol(Sym.PRINT); }
  "println"                      { return symbol(Sym.PRINTLN); }
  "print"                        { return symbol(Sym.PRINT); }
  "switch"                       { return symbol(Sym.SWITCH); }
  "case"                         { return symbol(Sym.CASE); }
  "default"                      { return symbol(Sym.DEFAULT); }
  "break"                        { return symbol(Sym.BREAK); }
  "continue"                     { return symbol(Sym.CONTINUE); }
  "readln"                       { return symbol(Sym.READLN); }
  "read"                         { return symbol(Sym.READ); }
  
  /* literals */
  "true"                         { return symbol(Sym.TRUE); }
  "false"                        { return symbol(Sym.NULL); }
  "null"                         { return symbol(Sym.NULL); }
     
  /* separators */
  "("                            { return symbol(Sym.LEFTPAR); }
  ")"                            { return symbol(Sym.RIGHTPAR); }
  "["                            { return symbol(Sym.LEFTBRACK); }
  "]"                            { return symbol(Sym.RIGHTBRACK); }  
  "{"                            { return symbol(Sym.LEFTBRACE); }
  "}"                            { return symbol(Sym.RIGHTBRACE); }
  ";"                            { return symbol(Sym.SEMICOLON); }
  ","                            { return symbol(Sym.COMMA); }
  ":"                            { return symbol(Sym.COLON); }

  /* operators */
  "++"                           { return symbol(Sym.INCREMENT); }
  "--"                           { return symbol(Sym.DECREMENT); }
  "+="                           { return symbol(Sym.PLUSASSIGN); }
  "-="                           { return symbol(Sym.MINUSASSIGN); }
  "*="                           { return symbol(Sym.MULTASSIGN); }
  "/="                           { return symbol(Sym.DIVASSIGN); }
  "%="                           { return symbol(Sym.REMAINDERASSIGN); }
  ".="                           { return symbol(Sym.CONCATASSIGN); }
  "+"                            { return symbol(Sym.PLUS); }
  "-"                            { return symbol(Sym.MINUS); }
  "*"                            { return symbol(Sym.MULT); }
  "/"                            { return symbol(Sym.DIV); }
  "<"                            { return symbol(Sym.LT); }
  "<="                           { return symbol(Sym.LE); }
  ">"                            { return symbol(Sym.GT); }
  ">="                           { return symbol(Sym.GE); }
  "!"                            { return symbol(Sym.NOT); }
  "!="                           { return symbol(Sym.NEQ); }
  "=="                           { return symbol(Sym.EQ); }
  "="                            { return symbol(Sym.ASSIGN); }
  "||"                           { return symbol(Sym.OR); }
  "or"                           { return symbol(Sym.OR); }
  "&&"                           { return symbol(Sym.AND); }
  "and"                          { return symbol(Sym.AND); }
  "%"                            { return symbol(Sym.REMAINDER); }
  "."                            { return symbol(Sym.CONCAT); }
  
  /* string literal */
  \"                             { yybegin(INTERPOLEDSTRING); string.setLength(0); }
  \'                             { yybegin(SIMPLESTRING);     string.setLength(0); }

  /* numeric literals */

  {DecIntegerLiteral}            { return symbol(Sym.LITERALINTEGER , new Integer(yytext())); }
  
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {VarIdentifier}                { return symbol(Sym.VARIDENT , yytext()); }  
  {FunctionIdentifier}           { return symbol(Sym.FUNCTIONIDENT , yytext() ); }
}

<INTERPOLEDSTRING> {
  \"                             { yybegin(YYINITIAL); return symbol(Sym.LITERALINTERPOLEDSTRING , string.toString()); }
  
  {InterpoledStringCharacter}+   { string.append( yytext() ); }
  
  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }

  
  /* error cases */
  \\.                            { Error.signalLexError((String)fileNames.peek() , yyline , "Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { Error.signalLexError((String)fileNames.peek() , yyline , "Unterminated string at end of line"); }
  <<EOF>>                        { Error.signalLexError((String)fileNames.peek() , yyline , "Unterminated string at end of line"); }
}


<SIMPLESTRING> {

  "\\"                           { string.append( '\\' ); }
   \'                            { yybegin(YYINITIAL); return symbol(Sym.LITERALSIMPLESTRING , string.toString()); }
  
  {SimpleStringCharacter}+       { string.append( yytext() ); }
  
  /* error cases */
  {LineTerminator}               { Error.signalLexError((String)fileNames.peek() , yyline , "Unterminated string at end of line"); }
  <<EOF>>                        { Error.signalLexError((String)fileNames.peek() , yyline , "Unterminated string at end of line"); }
}

<INCLUDEPREPROCESSOR> {

  [\b\t]* "(" [\b\t]* [\"\'] {FileCharacter}+ [\"\'] [\b\t]* ")" [\b\t]* ";"  
                                { 
                                    String file = "";
                                    try {  yypushStream(new java.io.FileReader(file = parseFileName()));
                                           fileNames.push( file );
                                           yybegin(YYINITIAL);
                                    }
                                    catch(Exception e) { Error.signalPrepError((String)fileNames.peek() , 
                                                         yyline , "Could not include file: " + file );
                                    } 
                                }

  
  /* error cases */
  .                              { Error.signalPrepError((String)fileNames.peek() , yyline , "Bad include statement"); }
  {LineTerminator}               { Error.signalPrepError((String)fileNames.peek() , yyline , "Bad include statement");  }
  <<EOF>>                        { Error.signalPrepError((String)fileNames.peek() , yyline , "Bad include statement");  }
}

/* error fallback */
.|\n                             { Error.signalLexError((String)fileNames.peek() , yyline , 
                                   "Invalid character(s) \"" + yytext() + "\"");
                                 }
															  
<<EOF>>                          { if(yymoreStreams()) { yypopStream(); fileNames.pop(); }
                                   else return symbol(Sym.EOF); 
                                 }