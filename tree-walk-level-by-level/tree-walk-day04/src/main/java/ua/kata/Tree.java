package ua.kata;

import java.util.ArrayDeque;
import java.util.Queue;

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
    final StringBuilder builder = new StringBuilder();
    Queue<Tree> currentLevel = new ArrayDeque<>();
    currentLevel.add(this);
    Queue<Tree> nextLevel = new ArrayDeque<>();
    while (!currentLevel.isEmpty()) {
      Tree node = currentLevel.remove();
      if (node.value != null) {
        if (builder.length() > 0 && builder.charAt(builder.length() - 1) != '\n') {
          builder.append(", ");
        }
        builder.append(node.value);
      }
      if (node.left != null) {
        nextLevel.add(node.left);
      }
      if (node.right != null) {
        nextLevel.add(node.right);
      }
      if (currentLevel.isEmpty()) {
        if (builder.length() > 0 && !nextLevel.isEmpty()) {
          builder.append('\n');
        }
        Queue<Tree> swap = currentLevel;
        currentLevel = nextLevel;
        nextLevel = swap;
      }
    }
    return builder.toString();
  }
}
