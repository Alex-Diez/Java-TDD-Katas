package org.stack;

import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unchecked")
public class LockFreeStack<E> implements Stack<E> {
    private static final int MIN_DELAY = 10;
    private static final int MAX_DELAY = 100;

    private static final Node<?> EMPTY = new Node(null) {
        @Override
        public Optional getValue() {
            return Optional.empty();
        }

        @Override
        public Node getPrevious() {
            return this;
        }

        @Override
        public String toString() {
            return "{ }";
        }
    };

    private AtomicReference<Node<E>> head = new AtomicReference<>((Node<E>) EMPTY);
    private Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
    private AtomicInteger size = new AtomicInteger();

    @Override
    public int size() {
        return size.get();
    }

    @Override
    public int capacity() {
        return 0;
    }

    @Override
    public Optional<E> pop() throws InterruptedException {
        while (true) {
            Node<E> returnNode = tryPop();
            if (returnNode != null) {
                size.decrementAndGet();
                return returnNode.getValue();
            }
            else {
                backoff.backoff();
            }
        }
    }

    private Node<E> tryPop() throws InterruptedException {
        Node<E> oldHead = head.get();
        if (oldHead == null) {
            return null;
        }
        Node<E> newHead = oldHead.getPrevious();
        if (head.compareAndSet(oldHead, newHead)) {
            return oldHead;
        }
        else {
            return null;
        }
    }

    @Override
    public void push(E v) throws InterruptedException {
        Node<E> node = new Node<>(v);
        while (true) {
            if(tryPush(node)) {
                size.incrementAndGet();
                return;
            }
            else {
                backoff.backoff();
            }
        }
    }

    private boolean tryPush(Node<E> node) {
        Node<E> oldHead = head.get();
        node.previous = oldHead;
        return (head.compareAndSet(oldHead, node));
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private Node<E> current = head.get();
            @Override
            public boolean hasNext() {
                return current != EMPTY;
            }

            @Override
            public E next() {
                Optional<E> v = current.getValue();
                current = current.getPrevious();
                return v.get();
            }
        };
    }

    private static class Node<E> {
        private final E value;
        private Node<E> previous;

        public Node(E value) {
            this.value = value;
        }

        public Optional<E> getValue() {
            return Optional.ofNullable(value);
        }

        public Node<E> getPrevious() {
            return previous;
        }

        @Override
        public String toString() {
            return "{ " + value + " -> " + previous + " }";
        }
    }

    private class Backoff {
        private final int maxDelay;
        private int limit;
        private final Random random;

        public Backoff(int minDelay, int maxDelay) {
            this.maxDelay = maxDelay;
            limit = minDelay;
            random = new Random();
        }

        void backoff() throws InterruptedException {
            int delay = random.nextInt(limit);
            limit = Math.min(maxDelay, 2 * limit);
            Thread.sleep(delay);
        }
    }
}
