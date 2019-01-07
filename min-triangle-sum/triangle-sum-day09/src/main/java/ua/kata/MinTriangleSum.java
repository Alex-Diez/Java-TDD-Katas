package ua.kata;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinTriangleSum {
  private final List<List<Integer>> triangle;

  MinTriangleSum(List<List<Integer>> triangle) {
    this.triangle = triangle;
  }

  int compute() {
    final int to = triangle.size();
    return IntStream.range(0, to)
        .map(i -> to - i - 1)
        .mapToObj(triangle::get)
        .reduce(
            (acc, level) -> IntStream.range(0, level.size())
                .map(j -> Math.min(acc.get(j), acc.get(j + 1)) + level.get(j))
                .boxed()
                .collect(Collectors.toList())
        ).stream()
        .findFirst()
        .map(l -> l.get(0))
        .orElse(0);
  }
}
