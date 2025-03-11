package Parser;

import Scanner.Token;
import Scanner.TokenType;

import java.util.List;

import static Scanner.TokenType.*;

public class Parser {
    private final List<Token> tokens ;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

//    private Expr expression(){
//        return equality();
//    }

    private Expr primary(){
        Expr  expr = new Expr.Literal("?");
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if(match(NUMBER,STRING,FLOAT)){
            String all_value = previous().getVisual();
            String literal = "";
            int id = all_value.length()-1;
            while(all_value.charAt(id) != ' '){
                //literal += all_value.charAt(id);
                id -= 1;
            }
            for(int i = id+1 ;i < all_value.length();i ++){
                literal += all_value.charAt(i);
            }
            //System.ou
            return new Expr.Literal(literal);
        }
        return expr;
    }

    private Expr unary(){
        if(match(BANG,MINUS)){
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator,right);
        }
        return primary();
    }

    private Expr term() {
        Expr expr = factor();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private  Expr factor(){
        Expr expr = unary();
        while (match(STAR,SLASH)){
            Token operator = previous();
            Expr right = unary();
            return new Expr.Binary(expr,operator,right);
        }
        return expr ;
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }
    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }
    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }


    public Expr parse(){
        return term();
    }


}
