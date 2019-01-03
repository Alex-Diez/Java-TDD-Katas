package ua.kata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class MinTriangleSum {
  private final List<List<Integer>> triangle;

  MinTriangleSum(List<List<Integer>> triangle) {
    this.triangle = triangle;
  }

  Mono<Integer> compute() {
    return Flux.range(0, to())
        .map(i -> to() - i - 1)
        .map(triangle::get)
        .reduce(
            last(),
            (acc, level) -> IntStream.range(0, level.size())
                .map(i -> Math.min(acc.get(i), acc.get(i + 1)) + level.get(i))
                .boxed()
                .collect(Collectors.toList())
        ).map(l -> l.get(0));
  }

  private int to() {
    return triangle.size() - 1;
  }

  private List<Integer> last() {
    return new ArrayList<>(triangle.get(triangle.size() - 1));
  }
}
