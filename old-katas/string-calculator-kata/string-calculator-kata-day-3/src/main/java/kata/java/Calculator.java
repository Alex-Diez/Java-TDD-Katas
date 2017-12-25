package kata.java;

import java.util.HashSet;
import java.util.Set;

public class Calculator {
    private static final Set<Character> OPERATIONS = new HashSet<Character>() {
        {
            add('+');
            add('-');
            add('×');
            add('÷');
        }
    };

    public static double calculate(String source) {
        return parseExpression(new Chars(source));
    }

    private static double parseExpression(Chars chars) {
        double val = parseTerm(chars);
        while (chars.hasNext()) {
            char sign = chars.current();
            if (sign == '+') {
                chars.iterate();
                val += parseTerm(chars);
            }
            else if (sign == '-') {
                chars.iterate();
                val -= parseTerm(chars);
            }
            else {
                break;
            }
        }
        return val;
    }

    private static double parseTerm(Chars chars) {
        double val = parseArg(chars);
        while (chars.hasNext()) {
            char sign = chars.current();
            if (sign == '×') {
                chars.iterate();
                val *= parseArg(chars);
            }
            else if (sign == '÷') {
                chars.iterate();
                val /= parseArg(chars);
            }
            else {
                break;
            }
        }
        return val;
    }

    private static double parseArg(Chars chars) {
        int start = chars.index();
        while (chars.hasNext() && !OPERATIONS.contains(chars.current())) {
            chars.iterate();
        }
        int end = chars.index();
        return Double.parseDouble(chars.extract(start, end));
    }
}

class Chars {
    private final String source;
    private int index;

    Chars(String source) {
        this.source = source;
    }

    boolean hasNext() {
        return index < source.length();
    }

    public char current() {
        return source.charAt(index);
    }

    public void iterate() {
        index += 1;
    }

    public int index() {
        return index;
    }

    public String extract(int start, int end) {
        return source.substring(start, end);
    }
}
