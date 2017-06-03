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
    public void createEmptyDeque() throws Exception {
        assertThat(deque.removeBack(), is(Optional.empty()));
    }
    
    @Test
    public void addItemInFront() throws Exception {
        deque.addFront(10);

        assertThat(deque.removeBack(), is(Optional.of(10)));
        assertThat(deque.removeBack(), is(Optional.empty()));
    }

    @Test
    public void addManyItems() throws Exception {
        deque.addFront(10);
        deque.addFront(20);
        deque.addFront(30);

        assertThat(deque.removeBack(), is(Optional.of(10)));
        assertThat(deque.removeBack(), is(Optional.of(20)));
        assertThat(deque.removeBack(), is(Optional.of(30)));
        assertThat(deque.removeBack(), is(Optional.empty()));
    }

    @Test
    public void addItemsMoreThanSegmentCapacity() throws Exception {
        IntStream.range(0, 20).forEach(deque::addFront);

        IntStream.range(0, 20).forEach(i -> assertThat(deque.removeBack(), is(Optional.of(i))));
        assertThat(deque.removeBack(), is(Optional.empty()));
    }

    @Test
    public void addItemsMoreThanOneSegment() throws Exception {
        IntStream.range(0, 80).forEach(deque::addFront);

        IntStream.range(0, 80).forEach(i -> assertThat(deque.removeBack(), is(Optional.of(i))));
        assertThat(deque.removeBack(), is(Optional.empty()));
    }

    @Test
    public void removeFront() throws Exception {
        deque.addFront(10);
        deque.addFront(20);
        deque.addFront(30);

        assertThat(deque.removeFront(), is(Optional.of(30)));
        assertThat(deque.removeFront(), is(Optional.of(20)));
        assertThat(deque.removeFront(), is(Optional.of(10)));
        assertThat(deque.removeFront(), is(Optional.empty()));
    }

    @Test
    public void removeFrontMoreThanSegmentCapacity() throws Exception {
        IntStream.range(0, 20).forEach(deque::addFront);

        IntStream.iterate(19, i -> --i).limit(20).forEach(i -> assertThat(deque.removeFront(), is(Optional.of(i))));
        assertThat(deque.removeFront(), is(Optional.empty()));
    }

    @Test
    public void removeFrontMoreThanOneSegment() throws Exception {
        IntStream.range(0, 80).forEach(deque::addFront);

        IntStream.iterate(79, i -> --i).limit(80).forEach(i -> assertThat(deque.removeFront(), is(Optional.of(i))));
        assertThat(deque.removeFront(), is(Optional.empty()));
    }

    @Test@Ignore
    public void addBackManyItems() throws Exception {
        deque.addBack(10);
        deque.addBack(20);
        deque.addBack(30);

        assertThat(deque.removeFront(), is(Optional.of(30)));
        assertThat(deque.removeFront(), is(Optional.of(20)));
        assertThat(deque.removeFront(), is(Optional.of(10)));
        assertThat(deque.removeFront(), is(Optional.empty()));
    }
}
