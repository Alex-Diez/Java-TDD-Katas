package ua.kata;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

class TreeSerializer {
  String serialize(Node root) {
    return "[" + serialization(root) + "]";
  }

  private String serialization(Node root) {
    Deque<Node> stack = new ArrayDeque<>();
    StringBuilder builder = new StringBuilder();
    stack.push(Objects.requireNonNullElse(root, Node.SENTINEL));
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      if (node == Node.SENTINEL) {
        builder.append('-');
      } else {
        builder.append(node.value);
        stack.push(Objects.requireNonNullElse(node.right, Node.SENTINEL));
        stack.push(Objects.requireNonNullElse(node.left, Node.SENTINEL));
      }
      if (!stack.isEmpty()) {
        builder.append(", ");
      }
    }
    return builder.toString();
  }

  Node deserialize(String serialized) {
    return new Walker(serialized.substring(1, serialized.length() - 1).split(", ")).deserialize();
  }

  private static class Walker {
    private final String[] nodes;
    private int index;

    Walker(String[] nodes) {
      this.nodes = nodes;
    }

    private boolean isSentinel() {
      return "-".equals(nodes[index]);
    }

    private Node deserialize() {
      if (isSentinel()) return null;
      else {
        Node node = new Node(Integer.parseInt(nodes[index]));
        index += 1;
        node.left = deserialize();
        index += 1;
        node.right = deserialize();
        return node;
      }
    }
  }

  static class Node {
    private static final Node SENTINEL = new Node(0);
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
