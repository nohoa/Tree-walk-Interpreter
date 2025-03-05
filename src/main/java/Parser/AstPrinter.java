package Parser;

public class AstPrinter implements Expr.Visitor<String> {
    public String print(Expr expr){
        //System.out.println("Call once");
       // It will go here and pass this as new AstPrinter class aka Impelementation of Expr.Visitor
        // expr can be consider as new Class instance that call each class for accept a new method
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr){
         return parenthesize(expr.operator.getVisual(),
                expr.Left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.getName(), expr.right);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            // Only iterate and recursion call the expression value class based on this blueprint class method
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }

}
