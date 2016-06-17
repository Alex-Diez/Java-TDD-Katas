package kata.java;

import org.junit.Test;
import org.junit.internal.Classes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringCalcTest {

    @Test
    public void calculateOneDigitNumber() throws Exception {
        assertThat(Calculator.calculate("2"), is(2.0));
    }

    @Test
    public void calculateManyDigitNumber() throws Exception {
        assertThat(Calculator.calculate("2453"), is(2453.0));
    }

    @Test
    public void calculateAddition() throws Exception {
        assertThat(Calculator.calculate("324+64"), is(388.0));
    }

    @Test
    public void calculateSubtraction() throws Exception {
        assertThat(Calculator.calculate("345-23"), is(345 - 23.0));
    }

    @Test
    public void calculateMultiplication() throws Exception {
        assertThat(Calculator.calculate("34*23"), is(34.0 * 23));
    }

    @Test
    public void calculateDivision() throws Exception {
        assertThat(Calculator.calculate("245/5"), is(245 / 5.0));
    }

    @Test
    public void calculateManyOperations() throws Exception {
        assertThat(Calculator.calculate("354+54/4-13+2*4-23"), is(354+54/4.0-13+2*4.0-23));
    }
}
