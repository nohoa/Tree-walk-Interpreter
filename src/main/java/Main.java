import Parser.AstPrinter;
import Parser.Expr;
import Parser.Parser;
import Scanner.Scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import Scanner.* ;
public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible
    // when running tests.
    System.err.println("Logs from your program will appear here!");
    if (args.length < 2) {
      System.err.println("Usage: ./your_program.sh tokenize <filename>");
      System.exit(1);
    }
    String command = args[0];
    String filename = args[1];
    if (!command.equals("tokenize") && !command.equals("parse")) {
      System.err.println("Unknown command: " + command);
      System.exit(1);
    }
    String fileContents = "";
    try {
      fileContents = Files.readString(Path.of(filename));
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
      System.exit(1);
    }
    // Uncomment this block to pass the first stage
    Scanner loxScanner = new Scanner(fileContents);
    List<Token> scanAll = loxScanner.scanTokens();
   // else {
    // Placeholder, remove this line when implementing the scanner
    if (command.equals("tokenize")) {
      boolean error = false;
      for (Token scan : scanAll) {
        String display = scan.toString();
        if (display.charAt(0) == '[') {
          error = true;
          System.err.println(display);
        } else {
          System.out.println(display);
        }
      }
      if (error) System.exit(65);
    }
    if (command.equals("parse")) {
      //System.out.println("here");
//      Expr expression = new Expr.Binary(
//              new Expr.Unary(
//                      new Token(TokenType.MINUS, "-", null, 1),
//                      new Expr.Literal(123)),
//              new Token(TokenType.STAR, "*", null, 1),
//              new Expr.Grouping(
//                      new Expr.Literal(45.67)));
//      AstPrinter printer = new AstPrinter();
//      //this will call method print of AstPrinter Class
//      System.out.println(printer.print(expression));
      Parser parser = new Parser(scanAll);
      Expr expression = parser.parse();

      System.out.println(new AstPrinter().print(expression));
    }
  //}
  }
}