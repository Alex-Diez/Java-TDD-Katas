package kata.java;

import org.junit.Before;
import org.junit.Test;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class DequeTest {

    private LinkedDeque deque;

    @Before
    public void setUp() throws Exception {
        deque = new LinkedDeque();
    }

    @Test
    public void createDeque() throws Exception {
    }

    @Test
    public void addFrontOne() throws Exception {
        deque.addFront(1);

        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }

    @Test
    public void addFrontMany() throws Exception {
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
        deque.addBack(1);
        deque.addBack(2);
        deque.addBack(3);

        assertThat(deque.removeFront(), is(OptionalInt.of(1)));
        assertThat(deque.removeFront(), is(OptionalInt.of(2)));
        assertThat(deque.removeFront(), is(OptionalInt.of(3)));
        assertThat(deque.removeFront(), is(OptionalInt.empty()));
    }
    
    @Test
    public void peekFront() throws Exception {
        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);

        assertThat(deque.peekFront(), is(OptionalInt.of(3)));
        assertThat(deque.peekFront(), is(OptionalInt.of(3)));
    }

    @Test
    public void peekBack() throws Exception {
        deque.addBack(1);
        deque.addBack(2);
        deque.addBack(3);

        assertThat(deque.peekBack(), is(OptionalInt.of(3)));
        assertThat(deque.peekBack(), is(OptionalInt.of(3)));
    }

    @Test
    public void containsAll() throws Exception {
        IntStream.range(0, 20).forEach(deque::addFront);

        assertThat(deque, containsInAnyOrder(IntStream.range(0, 20).boxed().toArray()));
    }
}
