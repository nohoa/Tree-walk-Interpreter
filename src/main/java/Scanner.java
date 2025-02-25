import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Scanner {
    private String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private String current_string  = "";
    private  Boolean terminated_string = true ;
    private String number_value = "";
    private  static final HashMap<String, TokenType> keywords ;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    TokenType.AND);
        keywords.put("class",  TokenType.CLASS);
        keywords.put("else",   TokenType.ELSE);
        keywords.put("false",  TokenType.FALSE);
        keywords.put("for",    TokenType.FOR);
        keywords.put("fun",    TokenType.FUN);
        keywords.put("if",     TokenType.IF);
        keywords.put("nil",    TokenType.NIL);
        keywords.put("or",     TokenType.OR);
        keywords.put("print",  TokenType.PRINT);
        keywords.put("return", TokenType.RETURN);
        keywords.put("super",  TokenType.SUPER);
        keywords.put("this",   TokenType.THIS);
        keywords.put("true",   TokenType.TRUE);
        keywords.put("var",    TokenType.VAR);
        keywords.put("while",  TokenType.WHILE);
    }


    Scanner(String souce) {
        this.source = souce;
    }

    List<Token> scanTokens() {
        while (current < source.length()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "EOF", "", line));
        return tokens;
    }

    private void scanToken() {
        char c = source.charAt(current++);
        char startStr = '"';
        //System.out.println("Character is :" + c);
         if (c == '(') {
            addToken(TokenType.LEFT_PAREN, "LEFT_PAREN");
        } else if (c == ')') {
            addToken(TokenType.RIGHT_PAREN, "RIGHT_PAREN");
        }
        else if(c == '{'){
            addToken(TokenType.LEFT_BRACE, "LEFT_BRACE");
        }
        else if(c == '}'){
            addToken(TokenType.RIGHT_BRACE, "RIGHT_BRACE");
        }
        else if(c == '*'){
            addToken(TokenType.STAR,"STAR");
        }
        else if(c =='.'){
            addToken(TokenType.DOT,"DOT");
        }
        else if(c == ','){
            addToken(TokenType.COMMA,"COMMA");
        }
        else if(c == '+'){
            addToken(TokenType.PLUS,"PLUS");
        }
        else if(c == '-'){
            addToken(TokenType.MINUS,"MINUS");
        }
        else if(c == ';') {
            addToken(TokenType.SEMICOLON, "SEMICOLON");
        }
        else if(c == '='){
            if(match('=')){
                addToken(TokenType.EQUAL_EQUAL,"EQUAL_EQUAL");
            }
            else {
                addToken(TokenType.EQUAL,"EQUAL");
            }
        }
        else if(c == '!'){
            if(match('=')){
                addToken(TokenType.BANG_EQUAL,"BANG_EQUAL");
            }
            else {
                addToken(TokenType.BANG,"BANG");
            }
        }
        else if(c == '>'){
            if(match('=')){
                addToken(TokenType.GREATER_EQUAL,"GREATER_EQUAL");
            }
            else {
                addToken(TokenType.GREATER,"GREATER");
            }
        }
        else if(c == '<'){
            if(match('=')){
                addToken(TokenType.LESS_EQUAL,"LESS_EQUAL");
            }
            else {
                addToken(TokenType.LESS,"LESS");
            }
        }
        else if(c == '/'){
            if(match('/')){
                while(current < source.length() && c != '\n'){
                    c = source.charAt(current++);
                }
                line ++ ;
            }
            else {
                addToken(TokenType.SLASH,"SLASH");
            }
        }
        else if(c == ' ' || c == '\t'){
            ;
        }
        else if(c == startStr){
            terminated_string = false ;
            current_string += c;
            c = source.charAt(current);
            current_string += c;
            while(isEnd() == false && c != startStr){
                c = source.charAt(current ++);
                current_string += c ;
                if(c == startStr) terminated_string = true ;
            }
            if(terminated_string == true){
                addToken(TokenType.STRING,"STRING");
            }
            else {
                String message = "[line " + line + "] Error: Unterminated string.";
                addToken(TokenType.ERROR_STRING, message);
            }
        }
        else if(c == '\n'){
            line ++;
        }
        else if(c == '.'){
                addToken(TokenType.DOT,"DOT");
         }
        else if(c >= '0' && c <= '9'){
            //number_value += c;
             current -- ;
                while(c  >= '0' && c <='9'){
                    current ++;
                    if(isEnd()) break;
                    c = source.charAt(current);
                }
                if(c == '.'){
                   // System.out.println("here");
                    if(current +1 >= source.length() || source.charAt(current +1) < '0' || source.charAt(current+1) >'9'){
                        addToken(TokenType.NUMBER,"NUMBER");
                    }
                    else {
                      //  System.out.println("here");
                        current ++;
                        c = source.charAt(current);
                        while(!isEnd() && c  >= '0' && c <='9'){
                            current ++ ;
                            if(isEnd()) break;
                            c = source.charAt(current);
                        }
                        addToken(TokenType.FLOAT,"NUMBER");
                    }
                }
                else {
                    //current --;
                   // System.out.println("here");
                    addToken(TokenType.NUMBER,"NUMBER");
                }
         }
        else if(isAlpha(c)){
            current --;
            while(current < source.length() && (isAlpha(c)|| isDigit(c))){
                current ++ ;
                if(isEnd()) break;
                c = source.charAt(current);
            }
            if(keywords.containsKey(source.substring(start,current))){
                String key = source.substring(start,current);
                TokenType type = keywords.get(key);
                addToken(type,key.toUpperCase());
            }
            else addToken(TokenType.IDENTIFIER,"IDENTIFIER");
         }
        else {
            String message = "[line " + line + "] Error: Unexpected character:";
            addToken(TokenType.ERROR, message);
        }

        // case '(': addToken(TokenType.LEFT_PAREN,"LEFT_PAREN","("); break;
        // case ')': addToken(TokenType.RIGHT_PAREN,"RIGHT_PAREN",")"); break;
    }
    private void addToken(TokenType token, String name) {
        String text = source.substring(start, current);
        switch (token) {
            case TokenType.STRING:
                text += " ";
                text += source.substring(start+1,current-1);
                break;

            case  TokenType.FLOAT:
                String current = text;
                text += " ";
                text += current;
                int id = text.length()-1;
                while(id > 0 && text.charAt(id) == '0') id --;
                text =text.substring(0,id+1);
                if(text.charAt(text.length()-1) =='.') text += '0';
                //System.out.println(text);
                break;

            case TokenType.NUMBER:
                current = text;
                text += " ";
                text += current ;
                text += ".0";
                break;
        }
        tokens.add(new Token(token, name, text, line));
    }
    private boolean isEnd(){
        return (current >= source.length());
    }

    private boolean match(char expected){
        if(isEnd()) return false;
        if(source.charAt(current) != expected) return false ;

        current ++ ;
        return true ;
    }
    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

}