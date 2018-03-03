package ua.kata;

import java.util.Map;
import java.util.function.IntBinaryOperator;

public class StringCalc {

  public int compute(String string) {
    return new Parser(string).parseExpression();
  }

  private class Parser {
    private final Map<Character, IntBinaryOperator> lowOrderOperators = Map.ofEntries(
        Map.entry('+', (a, b) -> a + b),
        Map.entry('-', (a, b) -> a - b)
    );
    private final Map<Character, IntBinaryOperator> highOrderOperators = Map.ofEntries(
        Map.entry('*', (a, b) -> a * b),
        Map.entry('/', (a, b) -> a / b)
    );
    private final String string;
    private int index;

    Parser(String string) {
      this.string = string;
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
      return string.charAt(index) == '(';
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
      return Integer.parseInt(string.substring(start, end));
    }

    private int endOfNumber() {
      return lastIndex() + (int)string.chars().skip(lastIndex()).takeWhile(Character::isDigit).count();
    }

    private int lastIndex() {
      return isMinus() ? index + 1 : index;
    }

    private boolean isMinus() {
      return string.charAt(index) == '-';
    }

    char parseOperation() {
      return index >= string.length() ? 0 : string.charAt(index);
    }

    void skip() {
      index++;
    }
  }
}
