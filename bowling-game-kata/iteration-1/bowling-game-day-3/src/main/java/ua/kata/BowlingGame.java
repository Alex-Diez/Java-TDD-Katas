package ua.kata;

public class BowlingGame {
  private final int[] rolls = new int[21];
  private int current;

  public void roll(int pin) {
    rolls[current++] = pin;
  }

  public int score() {
    int score = 0;
    int frameIndex = 0;

    for (int _$ = 0; _$ < 10; _$++) {
      if (rolls[frameIndex] == 10) {
        score += 10 + rolls[frameIndex + 1] + rolls[frameIndex + 2];
        frameIndex += 1;
      } else if (rolls[frameIndex] + rolls[frameIndex + 1] == 10) {
        score += 10 + rolls[frameIndex + 2];
        frameIndex += 2;
      } else {
        score += rolls[frameIndex] + rolls[frameIndex + 1];
        frameIndex += 2;
      }
    }

    return score;
  }
}
