import java.util.ArrayList;
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
    int count_dot = 0 ;

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
                while(!isEnd() && c  >= '0' && c <='9'){
                    c = source.charAt(current++);
                }
                if(c == '.'){
                    current -- ;
                   // System.out.println("here");
                    if(current +1 >= source.length() || source.charAt(current +1) < '0' || source.charAt(current+1) >'9'){
                        addToken(TokenType.NUMBER,"NUMBER");
                    }
                    else {
                      //  System.out.println("here");
                        current ++;
                        c = source.charAt(current);
                        while(!isEnd() && c  >= '0' && c <='9'){
                            c = source.charAt(current++);
                        }
                        current -- ;
                        addToken(TokenType.FLOAT,"NUMBER");
                    }
                }
                else {
                    addToken(TokenType.NUMBER,"NUMBER");
                }
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
                text += " ";
                text += text;
                break;

            case TokenType.NUMBER:
                text += " ";
                text += text ;
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
}