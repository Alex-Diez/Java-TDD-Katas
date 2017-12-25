package kata.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RxFizzBuzzTest {

  private RxFizzBuzz fizzBuzz;

  @BeforeEach
  void setUp() {
    fizzBuzz = new RxFizzBuzz();
  }

  @Test
  void emptyList_whenGivenEmptyList() throws Exception {
    assertThat(fizzBuzz.fizzBuzz()).isEqualTo(List.of());
  }

  @Test
  void singleFizz_whenGiven_singleDividedByThree() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(3)).isEqualTo(List.of("Fizz"));
  }

  @Test
  void singleBuzz_whenGiven_singleDividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(5)).isEqualTo(List.of("Buzz"));
  }

  @Test
  void strinedNumber_whenGiven_neitherDividedByThree_norDividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(1)).isEqualTo(List.of("1"));
  }

  @Test
  void singleFizzBuzz_whenGiven_dividedByThree_andDividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(3 * 5)).isEqualTo(List.of("FizzBuzz"));
  }
}
