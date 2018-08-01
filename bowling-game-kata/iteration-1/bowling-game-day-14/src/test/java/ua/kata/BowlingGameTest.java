package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameTest {

  private Single<Integer> score;
  private Consumer<Integer> rollConsumer;

  @BeforeEach
  void setUp() {
    BowlingGame game = new BowlingGame();
    score = game.score();
    rollConsumer = game::roll;
  }

  private Observable<Integer> rollMany(int times, int pin) {
    return Observable.fromIterable(
      IntStream.generate(() -> pin)
        .limit(times)
        .boxed()
        .collect(Collectors.toList())
    );
  }

  private Observable<Integer> rollSpare() {
    return Observable.just(4, 6);
  }

  private Observable<Integer> roll(Iterable<Integer> pins) {
    return Observable.fromIterable(pins);
  }

  private Observable<Integer> rollStrike() {
    return Observable.just(10);
  }

  @Test
  void gutterGame() throws Exception {
    rollMany(20, 0).subscribe(rollConsumer);

    assertThat(score.blockingGet()).isEqualTo(0);
  }

  @Test
  void allOnes() throws Exception {
    rollMany(20, 1).subscribe(rollConsumer);

    assertThat(score.blockingGet()).isEqualTo(20);
  }

  @Test
  void oneSpare() throws Exception {
    rollSpare().concatWith(roll(List.of(3))).concatWith(rollMany(17, 0)).subscribe(rollConsumer);

    assertThat(score.blockingGet()).isEqualTo(16);
  }

  @Test
  void oneStrike() throws Exception {
    rollStrike().concatWith(roll(List.of(4, 3))).concatWith(rollMany(16, 0)).subscribe(rollConsumer);

    assertThat(score.blockingGet()).isEqualTo(24);
  }

  @Test
  void perfectGame() throws Exception {
    rollMany(12, 10).subscribe(rollConsumer);

    assertThat(score.blockingGet()).isEqualTo(300);
  }
}
