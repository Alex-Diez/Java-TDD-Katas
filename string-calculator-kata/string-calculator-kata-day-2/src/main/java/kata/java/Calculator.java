package kata.java;

import java.util.HashSet;
import java.util.Set;

public class Calculator {
    private static Set<Character> DIGITS = new HashSet<Character>() {
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
            add('0');
            add('.');
        }
    };

    public static double calculate(String source) {
        Chars chars = new Chars(source);
        double ret = parseTerm(chars);
        while (chars.hasNext()) {
            char sign = chars.current();
            if (isAddition(sign)) {
                chars.iterate();
                ret += parseTerm(chars);
            }
            else if (isSubtraction(sign)) {
                chars.iterate();
                ret -= parseTerm(chars);
            }
            else {
                break;
            }
        }
        return ret;
    }

    private static boolean isSubtraction(char sign) {
        return sign == '-';
    }

    private static boolean isAddition(char sign) {
        return sign == '+';
    }

    private static double parseTerm(Chars chars) {
        double ret = parseArg(chars);
        while (chars.hasNext()) {
            char sign = chars.current();
            if (isMultiplication(sign)) {
                chars.iterate();
                ret *= parseArg(chars);
            }
            else if (isDivision(sign)) {
                chars.iterate();
                ret /= parseArg(chars);
            }
            else {
                break;
            }
        }
        return ret;
    }

    private static boolean isDivision(char sign) {
        return sign == '÷';
    }

    private static boolean isMultiplication(char sign) {
        return sign == '×';
    }

    private static double parseArg(Chars chars) {
        int start = chars.index();
        while (chars.hasNext() && DIGITS.contains(chars.current())) {
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

    char current() {
        return source.charAt(index);
    }

    void iterate() {
        index += 1;
    }

    boolean hasNext() {
        return index < source.length();
    }

    int index() {
        return index;
    }

    String substring(int start, int end) {
        return source.substring(start, end);
    }
}
