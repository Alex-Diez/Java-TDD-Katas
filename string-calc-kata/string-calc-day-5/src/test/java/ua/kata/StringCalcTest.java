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
    assertThat(calc.compute("5+4")).isEqualTo(5 + 4);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("10-4")).isEqualTo(10 - 4);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("4*5")).isEqualTo(4 * 5);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("12/3")).isEqualTo(12 / 3);
  }

  @Test
  void computeMultipleOperations() throws Exception {
    assertThat(calc.compute("4+2*10-42/7-1")).isEqualTo(4 + 2 * 10 - 42 / 7 - 1);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("(4+2)*10-42/(7-1)")).isEqualTo((4 + 2) * 10 - 42 / (7 - 1));
  }
}
