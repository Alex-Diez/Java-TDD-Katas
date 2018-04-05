package ua.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;

class KMaxItemsTest {
  @Test
  void oneItem_fromSingleItemArray() throws Exception {
    assertThat(new KMaxItems().from(new int[]{1}, 1))
        .isEqualTo(new int[]{1});
  }

  @Test
  void oneItem_fromTwoItemsArray_maxIsFirst() throws Exception {
    assertThat(new KMaxItems().from(new int[]{2, 1}, 1))
        .isEqualTo(new int[]{2});
  }

  @Test
  void oneItem_fromTwoItemsArray_maxIsLast() throws Exception {
    assertThat(new KMaxItems().from(new int[]{1, 2}, 1))
        .isEqualTo(new int[]{2});
  }

  @Test
  void oneItem_fromThreeItemsArray_maxIsLast() throws Exception {
    assertThat(new KMaxItems().from(new int[]{1, 2, 3}, 1))
        .isEqualTo(new int[]{3});
  }

  @Test
  void twoItems_fromThreeItemsArray() throws Exception {
    assertThat(new KMaxItems().from(new int[]{1, 2, 3}, 2))
        .contains(2, 3);
  }
}
