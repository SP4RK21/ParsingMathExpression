package expression;

import exception.EvaluatingException;
import exception.OverflowException;
import exception.ParsingException;
import parser.ExpressionParser;
import parser.Parser;

public class Main {

    public static void main(String[] args) throws Exception {
        Parser par = new ExpressionParser();
        try {
            System.out.println(par.parse(" x/  -  2\n").evaluate(2, 0, 0));
        } catch (EvaluatingException | ParsingException e) {
            System.out.println(e.getMessage());
        }
    }
}