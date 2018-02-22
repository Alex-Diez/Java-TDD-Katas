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

    private void skip() {
      index++;
    }

    int parseArgument() {
      if (src.charAt(index) == '(') {
        skip();
        int num = parseExpression();
        skip();
        return num;
      } else {
        int start = index;
        int end = index;
        while (end < src.length() && Character.isDigit(src.charAt(end)) || (start == end && src.charAt(end) == '-')) {
          end++;
        }
        index = end;
        return Integer.parseInt(src.substring(start, end));
      }
    }

    boolean notEmpty() {
      return index < src.length();
    }

    char parseOperator() {
      return src.charAt(index);
    }
  }
}
