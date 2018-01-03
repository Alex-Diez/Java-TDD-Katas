package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.subscribers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameTest {

  private BowlingGame game;

  @BeforeEach
  void setUp() {
    game = new BowlingGame();
  }

  private Observable<Integer> rollMany(int times, int pin) {
    return Observable.fromArray(IntStream.generate(() -> pin).limit(times).boxed().toArray(Integer[]::new));
  }

  private Observable<Integer> rollSpare() {
    return Observable.just(4, 6);
  }

  private Observable<Integer> rollStrike() {
    return Observable.just(10);
  }

  @Test
  void gutterGame() throws Exception {
    rollMany(20, 0).blockingSubscribe(game::roll);

    assertThat(game.score()).isEqualTo(0);
  }

  @Test
  void allOnes() throws Exception {
    rollMany(20, 1).blockingSubscribe(game::roll);

    assertThat(game.score()).isEqualTo(20);
  }

  @Test
  void oneSpare() throws Exception {
    rollSpare().concatWith(Observable.just(3)).concatWith(rollMany(17, 0)).blockingSubscribe(game::roll);

    assertThat(game.score()).isEqualTo(16);
  }

  @Test
  void oneStrike() throws Exception {
    rollStrike().concatWith(Observable.just(3, 4)).concatWith(rollMany(16, 0)).blockingSubscribe(game::roll);

    assertThat(game.score()).isEqualTo(24);
  }

  @Test
  void perfectGame() throws Exception {
    rollMany(12, 10).blockingSubscribe(game::roll);

    assertThat(game.score()).isEqualTo(300);
  }
}
