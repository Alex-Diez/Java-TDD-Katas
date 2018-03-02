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
    assertThat(calc.compute("13+2")).isEqualTo(13 + 2);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("21-12")).isEqualTo(21 - 12);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("4*2")).isEqualTo(4 * 2);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("16/4")).isEqualTo(16 / 4);
  }

  @Test
  void computeMultipleOperation() throws Exception {
    assertThat(calc.compute("4+12*8-121/11")).isEqualTo(4 + 12 * 8 - 121 / 11);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("(4+12)*8-121/(5+6)")).isEqualTo((4 + 12) * 8 - 121 / (5 + 6));
  }
}
