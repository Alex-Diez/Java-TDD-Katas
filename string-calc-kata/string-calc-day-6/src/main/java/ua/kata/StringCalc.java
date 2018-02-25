package ua.kata;

import java.util.Map;
import java.util.function.IntBinaryOperator;

public class StringCalc {

  public int compute(String src) {
    return new Parser(src).parseExpression();
  }

  private static class Parser {
    private final Map<Character, IntBinaryOperator> lowOrderOperations = Map.ofEntries(
        Map.entry('+', (a, b) -> a + b),
        Map.entry('-', (a, b) -> a - b)
    );

    private final Map<Character, IntBinaryOperator> highOrderOperations = Map.ofEntries(
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
      IntBinaryOperator operation;
      while ((operation = lowOrderOperations.get(parseOperator())) != null) {
        skip();
        num = operation.applyAsInt(num, parseTerm());
      }
      return num;
    }

    int parseTerm() {
      int num = parseArgument();
      IntBinaryOperator operation;
      while ((operation = highOrderOperations.get(parseOperator())) != null) {
        skip();
        num = operation.applyAsInt(num, parseArgument());
      }
      return num;
    }

    int parseArgument() {
      if (isSubexpression()) {
        skip();
        int ret = parseExpression();
        skip();
        return ret;
      } else {
        int start = index;
        int end = findEndOfNumber();
        index = end;
        return Integer.parseInt(src.substring(start, end));
      }
    }

    private int findEndOfNumber() {
      int end = src.charAt(index) == '-' ? index + 1 : index;
      while (end < src.length() && Character.isDigit(src.charAt(end))) {
        end++;
      }
      return end;
    }

    private boolean isSubexpression() {
      return src.charAt(index) == '(';
    }

    private char parseOperator() {
      if (index >= src.length()) return 0;
      else return src.charAt(index);
    }

    private void skip() {
      index++;
    }
  }
}
