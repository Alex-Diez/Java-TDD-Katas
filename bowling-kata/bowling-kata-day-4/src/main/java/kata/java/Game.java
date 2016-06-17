package kata.java;

public class Game {
    private final int[] rolls = new int[20];
    private int size;

    public void roll(int pins) {
        rolls[size] = pins;
        size += 1;
    }

    public int score() {
        int score = 0;
        int frameIndex = 0;
        for (int frame = 0; frame < 10; frame++) {
            if (isStrike(frameIndex)) {
                score += strikePoints(frameIndex);
                frameIndex += 1;
            }
            else if (isSpare(frameIndex)) {
                score += sparePoints(frameIndex);
                frameIndex += 2;
            }
            else {
                score += framePoints(frameIndex);
                frameIndex += 2;
            }
        }
        return score;
    }

    private int framePoints(int frameIndex) {
        return rolls[frameIndex] + rolls[frameIndex + 1];
    }

    private int sparePoints(int frameIndex) {
        return 10 + rolls[frameIndex + 2];
    }

    private int strikePoints(int frameIndex) {
        return 10 + rolls[frameIndex + 1] + rolls[frameIndex + 2];
    }

    private boolean isSpare(int frameIndex) {
        return framePoints(frameIndex) == 10;
    }

    private boolean isStrike(int frameIndex) {
        return rolls[frameIndex] == 10;
    }
}
