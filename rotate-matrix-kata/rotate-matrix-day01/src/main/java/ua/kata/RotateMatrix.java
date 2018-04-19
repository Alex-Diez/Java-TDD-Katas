package ua.kata;

public class RotateMatrix {
  public void rotate(int[][] matrix) {
    int l = matrix.length - 1;
    int f = 0;
    while (l > f) {
      for (int i = 0; i < l - f; i++) {
        int temp = matrix[l - i][f];
        matrix[l - i][f] = matrix[l][l - i];
        matrix[l][l - i] = matrix[f + i][l];
        matrix[f + i][l] = matrix[f][f + i];
        matrix[f][f + i] = temp;
      }
      l -= 1;
      f += 1;
    }
  }
}
