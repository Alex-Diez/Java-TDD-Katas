package org.mesh;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SudokuGameTest {

    public static final int SIZE = 3;
    private SudokuGame game;

    @Before
    public void setUp() throws Exception {
        game = new SudokuGame(SIZE);
    }

    private void moveThroughGame(SudokuGame g, DatumGenerator<Int> generator) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    for (int l = 0; l < SIZE; l++) {
                        Int datum = generator.generate(i, j, k, l);
                        game.get(i + 1, j + 1).set(k + 1, l + 1, datum);
                    }
                }
            }
        }
    }

    @Test
    public void testFillSudokuGame() throws Exception {
        Random random = new Random();
        moveThroughGame(game, (i, j, k, l) -> Int.fromInteger(random.nextInt(SIZE * SIZE) + 1));

        assertThat(game.isFill(), is(true));
    }

    @Test
    public void testResolveColumnWithoutOneDatum() throws Exception {
        moveThroughGame(game, (i, j, k, l) -> Int.fromInteger(((i * SIZE + k) * SIZE + (i * SIZE + k) / SIZE + j * SIZE + l) % (SIZE * SIZE) + 1));
        game.get(1, 3).set(1, 3, Int.ZERO);
        game.resolveByColumn(1);

        assertThat(game.get(1, 3).get(1, 3), is(Int.NINE));
    }

    private interface DatumGenerator<D> {

        D generate(int gameCol, int gameRow, int squareCol, int squareRow);
    }
}
