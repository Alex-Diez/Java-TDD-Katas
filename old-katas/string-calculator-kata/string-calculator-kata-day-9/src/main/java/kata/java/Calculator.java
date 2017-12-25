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

    private static class Expression extends RecursiveTask<Double> {
        private final String source;

        Expression(String source) {
            this.source = source;
        }

        @Override
        protected Double compute() {
            List<Term> terms = splitExpression();

            terms.forEach(ForkJoinTask::fork);

            double val = 0.0;
            for (Term term : terms) {
                val = term.operation.apply(val, term.join());
            }

            return val;
        }

        private List<Term> splitExpression() {
            List<Term> terms = new ArrayList<>();
            int index = 0;
            while (index < source.length()) {
                int start = index;
                int end = findEnd(start + 1);
                Operation operation;
                switch (source.charAt(start)) {
                    case '+':
                        start += 1;
                        operation = Operation.ADDITION;
                        break;
                    case '-':
                        start += 1;
                        operation = Operation.SUBTRACTION;
                        break;
                    default:
                        operation = Operation.ADDITION;
                        break;
                }
                terms.add(new Term(source, start, end, operation));
                index = end;
            }
            return terms;
        }

        private int findEnd(int start) {
            int index = start;
            while(index < source.length() && source.charAt(index) != '+' && source.charAt(index) != '-') {
                index += 1;
            }
            return index;
        }
    }

    private static class Term extends RecursiveTask<Double> {

        private final String source;
        private final int end;
        final Operation operation;
        private int index;

        Term(String source, int start, int end, Operation operation) {
            this.source = source;
            this.end = end;
            this.operation = operation;
            this.index = start;
        }

        @Override
        protected Double compute() {
            double args = parseArgs();
            while (index < end) {
                char sign = source.charAt(index);
                if (sign == '*') {
                    index += 1;
                    args *= parseArgs();
                }
                else if (sign == '/') {
                    index += 1;
                    args /= parseArgs();
                }
                else {
                    break;
                }
            }
            return args;
        }

        private double parseArgs() {
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
            double apply(double accumulator, double val) {
                return accumulator + val;
            }

            @Override
            public String toString() {
                return "+";
            }
        },
        SUBTRACTION {
            @Override
            double apply(double accumulator, double val) {
                return accumulator - val;
            }

            @Override
            public String toString() {
                return "-";
            }
        };
        abstract double apply(double accumulator, double val);
    }

        /*int start = 0;
        int index = 0;
        while (index < source.length() && source.charAt(index) != '+' && source.charAt(index) != '-' && source.charAt(index) != '*' && source.charAt(index) != '/') {
            index += 1;
        }
        double val = Double.parseDouble(source.substring(start, index));
        if (index < source.length()) {
            char sign = source.charAt(index);
            switch (sign) {
                case '+':
                    val += Double.parseDouble(source.substring(index + 1));
                    break;
                case '-':
                    val -= Double.parseDouble(source.substring(index + 1));
                    break;
                case '*':
                    val *= Double.parseDouble(source.substring(index + 1));
                    break;
                case '/':
                    val /= Double.parseDouble(source.substring(index + 1));
                    break;
            }
        }
        return val;*/
}
