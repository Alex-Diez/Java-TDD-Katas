package ua.kata;

import java.util.function.Predicate;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

public class PathSum {
  public Mono<List<List<Integer>>> computePath(TreeNode root, int sum) {
      return Flux.<Tuple2<TreeNode, List<Integer>>>create(
          emitter -> from(root, List.empty())
              .map(tuple -> recursiveEmission(emitter, tuple))
              .getOrElse(emitter)
              .complete()
      )
          .filter(asCurrentSum(sum).and(this::isLeaf))
          .map(this::reversePath)
          .collect(List.collector());
  }

  private Option<Tuple2<TreeNode, List<Integer>>> from(TreeNode treeNode, List<Integer> path) {
    return Option.of(treeNode).map(node -> new Tuple2<>(node, path.prepend(node.val)));
  }

  private List<Integer> reversePath(Tuple2<TreeNode, List<Integer>> tuple) {
    return tuple._2().reverse();
  }

  private boolean isLeaf(Tuple2<TreeNode, List<Integer>> tuple) {
    return tuple._1().isLeaf();
  }

  private Predicate<Tuple2<TreeNode, List<Integer>>> asCurrentSum(int sum) {
    return tuple -> tuple._2().sum().intValue() == sum;
  }

  private FluxSink<Tuple2<TreeNode, List<Integer>>> recursiveEmission(
      FluxSink<Tuple2<TreeNode, List<Integer>>> emitter,
      Tuple2<TreeNode, List<Integer>> tuple) {
    return tuple._1()
        .children()
        .map(child -> from(child, tuple._2()))
        .flatMap(Option::toList)
        .foldLeft(emitter, this::recursiveEmission)
        .next(tuple);
  }

  static class TreeNode {

    private final int val;
    private final TreeNode left;
    private final TreeNode right;

    TreeNode(int val) {
      this(val, null);
    }

    TreeNode(int val, TreeNode left) {
      this(val, left, null);
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }

    private List<TreeNode> children() {
      return List.of(left, right);
    }

    private boolean isLeaf() {
      return left == null && right == null;
    }
  }
}
