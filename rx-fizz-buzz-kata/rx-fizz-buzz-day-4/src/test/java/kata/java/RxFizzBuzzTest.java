package kata.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;

class RxFizzBuzzTest {

  private RxFizzBuzz fizzBuzz;

  @BeforeEach
  void setUp() {
    fizzBuzz = new RxFizzBuzz();
  }

  @Test
  void emptyList_whenGivenEmptyList() throws Exception {
    assertThat(fizzBuzz.fizzBuzz()).isEmpty();
  }
  
  @Test
  void singleFizz_whenGiven_singleDividedByThree() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(3)).isEqualTo(singletonList("Fizz"));
  }

  @Test
  void singleBuzz_whenGiven_singleDividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(5)).isEqualTo(singletonList("Buzz"));
  }

  @Test
  void singleFizzBuzz_whenGiven_singleDividedByThree_andFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(3 * 5)).isEqualTo(singletonList("FizzBuzz"));
  }
  
  @Test
  void singleStringNumber_whenGiven_neitherDividedByThree_norDividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(1)).isEqualTo(singletonList("1"));
  }
}
