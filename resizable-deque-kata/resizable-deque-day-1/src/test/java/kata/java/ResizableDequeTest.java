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
    public void createsDequeWithCapacityLesserThanDefault() throws Exception {
        ResizableDeque deque = new ResizableDeque(10);

        assertThat(deque.capacity(), is(16));
    }

    @Test
    public void createsDequeWithCapacityGreaterThanDefault() throws Exception {
        ResizableDeque deque = new ResizableDeque(32);

        assertThat(deque.capacity(), is(32));
    }

    @Test
    public void addFrontElement() throws Exception {
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
        deque.addFront(4);

        assertThat(deque.removeFront(), is(OptionalInt.of(4)));
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
        deque.addFront(4);

        assertThat(deque.removeBack(), is(OptionalInt.of(1)));
        assertThat(deque.removeBack(), is(OptionalInt.of(2)));
        assertThat(deque.removeBack(), is(OptionalInt.of(3)));
        assertThat(deque.removeBack(), is(OptionalInt.of(4)));
        assertThat(deque.removeBack(), is(OptionalInt.empty()));
    }

    @Test
    public void addBackMany() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addBack(1);
        deque.addBack(2);
        deque.addBack(3);
        deque.addBack(4);

        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.of(2)));
        assertThat(deque.removeFront(), is(OptionalInt.of(3)));
        assertThat(deque.removeFront(), is(OptionalInt.of(4)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void containsAddedElement() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addFront(1);

        assertThat(deque.contains(1), is(true));
    }

    @Test
    public void doesNotContainNonAddedElement() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addFront(1);

        assertThat(deque.contains(2), is(false));
    }

    @Test
    public void containsAddedElements() throws Exception {
        ResizableDeque deque = new ResizableDeque();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);

        assertThat(deque.contains(1), is(true));
        assertThat(deque.contains(2), is(true));
        assertThat(deque.contains(3), is(true));
    }

    @Test
    public void addFrontMoreThanInitialCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque(16);

        addManyFront(deque, 0, 20);

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 20), is(true));
    }

    private boolean containsAll(ResizableDeque deque, int from, int till) {
        for (int i = from; i < till; i++) {
            if (!deque.contains(i)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void addBackMoreThanInitialCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque(16);

        for (int i = 0; i < 20; i++) {
            deque.addBack(i);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 20), is(true));
    }

    @Test
    public void addFrontBackMoreThanInitialCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque(16);

        for (int i = 0; i < 10; i++) {
            deque.addFront(i);
            deque.addBack(i + 10);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 20), is(true));
    }

    @Test
    public void addBackFrontMoreThanInitialCapacity() throws Exception {
        ResizableDeque deque = new ResizableDeque(16);

        for (int i = 0; i < 10; i++) {
            deque.addBack(i + 10);
            deque.addFront(i);
        }

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 0, 20), is(true));
    }

    @Test
    public void addFrontRemoveFront_untilResize() throws Exception {
        ResizableDeque deque = new ResizableDeque(16);

        addManyFront(deque, 0, 10);
        removeManyFront(deque, 5);
        addManyFront(deque, 10, 20);
        removeManyFront(deque, 5);
        addManyFront(deque, 20, 30);

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 20, 30), is(true));
    }

    @Test
    public void addBackRemoveBack_untilResize() throws Exception {
        ResizableDeque deque = new ResizableDeque(16);

        addManyBack(deque, 0, 10);
        removeManyBack(deque, 5);
        addManyBack(deque, 10, 20);
        removeManyBack(deque, 5);
        addManyBack(deque, 20, 30);

        assertThat(deque.capacity(), is(32));
        assertThat(containsAll(deque, 20, 30), is(true));
    }

    private void removeManyBack(ResizableDeque deque, int size) {
        for (int i = 0; i < size; i++) {
            deque.removeBack();
        }
    }

    private void addManyBack(ResizableDeque deque, int from, int till) {
        for (int i = from; i < till; i++) {
            deque.addBack(i);
        }
    }

    private void addManyFront(ResizableDeque deque, int from, int till) {
        for (int i = from; i < till; i++) {
            deque.addFront(i);
        }
    }

    private void removeManyFront(ResizableDeque deque, int size) {
        for (int i = 0; i < size; i++) {
            deque.removeFront();
        }
    }
}
