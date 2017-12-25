package org.stack;

import java.util.Optional;

public interface Stack<E> extends Iterable<E> {
    int size();

    int capacity();

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean isFull() {
        return size() == capacity();
    }

    Optional<E> pop() throws InterruptedException;

    void push(E v) throws InterruptedException;
}
