package ua.kata;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Predicate;

public class PathSum {
  public Single<List<List<Integer>>> computePath(TreeNode root, int sum) {
    return Observable.<Triple>create(
      emitter -> {
        Queue<Triple> queue = new ArrayDeque<>();
        Triple.from(root).ifPresent(queue::add);
        while (!queue.isEmpty()) {
          Triple triple = queue.poll();
          emitter.onNext(triple);
          triple.children().forEach(queue::add);
        }
        emitter.onComplete();
      }
    ).filter(Triple::isLeaf)
      .filter(Triple.asSum(sum)).map(triple -> triple.path).toList();
  }

  static class Triple {
    final TreeNode node;
    final int prev;
    final List<Integer> path;

    private Triple(TreeNode node, int prev, List<Integer> path) {
      this.node = node;
      this.prev = prev;
      this.path = path;
      path.add(node.val);
    }

    private boolean isLeaf() {
      return node.left == null && node.right == null;
    }

    Stream<Triple> children() {
      return Stream.of(node.left, node.right)
        .filter(Objects::nonNull)
        .map(node -> new Triple(node, prev + node.val, new ArrayList<>(path)));
    }

    static Predicate<Triple> asSum(int sum) {
      return triple -> triple.prev == sum;
    }

    static Optional<Triple> from(TreeNode treeNode) {
      return Optional.ofNullable(treeNode).map(node -> new Triple(node, node.val, new ArrayList<>()));
    }
  }

  static class TreeNode {

    final int val;
    final TreeNode left;
    final TreeNode right;

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
  }
}
