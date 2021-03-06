package ua.kata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

public class PathSum {
  public Mono<List<List<Integer>>> computePath(TreeNode root, int sum) {
    return Flux.<Triple>create(
      emitter ->
        Triple.from(root, new ArrayList<>())
          .map(triple -> recursiveEmission(triple, emitter))
          .orElse(emitter).complete()
    )
      .filter(Triple.asSum(sum).and(Triple::isLeaf))
      .map(Triple::path)
      .collect(Collectors.toList());
  }

  private FluxSink<Triple> recursiveEmission(Triple node, FluxSink<Triple> emitter) {
    emitter.next(node);
    node.children().forEach(triple -> recursiveEmission(triple, emitter));
    return emitter;
  }

  static class Triple {
    private final TreeNode node;
    private final List<Integer> path;
    private final int currentSum;

    Triple(TreeNode node, List<Integer> path) {
      this.node = node;
      this.path = path;
      path.add(node.val);
      currentSum = path.stream().mapToInt(Integer::intValue).sum();
    }

    static Optional<Triple> from(TreeNode treeNode, List<Integer> path) {
      return Optional.ofNullable(treeNode).map(node -> new Triple(node, path));
    }

    List<Integer> path() {
      return path;
    }

    Stream<Triple> children() {
      return Stream.of(node.left, node.right)
        .map(node -> Triple.from(node, new ArrayList<>(path)))
        .flatMap(Optional::stream);
    }

    boolean isLeaf() {
      return node.left == null && node.right == null;
    }

    static Predicate<Triple> asSum(int sum) {
      return triple -> triple.currentSum == sum;
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
