package ua.kata;

import java.util.Objects;
import java.util.function.Predicate;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

public class PathSum {
  public Mono<Seq<Seq<Integer>>> computePath(TreeNode root, int sum) {
    return Flux.<Triple>create(
        emitter ->
            Option.of(root)
                .map(node -> new Triple(node, List.empty()))
                .map(triple -> recursiveEmission(triple, emitter))
                .getOrElse(emitter)
                .complete()
    )
        .filter(Triple.asIsSum(sum).and(Triple::isLeaf))
        .map(Triple::path)
        .collect(List.collector());
  }

  private FluxSink<Triple> recursiveEmission(Triple triple, FluxSink<Triple> emitter) {
    emitter.next(triple);
    return triple.children().foldLeft(emitter, (e, child) -> recursiveEmission(child, e));
  }

  static class Triple {

    private final TreeNode treeNode;
    private final List<Integer> path;

    Triple(TreeNode treeNode, List<Integer> path) {
      this.treeNode = treeNode;
      this.path = path.prepend(treeNode.val);
    }

    private static Predicate<Triple> asIsSum(int sum) {
      return node -> node.currentSum() == sum;
    }

    private int currentSum() {
      return path.sum().intValue();
    }

    private Seq<Triple> children() {
      return List.of(treeNode.left, treeNode.right).filter(Objects::nonNull).map(node -> new Triple(node, path));
    }

    private List<Integer> path() {
      return path.reverse();
    }

    private boolean isLeaf() {
      return treeNode.left == null && treeNode.right == null;
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
