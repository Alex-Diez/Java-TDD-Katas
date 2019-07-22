package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class BowlingGameTest {
  private BowlingGame game;

  private void rollMany(int times, int pins) {
    for (int i = 0; i < times; i++) {
      game.roll(Mono.just(pins));
    }
  }

  @BeforeEach
  void setUp() {
    game = new BowlingGame();
  }

  @Test
  void gutterGame() {
    rollMany(20, 0);

    game.score().as(StepVerifier::create).expectNext(0).verifyComplete();
  }

  @Test
  void allOnes() {
    rollMany(20, 1);

    game.score().as(StepVerifier::create).expectNext(20).verifyComplete();
  }

  @Test
  @Disabled
  void oneSpare() {
    game.roll(Mono.just(4));
    game.roll(Mono.just(6));
    game.roll(Mono.just(3));
    rollMany(17, 0);

    game.score().as(StepVerifier::create).expectNext(10 + 3 + 3).verifyComplete();
  }
}
