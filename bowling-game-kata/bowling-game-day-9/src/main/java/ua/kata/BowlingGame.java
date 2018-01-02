package ua.kata;

import java.util.stream.IntStream;

import io.reactivex.Observable;

public class BowlingGame {
  private static final int NUMBER_OF_FRAMES = 10;
  private int[] rolls = new int[21];
  private int current;

  public void roll(int pin) {
    rolls[current++] += pin;
  }

  public int score() {
    return Observable.fromArray(
      IntStream.iterate(0, this::generateNextFrameIndex)
        .limit(NUMBER_OF_FRAMES)
        .boxed()
        .toArray(Integer[]::new)
    ).map(this::computeFramePoints)
      .reduce(0, (acc, item) -> acc + item)
      .blockingGet();
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

  private int generateNextFrameIndex(int frameIndex) {
    if (isStrike(frameIndex)) return frameIndex + 1;
    else return frameIndex + 2;
  }

  private boolean isStrike(int frameIndex) {
    return rolls[frameIndex] == 10;
  }

  private int strikeBonus(int frameIndex) {
    return rolls[frameIndex + 1] + rolls[frameIndex + 2];
  }

  private boolean isSpare(int frameIndex) {
    return rolls[frameIndex] + rolls[frameIndex + 1] == 10;
  }

  private int spareBonus(int frameIndex) {
    return rolls[frameIndex + 2];
  }
}
