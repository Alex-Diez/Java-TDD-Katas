package kata.java;

import org.junit.Test;

import static kata.java.Calculator.calculate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringCalcTest {

    @Test
    public void calculateOneDigitNumber() throws Exception {
        assertThat(calculate("1"), is(1.0));
    }

    @Test
    public void calculateManyDigitsNumber() throws Exception {
        assertThat(calculate("101"), is(101.0));
    }
    
    @Test
    public void calculateAddition() throws Exception {
        assertThat(calculate("1+2"), is(3.0));
    }

    @Test
    public void calculateSubtraction() throws Exception {
        assertThat(calculate("2-1"), is(1.0));
    }

    @Test
    public void calculateMultiplication() throws Exception {
        assertThat(calculate("2×3"), is(6.0));
    }

    @Test
    public void calculateDivisions() throws Exception {
        assertThat(calculate("6÷2"), is(3.0));
    }
}
