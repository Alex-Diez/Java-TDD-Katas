package ua.katas;

import java.util.stream.IntStream;

public class BowlingGame {
  private int[] rolls = new int[21];
  private int current;

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public int score() {
    return IntStream.iterate(0, this::nextFrameIndex)
      .limit(10)
      .map(this::framePoints)
      .sum();
  }

  private int framePoints(int frameIndex) {
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

  private int spareBonus(int frameIndex) {
    return rolls[frameIndex + 2];
  }

  private boolean isSpare(int frameIndex) {
    return rolls[frameIndex] + rolls[frameIndex + 1] == 10;
  }

  private int strikeBonus(int frameIndex) {
    return rolls[frameIndex + 1] + rolls[frameIndex + 2];
  }

  private boolean isStrike(int frameIndex) {
    return rolls[frameIndex] == 10;
  }
}
