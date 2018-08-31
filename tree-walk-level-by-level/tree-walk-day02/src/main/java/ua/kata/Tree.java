package ua.kata;

import java.util.Optional;

public class Tree<V> {
  private final V value;
  private final Tree<V> left;
  private final Tree<V> right;

  public Tree(V value) {
    this(value, null, null);
  }

  public Tree(V value, Tree<V> left, Tree<V> right) {
    this.value = value;
    this.left = left;
    this.right = right;
  }

  public String walkLevelByLevel() {
    return Optional.ofNullable(value)
        .map(Object::toString)
        .map(s -> s + walk(left) + walk(right))
        .orElse("");
  }

  private String walk(Tree<V> node) {
    return Optional.ofNullable(node)
        .map(Tree::walkLevelByLevel)
        .filter(n -> !n.isEmpty())
        .map(n -> ", " + n)
        .orElse("");
  }
}
