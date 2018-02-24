package ua.kata;

import java.util.Map;
import java.util.function.IntBinaryOperator;

public class StringCalc {

  public int compute(String src) {
    return new Parser(src).parseExpression();
  }

  private static class Parser {
    private final Map<Character, IntBinaryOperator> lowerOrderOperations = Map.ofEntries(
        Map.entry('+', (a, b) -> a + b),
        Map.entry('-', (a, b) -> a - b)
    );
    private final Map<Character, IntBinaryOperator> higherOrderOperations = Map.ofEntries(
        Map.entry('*', (a, b) -> a * b),
        Map.entry('/', (a, b) -> a / b)
    );

    private final String src;
    private int index;

    Parser(String src) {
      this.src = src;
    }

    private int parseExpression() {
      int num = parseTerm();
      char op = parseOperation();
      IntBinaryOperator operator;
      while ((operator = lowerOrderOperations.get(op)) != null) {
        skip();
        num = operator.applyAsInt(num, parseTerm());
        op = parseOperation();
      }
      return num;
    }

    int parseTerm() {
      int num = parseArgument();
      char op = parseOperation();
      IntBinaryOperator operation;
      while ((operation = higherOrderOperations.get(op)) != null) {
        skip();
        num = operation.applyAsInt(num, parseArgument());
        op = parseOperation();
      }
      return num;
    }

    int parseArgument() {
      if (src.charAt(index) == '(') {
        skip();
        int num = parseExpression();
        skip();
        return num;
      } else {
        int start = index;
        int end = isMinusSign() ? index + 1 : index;
        while (end < src.length() && isDigit(end)) {
          end++;
        }
        index = end;
        return Integer.parseInt(src.substring(start, end));
      }
    }

    private boolean isMinusSign() {
      return src.charAt(index) == '-';
    }

    private boolean isDigit(int end) {
      return Character.isDigit(src.charAt(end));
    }

    boolean isEmpty() {
      return index >= src.length();
    }

    char parseOperation() {
      return isEmpty() ? 0 : src.charAt(index);
    }

    void skip() {
      index++;
    }
  }
}
