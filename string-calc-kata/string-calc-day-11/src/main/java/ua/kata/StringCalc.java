package ua.kata;

import java.util.Map;
import java.util.function.IntBinaryOperator;

public class StringCalc {
  public int compute(String src) {
    Parser parser = new Parser(src);
    return parser.parseExpression();
  }

  private static class Parser {
    private final Map<Character, IntBinaryOperator> lowOrderOperators = Map.ofEntries(
        Map.entry('+', (a, b) -> a + b),
        Map.entry('-', (a, b) -> a - b)
    );
    private final Map<Character, IntBinaryOperator> highOrderOperators = Map.ofEntries(
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
      IntBinaryOperator operator;
      while ((operator = lowOrderOperators.get(parseOperation())) != null) {
        skip();
        num = operator.applyAsInt(num, parseTerm());
      }
      return num;
    }

    int parseTerm() {
      int num = parseArgument();
      IntBinaryOperator operator;
      while ((operator = highOrderOperators.get(parseOperation())) != null) {
        skip();
        num = operator.applyAsInt(num, parseArgument());
      }
      return num;
    }

    int parseArgument() {
      if (isSubexpression()) {
        return parseSubexpression();
      } else {
        return parseNumber();
      }
    }

    private boolean isSubexpression() {
      return src.charAt(index) == '(';
    }

    private int parseSubexpression() {
      skip();
      int ret = parseExpression();
      skip();
      return ret;
    }

    private int parseNumber() {
      int start = index;
      int end = endOfNumber();
      index = end;
      return Integer.parseInt(src.substring(start, end));
    }

    private int endOfNumber() {
      return lastIndex() + (int) src.chars().skip(lastIndex()).takeWhile(Character::isDigit).count();
    }

    private int lastIndex() {
      return src.charAt(index) == '-' ? index + 1 : index;
    }

    char parseOperation() {
      return index >= src.length() ? 0 : src.charAt(index);
    }

    void skip() {
      index++;
    }
  }
}
