package ua.kata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.*;
import static java.util.stream.Collectors.*;

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
        .reduce(this::reduce)
        .map(r -> r.get(0))
        .orElse(0);
  }

  private List<Integer> reduce(List<Integer> acc, List<Integer> list) {
    return IntStream.range(0, list.size())
        .mapToObj(j -> min(acc.get(j), acc.get(j + 1)) + list.get(j))
        .collect(toCollection(() -> new ArrayList<>(acc.size())));
  }
}
