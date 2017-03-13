package kata.java;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ProbeMap {
    private static final int MIN_CAPACITY = 16;
    private static final int MAX_CAPACITY = Integer.MIN_VALUE >>> 1;
    private static final int DEFAULT_MISSED_VALUE = Integer.MIN_VALUE;

    private static int alignCapacity(int initCapacity) {
        int n = initCapacity - 1;
        n |= n >>> 1;
        n |= n >>> 1;
        n |= n >>> 1;
        n |= n >>> 1;
        n |= n >>> 1;
        return n < MIN_CAPACITY ? MIN_CAPACITY : n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }

    private final int missedValue;
    private int capacity;
    private int size;
    private int[] values;
    private int[] keys;

    public ProbeMap(int initCapacity, int missedValue) {
        this.missedValue = missedValue;
        capacity = alignCapacity(initCapacity);
        values = new int[capacity];
        Arrays.fill(values, missedValue);
        keys = new int[capacity];
        Arrays.fill(keys, missedValue);
        size = 0;
    }

    public ProbeMap(int initCapacity) {
        this(initCapacity, DEFAULT_MISSED_VALUE);
    }

    public ProbeMap() {
        this(MIN_CAPACITY);
    }

    public int capacity() {
        return capacity;
    }

    public void put(int key, int value) {
        int index = key & capacity - 1;
        if(keys[index] == key) {
            values[index] = value;
        } else {
            while (values[index] != missedValue) {
                index = index + 1 & capacity - 1;
            }
            values[index] = value;
            keys[index] = key;
            size += 1;
        }
        if (isFull()) {
            resize();
        }
    }

    private void resize() {
        int newCapacity = capacity << 1;
        int[] newKeys = new int[newCapacity];
        Arrays.fill(newKeys, missedValue);
        int[] newValues = new int[newCapacity];
        Arrays.fill(newValues, missedValue);

        for (int i = 0; i < capacity; i++) {
            if (keys[i] != missedValue) {
                int newIndex = keys[i] & newCapacity - 1;
                while (newValues[newIndex] != missedValue) {
                    newIndex = newIndex + 1 & newCapacity - 1;
                }
                newKeys[newIndex] = keys[i];
                newValues[newIndex] = values[i];
            }
        }

        capacity = newCapacity;
        keys = newKeys;
        values = newValues;
    }

    private boolean isFull() {
        return size == capacity - 1;
    }

    public OptionalInt get(int key) {
        int index = key & capacity - 1;
        if (keys[index] == missedValue) {
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
        return () -> IntStream.of(keys).filter(value -> value != missedValue).iterator();
    }

    public Iterable<Integer> values() {
        return () -> IntStream.of(values).filter(value -> value != missedValue).iterator();
    }
}
