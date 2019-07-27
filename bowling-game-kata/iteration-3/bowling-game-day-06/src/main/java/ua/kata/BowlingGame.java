package ua.kata;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BowlingGame {
  private Flux<Integer> rolls = Flux.empty();

  public void roll(Mono<Integer> roll) {
    rolls = rolls.concatWith(roll);
  }

  public Mono<Integer> score() {
    AtomicInteger counter = new AtomicInteger(0);
    return Flux.range(0, 10)
        .map(frame -> counter.getAndAccumulate(nextFrameIncrement(frame), Integer::sum))
        .flatMap(index -> {
          final int block = rolls.elementAt(index).block();
          if (block == 10) {
            return rolls.elementAt(index)
                .flux()
                .transform(strikeBonus(index))
                .reduce(Integer::sum);
          } else {
            final Mono<Integer> frame = frame(index).reduce(Integer::sum);
            if (frame.block() == 10) {
              return Flux.just(10)
                  .transform(spareBonus(index))
                  .reduce(Integer::sum);
            } else {
              return frame;
            }
          }
        })
        .reduce(Integer::sum);
  }

  private int nextFrameIncrement(int i) {
    return rolls.elementAt(i)
        .filter(value -> value == 10)
        .map(__ -> 1)
        .defaultIfEmpty(2)
        .block();
  }

  private Flux<Integer> frame(int index) {
    return rolls.elementAt(index)
        .concatWith(rolls.elementAt(index + 1));
  }

  private Function<Flux<Integer>, Flux<Integer>> spareBonus(int index) {
    return (frame) -> frame.concatWith(rolls.elementAt(index + 2));
  }

  private Function<Flux<Integer>, Flux<Integer>> strikeBonus(int index) {
    return (frame) ->
        frame.concatWith(rolls.elementAt(index + 1))
            .concatWith(rolls.elementAt(index + 2));
  }
}
