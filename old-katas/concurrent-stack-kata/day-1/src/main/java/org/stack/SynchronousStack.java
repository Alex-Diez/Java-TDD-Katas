package org.stack;

import java.util.Iterator;
import java.util.Optional;

public class SynchronousStack<E> implements Stack<E> {
    private int size;
    private E[] values;

    public SynchronousStack(int maxSize) {
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
        return Optional.of(values[--size]);
    }

    @Override
    public void push(E v) {
        values[size++] = v;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current != SynchronousStack.this.size;
            }

            @Override
            public E next() {
                return SynchronousStack.this.values[current++];
            }
        };
    }
}
