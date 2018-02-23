package ua.kata;

import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class StringCalc {

  public Optional<Integer> compute(String src) {
    return new Parser(src).parseExpression();
  }

  private static class Parser {
    private static int asInt(char c) {
      return (int)c;
    }

    private Map<Integer, BinaryOperator<Integer>> lowOrderOps = Map.ofEntries(
        Map.entry(asInt('+'), (a, b) -> a + b),
        Map.entry(asInt('-'), (a, b) -> a - b)
    );

    private Map<Integer, BinaryOperator<Integer>> highPriorityOps = Map.ofEntries(
        Map.entry(asInt('*'), (a, b) -> a * b),
        Map.entry(asInt('/'), (a, b) -> a / b)
    );

    private final String src;
    private int index;

    Parser(String src) {
      this.src = src;
    }

    private Optional<Integer> parseExpression() {
      Optional<Integer> num = parseTerm();
      Optional<BinaryOperator<Integer>> operator = parseLowOrderOperator();
      while (operator.isPresent()) {
        skip();
        Optional<Integer> orElse = num;
        num = operator.flatMap(from(num, parseTerm())::reduce).or(() -> orElse);
        operator = parseLowOrderOperator();
      }
      return num;
    }

    private static Stream<Integer> from(Optional<Integer> first, Optional<Integer> second) {
      return Stream.concat(first.stream(), second.stream());
    }

    Optional<Integer> parseTerm() {
      Optional<Integer> num = parseArgument();
      Optional<BinaryOperator<Integer>> operator = parseHighOrderOperator();
      while (operator.isPresent()) {
        skip();
        Optional<Integer> orElse = num;
        num = operator.flatMap(from(num, parseArgument())::reduce).or(() -> orElse);
        operator = parseHighOrderOperator();
      }
      return num;
    }

    Optional<Integer> parseArgument() {
      if (isEmpty()) {
        return Optional.empty();
      }
      if (src.charAt(index) == '(') {
        skip();
        Optional<Integer> ret = parseExpression();
        skip();
        return ret;
      }
      int start = index;
      int end = index;
      while (end < src.length() && (Character.isDigit(src.charAt(end)) || (start == end && src.charAt(end) == '-'))) {
        end++;
      }
      index = end;
      return Optional.of(Integer.parseInt(src.substring(start, end)));
    }

    Optional<BinaryOperator<Integer>> parseLowOrderOperator() {
      return parseOperator().flatMap(o -> Optional.ofNullable(lowOrderOps.get(o)));
    }

    Optional<BinaryOperator<Integer>> parseHighOrderOperator() {
      return parseOperator().flatMap(o -> Optional.ofNullable(highPriorityOps.get(o)));
    }

    Optional<Integer> parseOperator() {
      return isEmpty() ? Optional.empty() : Optional.of(asInt(src.charAt(index)));
    }

    void skip() {
      index++;
    }

    private boolean isEmpty() {
      return index >= src.length();
    }
  }
}
