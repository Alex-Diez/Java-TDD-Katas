package ua.kata;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Stream;

import io.reactivex.Observable;

public class PathSum {
  public List<List<Integer>> computePaths(TreeNode root, int sum) {
    return nodesSource(root).filter(triple -> triple.getLeft().isLeaf() && triple.getMiddle() == sum)
      .map(Triple::getRight)
      .toList()
      .blockingGet();
  }

  private Observable<Triple<TreeNode, Integer, List<Integer>>> nodesSource(TreeNode root) {
    return Observable.create(
      emitter -> {
        if (root != null) {
          Queue<Triple<TreeNode, Integer, List<Integer>>> nodes = new ArrayDeque<>();
          nodes.add(new ImmutableTriple<>(root, root.val, new ArrayList<>()));
          while (!nodes.isEmpty()) {
            Triple<TreeNode, Integer, List<Integer>> node = nodes.poll();
            node.getRight().add(node.getLeft().val);
            node.getLeft().children().map(child -> toTriple(child, node)).forEach(nodes::add);
            emitter.onNext(node);
          }
        }
        emitter.onComplete();
      }
    );
  }

  private Triple<TreeNode, Integer, List<Integer>> toTriple(TreeNode child, Triple<TreeNode, Integer, List<Integer>> parent) {
    return new ImmutableTriple<>(child, parent.getMiddle() + child.val, new ArrayList<>(parent.getRight()));
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

    private boolean isLeaf() {
      return left == null && right == null;
    }

    private Stream<TreeNode> children() {
      return Stream.of(left, right).filter(Objects::nonNull);
    }
  }
}
