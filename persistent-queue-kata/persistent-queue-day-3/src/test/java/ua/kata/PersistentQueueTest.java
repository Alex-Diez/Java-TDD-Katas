package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.kata.PersistentQueue.Tuple;

class PersistentQueueTest {

  private PersistentQueue empty;

  @BeforeEach
  void setUp() {
    empty = new PersistentQueue();
  }

  @Test
  void dequeueFromEmptyQueue() throws Exception {
    assertThat(empty.dequeue().value()).isEmpty();
    assertThat(empty.dequeue().queue()).isEmpty();
  }

  @Test
  void enqueueDequeueOneItem() throws Exception {
    PersistentQueue single = empty.enqueue(1);

    Tuple one = single.dequeue();

    assertThat(one.value()).hasValue(1);

    PersistentQueue prev = one.queue().get();

    Tuple empty = prev.dequeue();

    assertThat(empty.value()).isEmpty();
  }

  @Test@Disabled
  void enqueueDequeueManyItems() throws Exception {
    PersistentQueue queue = empty;
    for (int i = 1; i < 4; i++) {
      queue = queue.enqueue(i);
    }

    Tuple ret;

    ret = queue.dequeue();
    assertThat(ret.value()).hasValue(1);

    queue = ret.queue().get();
    ret = queue.dequeue();
    assertThat(ret.value()).hasValue(2);

    queue = ret.queue().get();
    ret = queue.dequeue();
    assertThat(ret.value()).hasValue(3);

    queue = ret.queue().get();
    ret = queue.dequeue();
    assertThat(ret.value()).isEmpty();
  }
}
