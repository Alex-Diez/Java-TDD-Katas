package org.stack;

import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronousStack<E> extends ConcurrentStack<E> {
    private final Lock lock;
    private final Condition empty;
    private final Condition full;

    public SynchronousStack(int maxSize) {
        super(maxSize);
        lock = new ReentrantLock();
        empty = lock.newCondition();
        full = lock.newCondition();
    }

    @Override
    public Optional<E> pop()
            throws InterruptedException {
        lock.lock();
        try {
            while (isEmpty()) {
                empty.await();
            }
            Optional<E> r = super.pop();
            full.signal();
            return r;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void push(E v) throws InterruptedException {
        lock.lock();
        try {
            while (isFull()) {
                full.await();
            }
            super.push(v);
            empty.signal();
        }
        finally {
            lock.unlock();
        }
    }
}
