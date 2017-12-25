package kata.java;

import org.junit.Test;

import java.util.OptionalInt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ResizableDequeTest {

    @Test
    public void createsDequeWithDefaultCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        assertThat(deque.capacity(), is(16));
    }

    @Test
    public void createsDequeWithLesserThanDefaultCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque(10);

        assertThat(deque.capacity(), is(16));
    }

    @Test
    public void createsDequeWithGreaterThanDefaultCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque(20);

        assertThat(deque.capacity(), is(32));
    }

    @Test
    public void addOneFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addFront(1);

        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void addThreeFront() throws Exception {
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
    public void addThreeBack() throws Exception {
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

        for (int i = 0; i < 20; i++) {
            deque.addFront(i);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 20), is(true));
    }

    @Test
    public void addBackMoreThanCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int i = 0; i < 20; i++) {
            deque.addBack(i);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 20), is(true));
    }

    @Test
    public void addFrontBackMoreThanCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int i = 0; i < 10; i++) {
            deque.addFront(i);
            deque.addBack(i + 10);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 20), is(true));
    }

    @Test
    public void addBackFrontMoreThanCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int i = 0; i < 10; i++) {
            deque.addBack(i);
            deque.addFront(i + 10);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 20), is(true));
    }

    @Test
    public void addRemoveAddFront() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        for (int i = 0; i < 20; i++) {
            deque.addFront(i);
        }

        for (int i = 0; i < 10; i++) {
            deque.removeFront();
        }

        for (int i = 20; i < 40; i++) {
            deque.addFront(i);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 10), is(true));
        assertThat(containsAll(deque, 20, 40), is(true));
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
