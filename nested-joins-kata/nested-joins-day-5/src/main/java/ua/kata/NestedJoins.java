package ua.kata;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.function.BiFunction;

import reactor.core.publisher.Flux;

public class NestedJoins<K extends Comparable<K>, V> {
  public Flux<Triple<K, V, V>> joinLeft(
      Flux<Pair<K, V>> left,
      Flux<Pair<K, V>> right) {
    return nestedJoin(left, right, (p, s) -> new ImmutableTriple<>(p.getKey(), p.getValue(), s.getValue()));
  }

  public Flux<Triple<K, V, V>> joinRight(
      Flux<Pair<K, V>> left,
      Flux<Pair<K, V>> right) {
    return nestedJoin(right, left, (p, s) -> new ImmutableTriple<>(p.getKey(), s.getValue(), p.getValue()));
  }

  private Flux<Triple<K, V, V>> nestedJoin(
      Flux<Pair<K, V>> primary,
      Flux<Pair<K, V>> secondary,
      BiFunction<Pair<K, V>, Pair<K, V>, Triple<K, V, V>> compose) {
    return primary.flatMap(primaryPair -> secondary.filter(secondaryPair -> equalsByKey(primaryPair, secondaryPair))
        .defaultIfEmpty(withNullableValue(primaryPair))
        .map(secondaryPair -> compose.apply(primaryPair, secondaryPair)));
  }

  private Pair<K, V> withNullableValue(Pair<K, V> primaryPair) {
    return new ImmutablePair<>(primaryPair.getKey(), null);
  }

  private boolean equalsByKey(Pair<K, V> primary, Pair<K, V> secondary) {
    return primary.getKey().compareTo(secondary.getKey()) == 0;
  }
}
