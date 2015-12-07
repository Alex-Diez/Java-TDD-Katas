package org.stack;

import co.unruly.matchers.OptionalMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static co.unruly.matchers.OptionalMatchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class SynchronousStackTest {

    private SynchronousStack stack;

    @Before
    public void setUp() throws Exception {
        stack = new SynchronousStack(20);
    }

    @Test
    public void itShouldCreateANewEmptyStack() throws Exception {
        assertThat(stack.size(), is(0));
        assertThat(stack.isEmpty(), is(true));
        assertThat(stack.capacity(), is(20));
    }

    @Test
    public void itShouldIncreaseTheStackSizeWhenPushIntoIt() throws Exception {
        int oldSize = stack.size();
        stack.push(1);

        assertThat(oldSize, lessThan(stack.size()));
        assertThat(stack.isEmpty(), is(false));
    }

    @Test
    public void itShouldDecreaseTheStackSizeWhenPopFromIt() throws Exception {
        stack.push(1);
        int oldSize = stack.size();
        stack.pop();

        assertThat(oldSize, greaterThan(stack.size()));
        assertThat(stack.isEmpty(), is(true));
    }
    
    @Test
    public void itShouldPopValueThatWasPushedIntoTheStack() throws Exception {
        stack.push(1);

        assertThat(stack.pop(), contains(1));

        stack.push(5);

        assertThat(stack.pop(), contains(5));
    }

    @Test
    public void itShouldFirstPushedLastPop() throws Exception {
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);

        assertThat(stack.pop(), contains(40));
        assertThat(stack.pop(), contains(30));
        assertThat(stack.pop(), contains(20));
        assertThat(stack.pop(), contains(10));
    }

    @Test
    public void itShouldContainsAllPushedValues() throws Exception {
        stack.push(10);
        stack.push(30);
        stack.push(50);
        stack.push(20);
        stack.push(40);

        assertThat(stack, containsInAnyOrder(10, 20, 30, 40, 50));
    }
}
