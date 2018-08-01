package ua.kata;

import java.util.stream.IntStream;

import io.reactivex.Observable;

public class BowlingGame {
  private int[] rolls = new int[21];
  private int current = 0;

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public int score() {
    Integer[] frameIndexes = IntStream.iterate(0, this::nextFrameIndex)
      .limit(10)
      .boxed()
      .toArray(Integer[]::new);
    return Observable.fromArray(frameIndexes)
      .map(this::computeFramePoint)
      .reduce(0, (score, framePoint) -> score + framePoint)
      .blockingGet();
  }

  private Integer computeFramePoint(Integer frameIndex) {
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
