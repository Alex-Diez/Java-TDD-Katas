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
    return IntStream.range(0, triangle.size())
        .map(i -> triangle.size() - i - 1)
        .mapToObj(triangle::get)
        .reduce(this::reduce)
        .map(l -> l.get(0))
        .orElse(0);
  }

  private List<Integer> reduce(List<Integer> acc, List<Integer> list) {
    return IntStream.range(0, list.size())
        .map(j -> Math.min(acc.get(j), acc.get(j + 1)) + list.get(j))
        .boxed()
        .collect(Collectors.toCollection(() -> new ArrayList<>(acc.size())));
  }
}
