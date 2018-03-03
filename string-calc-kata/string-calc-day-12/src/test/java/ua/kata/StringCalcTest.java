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
    assertThat(calc.compute("-41")).isEqualTo(-41);
  }

  @Test
  void computeAddition() throws Exception {
    assertThat(calc.compute("5+12")).isEqualTo(5 + 12);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("15-12")).isEqualTo(15 - 12);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("5*8")).isEqualTo(5 * 8);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("45/5")).isEqualTo(45 / 5);
  }

  @Test
  void computeManyOperations() throws Exception {
    assertThat(calc.compute("4+12*3-45/5")).isEqualTo(4 + 12 * 3 - 45 / 5);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("(4+12)*3-45/(4+5)")).isEqualTo((4 + 12) * 3 - 45 / (4 + 5));
  }
}
