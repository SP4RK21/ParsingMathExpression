package parser;

import exception.*;
import expression.*;

import java.util.HashMap;
import java.util.Map;

public class ExpressionParser implements Parser {
    private String parsedExpression;
    private int stringIndex = 0;
    private int value;
    private String variableName;
    private String constantString;
    private int bracketsBalance = 0;

    private enum Token {DEFAULT, NUMBER, VARIABLE, SUM, SUBTRACT, MULTIPLY, DIVIDE, LOG10, POW10, NEGATIVE, OR, AND, XOR, NOT, COUNT, OPEN_BRACKET, CLOSE_BRACKET}

    private static Map<String, Token> variables = new HashMap<>();

    static {
        variables.put("x", Token.VARIABLE);
        variables.put("y", Token.VARIABLE);
        variables.put("z", Token.VARIABLE);
    }

    private static Map<String, Token> operations = new HashMap<>();

    static {
        operations.put("pow10", Token.POW10);
        operations.put("log10", Token.LOG10);
        operations.put("count", Token.NOT);
        operations.put("+", Token.SUM);
        operations.put("-", Token.SUBTRACT);
        operations.put("*", Token.MULTIPLY);
        operations.put("/", Token.DIVIDE);
        operations.put("|", Token.OR);
        operations.put("&", Token.AND);
        operations.put("^", Token.XOR);
        operations.put("~", Token.NOT);
    }

    private Token curToken;

    private void skipWhitespace() {
        while (stringIndex < parsedExpression.length() && Character.isWhitespace(parsedExpression.charAt(stringIndex))) {
            stringIndex++;
        }
    }

    private String readNumber() {
        int left = stringIndex;
        while (stringIndex < parsedExpression.length() && Character.isDigit(parsedExpression.charAt(stringIndex))) {
            stringIndex++;
        }
        int right = stringIndex;
        if (stringIndex == parsedExpression.length()) {
            stringIndex++;
        }
        constantString = parsedExpression.substring(left, right);
        return constantString;
    }

    private Token readIdentifier() throws UnexpectedIdentifierException {
        int left = stringIndex;
        while (stringIndex < parsedExpression.length() && Character.isLetterOrDigit(parsedExpression.charAt(stringIndex))) {
            stringIndex++;
        }
        int right = stringIndex;
        if (stringIndex == parsedExpression.length()) {
            stringIndex++;
        }
        if (variables.containsKey(parsedExpression.substring(left, right))) {
            variableName = parsedExpression.substring(left, right);
            return variables.get(parsedExpression.substring(left, right));
        } else if (operations.containsKey(parsedExpression.substring(left, right))) {
            return operations.get(parsedExpression.substring(left, right));
        } else {
            throw new UnexpectedIdentifierException(parsedExpression.substring(left, right), parsedExpression);
        }
    }

    private void checkForOperand() throws OperandNotFoundException {
        if (curToken != Token.VARIABLE && curToken != Token.NUMBER && curToken != Token.CLOSE_BRACKET) {
            throw new OperandNotFoundException(stringIndex + 1, parsedExpression);
        }
    }

    private void checkForOperation() throws OperationNotFoundException {
        if (curToken == Token.NUMBER || curToken == Token.CLOSE_BRACKET || curToken == Token.VARIABLE) {
            throw new OperationNotFoundException(stringIndex + 1, parsedExpression);
        }
    }

    @Override
    public TripleExpression parse(String expression) throws ParsingException {
        curToken = Token.DEFAULT;
        parsedExpression = expression.trim();
        stringIndex = 0;
        bracketsBalance = 0;
        return or();
    }

    private TripleExpression or() throws ParsingException {
        TripleExpression result = xor();
        while (true) {
            switch (curToken) {
                case OR:
                    result = new Or(result, xor());
                    break;
                default:
                    return result;
            }
        }
    }

    private TripleExpression xor() throws ParsingException {
        TripleExpression result = and();
        while (true) {
            switch (curToken) {
                case XOR:
                    result = new Xor(result, and());
                    break;
                default:
                    return result;
            }
        }
    }

    private TripleExpression and() throws ParsingException {
        TripleExpression result = addSubtract();
        while (true) {
            switch (curToken) {
                case AND:
                    result = new And(result, addSubtract());
                    break;
                default:
                    return result;
            }
        }
    }

    private TripleExpression addSubtract() throws ParsingException {
        TripleExpression result = multiplyDivide();
        while (true) {
            switch (curToken) {
                case SUM:
                    result = new CheckedAdd(result, multiplyDivide());
                    break;
                case SUBTRACT:
                    result = new CheckedSubtract(result, multiplyDivide());
                    break;
                default:
                    return result;
            }
        }
    }


    private TripleExpression multiplyDivide() throws ParsingException {
        TripleExpression result = unaryConstVariable();
        while (true) {
            switch (curToken) {
                case MULTIPLY:
                    result = new CheckedMultiply(result, unaryConstVariable());
                    break;
                case DIVIDE:
                    result = new CheckedDivide(result, unaryConstVariable());
                    break;
                default:
                    return result;
            }
        }
    }

    private TripleExpression unaryConstVariable() throws ParsingException {
        readToken();
        TripleExpression result;
        switch (curToken) {
            case NUMBER:
                result = new Const(value);
                readToken();
                break;
            case VARIABLE:
                result = new Variable(variableName);
                readToken();
                break;
            case LOG10:
                result = new CheckedLog10(unaryConstVariable());
                break;
            case POW10:
                result = new CheckedPow10(unaryConstVariable());
                break;
            case NEGATIVE:
                result = new CheckedNegate(unaryConstVariable());
                break;
            case NOT:
                result = new BitNot(unaryConstVariable());
                break;
            case COUNT:
                result = new Count(unaryConstVariable());
                break;
            case OPEN_BRACKET:
                result = or();
                if (curToken != Token.CLOSE_BRACKET) {
                    throw new BracketsMismatchException("No closing bracket: " + parsedExpression);
                }
                readToken();
                break;
            default:
                throw new ParsingException("Incorrect expression" + parsedExpression);
        }
        return result;
    }

    private void readToken() throws ParsingException {
        skipWhitespace();
        if (stringIndex >= parsedExpression.length()) {
            if (bracketsBalance != 0) {
                throw new BracketsMismatchException("No closing bracket: " + parsedExpression);
            } else {
                checkForOperand();
            }
            curToken = Token.DEFAULT;
            return;
        }
        char curChar = parsedExpression.charAt(stringIndex);
        switch (curChar) {
            case '|':
                checkForOperand();
                curToken = Token.OR;
                break;
            case '^':
                checkForOperand();
                curToken = Token.XOR;
                break;
            case '&':
                checkForOperand();
                curToken = Token.AND;
                break;
            case '+':
                checkForOperand();
                curToken = Token.SUM;
                break;
            case '-':
                if (curToken == Token.NUMBER || curToken == Token.VARIABLE || curToken == Token.CLOSE_BRACKET) {
                    checkForOperand();
                    curToken = Token.SUBTRACT;
                } else {
                    if (stringIndex + 1 >= parsedExpression.length()) {
                        throw new OperandNotFoundException(stringIndex + 1, parsedExpression);
                    }
                    curToken = Token.NEGATIVE;
                }
                break;
            case '*':
                checkForOperand();
                curToken = Token.MULTIPLY;
                break;
            case '/':
                checkForOperand();
                curToken = Token.DIVIDE;
                break;
            case '~':
                curToken = Token.NOT;
                break;
            case '(':
                checkForOperation();
                bracketsBalance++;
                curToken = Token.OPEN_BRACKET;
                break;
            case ')':
                checkForOperand();
                bracketsBalance--;
                if (bracketsBalance < 0) {
                    throw new BracketsMismatchException("No opening bracket: " + parsedExpression);
                }
                curToken = Token.CLOSE_BRACKET;
                break;
            default:
                if (Character.isLetter(curChar)) {
                    curToken = readIdentifier();
                    stringIndex--;
                } else if (Character.isDigit(parsedExpression.charAt(stringIndex))) {
                    try {
                        value = Integer.parseInt("-" + readNumber());
                        if (curToken == Token.NEGATIVE) {
                        } else {
                            value = Integer.parseInt(readNumber());
                        }
                        curToken = Token.NUMBER;
                    } catch (NumberFormatException e) {
                        throw new IncorrectConstantException("Constant " + constantString + " doesn't fit in int");
                    }
                    stringIndex--;
                } else {
                    throw new UnexpectedSymbolException(parsedExpression.charAt(stringIndex), stringIndex + 1, parsedExpression);
                }
                break;
        }
        stringIndex++;
    }
}
