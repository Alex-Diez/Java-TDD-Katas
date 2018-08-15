package ua.kata;

public class Year {
  private final int val;

  public Year(int val) {
    this.val = val;
  }

  public boolean isLeap() {
    return (divisibleBy(4) && notDivisibleBy(100)) || divisibleBy(400);
  }

  private boolean divisibleBy(int div) {
    return val % div == 0;
  }

  private boolean notDivisibleBy(int div) {
    return !divisibleBy(div);
  }

  public boolean isNotLeap() {
    return !isLeap();
  }
}
