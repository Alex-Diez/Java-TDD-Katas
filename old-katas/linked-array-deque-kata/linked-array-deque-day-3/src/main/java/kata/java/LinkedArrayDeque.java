package kata.java;

import java.util.Optional;

public class LinkedArrayDeque {
    private Segment head;
    private Segment tail;

    public LinkedArrayDeque() {
        Segment segment = new Segment();
        head = segment;
        tail = segment;
    }

    public void addBack(Integer item) {
        head.prev = new Segment();
        head.prev.next = head;
        head = head.prev;
//        head.addBack(item);
    }

    public Optional<Integer> removeBack() {
        if (head.low == head.high && head.high == head.items.length && head != tail) {
            head = head.next;
        }
        return head.removeBack();
    }

    public void addFront(Integer item) {
        if (tail.high == tail.items.length) {
            tail.next = new Segment();
            tail.next.prev = tail;
            tail = tail.next;
        }
        tail.addFront(item);
    }

    public Optional<Integer> removeFront() {
        if (tail.high == 0 && tail != head) {
            tail = tail.prev;
        }
        return tail.removeFront();
    }

    private static class Segment {
        private final Integer[] items;
        private int high;
        private int low;
        private Segment next;
        private Segment prev;

        Segment() {
            items = new Integer[16];
        }

        Optional<Integer> removeBack() {
            if (high > low) {
                return Optional.of(items[low++]);
            }
            return Optional.empty();
        }

        void addFront(Integer item) {
            items[high++] = item;
        }

        public Optional<Integer> removeFront() {
            if (high > low) {
                return Optional.of(items[--high]);
            }
            return Optional.empty();
        }
    }
}
