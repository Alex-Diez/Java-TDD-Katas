package kata.java;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RxFizzBuzzTest {

  private RxFizzBuzz fizzBuzz;

  @Before
  public void setUp() throws Exception {
    fizzBuzz = new RxFizzBuzz();
  }

  @Test
  public void emptyList_whenGivenEmptyList() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(Collections.emptyList()), is(Collections.emptyList()));
  }

  @Test
  public void singleFizz_whenGivenSingle_dividedByThree() throws Exception {
      assertThat(fizzBuzz.fizzBuzz(singletonList(3)), is(singletonList("Fizz")));
  }

  @Test
  public void singleBuzz_whenGivenSingle_dividedByFive() throws Exception {
      assertThat(fizzBuzz.fizzBuzz(singletonList(5)), is(singletonList("Buzz")));
  }

  @Test
  public void singleStringifierNumber_whenGivenSingle_neitherDividedByFive_norByThree() throws Exception {
      assertThat(fizzBuzz.fizzBuzz(singletonList(1)), is(singletonList("1")));
  }

  @Test
  public void singleFizzBuzz_whenGivenSingle_dividedByThree_andFive() throws Exception {
    assertThat(fizzBuzz.fizzBuzz(singletonList(15)), is(singletonList("FizzBuzz")));
  }
}