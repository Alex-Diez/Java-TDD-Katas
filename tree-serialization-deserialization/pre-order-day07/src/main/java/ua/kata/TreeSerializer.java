package ua.kata;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

class TreeSerializer {
  String serialize(Node root) {
    return "[" + innerSerialization(root) + "]";
  }

  private String innerSerialization(Node node) {
    if (node == null) return "-";
    else return node.value + ", " + innerSerialization(node.left) + ", " + innerSerialization(node.right);
  }

  Node deserialize(String serialized) {
    return iterativeDeserialization(serialized.substring(1, serialized.length() - 1).split(", "));
  }

  private Node iterativeDeserialization(String[] tokens) {
    Node root = null;
    if (!"-".equals(tokens[0])) {
      root = new Node(Integer.parseInt(tokens[0]));
    }
    if (root != null) {
      Node node = root;
      Deque<Node> deque = new ArrayDeque<>();
      deque.addFirst(root);
      int i = 1;
      while (true) {
        while (!"-".equals(tokens[i])) {
          node.left = new Node(Integer.parseInt(tokens[i]));
          node = node.left;
          deque.addFirst(node);
          i += 1;
        }
        i += 1;
        while (!deque.isEmpty() && "-".equals(tokens[i])) {
          node = deque.removeFirst();
          i += 1;
        }
        if (!deque.isEmpty()) {
          node = deque.removeFirst();
        }
        if (i == tokens.length) {
          break;
        }
        node.right = new Node(Integer.parseInt(tokens[i]));
        node = node.right;
        deque.addFirst(node);
        i += 1;
      }
    }
    return root;
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
