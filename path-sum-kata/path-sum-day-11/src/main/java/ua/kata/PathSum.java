package ua.kata;

import java.util.Objects;
import java.util.function.Predicate;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Queue;
import io.vavr.control.Option;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PathSum {
  public Mono<List<List<Integer>>> computePath(TreeNode root, int sum) {
    return Flux.<Node>create(
        emitter -> {
          Queue<Node> queue = Option.of(root)
              .map(treeNode -> Queue.of(new Node(treeNode, List.empty())))
              .getOrElse(Queue.empty());
          while (queue.nonEmpty()) {
            Tuple2<Node, Queue<Node>> tuple = queue.dequeue();
            Node node = tuple._1;
            emitter.next(node);
            queue = node.children().foldLeft(tuple._2, Queue::enqueue);
          }
          emitter.complete();
        }
    ).filter(Node.asSum(sum).and(Node::isLeaf)).map(Node::path).collect(List.collector());
  }

  static class Node {
    final TreeNode treeNode;
    final List<Integer> path;

    Node(TreeNode treeNode, List<Integer> path) {
      this.treeNode = treeNode;
      this.path = path.prepend(treeNode.val);
    }

    static Predicate<Node> asSum(int sum) {
      return node -> node.sum() == sum;
    }

    private int sum() {
      return path.sum().intValue();
    }

    private List<Integer> path() {
      return path.reverse();
    }

    private boolean isLeaf() {
      return treeNode.left == null && treeNode.right == null;
    }

    private List<Node> children() {
      return List.of(treeNode.left, treeNode.right).filter(Objects::nonNull).map(treeNode -> new Node(treeNode, path));
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
