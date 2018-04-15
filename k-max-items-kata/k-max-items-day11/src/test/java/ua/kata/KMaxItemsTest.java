package ua.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KMaxItemsTest {
  @Test
  void oneItem_singleItemArray() throws Exception {
    assertThat(new KMaxItems().from(new int[]{1}, 1))
        .containsExactly(1);
  }

  @Test
  void oneItem_twoItemsArray_firstIsMax() throws Exception {
    assertThat(new KMaxItems().from(new int[]{2, 1}, 1))
        .containsExactly(2);
  }

  @Test
  void oneItem_twoItemsArray_lastIsMax() throws Exception {
    assertThat(new KMaxItems().from(new int[]{1, 2}, 1))
        .containsExactly(2);
  }

  @Test
  void oneItem_threeItemsArray() throws Exception {
    assertThat(new KMaxItems().from(new int[]{1, 2, 3}, 1))
        .containsExactly(3);
  }

  @Test
  void twoItems_threeItemsArray() throws Exception {
    assertThat(new KMaxItems().from(new int[]{1, 2, 3}, 2))
        .containsExactlyInAnyOrder(2, 3);
  }
}
