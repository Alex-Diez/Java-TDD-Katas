package ua.kata;

public class Year {
  private final int val;

  public Year(int val) {
    this.val = val;
  }

  public boolean isLeap() {
    return (divisibleBy(4) && notDivisible(100)) || divisibleBy(400);
  }

  private boolean notDivisible(int div) {
    return !(divisibleBy(div));
  }

  private boolean divisibleBy(int div) {
    return val % div == 0;
  }

  public boolean isNotLeap() {
    return !isLeap();
  }
}
