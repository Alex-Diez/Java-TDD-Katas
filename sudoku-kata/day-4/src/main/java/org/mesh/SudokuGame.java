package org.mesh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class SudokuGame implements Mesh<Int> {

    private final SudokuSquare[][] mesh;
    private int size;

    public SudokuGame(int size) {
        this.size = size;
        mesh = new SudokuSquare[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mesh[i][j] = new SudokuSquare(size);
            }
        }
        for (int i = 0; i < size * size; i++) {
            for (int j = 0; j < size * size; j++) {
                set(i, j, Int.ZERO);
            }
        }
    }

    @Override
    public Int get(int row, int column) {
        return mesh[(row - 1) / size][(column - 1) / size].get(row % size + 1, column % size + 1);
    }

    @Override
    public void set(int row, int column, Int datum) {
        mesh[(row - 1) / size][(column - 1) / size].set(row % size + 1, column % size + 1, datum);
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
        EnumSet<Int> data = EnumSet.of(Int.ONE, Int.TWO, Int.THREE, Int.FOUR, Int.FIFE, Int.SIX, Int.SEVEN, Int.EIGHT, Int.NINE);
        int zeroRow = 0;
        for (int i = 1; i <= size * size; i++) {
            Int datum = get(i, column);
            if (datum == Int.ZERO) {
                zeroRow = i;
            }
            data.remove(datum);
        }
        set(zeroRow, column, data.iterator().next());
    }

    public void resolveByRow(int row) {
        EnumSet<Int> data = EnumSet.of(Int.ONE, Int.TWO, Int.THREE, Int.FOUR, Int.FIFE, Int.SIX, Int.SEVEN, Int.EIGHT, Int.NINE);
        int zeroColumn = 0;
        for (int i = 1; i <= size * size; i++) {
            Int datum = get(row, i);
            if (datum == Int.ZERO) {
                zeroColumn = i;
            }
            data.remove(datum);
        }
        set(row, zeroColumn, data.iterator().next());
    }

    public void resolveBySquare(int row, int column) {
        EnumSet<Int> data = EnumSet.of(Int.ONE, Int.TWO, Int.THREE, Int.FOUR, Int.FIFE, Int.SIX, Int.SEVEN, Int.EIGHT, Int.NINE);
        int zeroRow = 0;
        int zeroColumn = 0;
        for (int i = (row - 1) * size + 1; i <= row * size; i++) {
            for (int j = (column - 1) * size + 1; j <= column * size; j++) {
                Int datum = get(i, j);
                if (datum == Int.ZERO) {
                    zeroColumn = j;
                    zeroRow = i;
                }
                data.remove(datum);
            }
        }
        set(zeroRow, zeroColumn, data.iterator().next());
    }

    public Collection<Integer> possibleRowsToResolve() {
        Collection<Integer> possibleRows = new ArrayList<>();
        for (int i = 1; i <= size * size; i++) {
            Set<Int> rowData = new HashSet<>();
            for (int j = 1; j <= size * size; j++) {
                Int datum = get(i, j);
                if (datum != Int.ZERO) {
                    rowData.add(datum);
                }
            }
            if (rowData.size() == size * size - 1) {
                possibleRows.add(i);
            }
        }
        return possibleRows;
    }
}
