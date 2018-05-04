package ua.kata;

public class ShiftArrayLeft {
  public void shift(int[] items, int k) {
    int j = 0;
    while (j < k) {
      int i = 1;
      while (items.length > i) {
        int temp = items[i - 1];
        items[i - 1] = items[i];
        items[i] = temp;
        i += 1;
      }
      j += 1;
    }
  }
}
