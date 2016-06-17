package kata.java;

import java.util.List;

public class GameScoreCalc {

    public int score(Game game) {
        final List<Integer> rolls = game.rolls();
        int score = 0;
        int frameIndex = 1;
        for (int frame = 0; frame < 10; frame += 1) {
            if (isStrike(rolls.get(frameIndex - 1))) {
                score += strike(rolls, frameIndex);
                frameIndex += 1;
            }
            else if (isSpare(rolls.get(frameIndex - 1), rolls.get(frameIndex))) {
                score += spare(rolls, frameIndex);
                frameIndex += 2;
            }
            else {
                score += frame(rolls, frameIndex);
                frameIndex += 2;
            }
        }
        return score;
    }

    private int frame(List<Integer> rolls, int frameIndex) {
        return rolls.get(frameIndex - 1) + rolls.get(frameIndex);
    }

    private int spare(List<Integer> rolls, int frameIndex) {
        return 10 + rolls.get(frameIndex + 1);
    }

    private int strike(List<Integer> rolls, int frameIndex) {
        return 10 + rolls.get(frameIndex) + rolls.get(frameIndex + 1);
    }

    private boolean isSpare(int firstRoll, int secondRoll) {
        return firstRoll + secondRoll == 10;
    }

    private boolean isStrike(int pins) {
        return pins == 10;
    }
}
