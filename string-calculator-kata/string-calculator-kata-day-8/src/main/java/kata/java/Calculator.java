package kata.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Calculator {

    static double calculate(String source) {
        return ForkJoinPool.commonPool().invoke(new Expression(source));
    }

    private static class Expression
            extends RecursiveTask<Double> {
        private static final Set<Character> OPERATIONS = new HashSet<Character>() {{
            add('+');
            add('-');
        }};

        private final String source;

        Expression(String source) {
            this.source = source;
        }

        @Override
        protected Double compute() {
            List<Term> terms = splitIntoTerms();

            terms.forEach(ForkJoinTask::fork);

            double val = 0.0;
            for (Term term : terms) {
                val = term.operation().apply(val, term.join());
            }
            return val;
        }

        private List<Term> splitIntoTerms() {
            int index = 0;
            List<Term> terms = new ArrayList<>();
            while (index < source.length()) {
                int start = index;
                int end = findEnd(start + 1);
                Operation op;
                switch (source.charAt(start)) {
                    case '-':
                        op = Operation.SUBTRACTION;
                        start += 1;
                        break;
                    case '+':
                        op = Operation.ADDITION;
                        start += 1;
                        break;
                    default:
                        op = Operation.ADDITION;
                }
                terms.add(new Term(source, start, end, op));
                index = end;
            }
            return terms;
        }

        private int findEnd(int start) {
            int index = start;
            while (index < source.length() && !OPERATIONS.contains(source.charAt(index))) {
                index += 1;
            }
            return index;
        }
    }

    private static class Term
            extends RecursiveTask<Double> {
        private final String source;
        private final int from;
        private final int to;
        private final Calculator.Operation op;
        private int index;

        Term(String source, int from, int to, Operation op) {
            this.source = source;
            this.from = from;
            this.to = to;
            this.op = op;
            this.index = from;
        }

        @Override
        protected Double compute() {
            double val = parseArg();
            while (index < to) {
                char sign = source.charAt(index);
                index += 1;
                switch (sign) {
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
            while (index < to && source.charAt(index) != '*' && source.charAt(index) != '/') {
                index += 1;
            }
            return Double.parseDouble(source.substring(start, index));
        }

        Operation operation() {
            return op;
        }

        @Override
        public String toString() {
            return "[ Term of " + source.substring(from, to) + " operation " + op + " ]";
        }
    }

    private enum Operation {
        ADDITION {
            @Override
            double apply(double val, double operand) {
                return val + operand;
            }

            @Override
            public String toString() {
                return "+";
            }
        },
        SUBTRACTION {
            @Override
            double apply(double val, double operand) {
                return val - operand;
            }

            @Override
            public String toString() {
                return "-";
            }
        };

        abstract double apply(double val, double operand);
    }

}
