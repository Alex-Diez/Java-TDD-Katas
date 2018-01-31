package ua.kata;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import reactor.core.publisher.Flux;

public class NestedJoins<K extends Comparable<K>, V> {
  public Flux<Triple<K, V, V>> joinLeft(Flux<Pair<K, V>> left, Flux<Pair<K, V>> right) {
    return nestedJoin(left, right, (p, s) -> new ImmutableTriple<>(p.getKey(), p.getValue(), s.getValue()));
  }

  public Flux<Triple<K, V, V>> joinRight(Flux<Pair<K, V>> left, Flux<Pair<K, V>> right) {
    return nestedJoin(right, left, (p, s) -> new ImmutableTriple<>(p.getKey(), s.getValue(), p.getValue()));
  }

  private Flux<Triple<K, V, V>> nestedJoin(
      Flux<Pair<K, V>> primary,
      Flux<Pair<K, V>> secondary,
      BiFunction<Pair<K, V>, Pair<K, V>, Triple<K, V, V>> pairComposition) {
    return primary.flatMap(
        primaryPair -> secondary.filter(byKeyEqualityWith(primaryPair))
            .defaultIfEmpty(withNullifiedValue(primaryPair))
            .map(composeWith(primaryPair, pairComposition))
    );
  }

  private Function<Pair<K, V>, Triple<K, V, V>> composeWith(
      Pair<K, V> primaryPair,
      BiFunction<Pair<K, V>, Pair<K, V>, Triple<K, V, V>> pairComposition) {
    return secondaryPair -> pairComposition.apply(primaryPair, secondaryPair);
  }

  private Predicate<Pair<K, V>> byKeyEqualityWith(Pair<K, V> primaryPair) {
    return secondaryPair -> primaryPair.getKey().compareTo(secondaryPair.getKey()) == 0;
  }

  private Pair<K, V> withNullifiedValue(Pair<K, V> primaryPair) {
    return new ImmutablePair<>(primaryPair.getKey(), null);
  }
}
