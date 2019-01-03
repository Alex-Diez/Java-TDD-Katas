package ua.kata;

import org.junit.jupiter.api.Test;

import java.util.List;

import reactor.test.StepVerifier;

class MinTriangleSumTest {
  @Test
  void oneLevelTriangle() {
    new MinTriangleSum(List.of(List.of(1)))
        .compute()
        .as(StepVerifier::create)
        .expectNext(1)
        .verifyComplete();
  }

  @Test
  void twoLevelsTriangle_withEqualSides() {
    new MinTriangleSum(List.of(List.of(1), List.of(2, 2))).compute()
        .as(StepVerifier::create)
        .expectNext(3)
        .verifyComplete();
  }

  @Test
  void twoLevelsTriangle_withNotEqualSides() {
    new MinTriangleSum(List.of(List.of(1), List.of(3, 2))).compute()
        .as(StepVerifier::create)
        .expectNext(3)
        .verifyComplete();
  }

  @Test
  void threeLevelsTriangle() {
    new MinTriangleSum(
        List.of(
            List.of(1),
            List.of(2, 3),
            List.of(4, 5, 6)
        )
    ).compute()
        .as(StepVerifier::create)
        .expectNext(7)
        .verifyComplete();
  }

  @Test
  void treeLevelsTriangle_negativeValue() {
    new MinTriangleSum(
        List.of(
            List.of(1),
            List.of(2, -3),
            List.of(4, 5, 6)
        )
    ).compute()
        .as(StepVerifier::create)
        .expectNext(3)
        .verifyComplete();
  }

  @Test
  void fourLevelsTriangle() {
    new MinTriangleSum(
        List.of(
            List.of(1),
            List.of(3, 2),
            List.of(6, 5, 4),
            List.of(10, 9, 8, 7)
        )
    ).compute()
        .as(StepVerifier::create)
        .expectNext(14)
        .verifyComplete();
  }
}
