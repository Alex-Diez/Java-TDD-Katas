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
        Optional<Integer> ret = head.removeBack();
        if (head.head == head.items.length) {
            head = head.next;
        }
        return ret;
    }

    public void addFront(Integer item) {
        tail.addFront(item);
        if (tail.tail == tail.items.length) {
            tail.next = new Segment();
            tail = tail.next;
        }
    }

    public Optional<Integer> removeFront() {
        Optional<Integer> ret = tail.removeFront();
        if (tail.tail == 0) {
            if (tail != head) {
                tail = tail.prev;
            }
        }
        return ret;
    }

    private static class Segment {
        private final Integer[] items;
        private int head;
        private int tail;
        private Segment next;
        private Segment prev;

        Segment() {
            items = new Integer[16];
        }

        Optional<Integer> removeBack() {
            Integer item = items[head];
            if (item != null) {
                head++;
                return Optional.of(item);
            }
            return Optional.empty();
        }

        Optional<Integer> removeFront() {
            tail--;
            if (tail > -1) {
                return Optional.of(items[tail]);
            }
            return Optional.empty();
        }

        void addFront(Integer item) {
            items[tail++] = item;
        }
    }

}
