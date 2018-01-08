package ua.kata;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.Single;

public class BowlingGame {
  private static final int NUMBER_OF_FRAMES = 10;

  private int[] rolls = new int[20];
  private int current;

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public Single<Integer> score() {
    return Single.defer(
      () -> Observable.fromIterable(frameIndexes())
        .map(this::computeFramePoints)
        .reduce(0, (gameScore, framePoints) -> gameScore + framePoints)
    );
  }

  private Iterable<Integer> frameIndexes() {
    return IntStream.iterate(0, this::nextFrameIndex).limit(NUMBER_OF_FRAMES).boxed().collect(Collectors.toList());
  }

  private int computeFramePoints(int frameIndex) {
    if (isStrike(frameIndex)) {
      return 10 + strikeBonus(frameIndex);
    } else if (isSpare(frameIndex)) {
      return 10 + spareBonus(frameIndex);
    } else {
      return rolls[frameIndex] + rolls[frameIndex + 1];
    }
  }

  private int nextFrameIndex(int frameIndex) {
    return isStrike(frameIndex) ? frameIndex + 1 : frameIndex + 2;
  }

  private int strikeBonus(int frameIndex) {
    return rolls[frameIndex + 1] + rolls[frameIndex + 2];
  }

  private boolean isStrike(int frameIndex) {
    return rolls[frameIndex] == 10;
  }

  private boolean isSpare(int frameIndex) {
    return rolls[frameIndex] + rolls[frameIndex + 1] == 10;
  }

  private int spareBonus(int frameIndex) {
    return rolls[frameIndex + 2];
  }
}
