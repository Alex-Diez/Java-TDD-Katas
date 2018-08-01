package ua.katas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

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

    assertThat(game.score()).isEqualTo(0);
  }

  @Test
  void allOnes() throws Exception {
    rollMany(20, 1);

    assertThat(game.score()).isEqualTo(20);
  }

  @Test
  void oneSpare() throws Exception {
    rollSpare();
    game.roll(3);
    rollMany(17, 0);

    assertThat(game.score()).isEqualTo(16);
  }

  @Test
  void oneStrike() throws Exception {
    rollStrike();
    game.roll(4);
    game.roll(3);
    rollMany(16, 0);

    assertThat(game.score()).isEqualTo(24);
  }

  @Test
  void perfectGame() throws Exception {
    rollMany(13, 10);

    assertThat(game.score()).isEqualTo(300);
  }
}
