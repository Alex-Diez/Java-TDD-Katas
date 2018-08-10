package ua.kata;

import java.util.stream.IntStream;

public class BowlingGame {
  private int[] rolls = new int[20];
  private int current;

  public void roll(int pins) {
    rolls[current++] = pins;
  }

  public int score() {
    return IntStream.iterate(0, this::nextFrame)
        .limit(10)
        .map(this::frameScore)
        .sum();
  }

  private int nextFrame(int frame) {
    if (isStrike(frame)) return frame + 1;
    else return frame + 2;
  }

  private int frameScore(int roll) {
    if (isStrike(roll)) {
      return 10 + strikeBonus(roll);
    } else if (isSpare(roll)) {
      return 10 + spareBonus(roll);
    } else {
      return rolls[roll] + rolls[roll + 1];
    }
  }

  private boolean isStrike(int roll) {
    return rolls[roll] == 10;
  }

  private int strikeBonus(int roll) {
    return rolls[roll + 1] + rolls[roll + 2];
  }

  private boolean isSpare(int roll) {
    return rolls[roll] + rolls[roll + 1] == 10;
  }

  private int spareBonus(int roll) {
    return rolls[roll + 2];
  }
}
