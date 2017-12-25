package kata.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RxFizzBuzzTest {

  private RxFizzBuzz fizzBuzz;

  @BeforeEach
  void setUp() {
    fizzBuzz = new RxFizzBuzz();
  }

  @Test
  void emptyList_whenGivenEmptyList() throws Exception {
    assertThat(fizzBuzz.fizzBuzz()).isEqualTo(emptyList());
  }

  @Test
  void singleFizz_whenGivenSingle_dividedByThree() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(3)).isEqualTo(singletonList("Fizz"));
  }

  @Test
  void singleBuzz_whenGivenSingle_dividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(5)).isEqualTo(singletonList("Buzz"));
  }

  @Test
  void singleFizzBuzz_whenGivenSingle_dividedByThree_andDividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(3 * 5)).isEqualTo(singletonList("FizzBuzz"));
  }

  @Test
  void singleStringedNumber_whenGivenSingle_neitherDividedByThree_norDividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(1)).isEqualTo(singletonList("1"));
  }
}
