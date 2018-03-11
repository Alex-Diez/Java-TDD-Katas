package ua.kata;

import java.util.Optional;

public class PersistentQueue {

  private final Node head;
  private final Node tail;

  public PersistentQueue() {
    this(null, null);
  }

  private PersistentQueue(Node head, Node tail) {
    this.head = head;
    this.tail = tail;
  }

  public Tuple dequeue() {
    return Optional.ofNullable(head).map(Tuple::new).orElse(new Tuple(null));
  }

  public PersistentQueue enqueue(Integer value) {
    return Optional.ofNullable(head)
        .map(h -> new PersistentQueue(h, new Node(value, this)))
        .orElseGet(() -> new PersistentQueue(new Node(value, this), new Node(value, this)));
  }

  @Override
  public String toString() {
    return "Queue {" +
        "head: " + head +
        ", tail:" + tail +
        '}';
  }

  private static class Node {
    final Integer value;
    final PersistentQueue prev;

    Node(Integer value, PersistentQueue prev) {
      this.value = value;
      this.prev = prev;
    }

    @Override
    public String toString() {
      return "Node[" + value + ']';
    }
  }

  public static class Tuple {
    private final Node value;

    Tuple(Node value) {
      this.value = value;
    }

    public Optional<PersistentQueue> queue() {
      return Optional.ofNullable(value).map(node -> node.prev);
    }

    public Optional<Integer> value() {
      return Optional.ofNullable(value).map(n -> n.value);
    }
  }
}
