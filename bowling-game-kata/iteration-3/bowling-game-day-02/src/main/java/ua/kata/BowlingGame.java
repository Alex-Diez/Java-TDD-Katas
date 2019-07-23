package ua.kata;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

public class BowlingGame {
  private Flux<Integer> rolls = Flux.empty();

  public void roll(Mono<Integer> pins) {
    rolls = rolls.concatWith(pins);
  }

  public Mono<Integer> score() {
    return rolls.zipWith(rolls)
        .reduce(
            Tuples.of(0, 0),
            (roll, acc) -> Tuples.of(
                roll.getT1() + roll.getT2() + acc.getT1(),
                0
            )
        ).map(t -> t.getT1() + t.getT2());
  }
}
