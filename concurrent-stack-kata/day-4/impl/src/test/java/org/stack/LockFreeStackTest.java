package org.stack;

import co.unruly.matchers.OptionalMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static co.unruly.matchers.OptionalMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class LockFreeStackTest {

    private LockFreeStack stack;

    @Before
    public void setUp() throws Exception {
        stack = new LockFreeStack();
    }

    @Test
    public void itShouldCreateANewEmptyStack() throws Exception {
        assertThat(stack.isEmpty(), is(true));
        assertThat(stack.size(), is(0));
    }

    @Test
    public void itShouldIncreaseTheStackSizeWhenPushIntoIt() throws Exception {
        int oldSize = stack.size();
        stack.push(1);

        assertThat(stack.isEmpty(), is(false));
        assertThat(stack.size(), greaterThan(oldSize));
    }

    @Test
    public void itShouldDecreaseTheStackSizeWhenPopFromIt() throws Exception {
        stack.push(1);
        int oldSize = stack.size();
        stack.pop();

        assertThat(stack.isEmpty(), is(true));
        assertThat(stack.size(), lessThan(oldSize));
    }

    @Test
    public void itShouldPopPushedValue() throws Exception {
        stack.push(10);

        assertThat(stack.pop(), contains(10));

        stack.push(20);

        assertThat(stack.pop(), contains(20));
    }

    @Test
    public void itShouldPopFirstPushedLast() throws Exception {
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);

        assertThat(stack.pop(), contains(40));
        assertThat(stack.pop(), contains(30));
        assertThat(stack.pop(), contains(20));
        assertThat(stack.pop(), contains(10));
    }
}
