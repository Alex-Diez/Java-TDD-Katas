package kata.java;

import org.junit.Test;

import java.util.OptionalInt;
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
    public void createsDequeWithGreaterThanDefaultCapacity() throws Exception {
        assertThat(new ResizableDeque(20).capacity(), is(32));
    }

    @Test
    public void addToFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addFront(1);

        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void addManyToFront() throws Exception {
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
    public void addManyBack() throws Exception {
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
    public void addFrontMoreThanCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int item = 0; item < 20; item++) {
            deque.addFront(item);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(deque, containsInAnyOrder(IntStream.range(0, 20).boxed().toArray()));
    }

    @Test
    public void addBackMoreThanCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int item = 0; item < 20; item++) {
            deque.addBack(item);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(deque, containsInAnyOrder(IntStream.range(0, 20).boxed().toArray()));
    }

    @Test
    public void addFrontBackMoreThanCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int item = 0; item < 10; item++) {
            deque.addFront(item);
            deque.addBack(item + 10);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(deque, containsInAnyOrder(IntStream.range(0, 20).boxed().toArray()));
    }

    @Test
    public void addFrontRemoveBackAddFrontRemoveBackAddFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int item = 0; item < 20; item++) {
            deque.addFront(item);
        }

        for (int item = 0; item < 10; item++) {
            deque.removeBack();
        }

        for (int item = 20; item < 40; item++) {
            deque.addFront(item);
        }

        for (int item = 0; item < 10; item++) {
            deque.removeBack();
        }

        for (int item = 40; item < 60; item++) {
            deque.addFront(item);
        }

        assertThat(deque.capacity(), is(64));
        assertThat(deque, containsInAnyOrder(IntStream.range(20, 60).boxed().toArray()));
    }

    @Test
    public void addBackRemoveFrontAddBackRemoveFrontAddBack() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int item = 0; item < 20; item++) {
            deque.addBack(item);
        }

        for (int i = 0; i < 10; i++) {
            deque.removeFront();
        }

        for (int item = 20; item < 40; item++) {
            deque.addBack(item);
        }

        for (int item = 0; item < 10; item++) {
            deque.removeFront();
        }

        for (int item = 40; item < 60; item++) {
            deque.addBack(item);
        }

        assertThat(deque.capacity(), is(64));
        assertThat(deque, containsInAnyOrder(IntStream.range(20, 60).boxed().toArray()));
    }
}
