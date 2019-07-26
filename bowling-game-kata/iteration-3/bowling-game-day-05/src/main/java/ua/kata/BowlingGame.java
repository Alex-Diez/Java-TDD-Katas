package ua.kata;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BowlingGame {
  private Flux<Integer> rolls = Flux.empty();

  public void roll(Mono<Integer> pins) {
    rolls = rolls.concatWith(pins);
  }

  public Mono<Integer> score() {
    return Flux.fromStream(IntStream.iterate(0, this::frameIndex).boxed())
        .take(10)
        .flatMap(this::frameScore)
        .reduce(Integer::sum);
  }

  private int frameIndex(int i) {
    if (isStrike(i)) return i + 1;
    else return i + 2;
  }

  private Mono<Integer> frameScore(Integer index) {
    if (isStrike(index)) {
      return Mono.just(10)
          .concatWith(rolls.elementAt(index + 1))
          .concatWith(rolls.elementAt(index + 2))
          .reduce(Integer::sum);
    } else if (isSpare(index)) {
      return Mono.just(10).concatWith(rolls.elementAt(index + 2)).reduce(Integer::sum);
    } else {
      return rolls.elementAt(index).concatWith(rolls.elementAt(index + 1)).reduce(Integer::sum);
    }
  }

  private boolean isSpare(int index) {
    return rolls.elementAt(index)
        .concatWith(rolls.elementAt(index + 1))
        .reduce(Integer::sum)
        .block() == 10;
  }

  private boolean isStrike(int index) {
    return rolls.elementAt(index).block() == 10;
  }
}
