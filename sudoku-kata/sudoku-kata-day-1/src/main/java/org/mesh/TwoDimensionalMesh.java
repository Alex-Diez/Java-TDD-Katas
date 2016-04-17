package org.mesh;

public interface TwoDimensionalMesh<E extends Element> extends Element {

    E get(int column, int row);

    void set(int column, int row, E datum);
}
