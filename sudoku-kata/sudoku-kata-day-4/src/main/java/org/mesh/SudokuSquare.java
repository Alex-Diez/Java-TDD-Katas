package org.mesh;

public class SudokuSquare implements Mesh<Int> {

    private final Int[][] data;

    public SudokuSquare(int size) {
        data = new Int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = Int.ZERO;
            }
        }
    }

    @Override
    public Int get(int row, int column) {
        return data[row - 1][column - 1];
    }

    @Override
    public void set(int row, int column, Int datum) {
        data[row - 1][column - 1] = datum;
    }

    @Override
    public boolean isFill() {
        for (Int[] row : data) {
            for (Int datum : row) {
                if (datum == Int.ZERO) {
                    return false;
                }
            }
        }
        return true;
    }
}
