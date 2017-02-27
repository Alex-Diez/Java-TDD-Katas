package kata.java;

import java.util.OptionalInt;

public class ResizableDeque {
    private static final int MAX_CAPACITY = (Integer.MAX_VALUE - 8);
    private static final int DEFAULT_CAPACITY = 16;

    private int capacity;
    private int[] items;
    private int head;
    private int tail;

    public ResizableDeque() {
        this(DEFAULT_CAPACITY);
    }

    public ResizableDeque(int initialCapacity) {
        capacity = nextCapacity(initialCapacity);
        items = new int[capacity];
        head = 0;
        tail = capacity - 1;
    }

    public int capacity() {
        return capacity;
    }

    public void addFront(int item) {
        this.items[getAndIncrementHead()] = item;
        if (isFull()) resize();
    }

    private int getAndIncrementHead() {
        int prev = head;
        head = head + 1 & capacity - 1;
        return prev;
    }

    public OptionalInt removeFront() {
        return isEmpty() ? OptionalInt.empty() : OptionalInt.of(items[decrementAndGetHead()]);
    }

    private int decrementAndGetHead() {
        return head = head - 1 & capacity - 1;
    }

    public void addBack(int item) {
        this.items[getAndDecrement()] = item;
        if (isFull()) resize();
    }

    private int getAndDecrement() {
        int prev = tail;
        tail = tail - 1 & capacity - 1;
        return prev;
    }

    public OptionalInt removeBack() {
        return isEmpty() ? OptionalInt.empty() : OptionalInt.of(items[incrementAndGet()]);
    }

    private int incrementAndGet() {
        return tail = (tail + 1) & capacity - 1;
    }

    private boolean isEmpty() {
        return (tail - head & capacity - 1) == capacity - 1;
    }

    public boolean contains(int item) {
        for(int index = tailElementIndex(); index != headElementIndex() + 1; index = next(index)) {
            if (items[index] == item) {
                return true;
            }
        }
        return false;
    }

    private int next(int index) {
        return (index + 1) & (capacity - 1);
    }

    private int headElementIndex() {
        return (head - 1) & (capacity - 1);
    }

    private int tailElementIndex() {
        return (tail + 1) & (capacity - 1);
    }

    private boolean isFull() {
        return head == tail;
    }

    private void resize() {
        int newCapacity = nextCapacity(capacity + 1);
        int[] newItems = new int[newCapacity];

        System.arraycopy(items, 0, newItems, 0, head);
        System.arraycopy(items, tail + 1 & capacity - 1, newItems, newCapacity - (capacity - tail - 1), capacity - tail - 1);

        tail = newCapacity - (capacity - tail);
        capacity = newCapacity;
        items = newItems;
    }

    private static int nextCapacity(int capacity) {
        int n = capacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }
}
