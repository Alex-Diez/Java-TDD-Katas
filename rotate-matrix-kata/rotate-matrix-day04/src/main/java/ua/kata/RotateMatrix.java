package ua.kata;

public class RotateMatrix {
  public void rotate(int[][] matrix) {
    int l = matrix.length - 1;
    int f = 0;

    while (l > f) {
      for (int i = 0; l - f > i; i++) {
        int temp = matrix[l][f + i];
        matrix[l][f + i] = matrix[l - i][l];
        matrix[l - i][l] = matrix[f][l - i];
        matrix[f][l - i] = matrix[f + i][f];
        matrix[f + i][f] = temp;
      }
      l -= 1;
      f += 1;
    }
  }
}
