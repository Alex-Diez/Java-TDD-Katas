package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringCalcTest {

  private StringCalc calc;

  @BeforeEach
  void setUp() {
    calc = new StringCalc();
  }

  @Test
  void computeNegativeNumber() throws Exception {
    assertThat(calc.compute("-13")).hasValue(-13);
  }

  @Test
  void computeAddition() throws Exception {
    assertThat(calc.compute("3+1")).hasValue(4);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("11-4")).hasValue(7);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("4*3")).hasValue(12);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("12/4")).hasValue(3);
  }

  @Test
  void computeMultipleOperations() throws Exception {
    assertThat(calc.compute("3+4*5-42/6")).hasValue(3+4*5-42/6);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("3+(4*5-41)/6*10")).hasValue(3+(4*5-41)/6*10);
  }
}
