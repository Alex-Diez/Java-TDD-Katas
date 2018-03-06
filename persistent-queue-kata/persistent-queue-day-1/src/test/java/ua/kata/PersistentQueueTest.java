package ua.kata;

import org.junit.jupiter.api.BeforeEach;
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
  void dequeueFromEmptyQueue() throws Exception {
    Node item = queue.dequeue();

    assertThat(item.queue).isNull();
    assertThat(item.value).isNull();
  }
  
  @Test
  void enqueueDequeueOneItem() throws Exception {
    PersistentQueue one = queue.enqueue(1);

    Node item = one.dequeue();

    assertThat(item.queue).isEqualTo(queue);
    assertThat(item.value).isEqualTo(1);
  }

  @Test
  void enqueueDequeueManyItems() throws Exception {
    PersistentQueue q = queue;
    for (int i = 0; i < 3; i++) {
      q = q.enqueue(i);
    }

    for (int i = 2; i > -1; i--) {
      Node item = q.dequeue();

      assertThat(item.value).isEqualTo(i);

      q = item.queue;
    }
  }
}
