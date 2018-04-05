package ua.kata;

public class KMaxItems {
  public int[] from(int[] numbers, int k) {
    int[] max = new int[k];
    System.arraycopy(numbers, 0, max, 0, k);
    for (int i = k; i < numbers.length; i++) {
      for (int j = 0; j < k; j++) {
        if (max[j] < numbers[i]) {
          max[j] = numbers[i];
          break;
        }
      }
    }
    return max;
  }
}
