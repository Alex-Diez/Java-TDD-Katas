package kata.java;

import java.util.List;

import io.reactivex.Observable;

public class RxFizzBuzz {
  public List<String> fizzBuzz(Integer... numbers) {
    return Observable.fromArray(numbers)
      .map(n -> toStringIfEmpty(n, toFizzOrEmpty(n) + toBuzzOrEmpty(n)))
      .toList()
      .blockingGet();
  }

  private String toStringIfEmpty(int n, String val) {
    return val.isEmpty() ? String.valueOf(n) : val;
  }

  private String toFizzOrEmpty(int n) {
    return n % 3 == 0 ? "Fizz" : "";
  }

  private String toBuzzOrEmpty(int n) {
    return n % 5 == 0 ? "Buzz" : "";
  }
}
