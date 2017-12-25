package kata.java;

import java.util.Iterator;
import java.util.OptionalInt;

public class ResizableDeque implements Iterable<Integer> {
    private static final int MAX_CAPACITY = Integer.MIN_VALUE >>> 1;
    private static final int MIN_CAPACITY = 16;

    private static int alignCapacity(int initCapacity) {
        int n = initCapacity - 1;
        n |= n >>> 1;
        n |= n >>> 1;
        n |= n >>> 1;
        n |= n >>> 1;
        n |= n >>> 1;
        return n < 0 ? MIN_CAPACITY : n > MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }

    private int capacity;
    private int[] items;
    private IntRing head;
    private IntRing tail;

    public ResizableDeque(int initCapacity) {
        capacity = alignCapacity(initCapacity);
        items = new int[capacity];
        head = new IntRing(0, capacity - 1);
        tail = new IntRing(capacity - 1, capacity - 1);
    }

    public ResizableDeque() {
        this(MIN_CAPACITY);
    }

    public int capacity() {
        return capacity;
    }

    public boolean addFront(int item) {
        if (isFull() && capacity == MAX_CAPACITY)
            return false;

        items[head.getAndIncrement()] = item;
        if (isFull()) resize();
        return true;
    }

    private boolean isFull() {
        return head.current() == tail.current();
    }

    private void resize() {
        int newCapacity = capacity << 1;
        items = copyItem(newCapacity);
        capacity = newCapacity;
        updateCursores();
    }

    private int[] copyItem(int newCapacity) {
        int[] items = new int[newCapacity];
        IntRing cursor = tail.withCursorUpdate(newCapacity - 1);
        forEach(item -> items[cursor.incrementAndGet()] = item);
        return items;
    }

    private void updateCursores() {
        tail = tail.withCursorUpdate(capacity - 1);
        head = head.withoutCursorUpdate(capacity - 1);
    }

    public OptionalInt removeFront() {
        if (isEmpty()) {
            return OptionalInt.empty();
        } else {
            OptionalInt item = OptionalInt.of(items[head.decrementAndGet()]);
            if (isFilledByQuarter()) shrink();
            return item;
        }
    }

    private boolean isEmpty() {
        return (tail.current() - head.current() & capacity - 1) == capacity - 1;
    }

    private boolean isFilledByQuarter() {
        return capacity > MIN_CAPACITY && (head.current() - tail.current() & capacity - 1) == (capacity >> 2) - 1;
    }

    private void shrink() {
        int newCapacity = capacity >> 1;
        items = copyItem(newCapacity);
        capacity = newCapacity;
        updateCursores();
    }

    public void addBack(int item) {
        items[tail.getAndDecrement()] = item;
        if (isFull()) {
            resize();
        }
    }

    public OptionalInt removeBack() {
        if (isEmpty()) {
            return OptionalInt.empty();
        } else {
            OptionalInt item = OptionalInt.of(items[tail.incrementAndGet()]);
            if (isFilledByQuarter()) {
                shrink();
            }
            return item;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private IntRing index = new IntRing(tail.peekAhead(), capacity - 1);

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

        int getAndIncrement() {
            int prev = current;
            current = current + 1 & mask;
            return prev;
        }

        int incrementAndGet() {
            return current = current + 1 & mask;
        }

        int decrementAndGet() {
            return current = current - 1 & mask;
        }

        int getAndDecrement() {
            int prev = current;
            current = current - 1 & mask;
            return prev;
        }

        int peekAhead() {
            return current + 1 & mask;
        }

        IntRing withCursorUpdate(int newMask) {
            return new IntRing(newMask - mask + current, newMask);
        }

        IntRing withoutCursorUpdate(int newMask) {
            return new IntRing(current, newMask);
        }
    }
}
