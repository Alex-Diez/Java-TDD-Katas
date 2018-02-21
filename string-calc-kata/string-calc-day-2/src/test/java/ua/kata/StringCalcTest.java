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
    assertThat(calc.compute("-23")).isEqualTo(-23);
  }

  @Test
  void computeAddition() throws Exception {
    assertThat(calc.compute("4+10")).isEqualTo(14);
  }

  @Test
  void computeSubtraction() throws Exception {
    assertThat(calc.compute("11-4")).isEqualTo(7);
  }

  @Test
  void computeMultiplication() throws Exception {
    assertThat(calc.compute("3*7")).isEqualTo(21);
  }
  
  @Test
  void computeDivision() throws Exception {
    assertThat(calc.compute("42/6")).isEqualTo(7);
  }

  @Test
  void computeManyOperations() throws Exception {
    assertThat(calc.compute("4+2*8-45/9-1")).isEqualTo(4+2*8-45/9-1);
  }

  @Test
  void computeOperationWithParenthesis() throws Exception {
    assertThat(calc.compute("(4+2)*8-40/(9-1)")).isEqualTo((4+2)*8-40/(9-1));
  }
}
