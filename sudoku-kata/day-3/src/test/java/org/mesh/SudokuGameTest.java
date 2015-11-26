package org.mesh;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class SudokuGameTest {

    public static final int SIZE = 3;
    private SudokuGame game;

    @Before
    public void setUp() throws Exception {
        game = new SudokuGame(SIZE);
    }

    private void moveThroughGame(SudokuGame g, DatumGenerator<Int> generator) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            for (int j = 0; j < SIZE * SIZE; j++) {
                Int datum = generator.generate(i, j);
                game.set(i + 1, j + 1, datum);
            }
        }
    }

    @Test
    public void testFillSudokuGame() throws Exception {
        Random random = new Random();
        moveThroughGame(game, (i, j) -> Int.fromInteger(random.nextInt(SIZE * SIZE) + 1));

        assertThat(game.isFill(), is(true));
    }

    @Test
    public void testResolveCellByColumn() throws Exception {
        moveThroughGame(game, (i, j) -> Int.fromInteger((i * SIZE + i / SIZE + j) % (SIZE * SIZE) + 1));
        game.set(9, 1, Int.ZERO);
        game.resolveByColumn(1);

        assertThat(game.get(9, 1), is(Int.NINE));
    }

    @Test
    public void testResolveCellByRow() throws Exception {
        moveThroughGame(game, (i, j) -> Int.fromInteger((i * SIZE + i / SIZE + j) % (SIZE * SIZE) + 1));
        game.set(9, 1, Int.ZERO);
        game.resolveByRow(9);

        assertThat(game.get(9, 1), is(Int.NINE));
    }

    @Test
    public void testResolveCellBySquare() throws Exception {
        moveThroughGame(game, (i, j) -> Int.fromInteger((i * SIZE + i / SIZE + j) % (SIZE * SIZE) + 1));
        game.set(9, 1, Int.ZERO);
        game.resolveBySquare(3, 1);

        assertThat(game.get(9, 1), is(Int.NINE));
    }

    @Test
    public void testFindPossibleRowToResolve() throws Exception {
        moveThroughGame(game, (i, j) -> Int.fromInteger((i * SIZE + i / SIZE + j) % (SIZE * SIZE) + 1));
        game.set(8, 1, Int.ZERO);
        game.set(9, 1, Int.ZERO);

        assertThat(game.possibleRowsToResolve(), containsInAnyOrder(8, 9));
    }

    private interface DatumGenerator<D> {

        D generate(int col, int row);
    }
}
