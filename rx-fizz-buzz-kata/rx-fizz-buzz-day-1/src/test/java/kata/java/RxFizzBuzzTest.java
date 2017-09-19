package kata.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class RxFizzBuzzTest {

  private RxFizzBuzz fizzBuzz;

  @BeforeEach
  void setUp() throws Exception {
    fizzBuzz = new RxFizzBuzz();
  }

  @Test
  void emptyList_whenGivenEmptyList() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(Collections.emptyList()), is(Collections.emptyList()));
  }

  @Test
  void singleFizz_whenGivenSingle_dividedByThree() throws Exception {
      assertThat(fizzBuzz.fizzBuzz(singletonList(3)), is(singletonList("Fizz")));
  }

  @Test
  void singleBuzz_whenGivenSingle_dividedByFive() throws Exception {
      assertThat(fizzBuzz.fizzBuzz(singletonList(5)), is(singletonList("Buzz")));
  }

  @Test
  void singleStringifierNumber_whenGivenSingle_neitherDividedByFive_norByThree() throws Exception {
      assertThat(fizzBuzz.fizzBuzz(singletonList(1)), is(singletonList("1")));
  }

  @Test
  void singleFizzBuzz_whenGivenSingle_dividedByThree_andFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(singletonList(15)), is(singletonList("FizzBuzz")));
  }
}