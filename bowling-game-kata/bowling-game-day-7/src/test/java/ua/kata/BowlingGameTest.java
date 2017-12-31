package ua.kata;

import org.assertj.core.api.AbstractAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static ua.kata.BowlingGameTest.BowlingGameAssertions.assertThat;

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
    rollMany(12, 10);

    assertThat(game).hasScore(300);
  }

  public static class BowlingGameAssertions extends AbstractAssert<BowlingGameAssertions, BowlingGame> {
    public BowlingGameAssertions(BowlingGame game) {
      super(game, BowlingGameAssertions.class);
    }

    public static BowlingGameAssertions assertThat(BowlingGame game) {
      return new BowlingGameAssertions(game);
    }

    public BowlingGameAssertions hasScore(int expected) {
      isNotNull();

      int actual = this.actual.score();
      if (actual != expected) {
        failWithMessage("%nExpected that game has score: <%d>%nBut had:                      <%d>%n", expected, actual);
      }

      return this;
    }
  }
}
