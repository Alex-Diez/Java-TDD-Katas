package ua.kata;

public class PersistentQueue<V> {
  private final V value;
  private final PersistentQueue<V> prev;

  public PersistentQueue() {
    this(null, null);
  }

  private PersistentQueue(V value, PersistentQueue<V> prev) {
    this.value = value;
    this.prev = prev;
  }

  public Node<V> dequeue() {
    return new Node<>(prev, value);
  }

  public PersistentQueue enqueue(V value) {
    return new PersistentQueue<>(value, this);
  }

  public static class Node<V> {
    public final PersistentQueue<V> queue;
    public final V value;

    Node(PersistentQueue<V> queue, V value) {
      this.queue = queue;
      this.value = value;
    }
  }
}
