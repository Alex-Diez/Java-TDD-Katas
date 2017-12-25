package org.stack;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack {
    private static final int MIN_DELAY = 1;
    private static final int MAX_DELAY = 3;

    private static class Backoff {
        final int maxDelay = MAX_DELAY;
        int limit = MIN_DELAY;

        public void backoff() throws InterruptedException {
            int delay = limit;
            limit = Math.min(maxDelay, 2 * limit);
            Thread.sleep(delay);
        }
    }

    private static class Node {
        final int index;
        final int value;
        Node previous;

        public Node(int index, int value) {
            this.index = index;
            this.value = value;
        }

        public Optional<Integer> getValue() {
            return Optional.of(value);
        }

        public Node getPrevious() {
            return previous;
        }
    }

    private static final Node EMPTY = new Node(-1, -1) {

        @Override
        public Optional<Integer> getValue() {
            return Optional.empty();
        }

        @Override
        public Node getPrevious() {
            return this;
        }
    };
    private final AtomicReference<Node> head = new AtomicReference<>(EMPTY);
    private final Backoff backoff = new Backoff();

    public boolean isEmpty() {
        return head.get() == EMPTY;
    }

    public int size() {
        return head.get().index + 1;
    }

    public boolean push(int value) throws InterruptedException {
        Node n = new Node(head.get().index + 1, value);
        while (true) {
            if (tryPush(n)) {
                return true;
            }
            else {
                backoff.backoff();
            }
        }
    }

    private boolean tryPush(Node n) {
        Node oldHead = head.get();
        n.previous = oldHead;
        return (head.compareAndSet(oldHead, n));
    }

    public Optional<Integer> pop() throws InterruptedException {
        while (true) {
            Node n = tryPop();
            if (n != null) {
                return n.getValue();
            }
            else {
                backoff.backoff();
            }
        }
    }

    private Node tryPop() {
        Node oldHead = head.get();
        if (oldHead == EMPTY) {
            return null;
        }
        Node newHead = oldHead.getPrevious();
        if (head.compareAndSet(oldHead, newHead)) {
            return oldHead;
        }
        else {
            return null;
        }
    }
}
