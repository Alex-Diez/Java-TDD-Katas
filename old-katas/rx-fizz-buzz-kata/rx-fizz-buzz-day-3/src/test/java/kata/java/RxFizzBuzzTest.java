package kata.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class RxFizzBuzzTest {

  private RxFizzBuzz fizzBuzz;

  @BeforeEach
  void setUp() {
    fizzBuzz = new RxFizzBuzz();
  }

  @Test
  void emptyList_whenGivenEmptyList() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(), is(emptyList()));
  }

  @Test
  void singleFizz_whenGivenSingle_dividedByThree() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(3), is(singletonList("Fizz")));
  }
  
  @Test
  void singleBuzz_whenGivenSingle_dividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(5), is(singletonList("Buzz")));
  }

  @Test
  void singleFizzBuzz_whenGivenSingle_dividedByThree_andFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(3 * 5), is(singletonList("FizzBuzz")));
  }

  @Test
  void singleStringNumber_whenGivenSingle_neitherDividedByThree_norDividedByFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(1), is(singletonList("1")));
  }
}
