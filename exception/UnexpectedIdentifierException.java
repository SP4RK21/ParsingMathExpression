package exception;

public class UnexpectedIdentifierException extends ParsingException {
    public UnexpectedIdentifierException(String identifier, String expression) {
        super("Unexpected identifier \"" + identifier + "\" in expression \n" + expression);
    }
}
