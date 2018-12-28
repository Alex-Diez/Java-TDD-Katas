package ua.kata;

import java.util.List;

import reactor.core.publisher.Mono;

import static java.lang.Math.min;

class MinTriangleSum {
  private final List<List<Integer>> triangle;

  MinTriangleSum(List<List<Integer>> triangle) {
    this.triangle = triangle;
  }

  int compute() {
    final int bottom = triangle.size() - 1;
    final List<Integer> bottomRow = triangle.get(bottom);

    final int[] sums = new int[bottomRow.size()];

    for (int current = bottom; current > -1; current--) {
      List<Integer> row = triangle.get(current);
      for (int i = 0; i < row.size(); i++) {
        final int item = row.get(i);
        sums[i] = item + min(
            Mono.just(i).map(index1 -> sums[index1]).onErrorReturn(0).block(),
            Mono.just(i + 1).map(index -> sums[index]).onErrorReturn(0).block()
        );
      }
    }
    return sums[0];
  }

}
