package ua.kata;

public class StringCalc {
  public int compute(String src) {
    return new Parser(src).parseExpression();
  }

  private static class Parser {
    private final String src;
    private int index;

    Parser(String src) {
      this.src = src;
    }

    private int parseExpression() {
      int num = parseTerm();
      while (notEmpty()) {
        char op = parseOperator();
        if (op == '+') {
          skip();
          num += parseTerm();
        } else if (op == '-') {
          skip();
          num -= parseTerm();
        } else {
          break;
        }
      }
      return num;
    }

    int parseTerm() {
      int num = parseArgument();
      while (notEmpty()) {
        char op = parseOperator();
        if (op == '*') {
          skip();
          num *= parseArgument();
        } else if (op == '/') {
          skip();
          num /= parseArgument();
        } else {
          break;
        }
      }
      return num;
    }

    int parseArgument() {
      if (nextIsSubexpression()) {
        skip();
        int num = parseExpression();
        skip();
        return num;
      } else {
        int start = index;
        int end = index;
        while (end < src.length() && (isDigitAt(end) || firstIsMinus(end))) {
          end++;
        }
        index = end;
        return Integer.parseInt(src.substring(start, end));
      }
    }

    private boolean firstIsMinus(int end) {
      return end == index && src.charAt(end) == '-';
    }

    private boolean isDigitAt(int end) {
      return Character.isDigit(src.charAt(end));
    }

    private boolean nextIsSubexpression() {
      return src.charAt(index) == '(';
    }

    boolean notEmpty() {
      return index < src.length();
    }

    char parseOperator() {
      return src.charAt(index);
    }

    void skip() {
      index++;
    }
  }
}
