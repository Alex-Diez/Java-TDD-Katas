package kata.java;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringCalcTest {

    @Test
    public void calculateOneDigitNumber() throws Exception {
        assertThat(Calculator.calculate("5"), is(5.0));
    }

    @Test
    public void calculateMultiDigitsNumber() throws Exception {
        assertThat(Calculator.calculate("345346"), is(345346.0));
    }

    @Test
    public void calculateAddition() throws Exception {
        assertThat(Calculator.calculate("45+21"), is(45.0 + 21.0));
    }

    @Test
    public void calculateSubtraction() throws Exception {
        assertThat(Calculator.calculate("63-21"), is(63.0 - 21.0));
    }

    @Test
    public void calculateMultiplication() throws Exception {
        assertThat(Calculator.calculate("45*23"), is(45.0 * 23.0));
    }

    @Test
    public void calculateDivision() throws Exception {
        assertThat(Calculator.calculate("676/3"), is(676.0 / 3.0));
    }

    @Test
    public void calculateMultipleOperations() throws Exception {
        assertThat(
                Calculator.calculate("45+341-45/5+32*32-12+48/4"),
                is(45.0 + 341.0 - 45.0 / 5.0 + 32.0 * 32.0 - 12.0 + 48.0 / 4.0)
        );
    }
}
