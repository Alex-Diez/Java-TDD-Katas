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
    ).filter(Triple.asCurrentSum(sum).and(Triple::isLeaf))
        .map(Triple::path)
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

    Stream<Triple> children() {
      return Stream.of(node.left, node.right)
          .map(node -> Triple.from(node, new ArrayList<>(path)))
          .flatMap(Optional::stream);
    }

    List<Integer> path() {
      return path;
    }

    int currentSum() {
      return path.stream().mapToInt(Integer::intValue).sum();
    }

    static Predicate<Triple> asCurrentSum(int sum) {
      return triple -> triple.currentSum() == sum;
    }

    private boolean isLeaf() {
      return node.left == null && node.right == null;
    }
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
  }
}
