
public class Token {
    enum TokenType {
        // Single-character tokens.
        LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
        COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

        // One or two character tokens.
        BANG, BANG_EQUAL,
        EQUAL, EQUAL_EQUAL,
        GREATER, GREATER_EQUAL,
        LESS, LESS_EQUAL,

        // Literals.
        IDENTIFIER, STRING, NUMBER,

        // Keywords.
        AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
        PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

        EOF
    }
    final TokenType type ;
    final String name ;
    final String visual ;
    final int line ;

    public Token(TokenType type, final String name, final String visual, int line) {
        this.type = type;
        this.name = name ;
        this.visual = visual;
        this.line = line;
    }
    public String getName(){
        return  name;
    }

    public String getVisual(){
        return visual ;
    }

}

