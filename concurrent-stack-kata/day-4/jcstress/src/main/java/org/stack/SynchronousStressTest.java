package org.stack;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult4;

@JCStressTest
@Outcome(id = "[1, 2, 3, 4]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[1, 2, 4, 3]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[1, 3, 2, 4]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[1, 3, 4, 2]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[1, 4, 2, 3]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[1, 4, 3, 2]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[2, 1, 3, 4]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[2, 1, 4, 3]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[2, 3, 1, 4]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[2, 3, 4, 1]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[2, 4, 3, 1]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[2, 4, 1, 3]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[3, 1, 2, 4]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[3, 1, 4, 2]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[3, 2, 1, 4]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[3, 2, 4, 1]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[3, 4, 1, 2]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[3, 4, 2, 1]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[4, 1, 2, 3]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[4, 1, 3, 2]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[4, 2, 1, 3]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[4, 2, 3, 1]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[4, 3, 1, 2]", expect = Expect.ACCEPTABLE)
@Outcome(id = "[4, 3, 2, 1]", expect = Expect.ACCEPTABLE)
public class SynchronousStressTest {

    @State
    public static class StackHolder {
        private final SynchronousStack stack = new SynchronousStack(16);
    }

    @Actor
    public void thread1(StackHolder holder, IntResult4 r) {
        try {
            holder.stack.push(1);
            holder.stack.pop().ifPresent(
                    (v) -> r.r1 = v
            );
        } catch (InterruptedException e) {
            r.r1 = -1;
        }
    }

    @Actor
    public void thread2(StackHolder holder, IntResult4 r) {
        try {
            holder.stack.push(2);
            holder.stack.pop().ifPresent(
                    v -> r.r2 = v
            );
        } catch (InterruptedException e) {
            r.r1 = -1;
        }
    }

    @Actor
    public void thread3(StackHolder holder, IntResult4 r) {
        try {
            holder.stack.push(3);
            holder.stack.pop().ifPresent(
                    v -> r.r3 = v
            );
        } catch (InterruptedException e) {
            r.r1 = -1;
        }
    }

    @Actor
    public void thread4(StackHolder holder, IntResult4 r) {
        try {
            holder.stack.push(4);
            holder.stack.pop().ifPresent(
                    v -> r.r4 = v
            );
        } catch (InterruptedException e) {
            r.r1 = -1;
        }
    }
}
