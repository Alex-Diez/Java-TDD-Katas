package ua.kata;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MinTriangleSumTest {
  @Test
  void oneLevelTriangle() {
    assertThat(new MinTriangleSum(List.of(List.of(1))).compute()).isEqualTo(1);
  }

  @Test
  void twoLevelsTriangle_withEqualSides() {
    assertThat(new MinTriangleSum(List.of(List.of(1), List.of(2, 2))).compute()).isEqualTo(3);
  }

  @Test
  void twoLevelsTriangle_withNotEqualSides() {
    assertThat(new MinTriangleSum(List.of(List.of(1), List.of(3, 2))).compute()).isEqualTo(3);
  }

  @Test
  void threeLevelsTriangle() {
    assertThat(new MinTriangleSum(List.of(List.of(1), List.of(2, 3), List.of(4, 5, 6))).compute()).isEqualTo(7);
  }

  @Test
  void threeLevelsTriangle_withNegativeValue() {
    assertThat(
        new MinTriangleSum(
            List.of(
                List.of(1),
                List.of(2, -3),
                List.of(4, 5, 6)
            )
        ).compute()
    ).isEqualTo(3);
  }

  @Test
  void fourLevelsTriangle() {
    assertThat(
        new MinTriangleSum(
            List.of(
                List.of(1),
                List.of(2, 3),
                List.of(4, 5, 6),
                List.of(7, 8, 9, 10)
            )
        ).compute()
    ).isEqualTo(14);
  }
}
