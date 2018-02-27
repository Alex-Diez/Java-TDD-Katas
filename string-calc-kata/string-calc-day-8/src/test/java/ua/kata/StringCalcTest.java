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
    assertThat(calc.compute("-12")).isEqualTo(-12);
  }

  @Test
  void computeAddition() throws Exception {
    assertThat(calc.compute("4+3")).isEqualTo(4 + 3);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("5-1")).isEqualTo(5 - 1);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("4*8")).isEqualTo(4 * 8);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("32/2")).isEqualTo(32 / 2);
  }

  @Test
  void computeMultipleOperations() throws Exception {
    assertThat(calc.compute("4+3*7-42/6")).isEqualTo(4 + 3 * 7 - 42 / 6);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("(4+3)*7-42/(6+1)")).isEqualTo((4 + 3) * 7 - 42 / (6 + 1));
  }
}
