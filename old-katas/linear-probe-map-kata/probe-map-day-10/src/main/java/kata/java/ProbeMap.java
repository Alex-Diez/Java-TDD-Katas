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
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n < MIN_CAPACITY ? MIN_CAPACITY : n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }

    private final int missedValue;
    private int size;
    private int capacity;
    private int[] keys;
    private int[] values;

    public ProbeMap(int initCapacity, int missedValue) {
        this.missedValue = missedValue;
        capacity = alignCapacity(initCapacity);
        keys = createArrayFilledMissedValues(capacity);
        values = createArrayFilledMissedValues(capacity);
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
        if (keys[index] == key) {
            values[index] = value;
        } else {
            index = findFreeSlot(capacity - 1, values, index);
            values[index] = value;
            keys[index] = key;
            size += 1;
            if (isFull()) {
                resize();
            }
        }
    }

    private void resize() {
        int newCapacity = capacity << 1;
        int[] newKeys = createArrayFilledMissedValues(newCapacity);
        int[] newValues = createArrayFilledMissedValues(newCapacity);

        for (int i = 0; i < capacity; i++) {
            if (keys[i] != missedValue) {
                int newIndex = findFreeSlot(newCapacity - 1, newValues, keys[i] & newCapacity - 1);
                newKeys[newIndex] = keys[i];
                newValues[newIndex] = values[i];
            }
        }

        capacity = newCapacity;
        values = newValues;
        keys = newKeys;
    }

    private int findFreeSlot(int mask, int[] items, int start) {
        int newIndex = start;
        while (items[newIndex] != missedValue) {
            newIndex = newIndex + 1 & mask;
        }
        return newIndex;
    }

    private int[] createArrayFilledMissedValues(int newCapacity) {
        int[] newKeys = new int[newCapacity];
        Arrays.fill(newKeys, missedValue);
        return newKeys;
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
        return () -> IntStream.of(keys).filter(key -> key != missedValue).iterator();
    }

    public Iterable<Integer> values() {
        return () -> IntStream.of(values).filter(value -> value != missedValue).iterator();
    }
}
