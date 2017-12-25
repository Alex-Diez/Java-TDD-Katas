package org.stack;

import java.util.Iterator;
import java.util.Optional;

public class UnsynchronousStack<E> implements Stack<E> {
    private E[] values;
    private int size;

    public UnsynchronousStack(int maxSize) {
        values = (E[])new Object[maxSize];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Optional<E> pop() {
        size -= 1;
        return Optional.of(values[size]);
    }

    @Override
    public void push(E v) {
        values[size] = v;
        size += 1;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current != UnsynchronousStack.this.size;
            }

            @Override
            public E next() {
                return UnsynchronousStack.this.values[current++];
            }
        };
    }
}
