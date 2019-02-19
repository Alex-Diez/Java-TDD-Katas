package ua.kata;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TreeSerializer {
  String serialize(Node root) {
    return "[" + innerSerialization(root) + "]";
  }

  private String innerSerialization(Node node) {
    if (node == null) {
      return "-";
    } else {
      return node.value + ", " + innerSerialization(node.left) + ", " + innerSerialization(node.right);
    }
  }

  Node deserialize(String serialized) {
    return innerDeserialization(List.of(serialized.substring(1, serialized.length() - 1).split(", ")).iterator());
  }

  private Node innerDeserialization(Iterator<String> tokens) {
    String token = tokens.next();
    if ("-".equals(token)) {
      return null;
    } else {
      Node node = new Node(Integer.parseInt(token));
      node.left = innerDeserialization(tokens);
      node.right = innerDeserialization(tokens);
      return node;
    }
  }

  private static class Wrapper {
    String[] tokens;
    int index;

    Wrapper(String[] tokens) {
      this.tokens = tokens;
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
    public boolean equals(Object object) {
      if (this == object) return true;
      if (object == null || getClass() != object.getClass()) return false;
      Node node = (Node) object;
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
