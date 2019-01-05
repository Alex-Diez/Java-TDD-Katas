package ua.kata;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class MinTriangleSum {
  private final List<List<Integer>> triangle;

  MinTriangleSum(List<List<Integer>> triangle) {
    this.triangle = triangle;
  }

  int compute() {
    final int to = to().orElse(0);
    return IntStream.range(0, to)
        .map(i -> to - i - 1)
        .mapToObj(triangle::get)
        .reduce(
            identity(),
            (acc, list) -> IntStream.range(0, list.size())
                .map(j -> Math.min(acc.get(j), acc.get(j + 1)) + list.get(j))
                .boxed()
                .collect(Collectors.toList())
        ).stream().findFirst().orElse(0);
  }

  private List<Integer> identity() {
    return triangle.size() > 0 ? new ArrayList<>(triangle.get(triangle.size() - 1)) : new ArrayList<>();
  }

  private OptionalInt to() {
    return triangle.size() > 0 ? OptionalInt.of(triangle.size() - 1) : OptionalInt.empty();
  }
}
