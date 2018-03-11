package ua.kata;

import java.util.Optional;

public class PersistentQueue {

  public Tuple dequeue() {
    return new Tuple();
  }

  public static class Tuple {

    public Optional<Integer> value() {
      return Optional.empty();
    }
  }
}
