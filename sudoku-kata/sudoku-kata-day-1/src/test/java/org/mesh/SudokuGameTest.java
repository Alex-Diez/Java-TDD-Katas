package org.mesh;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SudokuGameTest {

    @Test
    public void testCreateSudokuMesh() throws Exception {
        new SudokuMesh();
    }

    @Test
    @Ignore
    public void testFillSudokuMeshWithRandomGeneratedNumbers() throws Exception {
        SudokuMesh mesh = new SudokuMesh();
        Random r = new Random();
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                Int data = generateData(r.nextInt(9) + 1);
                mesh.get(i, j).set((i % 3 + 1), (j % 3 + 1), data);
            }
        }
        assertThat(mesh.isFill(), is(true));
    }

    private Int generateData(int i) {
        switch (i) {
            case 0:
                return Int.ZERO;
            case 1:
                return Int.ONE;
            case 2:
                return Int.TWO;
            case 3:
                return Int.THREE;
            case 4:
                return Int.FOUR;
            case 5:
                return Int.FIFE;
            case 6:
                return Int.SIX;
            case 7:
                return Int.SEVEN;
            case 8:
                return Int.EIGHT;
            case 9:
                return Int.NINE;
            default:
                throw new RuntimeException("IMPOSSIBLE");
        }
    }
}
