package org.mesh;

import static org.mesh.Int.ZERO;

public class SudokuSquare implements TwoDimensionalMesh<Int> {

    private Int[][] data = new Int[9][9];

    public SudokuSquare() {
        data = new Int[9][9];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = ZERO;
            }
        }
    }

    @Override
    public Int get(int column, int row) {
        return data[column-1][row-1];
    }

    @Override
    public void set(int column, int row, Int datum) {
        data[column-1][row-1] = datum;
    }
}
