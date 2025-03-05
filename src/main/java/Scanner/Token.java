package Scanner;

public class Token {
    public final TokenType type;
    final String name;
    final String visual;
    final int line;
    public Token(TokenType type, final String name, final String visual,
                 int line) {
        this.type = type;
        this.name = name;
        this.visual = visual;
        this.line = line;
    }
    public String getName() { return name; }
    public String getVisual() { return visual; }

    public String toString() {
        String ans = name  ;
        if(type != TokenType.ERROR_STRING){
            ans += " ";
            ans += visual;
            if(type != TokenType.ERROR && type != TokenType.STRING && type != TokenType.NUMBER
             && type != TokenType.FLOAT){
                ans += " null";
            }
        }
        return ans;
    }
}