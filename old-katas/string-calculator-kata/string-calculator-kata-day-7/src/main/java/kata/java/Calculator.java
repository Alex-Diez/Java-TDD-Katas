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
                chars.next();
                term += parseTerm(chars);
            }
            else if (sign == '-') {
                chars.next();
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
                chars.next();
                arg *= parseArg(chars);
            }
            else if (sign == '/') {
                chars.next();
                arg /= parseArg(chars);
            }
            else {
                break;
            }
        }
        return arg;
    }

    private static double parseArg(Chars chars) {
        int index = chars.index();
        while (chars.hasNext() && !OPERATIONS.contains(chars.current())) {
            chars.next();
        }
        return Double.parseDouble(chars.substring(index, chars.index()));
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

        void next() {
            index += 1;
        }

        int index() {
            return index;
        }

        String substring(int start, int end) {
            return source.substring(start, end);
        }
    }
}
