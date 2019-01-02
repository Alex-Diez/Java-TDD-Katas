package ua.kata;

import java.util.List;
import java.util.function.Function;

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

    for (int i = triangle.size() - 1; i > -1; i -= 1) {
      final List<Integer> current = triangle.get(i);
      for (int j = 0; j < current.size(); j++) {
        result[j] = Math.min(valOrZero.apply(j), valOrZero.apply(j + 1)) + current.get(j);
      }
    }
    return result[0];
  }
}
