package ua.kata;

import java.util.function.IntPredicate;

public class Year {
  private final int value;

  public Year(int value) {
    this.value = value;
  }

  public boolean isLeap() {
    return divisibleBy(4).and(notDivisibleBy(100)).or(divisibleBy(400)).test(value);
  }

  public boolean isNotLeap() {
    return !isLeap();
  }

  private static IntPredicate divisibleBy(int div) {
    return val -> val % div == 0;
  }

  private static IntPredicate notDivisibleBy(int div) {
    return divisibleBy(div).negate();
  }
}
