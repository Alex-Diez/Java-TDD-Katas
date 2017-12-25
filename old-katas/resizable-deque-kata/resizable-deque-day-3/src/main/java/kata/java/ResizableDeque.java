package kata.java;

import java.util.OptionalInt;

public class ResizableDeque {
    private static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;
    public static final int DEFAULT_CAPACITY = 16;

    private static int nextCapacity(int capacity) {
        int n = capacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n < 0 ? 1 : n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }

    private int capacity;
    private int[] items;
    private int head;
    private int tail;

    public ResizableDeque() {
        this(DEFAULT_CAPACITY);
    }

    public ResizableDeque(int initialCapacity) {
        this.capacity = nextCapacity(initialCapacity);
        this.items = new int[capacity];
        this.head = 0;
        this.tail = capacity - 1;
    }

    public int capacity() {
        return capacity;
    }

    public void addFront(int item) {
        this.items[head++] = item;
        if (isFull()) resize();
    }

    private void resize() {
        int newCapacity = nextCapacity(capacity + 1);
        int[] newItems = new int[newCapacity];

        int index = tailElementIndex();
        int newIndex = (newCapacity - (capacity - tail - 1)) & newCapacity - 1;
        for (;index != (headIndexElement() + 1 & capacity - 1); index = next(index), newIndex = newIndex + 1 & newCapacity - 1) {
            newItems[newIndex] = items[index];
        }

        tail = newCapacity - (capacity - tail);
        items = newItems;
        capacity = newCapacity;
    }

    public OptionalInt removeFront() {
        return isEmpty() ? OptionalInt.empty() : OptionalInt.of(items[decrementAndGetHead()]);
    }

    private int decrementAndGetHead() {
        return head = head - 1 & capacity - 1;
    }

    public void addBack(int item) {
        this.items[getAndDecrementTail()] = item;
        if (isFull()) resize();
    }

    private int getAndDecrementTail() {
        int prev = tail;
        tail = tail - 1 & capacity - 1;
        return prev;
    }

    public OptionalInt removeBack() {
        return isEmpty() ? OptionalInt.empty() : OptionalInt.of(items[incrementAndGetTail()]);
    }

    private int incrementAndGetTail() {
        return tail = (tail + 1) & capacity - 1;
    }

    public boolean contains(int item) {
        for (int index = tailElementIndex(); index != headIndexElement() + 1; index = next(index)) {
            if (items[index] == item) {
                return true;
            }
        }
        return false;
    }

    private boolean isFull() {
        return tail == head;
    }

    private boolean isEmpty() {
        return (tail - head & capacity - 1) == capacity - 1;
    }

    private int headIndexElement() {
        return head - 1 & capacity - 1;
    }

    private int tailElementIndex() {
        return tail + 1 & capacity - 1;
    }

    private int next(int index) {
        return index + 1 & capacity - 1;
    }
}
