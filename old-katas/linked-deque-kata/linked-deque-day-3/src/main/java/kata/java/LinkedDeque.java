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
        } else {
            Node oldHead = head;
            head = head.next;
            oldHead.next = null;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
            return OptionalInt.of(oldHead.item);
        }
    }

    public void addBack(int item) {
        Node newTail = new Node(item);
        newTail.prev = tail;
        if (tail != null) {
            tail.next = newTail;
        } else {
            head = newTail;
        }
        tail = newTail;
        size += 1;
    }

    public OptionalInt removeBack() {
        if (tail== null) {
            return OptionalInt.empty();
        } else {
            Node oldTail = tail;
            tail = tail.prev;
            oldTail.prev = null;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
            return OptionalInt.of(oldTail.item);
        }
    }

    public OptionalInt peekFront() {
        return head != null ? OptionalInt.of(head.item) : OptionalInt.empty();
    }

    public OptionalInt peekBack() {
        return tail != null ? OptionalInt.of(tail.item) : OptionalInt.empty();
    }

    @Override
    public Iterator<Integer> iterator() {
        return Stream.iterate(head, node -> node.next).limit(size).mapToInt(node -> node.item).iterator();
    }

    private static final class Node {
        final int item;
        Node next;
        Node prev;

        public Node(int item) {
            this.item = item;
        }
    }
}
