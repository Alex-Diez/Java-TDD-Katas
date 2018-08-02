package ua.kata;

import java.util.stream.IntStream;

public class BowlingGame {
  private int[] rolls = new int[20];
  private int current = 0;

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public int score() {
    return IntStream.iterate(0, this::nextFrameIndex)
        .limit(10)
        .map(this::currentFrameScore)
        .sum();
  }

  private int currentFrameScore(int frameIndex) {
    if (isStrike(frameIndex)) return 10 + strikeBonus(frameIndex);
    if (isSpare(frameIndex)) return 10 + spareBonus(frameIndex);
    return frameScore(frameIndex);
  }

  private int nextFrameIndex(int frameIndex) {
    if (isStrike(frameIndex)) return frameIndex + 1;
    else return frameIndex + 2;
  }

  private int spareBonus(int roll) {
    return rolls[roll + 2];
  }

  private boolean isSpare(int roll) {
    return rolls[roll] + rolls[roll + 1] == 10;
  }

  private int strikeBonus(int roll) {
    return rolls[roll + 1] + rolls[roll + 2];
  }

  private boolean isStrike(int roll) {
    return rolls[roll] == 10;
  }

  private int frameScore(int roll) {
    return rolls[roll] + rolls[roll + 1];
  }
}
