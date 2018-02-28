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
    assertThat(calc.compute("-5")).isEqualTo(-5);
  }

  @Test
  void computeAddition() throws Exception {
    assertThat(calc.compute("4+3")).isEqualTo(4 + 3);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("15-1")).isEqualTo(15 - 1);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("4*3")).isEqualTo(4 * 3);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("16/2")).isEqualTo(16 / 2);
  }

  @Test
  void computeManyOperations() throws Exception {
    assertThat(calc.compute("3+2*4-45/9")).isEqualTo(3 + 2 * 4 - 45 / 9);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("(3+2)*4-(45-10)/7")).isEqualTo((3 + 2) * 4 - (45 - 10) / 7);
  }
}
