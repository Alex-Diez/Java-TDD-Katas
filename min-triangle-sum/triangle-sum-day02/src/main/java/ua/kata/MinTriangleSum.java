package ua.kata;

import java.util.List;
import java.util.function.BiFunction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

class MinTriangleSum {
  private final List<List<Integer>> triangle;
  private final int[] buffer;

  MinTriangleSum(List<List<Integer>> triangle) {
    this.triangle = triangle;
    this.buffer = new int[triangle.get(triangle.size() - 1).size()];
  }

  int compute() {
    for (int last = triangle.size() - 1; last > -1; last -= 1) {
      final List<Integer> current = triangle.get(last);
      final int size = current.size();
      Flux.generate(() -> 0, nextIndex())
          .limitRequest(size)
          .subscribe(
              i -> buffer[i] = Math.min(ith(i), ith(i + 1)) + current.get(i)
          );
    }
    return buffer[0];
  }

  private BiFunction<Integer, SynchronousSink<Integer>, Integer> nextIndex() {
    return (i, s) -> {
      s.next(i);
      return i + 1;
    };
  }

  private int ith(int i) {
    try {
      return buffer[i];
    } catch (IndexOutOfBoundsException e) {
      return 0;
    }
  }
}
