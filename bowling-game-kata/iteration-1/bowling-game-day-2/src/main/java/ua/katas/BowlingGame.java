package ua.katas;

import java.util.stream.IntStream;

public class BowlingGame {
  private final int[] rolls;
  private int current;

  public BowlingGame() {
    rolls = new int[21];
  }

  public void roll(int pin) {
    this.rolls[current++] = pin;
  }

  public int score() {
    return IntStream.iterate(0, this::nextFrameIndex)
      .limit(10)
      .map(this::computeFramePoints)
      .reduce(0, (acc, framePoint) -> acc + framePoint);
  }

  private int nextFrameIndex(int prevRoll) {
    if (isStrike(prevRoll)) return prevRoll + 1;
    else return prevRoll + 2;
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
