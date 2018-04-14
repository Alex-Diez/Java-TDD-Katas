package ua.kata;

public class KMaxItems {
  public int[] from(int[] numbers, int k) {
    int[] max = new int[k];
    System.arraycopy(numbers, 0, max, 0, k);
    for (int i = 1; numbers.length > i; i++) {
      int j = 0;
      while (j < k && numbers[i] <= max[j]) {
        j++;
      }
      if (j < k) {
        max[j] = numbers[i];
      }
    }
    return max;
  }
}
