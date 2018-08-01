package ua.katas;

public class BowlingGame {
  private final int[] rolls;
  private int current;

  public BowlingGame() {
    rolls = new int[21];
    current = 0;
  }

  public void roll(int pin) {
    this.rolls[current++] = pin;
  }

  public int score() {
    int score = 0;
    int frameIndex = 0;

    for (int _$ = 0; _$ < 10; _$++) {
      if (isStrike(frameIndex)) {
        score += 10 + strikeBonus(frameIndex);
        frameIndex += 1;
      } else if (isSpare(frameIndex)) {
        score += 10 + spareBonus(frameIndex);
        frameIndex += 2;
      } else {
        score += rolls[frameIndex] + rolls[frameIndex + 1];
        frameIndex += 2;
      }
    }

    return score;
  }

  private int spareBonus(int frameIndex) {
    return rolls[frameIndex + 2];
  }

  private boolean isSpare(int frameIndex) {
    return rolls[frameIndex] + rolls[frameIndex + 1] == 10;
  }

  private boolean isStrike(int frameIndex) {
    return rolls[frameIndex] == 10;
  }

  private int strikeBonus(int frameIndex) {
    return rolls[frameIndex + 1] + rolls[frameIndex + 2];
  }
}
