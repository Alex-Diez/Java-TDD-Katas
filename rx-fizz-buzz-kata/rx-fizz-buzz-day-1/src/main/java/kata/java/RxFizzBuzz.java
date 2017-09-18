package kata.java;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class RxFizzBuzz {
  public List<String> fizzBuzz(List<Integer> numbers) {
    List<String> ret = new ArrayList<>();
    Observable.fromArray(numbers.toArray(new Integer[0]))
      .map(this::toFizzBuzz)
      .subscribe(ret::add);
    return ret;
  }

  private String toFizzBuzz(int n) {
    if (n % 3 == 0 && n % 5 == 0) return "FizzBuzz";
    if (n % 3 == 0) return "Fizz";
    else if (n % 5 == 0) return "Buzz";
    else return String.valueOf(n);
  }
}
