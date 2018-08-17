package ua.kata;

public class Year {
  private final int value;

  public Year(int value) {
    this.value = value;
  }

  public boolean isLeap() {
    return (divisibleBy(4) && notDivisible(100)) || divisibleBy(400);
  }

  private boolean notDivisible(int div) {
    return !divisibleBy(div);
  }

  private boolean divisibleBy(int div) {
    return value % div == 0;
  }

  public boolean isNotLeap() {
    return !isLeap();
  }
}
