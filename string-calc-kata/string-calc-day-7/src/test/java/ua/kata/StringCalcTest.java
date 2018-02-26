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
    assertThat(calc.compute("-15")).isEqualTo(-15);
  }

  @Test
  void computeAddition() throws Exception {
    assertThat(calc.compute("4+6")).isEqualTo(4 + 6);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("10-3")).isEqualTo(10 - 3);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("34*3")).isEqualTo(34 * 3);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("35/7")).isEqualTo(35 / 7);
  }

  @Test
  void computeManyOperations() throws Exception {
    assertThat(calc.compute("4-13*7+42/6")).isEqualTo(4 - 13 * 7 + 42 / 6);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("(4-13)*(7+42)/7")).isEqualTo((4 - 13) * (7 + 42) / 7);
  }
}
