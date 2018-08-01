package ua.kata;

import org.assertj.core.api.AbstractAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static ua.kata.BowlingGameTest.BowlingGameAssertions.*;

class BowlingGameTest {

  private BowlingGame game;

  @BeforeEach
  void setUp() {
    game = new BowlingGame();
  }

  private void rollMany(int times, int pin) {
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
  void gutterGame() throws Exception {
    rollMany(20, 0);

    assertThat(game).hasScore(0);
  }

  @Test
  void allOnes() throws Exception {
    rollMany(20, 1);

    assertThat(game).hasScore(20);
  }

  @Test
  void oneSpare() throws Exception {
    rollSpare();
    game.roll(3);
    rollMany(17, 0);

    assertThat(game).hasScore(16);
  }

  @Test
  void oneStrike() throws Exception {
    rollStrike();
    game.roll(4);
    game.roll(3);
    rollMany(16, 0);

    assertThat(game).hasScore(24);
  }

  @Test
  void perfectGame() throws Exception {
    rollMany(13, 10);

    assertThat(game).hasScore(300);
  }

  public static class BowlingGameAssertions extends AbstractAssert<BowlingGameAssertions, BowlingGame> {

    public BowlingGameAssertions(BowlingGame bowlingGame) {
      super(bowlingGame, BowlingGameAssertions.class);
    }

    public static BowlingGameAssertions assertThat(BowlingGame actual) {
      return new BowlingGameAssertions(actual);
    }

    public BowlingGameAssertions hasScore(int expected) {
      isNotNull();

      int actual = this.actual.score();
      if (expected != actual) {
        failWithMessage("%nExpected:%nThat game has <%d> score%nBut has       <%d>", expected, actual);
      }

      return this;
    }
  }

}
