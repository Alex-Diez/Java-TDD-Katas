package org.stack;

import java.util.Iterator;
import java.util.Optional;

public abstract class ArrayBasedStack<E> implements Stack<E> {
    private E[] values;
    private int size;

    public ArrayBasedStack(int maxSize) {
        values = (E[])new Object[maxSize];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return values.length;
    }

    protected E takeValue() {
        return values[--size];
    }

    protected void putValue(E value) {
        values[size++] = value;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current != ArrayBasedStack.this.size;
            }

            @Override
            public E next() {
                return ArrayBasedStack.this.values[current++];
            }
        };
    }
}
