package exception;

public class UnexpectedSymbolException extends ParsingException {
    public UnexpectedSymbolException(char c, int index, String expression) {
        super("Unexpected symbol \"" + String.valueOf(c) + "\" at position " + String.valueOf(index) + " in expression \n" + expression);
    }
}
