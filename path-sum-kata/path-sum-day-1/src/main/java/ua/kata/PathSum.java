package ua.kata;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathSum {
  public List<List<Integer>> computePaths(TreeNode root, int sum) {
    List<List<Integer>> paths = new ArrayList<>();
    if (root != null) {
      Queue<Triple<TreeNode, Integer, List<Integer>>> queue = new ArrayDeque<>();
      queue.add(toTripleNode(root, 0, new ArrayList<>()));
      while (!queue.isEmpty()) {
        Triple<TreeNode, Integer, List<Integer>> node = queue.poll();
        TreeNode treeNode = node.getLeft();
        int prev = node.getMiddle();
        List<Integer> path = node.getRight();
        path.add(treeNode.val);
        int currentLevelSum = treeNode.val + prev;
        if (currentLevelSum == sum && treeNode.isLeaf()) {
          paths.add(path);
        } else {
          queue.addAll(
            treeNode
              .children()
              .map(child -> toTripleNode(child, currentLevelSum, path))
              .collect(Collectors.toCollection(ArrayDeque::new))
          );
        }
      }
    }
    return paths;
  }

  private Triple<TreeNode, Integer, List<Integer>> toTripleNode(TreeNode treeNode, int prev, List<Integer> path) {
    return new ImmutableTriple<>(treeNode, prev, new ArrayList<>(path));
  }

  public static class TreeNode {

    private final int val;
    private final TreeNode left;
    private final TreeNode right;

    public TreeNode(int val) {
      this(val, null, null);
    }

    public TreeNode(int val, TreeNode left) {
      this(val, left, null);
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }

    Stream<TreeNode> children() {
      return Stream.of(Optional.ofNullable(left), Optional.ofNullable(right)).flatMap(Optional::stream);
    }

    boolean isLeaf() {
      return left == null && right == null;
    }
  }
}
