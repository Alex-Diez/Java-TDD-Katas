package kata.java;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringCalcTest {

    @Test
    public void calculateOneDigitNumber() throws Exception {
        assertThat(Calculator.calculate("2"), is(2.0));
    }

    @Test
    public void calculateMultiDigitNumber() throws Exception {
        assertThat(Calculator.calculate("1203"), is(1203.0));
    }

    @Test
    public void calculateAddition() throws Exception {
        assertThat(Calculator.calculate("23+31"), is(54.0));
    }

    @Test
    public void calculateSubtraction() throws Exception {
        assertThat(Calculator.calculate("12-3"), is(9.0));
    }

    @Test
    public void calculateMultiplication() throws Exception {
        assertThat(Calculator.calculate("24×2"), is(48.0));
    }

    @Test
    public void calculateDivision() throws Exception {
        assertThat(Calculator.calculate("25÷2"), is(12.5));
    }

    @Test
    public void calculateMultipleOperations() throws Exception {
        assertThat(Calculator.calculate("34+656-64÷8+3×6"), is(700.0));
    }
}
