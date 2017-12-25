package kata.java;

import org.junit.Before;
import org.junit.Test;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class LinkedDequeTest {

    private LinkedDeque deque;

    @Before
    public void setUp() throws Exception {
        deque = new LinkedDeque();
    }

    @Test
    public void addOneItemInFront() throws Exception {
        deque.addFront(1);

        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void addManyItemsInFront() throws Exception {
        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);

        assertThat(deque.removeFront(), is(OptionalInt.of(3)));
        assertThat(deque.removeFront(), is(OptionalInt.of(2)));
        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void removeItemsFromBack() throws Exception {
        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);

        assertThat(deque.removeBack(), is(OptionalInt.of(1)));
        assertThat(deque.removeBack(), is(OptionalInt.of(2)));
        assertThat(deque.removeBack(), is(OptionalInt.of(3)));
        assertThat(deque.removeBack(), is(OptionalInt.empty()));
    }

    @Test
    public void addManyItemsInBack() throws Exception {
        deque.addBack(1);
        deque.addBack(2);
        deque.addBack(3);

        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.of(2)));
        assertThat(deque.removeFront(), is(OptionalInt.of(3)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void peekFrontFromEmptyDeque() throws Exception {
        assertThat(deque.peekFront(), is(OptionalInt.empty()));
    }

    @Test
    public void peekFrontItem() throws Exception {
        deque.addFront(1);
        deque.addFront(2);

        assertThat(deque.peekFront(), is(OptionalInt.of(2)));
        assertThat(deque.peekFront(), is(OptionalInt.of(2)));
    }

    @Test
    public void peekBackFromEmptyDeque() throws Exception {
        assertThat(deque.peekBack(), is(OptionalInt.empty()));
    }

    @Test
    public void peekBackItem() throws Exception {
        deque.addFront(1);
        deque.addFront(2);

        assertThat(deque.peekBack(), is(OptionalInt.of(1)));
        assertThat(deque.peekBack(), is(OptionalInt.of(1)));
    }

    @Test
    public void containsAll() throws Exception {
        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);
        deque.addFront(4);

        deque.addBack(5);
        deque.addBack(6);
        deque.addBack(7);
        deque.addBack(8);

        assertThat(deque, containsInAnyOrder(IntStream.range(1, 9).boxed().toArray()));
    }
}
