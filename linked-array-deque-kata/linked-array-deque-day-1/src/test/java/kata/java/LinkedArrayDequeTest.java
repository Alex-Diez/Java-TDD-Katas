package kata.java;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LinkedArrayDequeTest {

    private LinkedArrayDeque deque;

    @Before
    public void setUp() throws Exception {
        deque = new LinkedArrayDeque();
    }

    @Test
    public void createDeque() throws Exception {
        assertThat(deque.removeBack(), is(Optional.empty()));
    }

    @Test
    public void enqueueItemInFront() throws Exception {
        deque.addFront(10);

        assertThat(deque.removeBack(), is(Optional.of(10)));
        assertThat(deque.removeBack(), is(Optional.empty()));
    }

    @Test
    public void addManyItemsInFront() throws Exception {
        deque.addFront(10);
        deque.addFront(20);
        deque.addFront(30);

        assertThat(deque.removeBack(), is(Optional.of(10)));
        assertThat(deque.removeBack(), is(Optional.of(20)));
        assertThat(deque.removeBack(), is(Optional.of(30)));
        assertThat(deque.removeBack(), is(Optional.empty()));
    }

    @Test
    public void addItemsMoreThanSegmentSize() throws Exception {
        IntStream.range(0, 40).forEach(deque::addFront);

        IntStream.range(0, 40).forEach(i -> assertThat(deque.removeBack(), is(Optional.of(i))));

        assertThat(deque.removeBack(), is(Optional.empty()));
    }

    @Test
    public void removeItemsFront() throws Exception {
        deque.addFront(10);
        deque.addFront(20);
        deque.addFront(30);

        assertThat(deque.removeFront(), is(Optional.of(30)));
        assertThat(deque.removeFront(), is(Optional.of(20)));
        assertThat(deque.removeFront(), is(Optional.of(10)));
        assertThat(deque.removeFront(), is(Optional.empty()));
    }
}
