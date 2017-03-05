package kata.java;

import java.util.Iterator;
import java.util.OptionalInt;

public class ResizableDeque implements Iterable<Integer> {
    private static final int MAX_CAPACITY = Integer.MIN_VALUE >>> 1;
    private static final int MIN_CAPACITY = 16;

    private static int alignCapacity(int capacity) {
        int n = capacity - 1;
        n |= n >>> 1;
        n |= n >>> 1;
        n |= n >>> 1;
        n |= n >>> 1;
        n |= n >>> 1;
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

    public boolean addFront(int item) {
        if (isMaxCapacityAchieved()) return false;
        this.items[head.getAndIncrement()] = item;
        if (isFull()) resize();
        return true;
    }

    private boolean isMaxCapacityAchieved() {
        return capacity == MAX_CAPACITY && size() == capacity - 1;
    }

    public OptionalInt removeFront() {
        if (isEmpty()) return OptionalInt.empty();
        else {
            int item = items[head.decrementAndGet()];
            if (isFilledByQuarter()) shrink();
            return OptionalInt.of(item);
        }
    }

    private void shrink() {
        int capacity = this.capacity >> 1;
        items = copyItems(capacity);
        this.capacity = capacity;
        updateCursores();
    }

    private int[] copyItems(int capacity) {
        int[] items = new int[capacity];
        IntRing cursor = tail.updateCursor(capacity - 1);
        for (int i : this) {
            items[cursor.incrementAndGet()] = i;
        }
        return items;
    }

    private int size() {
        return head.current() - tail.current() & capacity - 1;
    }

    private boolean isEmpty() {
        return (tail.current() - head.current() & capacity - 1) == capacity - 1;
    }

    public boolean addBack(int item) {
        if (isMaxCapacityAchieved()) return false;
        items[tail.getAndDecrement()] = item;
        if (isFull()) resize();
        return true;
    }

    private void resize() {
        int capacity = this.capacity << 1;
        items = copyItems(capacity);
        this.capacity = capacity;
        updateCursores();
    }

    private void updateCursores() {
        tail = tail.updateCursor(capacity - 1);
        head = head.withoutUpdateCursor(capacity - 1);
    }

    private boolean isFull() {
        return tail.current() == head.current();
    }

    public OptionalInt removeBack() {
        if (isEmpty()) return OptionalInt.empty();
        else {
            OptionalInt item = OptionalInt.of(items[tail.incrementAndGet()]);
            if (isFilledByQuarter()) shrink();
            return item;
        }
    }

    private boolean isFilledByQuarter() {
        return capacity > MIN_CAPACITY && (size()) == capacity >> 2;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private IntRing index = new IntRing(tail.current() + 1, capacity - 1);
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

    private static class IntRing {
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

        IntRing updateCursor(int mask) {
            return new IntRing(mask - this.mask + current, mask);
        }

        public IntRing withoutUpdateCursor(int mask) {
            return new IntRing(current, mask);
        }
    }
}
