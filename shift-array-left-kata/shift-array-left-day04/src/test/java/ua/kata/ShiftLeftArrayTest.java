package ua.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShiftLeftArrayTest {
  @Test
  void twoItemsArray_shiftByOne() throws Exception {
    final int[] items = {1, 2};
    new ShiftLeftArray().shift(items, 1);
    assertThat(items).isEqualTo(new int[]{2, 1});
  }

  @Test
  void fourItemsArray_shiftByTwo() throws Exception {
    final int[] items = {1, 2, 3, 4};
    new ShiftLeftArray().shift(items, 2);
    assertThat(items).isEqualTo(new int[]{3, 4, 1, 2});
  }

  @Test
  void fourItemsArray_shiftByFive() throws Exception {
    final int[] items = {1, 2, 3, 4};
    new ShiftLeftArray().shift(items, 5);
    assertThat(items).isEqualTo(new int[]{2, 3, 4, 1});
  }
}
