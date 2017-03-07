package kata.java;

import java.util.Iterator;
import java.util.OptionalInt;

public class ResizableDeque implements Iterable<Integer> {
    private static final int MIN_CAPACITY = 16;
    private static final int MAX_CAPACITY = Integer.MIN_VALUE >>> 1;

    private static int alignCapacity(int initialCapacity) {
        int n = initialCapacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n < 0 ? MIN_CAPACITY : n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;

    }

    private int capacity;
    private int[] items;
    private IntRing head;
    private IntRing tail;

    public ResizableDeque() {
        this(MIN_CAPACITY);
    }

    public ResizableDeque(int initialCapacity) {
        capacity = alignCapacity(initialCapacity);
        items = new int[capacity];
        head = new IntRing(0, capacity - 1);
        tail = new IntRing(capacity - 1, capacity - 1);
    }

    public int capacity() {
        return capacity;
    }

    public void addFront(int item) {
        items[head.getAndIncrement()] = item;
        if (isFull()) {
            resize();
        }
    }

    private boolean isFull() {
        return tail.current() == head.current();
    }

    private void resize() {
        int newCapacity = capacity << 1;
        items = copyItems(newCapacity);

        capacity = newCapacity;
        updateCursors();
    }

    private void updateCursors() {
        tail = tail.withCursorUpdate(capacity - 1);
        head = head.withoutCursorUpdate(capacity - 1);
    }

    public OptionalInt removeFront() {
        if (isEmpty()) return OptionalInt.empty();
        OptionalInt item = OptionalInt.of(items[head.decrementAndGet()]);
        if (isFilledByQuarter()) shrink();
        return item;
    }

    private boolean isFilledByQuarter() {
        return capacity > MIN_CAPACITY && ((head.current() - tail.current()) & capacity - 1) < capacity >> 2;
    }

    private void shrink() {
        int newCapacity = capacity >> 1;
        items = copyItems(newCapacity);

        capacity = newCapacity;
        updateCursors();
    }

    private int[] copyItems(int newCapacity) {
        int[] items = new int[newCapacity];
        IntRing cursor = tail.withCursorUpdate(newCapacity - 1);
        forEach(item -> items[cursor.incrementAndGet()] = item);
        return items;
    }

    private boolean isEmpty() {
        return (tail.current() - head.current() & capacity - 1) == capacity - 1;
    }

    public void addBack(int item) {
        items[tail.getAndDecrement()] = item;
        if (isFull()) resize();
    }

    public OptionalInt removeBack() {
        if (isEmpty()) return OptionalInt.empty();
        OptionalInt item = OptionalInt.of(items[tail.incrementAndGet()]);
        if (isFilledByQuarter()) shrink();
        return item;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new DequeIterator(items, tail.current(), capacity, head.current());
    }

    private static final class DequeIterator implements Iterator<Integer> {
        private final IntRing index;
        private final int[] items;
        private final int end;

        DequeIterator(int[] items, int tail, int capacity, int end) {
            this.items = items;
            this.end = end;
            this.index = new IntRing(tail + 1, capacity - 1);
        }

        @Override
        public boolean hasNext() {
            return index.current() != end;
        }

        @Override
        public Integer next() {
            return items[index.getAndIncrement()];
        }
    }

    private static final class IntRing {
        private final int mask;
        private int current;

        IntRing(int begin, int mask) {
            this.mask = mask;
            this.current = begin & mask;
        }

        int current() {
            return current;
        }

        int incrementAndGet() {
            return current = current + 1 & mask;
        }

        int getAndIncrement() {
            int prev = current;
            current = current + 1 & mask;
            return prev;
        }

        int decrementAndGet() {
            return current = current - 1 & mask;
        }

        int getAndDecrement() {
            int prev = current;
            current = current - 1 & mask;
            return prev;
        }

        IntRing withCursorUpdate(int mask) {
            return new IntRing(mask - this.mask + current, mask);
        }

        IntRing withoutCursorUpdate(int mask) {
            return new IntRing(current, mask);
        }
    }
}
