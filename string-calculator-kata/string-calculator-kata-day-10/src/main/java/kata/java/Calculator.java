package kata.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Calculator {
    public static double calculate(String source) {
        return ForkJoinPool.commonPool().invoke(new Expression(source));
    }

    private static class Expression
            extends RecursiveTask<Double> {
        private final String source;

        Expression(String source) {
            this.source = source;
        }

        @Override
        protected Double compute() {
            List<Term> terms = split();

            terms.forEach(ForkJoinTask::fork);

            double val = 0.0;
            for (Term term : terms) {
                val = term.operation.apply(val, term.join());
            }

            return val;
        }

        private List<Term> split() {
            List<Term> terms = new ArrayList<>();
            int index = 0;
            while (index < source.length()) {
                int start = index;
                Operation operation;
                switch (source.charAt(start)) {
                    case '+':
                        operation = Operation.ADDITION;
                        start += 1;
                        break;
                    case '-':
                        operation = Operation.SUBTRACTION;
                        start += 1;
                        break;
                    default:
                        operation = Operation.ADDITION;
                        break;
                }
                int end = findEnd(start);
                terms.add(new Term(source, start, end, operation));
                index = end;
            }
            return terms;
        }

        private int findEnd(int start) {
            int index = start;
            while (index < source.length() && source.charAt(index) != '+' && source.charAt(index) != '-') {
                index += 1;
            }
            return index;
        }
    }

    private static class Term
            extends RecursiveTask<Double> {

        private final String source;
        private final int end;
        private int index;
        final Operation operation;

        Term(String source, int start, int end, Operation operation) {
            this.source = source;
            this.index = start;
            this.end = end;
            this.operation = operation;
        }

        @Override
        protected Double compute() {
            double val = parseArg();
            while (index < end) {
                char op = source.charAt(index);
                index += 1;
                switch (op) {
                    case '*':
                        val *= parseArg();
                        break;
                    case '/':
                        val /= parseArg();
                        break;
                }
            }
            return val;
        }

        private double parseArg() {
            int start = index;
            while (index < end && source.charAt(index) != '*' && source.charAt(index) != '/') {
                index += 1;
            }
            return Double.parseDouble(source.substring(start, index));
        }
    }

    private enum Operation {
        ADDITION {
            @Override
            double apply(double accumulator, double delta) {
                return accumulator + delta;
            }
        },
        SUBTRACTION {
            @Override
            double apply(double accumulator, double delta) {
                return accumulator - delta;
            }
        };

        abstract double apply(double accumulator, double delta);
    }
}
