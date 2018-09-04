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
    return "[" + new TreeWalker(this).walk() + "]";
  }

  private static class TreeWalker {
    private Deque<Tree> currentLevel = new ArrayDeque<>();
    private Deque<Tree> nextLevel = new ArrayDeque<>();
    private StringBuilder builder = new StringBuilder();

    TreeWalker(Tree tree) {
      if (tree.value != null) {
        currentLevel.addFirst(tree);
      }
    }

    String walk() {
      while (!currentLevel.isEmpty()) {
        printCurrentLevel();
        if (currentLevel.isEmpty()) {
          if (!nextLevel.isEmpty()) {
            builder.append(", ");
          }
          swap();
        }
      }
      return builder.toString();
    }

    private void swap() {
      Deque<Tree> swap = nextLevel;
      nextLevel = currentLevel;
      currentLevel = swap;
    }

    private void printCurrentLevel() {
      builder.append('[');
      while (!currentLevel.isEmpty()) {
        Tree node = currentLevel.removeLast();
        builder.append(node.value);
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
}
