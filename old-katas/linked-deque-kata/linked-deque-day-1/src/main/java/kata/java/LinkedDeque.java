package kata.java;

import java.util.Iterator;
import java.util.OptionalInt;
import java.util.stream.IntStream;
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
        }
        head = newHead;
        if (tail == null) {
            tail = newHead;
        }
        size += 1;
    }

    public OptionalInt removeFront() {
        if (isEmpty()) {
            return OptionalInt.empty();
        }
        Node oldHead = head;
        head = head.next;
        oldHead.next = null;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size -= 1;
        return OptionalInt.of(oldHead.item);
    }

    public void addBack(int item) {
        Node newTail = new Node(item);
        newTail.prev = tail;
        if (tail != null) {
            tail.next = newTail;
        }
        tail = newTail;
        if (head == null) {
            head = newTail;
        }
        size += 1;
    }

    public OptionalInt peekFront() {
        return head == null ? OptionalInt.empty() : OptionalInt.of(head.item);
    }

    public OptionalInt removeBack() {
        if (isEmpty()) {
            return OptionalInt.empty();
        }
        Node oldTail = tail;
        tail = tail.prev;
        oldTail.next = null;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        size -= 1;
        return OptionalInt.of(oldTail.item);
    }

    private boolean isEmpty() {
        return size == 0;
    }

    public OptionalInt peekBack() {
        return tail == null ? OptionalInt.empty() : OptionalInt.of(tail.item);
    }

    @Override
    public Iterator<Integer> iterator() {
        return Stream.iterate(head, node -> node.next).limit(size).mapToInt(node -> node.item).iterator();
    }

    private static class Node {
        final int item;
        Node next;
        Node prev;

        Node(int item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    '}';
        }
    }
}
