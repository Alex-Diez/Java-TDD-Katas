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
    assertThat(calc.compute("23+3")).isEqualTo(26);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("41-21")).isEqualTo(20);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("4*8")).isEqualTo(32);
  }

  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("12/3")).isEqualTo(4);
  }

  @Test
  void computeMultipleOperations() throws Exception {
    assertThat(calc.compute("4+2*1-12/4+5")).isEqualTo(4+2*1-12/4+5);
  }

  @Test
  void computeOperationsWithParenthesis() throws Exception {
    assertThat(calc.compute("((4+2)*5-14)/4+5")).isEqualTo(((4+2)*5-14)/4+5);
  }
}
