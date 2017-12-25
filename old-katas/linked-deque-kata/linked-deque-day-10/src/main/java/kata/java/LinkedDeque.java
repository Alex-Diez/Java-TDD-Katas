package kata.java;

import java.util.Iterator;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class LinkedDeque implements Iterable<Integer> {
    private Node head;
    private Node tail;
    private int size;

    public void addFront(int item) {
        Node newHead = new Node(item);
        newHead.next = head;
        updateFront(newHead);
        size += 1;
        head = newHead;
    }

    public OptionalInt removeFront() {
        if (head == null) {
            return OptionalInt.empty();
        }
        Node oldHead = head;
        head = head.next;
        updateFront(null);
        oldHead.next = null;
        size -= 1;
        return OptionalInt.of(oldHead.item);
    }

    private void updateFront(Node newHead) {
        if (head != null) {
            head.prev = newHead;
        } else {
            tail = newHead;
        }
    }

    public void addBack(int item) {
        Node newTail = new Node(item);
        newTail.prev = tail;
        updateBack(newTail);
        size += 1;
        tail = newTail;
    }

    public OptionalInt removeBack() {
        if (tail == null) {
            return OptionalInt.empty();
        }
        Node oldTail = tail;
        tail = tail.prev;
        updateBack(null);
        oldTail.prev = null;
        size -= 1;
        return OptionalInt.of(oldTail.item);
    }

    private void updateBack(Node newTail) {
        if (tail != null) {
            tail.next = newTail;
        } else {
            head = newTail;
        }
    }

    public OptionalInt peekFront() {
        return head == null ? OptionalInt.empty() : OptionalInt.of(head.item);
    }

    public OptionalInt peekBack() {
        return tail == null ? OptionalInt.empty() : OptionalInt.of(tail.item);
    }

    @Override
    public Iterator<Integer> iterator() {
        return Stream.iterate(head, node -> node.next).limit(size).mapToInt(node -> node.item).iterator();
    }

    private static final class Node {
        private final int item;
        private Node next;
        private Node prev;

        public Node(int item) {
            this.item = item;
        }
    }
}
