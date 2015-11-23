package org.mesh;

import static org.mesh.Int.ZERO;

public class SudokuMesh implements TwoDimensionalMesh<SudokuSquare> {

    private SudokuSquare[][] data;

    public SudokuMesh() {
        data = new SudokuSquare[9][9];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = new SudokuSquare();
            }
        }
    }

    @Override
    public SudokuSquare get(int column, int row) {
        return data[column-1][row-1];
    }

    @Override
    public void set(int column, int row, SudokuSquare datum) {
        data[column-1][row-1] = datum;
    }

    public boolean isFill() {
        for (SudokuSquare[] squares : data) {
            for (SudokuSquare square : squares) {
                for (int i = 1; i < 4; i++) {
                    for (int j = 1; j < 4; j++) {
                        if (square.get(i, j) == ZERO) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
