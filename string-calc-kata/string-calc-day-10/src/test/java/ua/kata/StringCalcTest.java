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
    assertThat(calc.compute("-14")).isEqualTo(-14);
  }

  @Test
  void computeAddition() throws Exception {
    assertThat(calc.compute("4+5")).isEqualTo(4 + 5);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("31-12")).isEqualTo(31 - 12);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("4*8")).isEqualTo(4 * 8);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("16/4")).isEqualTo(16 / 4);
  }

  @Test
  void computeMultipleOperations() throws Exception {
    assertThat(calc.compute("4+3*8-90/3")).isEqualTo(4 + 3 * 8 - 90 / 3);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("(4+3)*8-(90-9)/3")).isEqualTo((4 + 3) * 8 - (90 - 9) / 3);
  }
}
