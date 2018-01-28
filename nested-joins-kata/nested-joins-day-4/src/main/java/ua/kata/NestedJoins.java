package ua.kata;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import reactor.core.publisher.Flux;

public class NestedJoins<K extends Comparable<K>, LV, RV> {
  public Flux<Triple<K, LV, RV>> joinLeft(
      Flux<Pair<K, LV>> left,
      Flux<Pair<K, RV>> right) {
    return left.flatMap(leftPair -> right.filter(rightPair -> leftPair.getKey().compareTo(rightPair.getKey()) == 0)
        .defaultIfEmpty(new ImmutablePair<>(leftPair.getKey(), null))
        .map(rightPair -> new ImmutableTriple<>(leftPair.getKey(), leftPair.getValue(), rightPair.getValue())));
  }

  public Flux<Triple<K, LV, RV>> joinRight(
      Flux<Pair<K, LV>> left,
      Flux<Pair<K, RV>> right) {
    return right.flatMap(rightPair -> left.filter(leftPair -> rightPair.getKey().compareTo(leftPair.getKey()) == 0)
        .defaultIfEmpty(new ImmutablePair<>(rightPair.getKey(), null))
        .map(leftPair -> new ImmutableTriple<>(rightPair.getKey(), leftPair.getValue(), rightPair.getValue())));
  }
}
