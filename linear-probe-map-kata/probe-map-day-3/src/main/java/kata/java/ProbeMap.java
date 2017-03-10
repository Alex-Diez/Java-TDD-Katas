package kata.java;

import java.util.Iterator;
import java.util.OptionalInt;
import java.util.function.IntPredicate;

public class ProbeMap {
    private static final int MIN_CAPACITY = 16;
    private static final int MAX_CAPACITY = Integer.MIN_VALUE >>> 1;

    private static int alignCapacity(int initCapacity) {
        int n = initCapacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n < 16 ? 16 : n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }

    private final int missedValue;
    private int capacity;
    private int size;
    private int[] values;
    private int[] keys;

    public ProbeMap() {
        this(MIN_CAPACITY);
    }

    public ProbeMap(int initCapacity) {
        this(initCapacity, Integer.MIN_VALUE);
    }

    public ProbeMap(int initCapacity, int missedValue) {
        capacity = alignCapacity(initCapacity);
        values = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            values[i] = missedValue;
        }
        keys = new int[capacity];
        size = 0;
        this.missedValue = missedValue;
    }

    public int capacity() {
        return capacity;
    }

    public void put(int key, int value) {
        int index = key & capacity - 1;
        int oldKey = keys[index];
        if (oldKey == key)
            values[index] = value;
        else {
            while (values[index] != missedValue) {
                index = index + 1 & capacity - 1;
            }
            keys[index] = key;
            values[index] = value;
            size += 1;
        }
    }

    public OptionalInt get(int key) {
        int index = key & capacity - 1;
        while (values[index] != missedValue) {
            if (keys[index] == key) {
                return OptionalInt.of(values[index]);
            }
            index = index + 1 & capacity - 1;
        }
        return OptionalInt.empty();
    }

    public int missedValue() {
        return missedValue;
    }

    public Iterable<Integer> keys() {
        return createIteratorOf(keys, valuePredicate());
    }

    private IntPredicate valuePredicate() {
        return index -> values[index] != missedValue;
    }

    private Iterable<Integer> createIteratorOf(int[] array, IntPredicate predicate) {
        return () -> new Iterator<Integer>() {
            int[] items = new int[size];
            int index = 0;
            {
                int index = 0;
                for (int i = 0; i < capacity; i++) {
                    if (predicate.test(i)) {
                        items[index++] = array[i];
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

    public Iterable<Integer> values() {
        return createIteratorOf(values, valuePredicate());
    }
}
