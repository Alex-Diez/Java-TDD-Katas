package ua.kata;

public class ShiftArrayLeft {
  public void shift(int[] items, int k) {
    int actual = k % items.length;
    reverse(items, 0, items.length - 1);
    reverse(items, 0, items.length - 1 - actual);
    reverse(items, items.length - actual, items.length - 1);
  }

  private void reverse(int[] items, int first, int last) {
    while (last > first) {
      int temp = items[first];
      items[first] = items[last];
      items[last] = temp;

      last -= 1;
      first += 1;
    }
  }
}
