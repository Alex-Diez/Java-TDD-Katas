package org.stack;

import java.util.Iterator;
import java.util.Optional;

public abstract class ConcurrentStack<E> implements Iterable<E> {
    private E[] values;
    private int size;

    public ConcurrentStack(int maxSize) {
        values = (E[])new Object[maxSize];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    protected boolean isFull() {
        return size == values.length;
    }

    public Optional<E> pop() throws InterruptedException {
        return Optional.of(values[--size]);
    }

    public void push(E v) throws InterruptedException {
        values[size++] = v;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current != ConcurrentStack.this.size;
            }

            @Override
            public E next() {
                return ConcurrentStack.this.values[current++];
            }
        };
    }
}
