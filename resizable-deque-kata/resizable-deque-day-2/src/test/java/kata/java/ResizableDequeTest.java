package kata.java;

import org.junit.Test;

import java.util.OptionalInt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ResizableDequeTest {

    @Test
    public void createDequeWithDefaultCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        assertThat(deque.capacity(), is(16));
    }

    @Test
    public void createDequeWithCapacityLesserThanDefault() throws Exception {
        ResizableDeque deque = new ResizableDeque(10);

        assertThat(deque.capacity(), is(16));
    }

    @Test
    public void createDequeWithCapacityGreaterThanDefault() throws Exception {
        ResizableDeque deque = new ResizableDeque(20);

        assertThat(deque.capacity(), is(32));
    }

    @Test
    public void addOneInFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addFront(1);

        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void addManyInFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);
        deque.addFront(4);

        assertThat(deque.removeFront(), is(OptionalInt.of(4)));
        assertThat(deque.removeFront(), is(OptionalInt.of(3)));
        assertThat(deque.removeFront(), is(OptionalInt.of(2)));
        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void addManyBack() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addBack(1);
        deque.addBack(2);
        deque.addBack(3);
        deque.addBack(4);

        assertThat(deque.removeBack(), is(OptionalInt.of(4)));
        assertThat(deque.removeBack(), is(OptionalInt.of(3)));
        assertThat(deque.removeBack(), is(OptionalInt.of(2)));
        assertThat(deque.removeBack(), is(OptionalInt.of(1)));
        assertThat(deque.removeBack(), is(OptionalInt.empty()));
        assertThat(deque.removeBack(), is(OptionalInt.empty()));
    }

    @Test
    public void containsAddedItems() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addBack(1);
        deque.addFront(2);
        deque.addBack(3);
        deque.addFront(4);

        assertThat(deque.contains(1), is(true));
        assertThat(deque.contains(2), is(true));
        assertThat(deque.contains(3), is(true));
        assertThat(deque.contains(4), is(true));
    }

    @Test
    public void addFrontMoreThanInitialCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int i = 0; i < 21; i++) {
            deque.addFront(i);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 21), is(true));
    }

    @Test
    public void addBacMoreThanInitialCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int i = 0; i < 21; i++) {
            deque.addBack(i);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 21), is(true));
    }

    @Test
    public void addFrontBackMoreThanInitialCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int i = 0; i < 11; i++) {
            deque.addFront(i);
            deque.addBack(i + 10);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 21), is(true));
    }

    @Test
    public void addBackFrontMoreThanInitialCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int i = 0; i < 11; i++) {
            deque.addBack(i + 10);
            deque.addFront(i);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 21), is(true));
    }

    @Test
    public void addManyBackThenManyFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int i = 0; i < 21; i++) {
            deque.addBack(i);
        }

        for (int i = 20; i < 41; i++) {
            deque.addFront(i);
        }

        assertThat(deque.capacity(), is(64));
        assertThat(containsAll(deque, 0, 41), is(true));
    }

    private boolean containsAll(ResizableDeque deque, int from, int till) {
        for (int i = from; i < till; i++) {
            if (!deque.contains(i)) {
                return false;
            }
        }
        return true;
    }
}
