import java.util.ArrayList;
import java.util.List;
public class Scanner {
    private String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    Scanner(String souce) { this.source = souce; }
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
                current ++;
            }
            else {
                addToken(TokenType.SLASH,"SLASH");
            }
        }
        else if(c >='a' && c <='z'){
            current ++;
        }
        else if(c >= 'A' && c <= 'Z'){
            current ++;
        }
        else {
            addToken(TokenType.ERROR,"[line 1] Error: Unexpected character:");
        }
        // case '(': addToken(TokenType.LEFT_PAREN,"LEFT_PAREN","("); break;
        // case ')': addToken(TokenType.RIGHT_PAREN,"RIGHT_PAREN",")"); break;
    }
    private void addToken(TokenType token, String name) {
        String text = source.substring(start, current);
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