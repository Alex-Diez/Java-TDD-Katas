package ua.kata;

public class KMaxItems {
  public int[] from(int[] numbers, int k) {
    int[] max = new int[k];
    System.arraycopy(numbers, 0, max, 0, k);
    for (int i = k; i < numbers.length; i++) {
      int j = 0;
      while (j < k && max[j] >= numbers[i]) {
        j++;
      }
      if (j < k) {
        max[j] = numbers[i];
      }
    }
    return max;
  }
}
