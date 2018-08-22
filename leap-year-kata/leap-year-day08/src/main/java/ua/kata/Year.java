package ua.kata;

public class Year {
  private final int value;

  public Year(int value) {
    this.value = value;
  }

  public boolean isLeap() {
    return (divisibleBy(4) && notDivisibleBy(100)) || divisibleBy(400);
  }

  public boolean isNotLeap() {
    return !isLeap();
  }

  private boolean notDivisibleBy(int div) {
    return !divisibleBy(div);
  }

  private boolean divisibleBy(int div) {
    return value % div == 0;
  }
}
