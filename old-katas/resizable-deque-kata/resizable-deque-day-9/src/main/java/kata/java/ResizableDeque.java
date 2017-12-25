package kata.java;

import java.util.Iterator;
import java.util.OptionalInt;

public class ResizableDeque implements Iterable<Integer> {
    private static final int MAX_CAPACITY = Integer.MIN_VALUE >>> 1;
    private static final int MIN_CAPACITY = 16;

    private static int alignCapacity(int initCapacity) {
        int n = initCapacity - 1;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return n < 0 ? MIN_CAPACITY : n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }

    private int capacity;
    private int[] items;
    private IntRing head;
    private IntRing tail;

    public ResizableDeque() {
        this(MIN_CAPACITY);
    }

    public ResizableDeque(int initCapacity) {
        capacity = alignCapacity(initCapacity);
        items = new int[capacity];
        head = new IntRing(0, capacity - 1);
        tail = new IntRing(capacity - 1, capacity - 1);
    }

    public int capacity() {
        return capacity;
    }

    public void addFront(int item) {
        items[head.getAndIncrement()] = item;
        if (isFull()) resize();
    }

    private void resize() {
        int newCapacity = capacity << 1;
        items = copyItems(newCapacity);
        capacity = newCapacity;
        updateCursores();
    }

    private boolean isFull() {
        return head.current() == tail.current();
    }

    private int[] copyItems(int newCapacity) {
        int[] items = new int[newCapacity];
        IntRing count = tail.withCursorUpdate(newCapacity - 1);
        for (int i : this) {
            items[count.incrementAndGet()] = i;
        }
        return items;
    }

    private void updateCursores() {
        tail = tail.withCursorUpdate(capacity - 1);
        head = head.withoutCursorUpdate(capacity - 1);
    }

    public OptionalInt removeFront() {
        if (isEmpty()) return OptionalInt.empty();
        else {
            OptionalInt item = OptionalInt.of(items[head.decrementAndGet()]);
            if (isFilledByQuarter()) shrink();
            return item;
        }
    }

    private void shrink() {
        int newCapacity = capacity >> 1;
        items = copyItems(newCapacity);
        capacity = newCapacity;
        updateCursores();
    }

    private boolean isFilledByQuarter() {
        return capacity > MIN_CAPACITY && (head.current() - tail.current() & capacity - 1) < capacity >> 2;
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
        else {
            OptionalInt item = OptionalInt.of(items[tail.incrementAndGet()]);
            if (isFilledByQuarter()) shrink();
            return item;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private IntRing index = new IntRing(tail.current()+ 1, capacity - 1);
            @Override
            public boolean hasNext() {
                return index.current() != head.current();
            }

            @Override
            public Integer next() {
                return items[index.getAndIncrement()];
            }
        };
    }
}

final class IntRing {
    private final int mask;
    private int current;

    IntRing(int begin, int mask) {
        this.mask = mask;
        this.current = begin & mask;
    }

    int current() {
        return current;
    }

    int getAndIncrement() {
        int prev = current;
        current = current + 1 & mask;
        return prev;
    }

    int incrementAndGet() {
        return current = current + 1 & mask;
    }

    int getAndDecrement() {
        int prev = current;
        current = current - 1 & mask;
        return prev;
    }

    int decrementAndGet() {
        return current = current - 1 & mask;
    }

    IntRing withoutCursorUpdate(int newMask) {
        return new IntRing(current, newMask);
    }

    IntRing withCursorUpdate(int newMask) {
        return new IntRing(newMask - mask + current, newMask);
    }
}
