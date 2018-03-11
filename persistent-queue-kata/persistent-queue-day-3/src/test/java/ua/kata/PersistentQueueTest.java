package ua.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.kata.PersistentQueue.Tuple;

class PersistentQueueTest {
  @Test
  void dequeueFromEmptyQueue() throws Exception {
    PersistentQueue queue = new PersistentQueue();

    Tuple ret = queue.dequeue();

    assertThat(ret.value()).isEmpty();
  }
}
