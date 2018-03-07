package ua.kata;

public class PersistentQueue {
  private final Integer value;
  private final PersistentQueue prev;

  public PersistentQueue() {
    this(null, null);
  }

  private PersistentQueue(Integer value, PersistentQueue prev) {
    this.value = value;
    this.prev = prev;
  }

  public PersistentQueue enqueue(Integer value) {
    return new PersistentQueue(value, this);
  }

  public Node dequeue() {
    return new Node(value, prev);
  }

  public static class Node {
    public final Integer item;
    public final PersistentQueue queue;

    public Node(Integer item, PersistentQueue queue) {
      this.item = item;
      this.queue = queue;
    }
  }
}
