package ua.kata;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BowlingGame {
  private Flux<Integer> rolls = Flux.empty();

  public void roll(Mono<Integer> pins) {
    rolls = rolls.concatWith(pins);
  }

  public Mono<Integer> score() {
    return rolls.reduce(0, Integer::sum);
  }
}
