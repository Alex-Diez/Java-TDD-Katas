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
    assertThat(calc.compute("-42")).isEqualTo(-42);
  }

  @Test
  void computeAddition() throws Exception {
    assertThat(calc.compute("1+2")).isEqualTo(3);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("4-3")).isEqualTo(1);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("5*3")).isEqualTo(15);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("4/2")).isEqualTo(2);
  }

  @Test
  void computeMultipleOperation() throws Exception {
    assertThat(calc.compute("4+2*3-15/5+2")).isEqualTo(4+2*3-15/5+2);
  }
}
