package ua.kata;

import java.util.ArrayDeque;
import java.util.Deque;

public class Tree {
  private final Integer value;
  private final Tree left;
  private final Tree right;

  public Tree(Integer value) {
    this(value, null, null);
  }

  public Tree(Integer value, Tree left, Tree right) {
    this.value = value;
    this.left = left;
    this.right = right;
  }

  public String walkLevelByLevel() {
    StringBuilder builder = new StringBuilder();
    builder.append('[');
    Deque<Tree> currentLevel = new ArrayDeque<>();
    if (this.value != null) {
      currentLevel.addFirst(this);
    }
    Deque<Tree> nextLevel = new ArrayDeque<>();
    while (!currentLevel.isEmpty()) {
      printLevel(builder, currentLevel, nextLevel);
      if (!nextLevel.isEmpty()) {
        builder.append(", ");
      }
      if (currentLevel.isEmpty()) {
        Deque<Tree> swap = nextLevel;
        nextLevel = currentLevel;
        currentLevel = swap;
      }
    }
    builder.append(']');
    return builder.toString();
  }

  private void printLevel(StringBuilder builder, Deque<Tree> currentLevel, Deque<Tree> nextLevel) {
    builder.append('[');
    while (!currentLevel.isEmpty()) {
      Tree node = currentLevel.removeLast();
      if (node.value != null) {
        builder.append(node.value);
      }
      if (!currentLevel.isEmpty()) {
        builder.append(", ");
      }
      if (node.left != null) {
        nextLevel.addFirst(node.left);
      }
      if (node.right != null) {
        nextLevel.addFirst(node.right);
      }
    }
    builder.append(']');
  }
}
