package org.stack;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronousStack implements Iterable<Integer> {
    private final int[] values;
    private final Lock lock;
    private final Condition empty;
    private final Condition full;
    private volatile int size = 0;

    public SynchronousStack(int maxSize) {
        values = new int[maxSize];
        lock = new ReentrantLock();
        empty = lock.newCondition();
        full = lock.newCondition();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private boolean isFull() {
        return size() == capacity();
    }

    public boolean push(int value) throws InterruptedException {
        lock.lock();
        try {
            while (isFull()) {
                full.await();
            }
            values[size++] = value;
            empty.signalAll();
        } finally {
            lock.unlock();
        }
        return true;
    }

    public Optional<Integer> pop() throws InterruptedException {
        lock.lock();
        try {
            while (isEmpty()) {
                empty.await();
            }
            int value = values[--size];
            full.signalAll();
            return Optional.of(value);
        }
        finally {
            lock.unlock();
        }
    }

    public int capacity() {
        return values.length;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public Integer next() {
                return values[current++];
            }
        };
    }
}
