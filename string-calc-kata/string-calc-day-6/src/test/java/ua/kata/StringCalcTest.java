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
    assertThat(calc.compute("-20")).isEqualTo(-20);
  }

  @Test
  void computeAddition() throws Exception {
    assertThat(calc.compute("4+6")).isEqualTo(4 + 6);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("6-1")).isEqualTo(6 - 1);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("5*8")).isEqualTo(5 * 8);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("40/5")).isEqualTo(40 / 5);
  }

  @Test
  void computeMultipleOperations() throws Exception {
    assertThat(calc.compute("4+2*8-42/6")).isEqualTo(4 + 2 * 8 - 42 / 6);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("(4+2)*8-42/(6+1)")).isEqualTo((4 + 2) * 8 - 42 / (6 + 1));
  }
}
