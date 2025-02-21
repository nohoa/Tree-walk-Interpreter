import java.util.ArrayList;
import java.util.List;


public class Scanner {
    private String source ;
    private final List<Token> tokens = new ArrayList<>();
    private int start  = 0;
    private int current  = 0;
    private int line  = 1;

    Scanner(String souce){
        this.source = souce;
    }

    List<Token> scanTokens(){
        while(current < source.length()){
            scanToken();
        }
        tokens.add(new Token(Token.TokenType.EOF,"","EOF",line));
        return tokens;
    }

    private  void scanToken(){
        char c = source.charAt(current ++);
            if(c == '('){
                addToken(Token.TokenType.LEFT_PAREN,"(","LEFT_PAREN");
            }
            else if( c == ')'){
                addToken(Token.TokenType.RIGHT_PAREN,")","RIGHT_PAREN");
            }
            //case '(': addToken(TokenType.LEFT_PAREN,"LEFT_PAREN","("); break;
            //case ')': addToken(TokenType.RIGHT_PAREN,"RIGHT_PAREN",")"); break;
    }

    private void addToken(Token.TokenType token, String visual, String name){
        String text = source.substring(start, current);
        tokens.add(new Token(token,name,visual,line));
    }
}
