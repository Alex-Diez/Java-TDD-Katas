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
    public void calculateManyDigitNumber() throws Exception {
        assertThat(Calculator.calculate("23054"), is(23054.0));
    }

    @Test
    public void calculateAddition() throws Exception {
        assertThat(Calculator.calculate("34+23"), is(57.0));
    }

    @Test
    public void calculateSubtraction() throws Exception {
        assertThat(Calculator.calculate("56-12"), is(44.0));
    }

    @Test
    public void calculateMultiplication() throws Exception {
        assertThat(Calculator.calculate("21*3"), is(63.0));
    }

    @Test
    public void calculateDivision() throws Exception {
        assertThat(Calculator.calculate("56/7"), is(8.0));
    }

    @Test
    public void calculateMultipleOperationExpression() throws Exception {
        assertThat(Calculator.calculate("45+67-513/5*3-148+584*8"), is(4328.2));
    }
}
