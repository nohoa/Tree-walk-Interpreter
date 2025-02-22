public class Token {
    final TokenType type;
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
        String ans = name + " " + visual ;
        if(type != TokenType.ERROR){
             ans += " null";
        }
        return ans;
    }
}