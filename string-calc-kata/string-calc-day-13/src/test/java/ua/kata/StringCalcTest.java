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
    assertThat(calc.compute("4+6")).isEqualTo(4 + 6);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("15-2")).isEqualTo(15 - 2);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("4*8")).isEqualTo(4 * 8);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("35/7")).isEqualTo(35 / 7);
  }

  @Test
  void computeManyOperations() throws Exception {
    assertThat(calc.compute("4+3*8-42/6")).isEqualTo(4 + 3 * 8 - 42 / 6);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("(4+3)*8-42/(6+1)")).isEqualTo((4 + 3) * 8 - 42 / (6 + 1));
  }
}
