package org.stack;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult2;

import java.util.Optional;

@JCStressTest
@Outcome(id = "[0, 1]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[1, 0]", expect = Expect.ACCEPTABLE)
public class SynchronousStackStressTest {

    @State
    public static class StackHolder {
        public final ConcurrentStack<Integer> stack = new SynchronousStack<>(16);
    }

    @Actor
    public void thread1(StackHolder holder, IntResult2 r) {
        try {
            holder.stack.push(0);
            Optional<Integer> pop = holder.stack.pop();
            pop.ifPresent((o) -> r.r1 = o);
        } catch (InterruptedException e) {
            r.r1 = -1;
        }
    }

    @Actor
    public void thread2(StackHolder holder, IntResult2 r) {
        try {
            holder.stack.push(1);
            Optional<Integer> pop = holder.stack.pop();
            pop.ifPresent((o) -> r.r2 = o);
        } catch (InterruptedException e) {
            r.r2 = -1;
        }
    }
}
