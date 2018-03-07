package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.kata.PersistentQueue.Node;

class PersistentQueueTest {

  private PersistentQueue queue;

  @BeforeEach
  void setUp() {
    queue = new PersistentQueue();
  }

  @Test
  void enqueueDequeueOneItem() throws Exception {
    PersistentQueue one = queue.enqueue(1);

    Node node = one.dequeue();

    assertThat(node.item).isEqualTo(1);
  }

  @Test@Disabled
  void enqueueDequeueManyItems() throws Exception {
    PersistentQueue q = queue;
    q = q.enqueue(1);
    q = q.enqueue(2);
    q = q.enqueue(3);

    Node node;
    node = q.dequeue();
    assertThat(node.item).isEqualTo(1);

    q = node.queue;
    node = q.dequeue();
    assertThat(node.item).isEqualTo(2);

    q = node.queue;
    node = q.dequeue();
    assertThat(node.item).isEqualTo(3);
  }
}
