package ua.kata;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.assertj.core.api.AbstractAssert;

import java.util.stream.IntStream;

import static ua.kata.BowlingGameTest.BowlingGameAssert.assertThat;

class BowlingGameTest {
  private BowlingGame game;

  @BeforeEach
  void setUp() {
    game = new BowlingGame();
  }

  private void rollMany(int pin, int times) {
    IntStream.generate(() -> pin).limit(times).forEach(game::roll);
  }

  private void rollSpare() {
    game.roll(4);
    game.roll(6);
  }

  private void rollStrike() {
    game.roll(10);
  }

  @Test
  void gutterGame() {
    rollMany(0, 20);

    assertThat(game).hasScore(0);
  }

  @Test
  void allOnes() throws Exception {
    rollMany(1, 20);

    assertThat(game).hasScore(20);
  }

  @Test
  void oneSpare() throws Exception {
    rollSpare();
    game.roll(3);
    rollMany(0, 17);

    assertThat(game).hasScore(16);
  }

  @Test
  void oneStrike() throws Exception {
    rollStrike();
    game.roll(3);
    game.roll(4);
    rollMany(0, 16);

    assertThat(game).hasScore(24);
  }

  @Test
  void perfectGame() throws Exception {
    rollMany(10, 12);

    assertThat(game).hasScore(300);
  }

  public static class BowlingGameAssert extends AbstractAssert<BowlingGameAssert, BowlingGame> {
    public BowlingGameAssert(BowlingGame actual) {
      super(actual, BowlingGameAssert.class);
    }

    public static BowlingGameAssert assertThat(BowlingGame actual) {
      return new BowlingGameAssert(actual);
    }

    public BowlingGameAssert hasScore(int expected) {
      if (actual.score() != expected) {
        failWithMessage("Expected score to be <%d> but was <%d>", expected, actual.score());
      }
      return this;
    }
  }
}
