package org.stack;

import java.util.Optional;

public interface Stack<E> extends Iterable<E> {
    int size();

    boolean isEmpty();

    Optional<E> pop();

    void push(E v);
}
