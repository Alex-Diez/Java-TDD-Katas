package kata.java;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringCalcTest {

    @Test
    public void calculateSingleDigitNumber() throws Exception {
        assertThat(Calculator.calculate("2"), is(2.0));
    }

    @Test
    public void calculateMultipleDigitNumber() throws Exception {
        assertThat(Calculator.calculate("2354"), is(2354.0));
    }

    @Test
    public void calculateAddition() throws Exception {
        assertThat(Calculator.calculate("34+21"), is(55.0));
    }

    @Test
    public void calculateSubtraction() throws Exception {
        assertThat(Calculator.calculate("45-32"), is(13.0));
    }

    @Test
    public void calculateMultiplication() throws Exception {
        assertThat(Calculator.calculate("4×5"), is(20.0));
    }

    @Test
    public void calculateDivision() throws Exception {
        assertThat(Calculator.calculate("35÷7"), is(5.0));
    }

    @Test
    public void calculateMultipleOperations() throws Exception {
        assertThat(Calculator.calculate("34+65-88+4×3-64÷2"), is(-9.0));
    }
}
