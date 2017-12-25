package kata.java;

import java.util.HashSet;
import java.util.Set;

public class Calculator {

    private static final Set<Character> LEGAL_NUMBER_CHAR = new HashSet<Character>() {
        {
            add('0');
            add('1');
            add('2');
            add('3');
            add('4');
            add('5');
            add('6');
            add('7');
            add('8');
            add('9');
            add('.');
        }
    };

    public static double calculate(String source) {
        Chars chars = new Chars(source);
        return parseExpression(chars);
    }

    private static double parseExpression(Chars chars) {
        double term = parseTerm(chars);
        while (chars.hasNext()) {
            char c = chars.peek();
            if (c == '+') {
                chars.next();
                term += parseTerm(chars);
            }
            else if (c == '-') {
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
            char c = chars.peek();
            if (c == '×') {
                chars.next();
                arg *= parseArg(chars);
            }
            else if (c == '÷') {
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
        int start = chars.index();
        while (chars.hasNext() && LEGAL_NUMBER_CHAR.contains(chars.peek())) {
            chars.next();
        }
        int end = chars.index();
        return Double.parseDouble(chars.subString(start, end));
    }

}

class Chars {
    private final String source;
    private int index;

    Chars(String source) {
        this.source = source;
    }

    char next() {
        return source.charAt(index++);
    }

    char peek() {
        return source.charAt(index);
    }

    int index() {
        return index;
    }

    String subString(int start, int end) {
        return source.substring(start, end);
    }

    boolean hasNext() {
        return index < source.length();
    }
}
