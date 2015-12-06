package org.stack;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(value = 1, jvmArgs = "-ea")
public class SynchronousStackBenchmarks {

    @State(Scope.Benchmark)
    public static class Data {
        private final int d = 10;
    }

    private Stack<Integer> stack;

    @Setup(Level.Iteration)
    public void setUp() {
        stack = new SynchronousStack<>(100);
    }

    @Benchmark
    @Group("baseline")
    public void f() {
    }

    @Benchmark
    @Group("s_stack")
    public int write1(Data d) throws InterruptedException {
        stack.push(d.d);
        return d.d;
    }

    @Benchmark
    @Group("s_stack")
    public Optional<Integer> read1() throws InterruptedException {
        return stack.pop();
    }

    @Benchmark
    @Group("s_stack")
    public int write2(Data d) throws InterruptedException {
        stack.push(d.d);
        return d.d;
    }

    @Benchmark
    @Group("s_stack")
    public Optional<Integer> read2() throws InterruptedException {
        return stack.pop();
    }

    @Benchmark
    @Group("s_stack")
    public int write3(Data d) throws InterruptedException {
        stack.push(d.d);
        return d.d;
    }

    @Benchmark
    @Group("s_stack")
    public Optional<Integer> read3() throws InterruptedException {
        return stack.pop();
    }

    @Benchmark
    @Group("s_stack")
    public int write4(Data d) throws InterruptedException {
        stack.push(d.d);
        return d.d;
    }

    @Benchmark
    @Group("s_stack")
    public Optional<Integer> read4() throws InterruptedException {
        return stack.pop();
    }

}
