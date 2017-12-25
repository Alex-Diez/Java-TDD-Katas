package kata.java;

import java.util.OptionalInt;

public class ResizableDeque {
    private static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE;
    private static final int MINIMUM_CAPACITY = 16;

    private static int nextCapacity(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    private int capacity;
    private int[] items;
    private int head;
    private int tail;

    public ResizableDeque() {
        this(MINIMUM_CAPACITY);
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
        items[getAndIncrementHead()] = item;
        if (isFull()) resize();
    }

    public OptionalInt removeFront() {
        return isEmpty() ? OptionalInt.empty() : OptionalInt.of(items[decrementAndGetHead()]);
    }

    public void addBack(int item) {
        items[getAndIncrementTail()] = item;
        if (isFull()) resize();
    }

    public OptionalInt removeBack() {
        return isEmpty() ? OptionalInt.empty() : OptionalInt.of(items[decrementAndGetTail()]);
    }

    public boolean contains(int item) {
        for (int index = tailElementIndex(); index != headElementIndex() + 1; index = next(index)) {
            if (items[index] == item) {
                return true;
            }
        }
        return false;
    }

    private void resize() {
        int newCapacity = nextCapacity(capacity + 1);
        int[] newItems = new int[newCapacity];
        System.arraycopy(items, 0, newItems, 0, fromBeginning());
        System.arraycopy(items, tailElementIndex(), newItems, newCapacity - fromEnd(), fromEnd());
        tail = newCapacity - capacity + tail;
        capacity = newCapacity;
        items = newItems;
    }

    private int fromEnd() {
        return capacity - tail - 1;
    }

    private int fromBeginning() {
        return head;
    }

    private int getAndIncrementHead() {
        int prev = head;
        head = head + 1 & capacity - 1;
        return prev;
    }

    private int decrementAndGetHead() {
        return head = (head - 1) & (capacity - 1);
    }

    private int getAndIncrementTail() {
        int prev = tail;
        tail = tail - 1 & capacity - 1;
        return prev;
    }

    private int decrementAndGetTail() {
        return tail = tail + 1 & capacity - 1;
    }

    private boolean isEmpty() {
        return (tail - head & capacity - 1) == capacity - 1;
    }

    private boolean isFull() {
        return head == tail;
    }

    private int tailElementIndex() {
        return tail + 1 & capacity - 1;
    }

    private int headElementIndex() {
        return head - 1 & capacity - 1;
    }

    private int next(int index) {
        return index + 1 & capacity - 1;
    }
}
