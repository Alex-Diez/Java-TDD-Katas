package ua.kata;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class NestedJoinsTest {

  private NestedJoins<Integer, String> nestedJoins;

  @BeforeEach
  void setUp() {
    nestedJoins = new NestedJoins<>();
  }

  @Test
  void resultSetIsEmpty_whenJoinLeftTwoEmptySets() throws Exception {
    nestedJoins.joinLeft(Flux.empty(), Flux.empty())
        .as(StepVerifier::create)
        .verifyComplete();
  }

  @Test
  void resultSetHasNullsOnRight_whenJoinLeftNonemptyAndEmptySets() throws Exception {
    nestedJoins.joinLeft(
        Flux.<Pair<Integer, String>>create(
            emitter -> {
              emitter.next(new ImmutablePair<>(1, "l1"));
              emitter.next(new ImmutablePair<>(2, "l2"));
              emitter.next(new ImmutablePair<>(3, "l3"));
              emitter.complete();
            }
        ),
        Flux.empty()
    ).as(StepVerifier::create)
        .expectNext(new ImmutableTriple<>(1, "l1", null))
        .expectNext(new ImmutableTriple<>(2, "l2", null))
        .expectNext(new ImmutableTriple<>(3, "l3", null))
        .verifyComplete();
  }

  @Test
  void resultSetHasValuesOnRight_forCorrespondingKeys_whenJoinLeftTwoNonemptySets() throws Exception {
    nestedJoins.joinLeft(
        Flux.<Pair<Integer, String>>create(
            emitter -> {
              emitter.next(new ImmutablePair<>(1, "l1"));
              emitter.next(new ImmutablePair<>(2, "l2"));
              emitter.next(new ImmutablePair<>(3, "l3"));
              emitter.complete();
            }
        ),
        Flux.<Pair<Integer, String>>create(
            emitter -> {
              emitter.next(new ImmutablePair<>(1, "r1"));
              emitter.next(new ImmutablePair<>(3, "r3"));
              emitter.complete();
            }
        )
    ).as(StepVerifier::create)
        .expectNext(new ImmutableTriple<>(1, "l1", "r1"))
        .expectNext(new ImmutableTriple<>(2, "l2", null))
        .expectNext(new ImmutableTriple<>(3, "l3", "r3"))
        .verifyComplete();
  }

  @Test
  void resultSetIsEmpty_whenJoinRightTwoEmptySets() throws Exception {
    nestedJoins.joinRight(Flux.empty(), Flux.empty())
        .as(StepVerifier::create)
        .verifyComplete();
  }

  @Test
  void resultSetHasNullsOnLeft_whenJoinRightEmptyAndNonemptySets() throws Exception {
    nestedJoins.joinRight(
        Flux.empty(),
        Flux.create(
            emitter -> {
              emitter.next(new ImmutablePair<>(1, "r1"));
              emitter.next(new ImmutablePair<>(2, "r2"));
              emitter.next(new ImmutablePair<>(3, "r3"));
              emitter.complete();
            }
        )
    ).as(StepVerifier::create)
        .expectNext(new ImmutableTriple<>(1, null, "r1"))
        .expectNext(new ImmutableTriple<>(2, null, "r2"))
        .expectNext(new ImmutableTriple<>(3, null, "r3"))
        .verifyComplete();
  }

  @Test
  void resultSetHasValuesOnLeft_forCorrespondingKeys_whenJoinRightTwoNonemptySets() throws Exception {
    nestedJoins.joinRight(
        Flux.create(
            emitter -> {
              emitter.next(new ImmutablePair<>(1, "l1"));
              emitter.next(new ImmutablePair<>(3, "l3"));
              emitter.complete();
            }
        ),
        Flux.create(
            emitter -> {
              emitter.next(new ImmutablePair<>(1, "r1"));
              emitter.next(new ImmutablePair<>(2, "r2"));
              emitter.next(new ImmutablePair<>(3, "r3"));
              emitter.complete();
            }
        )
    ).as(StepVerifier::create)
        .expectNext(new ImmutableTriple<>(1, "l1", "r1"))
        .expectNext(new ImmutableTriple<>(2, null, "r2"))
        .expectNext(new ImmutableTriple<>(3, "l3", "r3"))
        .verifyComplete();
  }
}
