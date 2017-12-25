package kata.java;

import java.util.Optional;

public class LinkedArrayDeque {
    private Segment head;
    private Segment tail;

    public LinkedArrayDeque() {
        Segment segment = new Segment(true);
        head = segment;
        tail = segment;
    }

    public Optional<Integer> removeFront() {
        if (tail.isEmpty() && tail != head) {
            tail = tail.prev;
            tail.next = null;
        }
        return tail.removeFront();
    }

    public void addFront(Integer item) {
        if (tail.isFull()) {
            tail.next = new Segment(true);
            tail.next.prev = tail;
            tail = tail.next;
        }
        tail.addFront(item);
    }

    public Optional<Integer> removeBack() {
        if (head.isEmpty() && head != tail) {
            head = head.next;
            head.prev = null;
        }
        return head.removeBack();
    }

    public void addBack(Integer item) {

    }

    private static class Segment {
        private static final int MAX_CAPACITY = 16;
        private final boolean order;
        private Integer[] items;
        private int size;
        private int current;
        private int low;
        private Segment next;
        private Segment prev;

        Segment(boolean order) {
            this.order = order;
            items = new Integer[MAX_CAPACITY];
            current = order ? 0 : MAX_CAPACITY - 1;
        }

        Optional<Integer> removeFront() {
            if (isEmpty()) {
                return Optional.empty();
            }
            size--;
            current = order ? current - 1 : current + 1;
            return Optional.of(items[current]);
        }

        void addFront(Integer item) {
            size++;
            items[current] = item;
            current = order ? current + 1 : current - 1;
        }

        Optional<Integer> removeBack() {
            if (isEmpty()) {
                return Optional.empty();
            }
            size--;
            Optional<Integer> item = Optional.of(items[low]);
            low = order ? low + 1 : low - 1;
            return item;
        }

        boolean isEmpty() {
            return size == 0;
        }

        boolean isFull() {
            return size == MAX_CAPACITY;
        }
    }
}
