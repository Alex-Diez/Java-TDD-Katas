package org.mesh;

import java.util.EnumSet;

import static org.mesh.Int.*;

public class SudokuGame implements Mesh<SudokuSquare> {

    private final SudokuSquare[][] mesh;

    public SudokuGame(int size) {
        mesh = new SudokuSquare[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mesh[i][j] = new SudokuSquare(size);
            }
        }
    }

    @Override
    public SudokuSquare get(int column, int row) {
        return mesh[column - 1][row - 1];
    }

    @Override
    public void set(int column, int row, SudokuSquare datum) {
        mesh[column - 1][row - 1] = datum;
    }

    @Override
    public boolean isFill() {
        for (SudokuSquare[] row : mesh) {
            for (SudokuSquare square : row) {
                if (!square.isFill()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void resolveByColumn(int column) {
        EnumSet<Int> data = EnumSet.of(ONE, TWO, THREE, FOUR, FIFE, SIX, SEVEN, EIGHT, NINE);
        int zeroSquareRow = 0;
        int zeroDataRow = 0;
        for (int i = 0; i < 3; i++) {
            SudokuSquare square = mesh[column - 1][i];
            for (int j = 0; j < 3; j++) {
                Int datum = square.get(column, j + 1);
                if (datum == ZERO) {
                    zeroDataRow = j + 1;
                    zeroSquareRow = i + 1;
                }
                data.remove(datum);
            }
        }
        get(column, zeroSquareRow).set(column, zeroDataRow, data.iterator().next());
    }
}
