package ua.kata;

import java.util.Map;
import java.util.Set;
import java.util.function.IntBinaryOperator;

public class StringCalc {
  public int compute(String src) {
    return new Parser(src).parseExpression();
  }

  private static class Parser {
    private final Map<Character, IntBinaryOperator> highOrderOps = Map.ofEntries(
        Map.entry('*', (a, b) -> a * b),
        Map.entry('/', (a, b) -> a / b)
    );
    private final Map<Character, IntBinaryOperator> lowOrderOps = Map.ofEntries(
        Map.entry('+', (a, b) -> a + b),
        Map.entry('-', (a, b) -> a - b)
    );
    private final Set<Character> hOps = Set.of('*', '/');
    private final Set<Character> lOps = Set.of('-', '+');

    private final String src;
    private int index;

    Parser(String src) {
      this.src = src;
    }

    int parseExpression() {
      int num = parseTerm();
      IntBinaryOperator op;
      while (notEmpty() && (op = (lowOrderOps.get(parseLowOrderOperation()))) != null) {
        num = op.applyAsInt(num, parseTerm());
      }
      return num;
    }

    int parseTerm() {
      int num = parseArgument();
      IntBinaryOperator op;
      while (notEmpty() && (op = (highOrderOps.get(parseHighOrderOperation()))) != null) {
        num = op.applyAsInt(num, parseArgument());
      }
      return num;
    }

    int parseArgument() {
      int start = index;
      int end = index;
      while (end < src.length() && (isDigitAt(end) || isFirstCharMinus(start, end))) {
        end++;
      }
      index = end;
      return Integer.parseInt(src.substring(start, end));
    }

    private boolean isFirstCharMinus(int start, int end) {
      return start == end && src.charAt(end) == '-';
    }

    private boolean isDigitAt(int end) {
      return Character.isDigit(src.charAt(end));
    }

    char parseHighOrderOperation() {
      char o = src.charAt(index);
      if (hOps.contains(o)) {
        index++;
        return o;
      } else {
        return 0;
      }
    }

    char parseLowOrderOperation() {
      char o = src.charAt(index);
      if (lOps.contains(o)) {
        index++;
        return o;
      } else {
        return 0;
      }
    }

    private boolean notEmpty() {
      return index != src.length();
    }
  }
}
