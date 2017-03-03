package kata.java;

import java.util.Iterator;
import java.util.OptionalInt;

public class ResizableDeque implements Iterable<Integer> {
    private static final int MIN_CAPACITY = 16;
    private static final int MAX_CAPACITY = Integer.MAX_VALUE >> 1;

    private static int alignCapacity(int initialCapacity) {
        int n = initialCapacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n < 0 ? MIN_CAPACITY : n >= MAX_CAPACITY ? Integer.MAX_VALUE >> 1 : n + 1;
    }

    private int capacity;
    private int[] items;
    private IntRing head;
    private IntRing tail;
    private int size;

    public ResizableDeque(int initialCapacity) {
        capacity = alignCapacity(initialCapacity);
        items = new int[capacity];
        head = new IntRing(0, capacity - 1);
        tail = new IntRing(capacity - 1, capacity - 1);
        size = 0;
    }

    public ResizableDeque() {
        this(MIN_CAPACITY);
    }

    public int capacity() {
        return capacity;
    }

    public void addFront(int item) {
        size += 1;
        items[head.getAndIncrement()] = item;
        if (isFull()) resize();
    }

    private boolean isFull() {
        return head.current() == tail.current();
    }

    private void resize() {
        int newCapacity = capacity << 1;
        newCapacity = newCapacity >= MAX_CAPACITY ? MAX_CAPACITY : newCapacity;
        items = copyItems(newCapacity);
        capacity = newCapacity;
        updateCursores();
    }

    public OptionalInt removeFront() {
        if (isEmpty()) return OptionalInt.empty();
        else {
            size -= 1;
            OptionalInt item = OptionalInt.of(items[head.decrementAndGet()]);
            if (isFilledByQuarter()) shrink();
            return item;
        }
    }

    private boolean isFilledByQuarter() {
        return capacity > MIN_CAPACITY && size == (capacity >> 2) - 2;
    }

    private void shrink() {
        int newCapacity = capacity >> 1;
        newCapacity = newCapacity < MIN_CAPACITY ? MIN_CAPACITY : newCapacity;
        items = copyItems(newCapacity);
        capacity = newCapacity;
        updateCursores();
    }

    private int[] copyItems(int newCapacity) {
        int[] newItems = new int[newCapacity];

        IntRing counter = tail.withUpdate(newCapacity - 1);

        for (int i : this) {
            newItems[counter.incrementAndGet()] = i;
        }

        return newItems;
    }

    private void updateCursores() {
        tail = tail.withUpdate(capacity - 1);
        head = head.withoutUpdate(capacity - 1);
    }

    private boolean isEmpty() {
        return (tail.current() - head.current() & capacity - 1) == capacity - 1;
    }

    public void addBack(int item) {
        size += 1;
        items[tail.getAndDecrement()] = item;
        if (isFull()) resize();
    }

    public OptionalInt removeBack() {
        if (isEmpty()) {
            return OptionalInt.empty();
        } else {
            size -= 1;
            OptionalInt item = OptionalInt.of(items[tail.incrementAndGet()]);
            if (isFilledByQuarter()) shrink();
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

        int getAndDecrement() {
            int prev = current;
            current = current - 1 & mask;
            return prev;
        }

        int decrementAndGet() {
            return current = current - 1 & mask;
        }

        int peekAhead() {
            return current + 1;
        }

        IntRing withoutUpdate(int newMask) {
            return new IntRing(current, newMask);
        }

        IntRing withUpdate(int newMask) {
            return new IntRing(newMask - mask + current, newMask);
        }
    }
}
