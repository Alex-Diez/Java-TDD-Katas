package ua.kata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinTriangleSum {
  private final List<List<Integer>> triangle;

  MinTriangleSum(List<List<Integer>> triangle) {
    this.triangle = triangle;
  }

  int compute() {
    final int last = triangle.size() - 1;

    return IntStream.range(0, last)
        .map(i -> last - i - 1)
        .mapToObj(triangle::get)
        .reduce(
            new ArrayList<>(triangle.get(last)),
            (acc, current) -> IntStream.range(0, current.size())
                .map(j -> Math.min(acc.get(j), acc.get(j + 1)) + current.get(j))
                .boxed()
                .collect(Collectors.toList())
        ).get(0);
  }
}
