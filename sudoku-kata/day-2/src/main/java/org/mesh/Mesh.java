package org.mesh;

public interface Mesh<T> {

    T get(int column, int row);

    void set(int column, int row, T datum);

    boolean isFill();
}
