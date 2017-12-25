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
        if (head != null) {
            head.prev = newHead;
        } else {
            tail = newHead;
        }
        head = newHead;
        size += 1;
    }

    public OptionalInt removeFront() {
        if (head == null) {
            return OptionalInt.empty();
        }
        Node oldHead = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        oldHead.next = null;
        size -= 1;
        return OptionalInt.of(oldHead.item);
    }

    public void addBack(int item) {
        Node newTail = new Node(item);
        newTail.prev = tail;
        if (tail != null) {
            tail.next = newTail;
        } else {
            head = newTail;
        }
        size += 1;
        tail = newTail;
    }

    public OptionalInt removeBack() {
        if (tail == null) {
            return OptionalInt.empty();
        }
        Node oldTail = tail;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        size -= 1;
        oldTail.prev = null;
        return OptionalInt.of(oldTail.item);
    }

    public OptionalInt peekFront() {
        return head == null ? OptionalInt.empty() : OptionalInt.of(head.item);
    }

    public OptionalInt peekBack() {
        return tail == null ? OptionalInt.empty() : OptionalInt.of(tail.item);
    }

    @Override
    public Iterator<Integer> iterator() {
        return Stream.iterate(head, node -> node.next).mapToInt(node -> node.item).limit(size).iterator();
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
