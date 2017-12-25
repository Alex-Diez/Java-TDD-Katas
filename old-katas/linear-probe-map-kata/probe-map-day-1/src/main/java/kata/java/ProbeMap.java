package kata.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProbeMap {
    private static final int MIN_CAPACITY = 16;
    private static final int MAX_CAPACITY = Integer.MIN_VALUE >>> 2;

    private static int alignCapacity(int initCapacity) {
        int n = initCapacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n < 0 ? MIN_CAPACITY : n >= MAX_CAPACITY ? MAX_CAPACITY : n + 1;
    }

    private final int missedValue;
    private int capacity;
    private int[] values;
    private int[] keys;

    public ProbeMap() {
        this(MIN_CAPACITY);
    }

    public ProbeMap(int initCapacity) {
        this(initCapacity, 0);
    }

    public ProbeMap(int initCapacity, int missedValue) {
        this.capacity = alignCapacity(initCapacity);
        this.missedValue = missedValue;
        this.values = new int[capacity];
        this.keys = new int[capacity];
    }

    public int capacity() {
        return capacity;
    }

    public void put(int key, int value) {
        int index = key & capacity - 1;
        int mapKey = keys[index];
        if (mapKey == key) {
            values[index] = value;
        } else {
            while (values[index] != missedValue) {
                index = index + 1 & capacity - 1;
            }
            values[index] = value;
            keys[index] = key;
        }
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

    public int missingValue() {
        return missedValue;
    }
}
