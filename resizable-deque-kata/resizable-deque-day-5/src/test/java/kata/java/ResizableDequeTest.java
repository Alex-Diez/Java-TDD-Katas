package kata.java;

import org.junit.Test;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class ResizableDequeTest {

    @Test
    public void createDequeWithDefaultCapacity() throws Exception {
        assertThat(new ResizableDeque().capacity(), is(16));
    }

    @Test
    public void createDequeLesserThanDefaultCapacity() throws Exception {
        assertThat(new ResizableDeque(10).capacity(), is(16));
    }

    @Test
    public void createDequeMoreThanDefaultCapacity() throws Exception {
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

        deque.addBack(1);
        deque.addFront(2);
        deque.addBack(3);
        deque.addFront(4);
        deque.addBack(5);
        deque.addFront(6);

        assertThat(deque, containsInAnyOrder(IntStream.range(1, 7).boxed().toArray()));
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

        for (int item = 0; item < 20; item++) {
            deque.addFront(item);
            deque.addBack(item + 20);
        }

        assertThat(deque.capacity(), is(64));
        assertThat(deque, containsInAnyOrder(IntStream.range(0, 40).boxed().toArray()));
    }

    @Test
    public void addFrontRemoveBackAddFrontRemoveBackAddFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int item = 0; item < 20; item++) {
            deque.addFront(item);
        }

        for (int i = 0; i < 10; i++) {
            deque.removeBack();
        }

        for (int item = 20; item < 40; item++) {
            deque.addFront(item);
        }

        for (int i = 0; i < 10; i++) {
            deque.removeBack();
        }

        for (int item = 40; item < 60; item++) {
            deque.addFront(item);
        }

        assertThat(deque.capacity(), is(64));
        assertThat(deque, containsInAnyOrder(IntStream.range(20, 60).boxed().toArray()));
    }

    @Test
    public void shrinkCapacityInHalfAddBackRemoveFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int item = 0; item < 60; item++) {
            deque.addBack(item);
        }

        assertThat(deque.capacity(), is(64));
        assertThat(deque, containsInAnyOrder(IntStream.range(0, 60).boxed().toArray()));

        for (int i = 0; i < 50; i++) {
            deque.removeFront();
        }

        assertThat(deque.capacity(), is(32));
        assertThat(deque, containsInAnyOrder(IntStream.range(50, 60).boxed().toArray()));
    }

    @Test
    public void shrinkCapacityInHalfAddFrontRemoveBack() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int item = 0; item < 60; item++) {
            deque.addFront(item);
        }

        assertThat(deque.capacity(), is(64));
        assertThat(deque, containsInAnyOrder(IntStream.range(0, 60).boxed().toArray()));

        for (int i = 0; i < 50; i++) {
            deque.removeBack();
        }

        assertThat(deque.capacity(), is(32));
        assertThat(deque, containsInAnyOrder(IntStream.range(50, 60).boxed().toArray()));
    }

    @Test
    public void shrinkToDefaultCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int item = 0; item < 30; item++) {
            deque.addBack(item);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(deque, containsInAnyOrder(IntStream.range(0, 30).boxed().toArray()));

        for (int i = 0; i < 26; i++) {
            deque.removeFront();
        }

        assertThat(deque.capacity(), is(16));
        assertThat(deque, containsInAnyOrder(IntStream.range(26, 30).boxed().toArray()));
    }
}
