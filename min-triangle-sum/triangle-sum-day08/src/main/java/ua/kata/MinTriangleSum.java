package ua.kata;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

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
            new ArrayList<>(triangle.get(triangle.size() - 1)),
            (acc, list) -> IntStream.range(0, list.size())
                .map(j -> Math.min(acc.get(j), acc.get(j + 1)) + list.get(j))
                .boxed()
                .collect(toList())
        ).stream()
        .findFirst()
        .orElse(0);
  }

  private OptionalInt to() {
    return triangle.size() > 0 ? OptionalInt.of(triangle.size() - 1) : OptionalInt.empty();
  }
}
