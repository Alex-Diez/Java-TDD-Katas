package kata.java;

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
        assertThat(Calculator.calculate("45667"), is(45667.0));
    }

    @Test
    public void calculateAddition() throws Exception {
        assertThat(Calculator.calculate("26+87"), is(113.0));
    }

    @Test
    public void calculateSubtraction() throws Exception {
        assertThat(Calculator.calculate("564-23"), is(541.0));
    }

    @Test
    public void calculateMultiplication() throws Exception {
        assertThat(Calculator.calculate("43*3"), is(129.0));
    }

    @Test
    public void calculateDivision() throws Exception {
        assertThat(Calculator.calculate("42/7"), is(6.0));
    }

    @Test
    public void calculateMultipleOperation() throws Exception {
        assertThat(Calculator.calculate("45+8*2-31+6/3-43"), is(-11.0));
    }
}
