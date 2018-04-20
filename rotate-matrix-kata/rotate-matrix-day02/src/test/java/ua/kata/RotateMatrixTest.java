package ua.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RotateMatrixTest {
  @Test
  void rotateOneItemMatrix() throws Exception {
    final int[][] matrix = new int[][]{{1}};
    new RotateMatrix().rotate(matrix);
    assertThat(matrix)
        .isEqualTo(
            new int[][]{
                {1}
            }
        );
  }

  @Test
  void rotateTwoByTwoMatrix() throws Exception {
    final int[][] matrix = new int[][]{
        {1, 2},
        {3, 4}
    };
    new RotateMatrix().rotate(matrix);
    assertThat(matrix)
        .isEqualTo(
            new int[][]{
                {3, 1},
                {4, 2}
            }
        );
  }

  @Test
  void rotateThreeByThreeMatrix() throws Exception {
    final int[][] matrix = new int[][] {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };
    new RotateMatrix().rotate(matrix);
    assertThat(matrix)
        .isEqualTo(
            new int[][]{
                {7, 4, 1},
                {8, 5, 2},
                {9, 6, 3}
            }
        );
  }

  @Test
  void rotateFourByFourMatrix() throws Exception {
    final int[][] matrix = new int[][] {
        { 1,  2,  3,  4},
        { 5,  6,  7,  8},
        { 9, 10, 11, 12},
        {13, 14, 15, 16}
    };
    new RotateMatrix().rotate(matrix);
    assertThat(matrix)
        .isEqualTo(
            new int[][]{
                {13,  9,  5,  1},
                {14, 10,  6,  2},
                {15, 11,  7,  3},
                {16, 12,  8,  4}
            }
        );
  }
}
