package ua.kata;

import java.util.Arrays;

public class ShiftArrayLeft {
  public void shift(int[] items, int k) {
    int actual = k % items.length;
    reverse(items, 0, items.length - 1);
    reverse(items, 0, items.length - 1 - actual);
    reverse(items, items.length - actual, items.length - 1);
  }

  private void reverse(int[] items, int left, int right) {
    while (left < right) {
      int temp = items[left];
      items[left] = items[right];
      items[right] = temp;

      right -= 1;
      left += 1;
    }
  }
}
