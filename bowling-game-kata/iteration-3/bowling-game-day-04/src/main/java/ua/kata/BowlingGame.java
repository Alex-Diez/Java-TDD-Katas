package ua.kata;

import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BowlingGame {
  private Flux<Integer> rolls = Flux.empty();

  public void roll(Mono<Integer> pins) {
    rolls = rolls.concatWith(pins);
  }

  public Mono<Integer> score() {
    return Flux.fromStream(
        Stream.iterate(0, index -> {
          if (rolls.elementAt(index).block() == 10) {
            return index + 1;
          } else {
            return index + 2;
          }
        })
    )
        .take(10)
        .flatMap(index ->
                     rolls.elementAt(index)
                         .flatMap(val -> {
                                    if (val == 10) {
                                      return Mono.just(10)
                                          .concatWith(rolls.elementAt(index + 1))
                                          .concatWith(rolls.elementAt(index + 2))
                                          .reduce(Integer::sum);
                                    } else {
                                      return Mono.just(val)
                                          .concatWith(rolls.elementAt(index + 1))
                                          .reduce(Integer::sum)
                                          .flatMap(value -> {
                                            if (value == 10) {
                                              return Mono.just(10)
                                                  .concatWith(rolls.elementAt(index + 2))
                                                  .reduce(Integer::sum);
                                            } else {
                                              return Mono.just(value);
                                            }
                                          });
                                    }
                                  }
                         )
        )
        .reduce(Integer::sum);
  }
}
