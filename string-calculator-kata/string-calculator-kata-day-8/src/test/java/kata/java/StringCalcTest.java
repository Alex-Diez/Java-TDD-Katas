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
    public void calculateManyDigitNumber() throws Exception {
        assertThat(Calculator.calculate("2345"), is(2345.0));
    }

    @Test
    public void calculateAddition() throws Exception {
        assertThat(Calculator.calculate("34+21"), is(34+21.0));
    }

    @Test
    public void calculateSubtraction() throws Exception {
        assertThat(Calculator.calculate("23-12"), is(23-12.0));
    }

    @Test
    public void calculateMultiplication() throws Exception {
        assertThat(Calculator.calculate("34*2"), is(34*2.0));
    }

    @Test
    public void calculateDivision() throws Exception {
        assertThat(Calculator.calculate("45/4"), is(45/4.0));
    }

    @Test
    public void calculateMultipleOperations() throws Exception {
        assertThat(Calculator.calculate("-45+598+8*53-546/31+84-983"), is(-45+598+8*53-546/31.0+84-983));
    }
}
