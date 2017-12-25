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
        assertThat(Calculator.calculate("346598"), is(346598.0));
    }

    @Test
    public void calculateAddition() throws Exception {
        assertThat(Calculator.calculate("45+123"), is(45.0 + 123.0));
    }

    @Test
    public void calculateSubtraction() throws Exception {
        assertThat(Calculator.calculate("436-43"), is(436.0 - 43.0));
    }

    @Test
    public void calculateMultiplication() throws Exception {
        assertThat(Calculator.calculate("45*12"), is(45.0 * 12.0));
    }

    @Test
    public void calculateDivision() throws Exception {
        assertThat(Calculator.calculate("4567/78"), is(4567.0 / 78.0));
    }

    @Test
    public void calculateMultipleOperations() throws Exception {
        assertThat(Calculator.calculate("436+68/321-546*5"), is(436.0 + 68.0 / 321.0 - 546.0 * 5.0));
    }
}
