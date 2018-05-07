package ua.kata;

public class ShiftLeftArray {
  public void shift(int[] items, int k) {
    int actual = k % items.length;
    reverse(items, 0, items.length - 1);
    reverse(items, 0, items.length - 1 - actual);
    reverse(items, items.length - actual, items.length - 1);
  }

  private void reverse(int[] items, int f, int l) {
    while (l > f) {
      int temp = items[f];
      items[f] = items[l];
      items[l] = temp;

      l -= 1;
      f += 1;
    }
  }
}
