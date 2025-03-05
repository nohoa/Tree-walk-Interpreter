package Parser;
import java.util.List;

import Scanner.Token;

public abstract class Expr{
  interface Visitor<R> {
    R visitBinaryExpr(Binary expr);
    R visitGroupingExpr(Grouping expr);
    R visitLiteralExpr(Literal expr);
    R visitUnaryExpr(Unary expr);
  }
  public static class Binary extends Expr {
    public Binary(Expr Left, Token operator, Expr right) {
      this.Left = Left;
      this.operator = operator;
      this.right = right;
    }

    @Override
     <R> R accept(Visitor<R> visitor) {
      return visitor.visitBinaryExpr(this);
    }

    final Expr Left;
    final Token operator;
    final Expr right;
  }
  public static class Grouping extends Expr {

    public Grouping(Expr expression) {
      this.expression = expression;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {

      return visitor.visitGroupingExpr(this);
    }

    final Expr expression;
  }
  public static class Literal extends Expr {
    public Literal(Object value) {
      //System.out.println("Called fifth");
      this.value = value;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitLiteralExpr(this);
    }

    final Object value;
  }
  public static class Unary extends Expr {
    public Unary(Token operator, Expr right) {
      this.operator = operator;
      this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {

      return visitor.visitUnaryExpr(this);
    }

    final Token operator;
    final Expr right;
  }

 abstract <R> R accept(Visitor<R> visitor);
}
