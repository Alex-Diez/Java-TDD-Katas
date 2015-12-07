package org.stack;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(value = 1, jvmArgs = "-ea")
public class LockFreeStackBenchmark {

    @State(Scope.Benchmark)
    public static class Data {
        private int c = 0;
        private final int size = 100;
        private int[] data = new int[size];

        public Data() {
            Random random = new Random();
            for (int i = 0; i < size; i++) {
                data[i] = random.nextInt();
            }
        }

        public int datum() {
            return data[c++%size];
        }
    }

    private LockFreeStack stack;

    @Setup(Level.Iteration)
    public void setUp() {
        stack = new LockFreeStack();
    }

    @Benchmark
    @Group("baseline")
    public void f() {
    }

    @Benchmark
    @Group("lf_stack")
    @GroupThreads(4)
    public boolean write(Data d) throws InterruptedException {
        int datum = d.datum();
        return stack.push(datum);
    }

    @Benchmark
    @Group("lf_stack")
    @GroupThreads(4)
    public Optional<Integer> read() throws InterruptedException {
        return stack.pop();
    }
}
