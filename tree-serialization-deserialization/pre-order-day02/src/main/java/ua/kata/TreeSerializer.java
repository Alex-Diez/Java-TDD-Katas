package ua.kata;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

class TreeSerializer {
  String serialize(Node root) {
    return "[" + innerSerialization(root) + "]";
  }

  private String innerSerialization(Node root) {
    StringBuilder builder = new StringBuilder();
    Deque<Node> stack = new ArrayDeque<>();
    stack.push(Objects.requireNonNullElse(root, Sentinel.SENTINEL));
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      if (node == Sentinel.SENTINEL) {
        builder.append('-');
      } else {
        builder.append(node.value);
        stack.push(Objects.requireNonNullElse(node.right, Sentinel.SENTINEL));
        stack.push(Objects.requireNonNullElse(node.left, Sentinel.SENTINEL));
      }
      if (!stack.isEmpty()) {
        builder.append(", ");
      }
    }
    return builder.toString();
  }

  Node deserialize(String serialized) {
    return innerDeserialization(new Walker(serialized.substring(1, serialized.length() - 1).split(", ")));
  }

  private Node innerDeserialization(Walker walker) {
    if ("-".equals(walker.next())) {
      return null;
    } else {
      final Node node = new Node(Integer.parseInt(walker.next()));
      walker.index += 1;
      node.left = innerDeserialization(walker);
      walker.index += 1;
      node.right = innerDeserialization(walker);
      return node;
    }
  }

  static class Walker {
    private final String[] nodes;
    private int index;

    Walker(String[] nodes) {
      this.nodes = nodes;
    }

    private String next() {
      return nodes[index];
    }
  }

  static class Sentinel extends Node {
    static final Sentinel SENTINEL = new Sentinel(0, null, null);

    private Sentinel(int value, Node left, Node right) {
      super(value, left, right);
    }
  }

  static class Node {
    private final int value;
    private Node left;
    private Node right;

    Node(int value) {
      this(value, null);
    }

    Node(int value, Node left) {
      this(value, left, null);
    }

    Node(int value, Node left, Node right) {
      this.value = value;
      this.left = left;
      this.right = right;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Node node = (Node) o;
      return value == node.value &&
          Objects.equals(left, node.left) &&
          Objects.equals(right, node.right);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, left, right);
    }

    @Override
    public String toString() {
      return "Node{" +
          "value=" + value +
          ", left=" + left +
          ", right=" + right +
          '}';
    }
  }
}
