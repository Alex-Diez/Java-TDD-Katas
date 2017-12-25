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

    public Optional<Integer> removeBack() {
        if (head.low >= head.high && head != tail) {
            head = head.next;
        }
        return head.removeBack();
    }

    public Optional<Integer> removeFront() {
        if (tail.high == 0 && tail != head) {
            tail = tail.prev;
            tail.next = null;
        }
        return tail.removeFront();
    }

    public void addBack(Integer item) {
        if (head.low == 0) {
            head.prev = new Segment();
            head.prev.next = head;
            head = head.prev;
        }
        head.addBack(item);
    }

    public void addFront(Integer item) {
        if (tail.high == tail.items.length) {
            tail.next = new Segment();
            tail.next.prev = tail;
            tail = tail.next;
        }
        tail.addFront(item);
    }

    private static class Segment {
        private Integer[] items;
        private int low;
        private int high;
        private Segment next;
        private Segment prev;

        Segment() {
            items = new Integer[16];
        }

        void addBack(Integer item) {

        }

        Optional<Integer> removeBack() {
            if (low < high) {
                return Optional.of(items[low++]);
            }
            return Optional.empty();
        }

        void addFront(Integer item) {
            items[high++] = item;
        }

        Optional<Integer> removeFront() {
            if (high > low && high != 0) {
                return Optional.of(items[--high]);
            }
            return Optional.empty();
        }
    }
}
