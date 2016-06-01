package kata.java;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private int[] rolls = new int[20];
    private int size = 0;

    public void roll(int pins) {
        rolls[size] = pins;
        size += 1;
    }

    public List<Integer> rolls() {
        return IntStream.of(rolls).limit(20).boxed().collect(Collectors.toList());
    }
}
