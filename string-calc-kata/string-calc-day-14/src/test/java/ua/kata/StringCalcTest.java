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
    assertThat(calc.compute("-43")).isEqualTo(-43);
  }

  @Test
  void computeAddition() throws Exception {
    assertThat(calc.compute("4+3")).isEqualTo(4 + 3);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("14-2")).isEqualTo(14 - 2);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("4*3")).isEqualTo(4 * 3);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("15/5")).isEqualTo(15 / 5);
  }

  @Test
  void computeManyOperations() throws Exception {
    assertThat(calc.compute("4+3*8-81/9")).isEqualTo(4 + 3 * 8 - 81 / 9);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("(4+3)*8-81/(4+5)")).isEqualTo((4 + 3) * 8 - 81 / (4 + 5));
  }
}
