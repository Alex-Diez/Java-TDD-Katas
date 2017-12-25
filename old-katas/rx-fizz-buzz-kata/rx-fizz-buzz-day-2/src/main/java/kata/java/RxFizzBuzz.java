package kata.java;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RxFizzBuzz {
  public List<String> fizzBuzz(Integer... numbers) {
    List<String> ret = new ArrayList<>();
    Observable.fromArray(numbers)
      .subscribe(
        n -> ret.add(toStringIfEmpty(n, toFizzOrEmpty(n) + toBuzzOrEmpty(n)))
      );
    return ret;
  }

  private String toFizzOrEmpty(int n) {
    return n % 3 == 0 ? "Fizz" : "";
  }

  private String toBuzzOrEmpty(int n) {
    return n % 5 == 0 ? "Buzz" : "";
  }

  private String toStringIfEmpty(int n, String val) {
    return val.isEmpty() ? String.valueOf(n) : val;
  }
}
