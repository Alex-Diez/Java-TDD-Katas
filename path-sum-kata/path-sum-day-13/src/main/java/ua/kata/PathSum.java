package ua.kata;

import java.util.Objects;
import java.util.function.Predicate;

import io.vavr.Value;
import io.vavr.collection.List;
import io.vavr.control.Option;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class PathSum {
  public Flux<List<Integer>> computePath(TreeNode root, int sum) {
    return Flux.<Node>create(
        emitter -> Node.from(root, List.empty())
            .map(node -> recursiveEmission(node, emitter))
            .getOrElse(emitter)
            .complete()
    )
        .filter(asNodeSum(sum).and(Node::isLeaf))
        .map(Node::path);
  }

  private static Predicate<Node> asNodeSum(int sum) {
    return node -> node.sum() == sum;
  }

  private FluxSink<Node> recursiveEmission(Node node, FluxSink<Node> emitter) {
    return node.children().foldLeft(emitter, (e, child) -> recursiveEmission(child, e)).next(node);
  }

  static class Node {
    final TreeNode treeNode;
    final List<Integer> path;

    Node(TreeNode treeNode, List<Integer> path) {
      this.treeNode = treeNode;
      this.path = path.prepend(treeNode.val);
    }

    static Option<Node> from(TreeNode treeNode, List<Integer> path) {
      return Option.of(treeNode).map(node -> new Node(node, path));
    }

    List<Node> children() {
      return List.of(treeNode.left, treeNode.right).map(treeNode -> Node.from(treeNode, path)).flatMap(Value::toList);
    }

    int sum() {
      return path.sum().intValue();
    }

    private boolean isLeaf() {
      return treeNode.left == null && treeNode.right == null;
    }

    private List<Integer> path() {
      return path.reverse();
    }
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
  }
}
