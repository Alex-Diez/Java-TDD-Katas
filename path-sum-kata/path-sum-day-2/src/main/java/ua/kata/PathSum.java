package ua.kata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathSum {
  public List<List<Integer>> computePaths(TreeNode root, int sum) {
    return computePathsRecursively(root, sum, new ArrayList<>());
  }

  private List<List<Integer>> computePathsRecursively(TreeNode treeNode, int sum, List<Integer> path) {
    return Optional.ofNullable(treeNode)
      .stream()
      .peek(node -> path.add(node.val))
      .flatMap(node -> {
        if (node.val == sum && node.isLeaf()) {
          return List.of(path).stream();
        } else {
          return node.children()
            .map(child -> computePathsRecursively(child, sum - node.val, new ArrayList<>(path)))
            .flatMap(List::stream);
        }
      }).collect(Collectors.toList());
  }

  static class TreeNode {

    private final int val;
    private final TreeNode left;
    private final TreeNode right;

    TreeNode(int val) {
      this(val, null, null);
    }

    TreeNode(int val, TreeNode left) {
      this(val, left, null);
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }

    boolean isLeaf() {
      return left == null && right == null;
    }

    Stream<TreeNode> children() {
      return Stream.of(Optional.ofNullable(left), Optional.ofNullable(right)).flatMap(Optional::stream);
    }
  }
}
