package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class BowlingGameTest {

  private BowlingGame game;

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
  void oneSpare() {
    rollSpare();
    game.roll(Mono.just(3));
    rollMany(17, 0);

    game.score().as(StepVerifier::create).expectNext(10 + 3 + 3).verifyComplete();
  }

  @Test
  void oneStrike() {
    rollStrike();
    game.roll(Mono.just(4));
    game.roll(Mono.just(3));
    rollMany(16, 0);

    game.score().as(StepVerifier::create).expectNext(10 + 4 + 3 + 7).verifyComplete();
  }

  @Test
  void perfectGame() {
    rollMany(12, 10);

    game.score().as(StepVerifier::create).expectNext(300).verifyComplete();
  }

  private void rollStrike() {
    game.roll(Mono.just(10));
  }

  private void rollSpare() {
    game.roll(Mono.just(4));
    game.roll(Mono.just(6));
  }

  private void rollMany(int times, int pins) {
    Flux.range(0, times).subscribe(__ -> game.roll(Mono.just(pins)));
  }
}
