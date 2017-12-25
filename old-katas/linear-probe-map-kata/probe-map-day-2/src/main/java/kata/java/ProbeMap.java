package kata.java;

import java.util.Iterator;
import java.util.OptionalInt;
import java.util.Set;

public class ProbeMap {
    private static final int MAX_CAPACITY = Integer.MIN_VALUE >>> 1;
    private static final int MIN_CAPACITY = 16;

    private final int missedValue;
    private int capacity;
    private int[] values;
    private int[] keys;
    private int size;

    public ProbeMap() {
        this(16);
    }

    public ProbeMap(int initCapacity) {
        this(initCapacity, 0);
    }

    public ProbeMap(int initCapacity, int missedValue) {
        this.capacity = alignCapacity(initCapacity);
        this.missedValue = missedValue;
        this.values = new int[capacity];
        this.keys = new int [capacity];
    }

    private static int alignCapacity(int initCapacity) {
        int n = initCapacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n < 0 ? MIN_CAPACITY : n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }

    public int capacity() {
        return capacity;
    }

    public void put(int key, int value) {
        size += 1;
        int index = key & capacity - 1;
        while (values[index] != missedValue) {
            index = index + 1 & capacity - 1;
        }
        keys[index] = key;
        values[index] = value;
    }

    public OptionalInt get(int key) {
        int index = key & capacity - 1;
        int value = values[index];
        if (value == missedValue) {
            return OptionalInt.empty();
        } else {
            while (keys[index] != key) {
                index = index + 1 & capacity - 1;
            }
            return OptionalInt.of(values[index]);
        }
    }

    public int missedValue() {
        return missedValue;
    }

    public Iterable<Integer> keys() {
        return createIterableOf(keys);
    }

    public Iterable<Integer> values() {
        return createIterableOf(values);
    }

    private Iterable<Integer> createIterableOf(int[] array) {
        return () -> new Iterator<Integer>() {
            private final int[] items;
            private int index;

            {
                this.items = new int[size];
                int i = 0;
                for (int value : array) {
                    if (value != missedValue) {
                        this.items[i] = value;
                        i += 1;
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return index < items.length;
            }

            @Override
            public Integer next() {
                return items[index++];
            }
        };
    }
}
