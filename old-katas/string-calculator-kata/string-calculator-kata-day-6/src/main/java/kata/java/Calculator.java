package kata.java;

import java.util.HashSet;
import java.util.Set;

public class Calculator {
    private static final Set<Character> OPERATIONS = new HashSet<Character>() {{
        add('+');
        add('-');
        add('*');
        add('/');
    }};

    public static double calculate(String source) {
        return parseExpression(new Chars(source));
    }

    private static double parseExpression(Chars chars) {
        double term = parseTerm(chars);
        while (chars.hasNext()) {
            char sign = chars.current();
            if (sign == '+') {
                chars.iterate();
                term += parseTerm(chars);
            }
            else if (sign == '-') {
                chars.iterate();
                term -= parseTerm(chars);
            }
            else {
                break;
            }
        }
        return term;
    }

    private static double parseTerm(Chars chars) {
        double arg = parseArg(chars);
        while (chars.hasNext()) {
            char sign = chars.current();
            if (sign == '*') {
                chars.iterate();
                arg *= parseArg(chars);
            }
            else if (sign == '/') {
                chars.iterate();
                arg /= parseArg(chars);
            }
            else {
                break;
            }
        }
        return arg;
    }

    private static double parseArg(Chars chars) {
        int start = chars.index;
        while (chars.hasNext() && !OPERATIONS.contains(chars.current())) {
            chars.iterate();
        }
        return Double.parseDouble(chars.substring(start, chars.index));
    }

    private static class Chars {
        private final String source;
        private int index;

        Chars(String source) {
            this.source = source;
        }

        boolean hasNext() {
            return index < source.length();
        }

        char current() {
            return source.charAt(index);
        }

        void iterate() {
            index += 1;
        }

        String substring(int start, int end) {
            return source.substring(start, end);
        }
    }
}
