package kata.java;

import org.junit.Ignore;
import org.junit.Test;

import static kata.java.Calculator.calculate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringCalcTest {

    @Test
    public void calculateOneDigitNumber() throws Exception {
        assertThat(calculate("6"), is(6.0));
    }

    @Test
    public void calculateMultipleDigitNumber() throws Exception {
        assertThat(calculate("152"), is(152.0));
    }

    @Test
    public void calculateAddition() throws Exception {
        assertThat(calculate("4+5"), is(9.0));
    }

    @Test
    public void calculateSubtraction() throws Exception {
        assertThat(calculate("9-2"), is(7.0));
    }

    @Test
    public void calculateMultiplication() throws Exception {
        assertThat(calculate("5×2"), is(10.0));
    }

    @Test
    public void calculateDivision() throws Exception {
        assertThat(calculate("25÷5"), is(5.0));
    }

    @Test
    public void calculateMultipleOperations() throws Exception {
        assertThat(calculate("24÷6+2-5×2-3"), is(-7.0));
    }
}
