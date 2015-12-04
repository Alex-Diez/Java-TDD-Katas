package org.stack;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static co.unruly.matchers.OptionalMatchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class StackTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{UnsynchronousStack.class}, {SynchronousStack.class}});
    }

    private final Class<Stack> stackClass;

    public StackTest(Class<Stack> stackClass) {
        this.stackClass = stackClass;
    }

    private Stack<Integer> s;

    @Before
    public void setUp() throws Exception {
        if (stackClass.equals(UnsynchronousStack.class)) {
            s = new UnsynchronousStack<>(16);
        }
        if (stackClass.equals(SynchronousStack.class)) {
            s = new SynchronousStack<>(16);
        }
    }

    @Test
    public void itShouldBeCreatedEmptyStack() throws Exception {
        assertThat(s.size(), is(0));
        assertThat(s.isEmpty(), is(true));
    }

    @Test
    public void itShouldPopFromPushedIntoStackValue() throws Exception {
        s.push(1);

        assertThat(s.pop(), contains(1));

        s.push(2);

        assertThat(s.pop(), contains(2));
    }

    @Test
    public void itShouldIncreaseTheStackSizeWhenPushInto() throws Exception {
        int oldSize = s.size();
        s.push(1);

        assertThat(oldSize, lessThan(s.size()));
        assertThat(s.isEmpty(), is(false));
    }

    @Test
    public void itShouldDecreaseTheStackSizeWhenPopFrom() throws Exception {
        s.push(2);
        int oldSize = s.size();

        s.pop();
        assertThat(oldSize, greaterThan(s.size()));
    }

    @Test
    public void itShouldPushedFirstPopLast() throws Exception {
        s.push(10);
        s.push(20);
        s.push(30);
        s.push(40);

        assertThat(s.pop(), contains(40));
        assertThat(s.pop(), contains(30));
        assertThat(s.pop(), contains(20));
        assertThat(s.pop(), contains(10));
    }

    @Test
    public void itShouldStackContainsValueWhichWerePushed() throws Exception {
        s.push(10);
        s.push(30);
        s.push(20);
        s.push(50);
        s.push(60);

        assertThat(s, containsInAnyOrder(10, 30, 20, 50, 60));
    }
}
