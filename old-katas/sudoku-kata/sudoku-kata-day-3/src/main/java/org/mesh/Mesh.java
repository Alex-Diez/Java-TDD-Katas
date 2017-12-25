package org.mesh;

public interface Mesh<T> {

    T get(int row, int column);

    void set(int row, int column, T datum);

    boolean isFill();
}
