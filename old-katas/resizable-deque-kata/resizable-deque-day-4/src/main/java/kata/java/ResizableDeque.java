package kata.java;

import java.util.Iterator;
import java.util.OptionalInt;

public class ResizableDeque implements Iterable<Integer> {
    private static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;
    private static final int DEFAULT_CAPACITY = 16;

    private int capacity;
    private int items[];
    private IntRing head;
    private IntRing tail;

    public ResizableDeque(int initialCapacity) {
        this.capacity = nextCapacity(initialCapacity);
        this.items = new int[capacity];
        this.head = new IntRing(0, capacity - 1);
        this.tail = new IntRing(capacity - 1, capacity - 1);
    }

    private static int nextCapacity(int capacity) {
        int n = capacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n < 0 ? 1 : n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }

    public ResizableDeque() {
        this(DEFAULT_CAPACITY);
    }

    public int capacity() {
        return capacity;
    }

    public void addFront(int item) {
        items[head.getAndIncrement()] = item;
        if (isFull()) resize();
    }

    private void resize() {
        int newCapacity = nextCapacity(capacity + 1);
        int[] newItems = new int[newCapacity];
        IntRing counter = tail.withUpdate(newCapacity - 1);
        for (int item : this) {
            newItems[counter.incrementAndGet()] = item;
        }
        tail = tail.withUpdate(newCapacity - 1);
        head = head.withoutUpdate(newCapacity - 1);
        items = newItems;
        capacity = newCapacity;
    }

    public OptionalInt removeFront() {
        return isEmpty() ? OptionalInt.empty() : OptionalInt.of(items[head.decrementAndGet()]);
    }

    public void addBack(int item) {
        items[tail.getAndDecrement()] = item;
        if (isFull()) resize();
    }

    private boolean isFull() {
        return tail.current() == head.current();
    }

    public OptionalInt removeBack() {
        return isEmpty() ? OptionalInt.empty() : OptionalInt.of(items[tail.incrementAndGet()]);
    }

    private boolean isEmpty() {
        return tail.peekFront() == head.current() && tail.current() == head.peekBack();
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private IntRing index = new IntRing(tail.peekFront(), capacity - 1);

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

    private class IntRing {
        private final int mask;
        private int current;

        IntRing(int begin, int mask) {
            this.mask = mask;
            this.current = begin & mask;
        }

        int current() {
            return current;
        }

        int peekFront() {
            return current + 1 & mask;
        }

        int peekBack() {
            return current - 1 & mask;
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

        IntRing withUpdate(int newMask) {
            return new IntRing(newMask - mask + current, newMask);
        }

        IntRing withoutUpdate(int newMask) {
            return new IntRing(current, newMask);
        }
    }
}
