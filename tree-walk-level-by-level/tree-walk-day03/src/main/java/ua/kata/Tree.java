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
    final Queue<Tree> queue = new ArrayDeque<>();
    queue.add(this);
    while (!queue.isEmpty()) {
      final Tree node = queue.poll();
      if (builder.length() > 0) {
        builder.append(", ");
      }
      if (node.value != null) {
        builder.append(node.value);
      }
      if (node.left != null) {
        queue.add(node.left);
      }
      if (node.right != null) {
        queue.add(node.right);
      }
    }
    return builder.toString();
  }
}
