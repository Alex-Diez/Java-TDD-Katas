package ua.kata;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class MinTriangleSum {
  private final List<List<Integer>> triangle;

  MinTriangleSum(List<List<Integer>> triangle) {
    this.triangle = triangle;
  }

  int compute() {
    int[] result = new int[triangle.size()];
    Function<Integer, Integer> valOrZero = (index) -> {
      try {
        return result[index];
      } catch (IndexOutOfBoundsException e) {
        return 0;
      }
    };

    final int from = 0;
    final int to = triangle.size();
    List<List<Integer>> reversed = IntStream.range(from, to)
        .map(i -> to - i + from - 1)
        .mapToObj(triangle::get).collect(Collectors.toList());

    reversed.forEach(
        current -> IntStream.range(0, current.size())
            .forEach(
                i -> result[i] = Math.min(valOrZero.apply(i), valOrZero.apply(i + 1)) + current.get(i)
            )
    );
    return result[0];
  }
}
