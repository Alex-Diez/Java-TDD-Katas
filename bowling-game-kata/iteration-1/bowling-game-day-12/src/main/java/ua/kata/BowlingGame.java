package ua.kata;

import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.Single;

public class BowlingGame {
  private static final int FRAME_NUMBER = 10;
  private int[] rolls = new int[21];
  private int current;

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public Single<Integer> score() {
    return frameIndexes().map(this::computeFramePoints).reduce(0, (acc, framePoints) -> acc + framePoints);
  }

  private Observable<Integer> frameIndexes() {
    return Observable.fromArray(
      IntStream.iterate(0, this::generateNextFrameIndex)
        .limit(FRAME_NUMBER)
        .boxed()
        .toArray(Integer[]::new)
    );
  }

  private Integer computeFramePoints(Integer frameIndex) {
    if (isStrike(frameIndex)) {
      return  10 + strikeBonus(frameIndex);
    } else if (isSpare(frameIndex)) {
      return 10 + spareBonus(frameIndex);
    } else {
      return rolls[frameIndex] + rolls[frameIndex + 1];
    }
  }

  private int generateNextFrameIndex(int frameIndex) {
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
