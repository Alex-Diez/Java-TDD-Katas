package kata.java;

import org.junit.Test;

import java.util.OptionalInt;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class ResizableDequeTest {

    @Test
    public void createsDequeWithDefaultCapacity() throws Exception {
        assertThat(new ResizableDeque().capacity(), is(16));
    }

    @Test
    public void createsDequeWithLesserThanDefaultCapacity() throws Exception {
        assertThat(new ResizableDeque(10).capacity(), is(16));
    }
    
    @Test
    public void createDequeWithGreaterThanDefaultCapacity() throws Exception {
        assertThat(new ResizableDeque(20).capacity(), is(32));
    }
    
    @Test
    public void addFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addFront(1);

        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void addFrontMany() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);

        assertThat(deque.removeFront(), is(OptionalInt.of(3)));
        assertThat(deque.removeFront(), is(OptionalInt.of(2)));
        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void removeBack() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);

        assertThat(deque.removeBack(), is(OptionalInt.of(1)));
        assertThat(deque.removeBack(), is(OptionalInt.of(2)));
        assertThat(deque.removeBack(), is(OptionalInt.of(3)));
        assertThat(deque.removeBack(), is(OptionalInt.empty()));
    }

    @Test
    public void addBackMany() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addBack(1);
        deque.addBack(2);
        deque.addBack(3);

        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.of(2)));
        assertThat(deque.removeFront(), is(OptionalInt.of(3)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void containsAll() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int item = 0; item < 5; item++) {
            deque.addFront(item);
            deque.addBack(item + 5);
        }

        assertThat(deque, containsInAnyOrder(IntStream.range(0, 10).boxed().toArray()));
    }

    @Test
    public void addFrontMoreThanCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        addMany(deque::addFront, 0, 20);

        assertThat(deque.capacity(), is(32));
        assertThat(deque, containsInAnyOrder(IntStream.range(0, 20).boxed().toArray()));
    }

    @Test
    public void addBackMoreThanCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        addMany(deque::addBack, 0, 20);

        assertThat(deque.capacity(), is(32));
        assertThat(deque, containsInAnyOrder(IntStream.range(0, 20).boxed().toArray()));
    }

    @Test
    public void addFrontRemoveBackAddFrontRemoveBackAddFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        addMany(deque::addFront, 0, 20);
        removeMany(deque::removeBack, 10);
        addMany(deque::addFront, 20, 40);
        removeMany(deque::removeBack, 10);
        addMany(deque::addFront, 40, 60);

        assertThat(deque.capacity(), is(64));
        assertThat(deque, containsInAnyOrder(IntStream.range(20, 60).boxed().toArray()));
    }

    @Test
    public void addBackRemoveFrontAddBackRemoveFrontAddBack() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        addMany(deque::addBack, 0, 20);
        removeMany(deque::removeFront, 10);
        addMany(deque::addBack, 20, 40);
        removeMany(deque::removeFront, 10);
        addMany(deque::addBack, 40, 60);

        assertThat(deque.capacity(), is(64));
        assertThat(deque, containsInAnyOrder(IntStream.range(20, 60).boxed().toArray()));
    }

    @Test
    public void shrinkWhenRemoveFrontThreeQuartersOfItems() throws Exception {
        ResizableDeque deque = new ResizableDeque(64);

        addMany(deque::addBack, 0, 64 - 2);
        removeMany(deque::removeFront, 64 - 4);

        assertThat(deque.capacity(), is(16));
        assertThat(deque, containsInAnyOrder(IntStream.range(64 - 4, 64 - 2).boxed().toArray()));
    }

    @Test
    public void shrinkWhenRemoveBackThreeQuartersOfItems() throws Exception {
        ResizableDeque deque = new ResizableDeque(64);

        addMany(deque::addFront, 0, 64 - 2);
        removeMany(deque::removeBack, 64 - 4);

        assertThat(deque.capacity(), is(16));
        assertThat(deque, containsInAnyOrder(IntStream.range(64 - 4, 64 - 2).boxed().toArray()));
    }

    private void addMany(IntConsumer consumer, int from, int to) {
        IntStream.range(from, to).forEach(consumer);
    }

    private void removeMany(Operation operation, int times) {
        for (int i = 0; i < times; i++) {
            operation.perform();
        }
    }

    private interface Operation {
        void perform();
    }
}
