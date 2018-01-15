package ua.kata;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PathSum {
  public Mono<List<List<Integer>>> computePath(TreeNode root, int sum) {
    return Flux.<Triple>create(
        emitter -> {
          Queue<Triple> queue = new ArrayDeque<>();
          Triple.from(root, new ArrayList<>()).ifPresent(queue::add);
          while (!queue.isEmpty()) {
            Triple triple = queue.poll();
            emitter.next(triple);
            triple.children().forEach(queue::add);
          }
          emitter.complete();
        }
    ).filter(Triple.tripleCurrentSumAs(sum).and(Triple::isLeaf))
        .map(node -> node.path)
        .collect(Collectors.toList());
  }

  static class Triple {
    final TreeNode node;
    final List<Integer> path;

    Triple(TreeNode node, List<Integer> path) {
      this.node = node;
      this.path = path;
      path.add(node.val);
    }

    static Optional<Triple> from(TreeNode treeNode, List<Integer> path) {
      return Optional.ofNullable(treeNode).map(node -> new Triple(node, path));
    }

    static Predicate<Triple> tripleCurrentSumAs(int sum) {
      return triple -> triple.currentSum() == sum;
    }

    Stream<Triple> children() {
      return Stream.of(node.left, node.right)
          .map(node -> Triple.from(node, new ArrayList<>(path)))
          .flatMap(Optional::stream);
    }

    int currentSum() {
      return path.stream().mapToInt(Integer::intValue).sum();
    }

    boolean isLeaf() {
      return this.node.left == null && this.node.right == null;
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
