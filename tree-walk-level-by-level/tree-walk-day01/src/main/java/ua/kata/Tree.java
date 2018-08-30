package ua.kata;

import java.util.Optional;

public class Tree {
  public static final Tree EMPTY_LEAF = new Tree(null);
  private final Integer value;
  private final Tree left;
  private final Tree right;

  public Tree(Integer value) {
    this(value, EMPTY_LEAF);
  }

  public Tree(Integer value, Tree left) {
    this(value, left, EMPTY_LEAF);
  }

  public Tree(Integer value, Tree left, Tree right) {
    this.value = value;
    this.left = left;
    this.right = right;
  }

  public String walkLevelByLevel() {
    return currentNode() + leftWalk() + rightWalk();
  }

  private String leftWalk() {
    return walk(left);
  }

  private String walk(Tree subTree) {
    return Optional.ofNullable(subTree)
        .map(Tree::walkLevelByLevel)
        .filter(node -> !node.isEmpty())
        .map(node -> ", " + node)
        .orElse("");
  }

  private String rightWalk() {
    return walk(right);
  }

  private String currentNode() {
    return value == null ? "" : value.toString();
  }
}
