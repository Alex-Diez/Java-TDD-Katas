package kata.java;

public class Calculator {
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
            if (sign == '×') {
                chars.iterate();
                arg *= parseArg(chars);
            }
            else if (sign == '÷') {
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
        int start = chars.index();
        while (chars.hasNext() && chars.current() != '+' && chars.current() != '-' && chars.current() != '×' &&
                chars.current() != '÷') {
            chars.iterate();
        }
        return Double.parseDouble(chars.substring(start, chars.index()));
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

    char current() {
        return source.charAt(index);
    }

    void iterate() {
        index += 1;
    }

    int index() {
        return index;
    }

    String substring(int start, int end) {
        return source.substring(start, end);
    }
}
