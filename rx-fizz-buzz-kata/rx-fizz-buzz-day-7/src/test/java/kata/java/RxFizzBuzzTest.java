package kata.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class RxFizzBuzzTest {

  private RxFizzBuzz fizzBuzz;

  @BeforeEach
  void setUp() {
    fizzBuzz = new RxFizzBuzz();
  }

  @Test
  void emptyList_whenGiven_emptyList() throws Exception {
    assertThat(fizzBuzz.fizzBuzz()).isEqualTo(List.of());
  }

  @Test
  void singleFizz_when_singleDividedByThree() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(3)).isEqualTo(List.of("Fizz"));
  }

  @Test
  void singleBuzz_when_singleDividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(5)).isEqualTo(List.of("Buzz"));
  }

  @Test
  void singleFizzBuzz_when_singleDividedByThree_andFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(3 * 5)).isEqualTo(List.of("FizzBuzz"));
  }

  @Test
  void singleStringedNumber_when_neitherDividedByThree_norFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(1)).isEqualTo(List.of("1"));
  }
}
