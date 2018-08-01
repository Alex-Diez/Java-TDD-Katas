package ua.kata;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.Single;

public class BowlingGame {
  private int[] rolls = new int[21];
  private int current;

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public Single<Integer> score() {
    return Single.defer(
      () ->
        Observable.fromIterable(
          IntStream.iterate(0, this::nextFrameIndex)
            .limit(10)
            .boxed()
            .collect(Collectors.toList())
        )
          .map(this::computeFramePoints)
          .reduce(0, (gameScore, framePoints) -> gameScore + framePoints)
    );
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
