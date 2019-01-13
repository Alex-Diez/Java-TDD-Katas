package ua.kata;

import java.util.Objects;

class TreeSerializer {
  String serialize(Node root) {
    return "[" + innerSerialization(root) + "]";
  }

  private String innerSerialization(Node node) {
    if (node == null) return "-";
    else
      return node.val + ", " +
        innerSerialization(node.left) + ", " +
        innerSerialization(node.right);
  }

  Node deserialize(String serialized) {
    return innerDeserialization(new Walker(serialized.substring(1, serialized.length() - 1)));
  }

  private Node innerDeserialization(Walker walker) {
    if (walker.isExhausted() || walker.isSentinel()) return null;
    Node node = new Node(walker.nodeValue());
    walker.next();
    node.left = innerDeserialization(walker);
    walker.next();
    node.right = innerDeserialization(walker);
    return node;
  }

  static class Walker {
    final String[] nodes;
    int index;

    Walker(String serialized) {
      this.nodes = serialized.split(", ");
      this.index = 0;
    }

    private boolean isExhausted() {
      return index == nodes.length;
    }

    private boolean isSentinel() {
      return "-".equals(nodes[index]);
    }

    private int nodeValue() {
      return Integer.parseInt(nodes[index]);
    }

    void next() {
      index += 1;
    }
  }

  static class Node {
    final int val;
    Node left;
    Node right;

    Node(int val) {
      this(val, null);
    }

    Node(int val, Node left) {
      this(val, left, null);
    }

    Node(int val, Node left, Node right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Node node = (Node) o;
      return val == node.val &&
          Objects.equals(left, node.left) &&
          Objects.equals(right, node.right);
    }

    @Override
    public int hashCode() {
      return Objects.hash(val, left, right);
    }

    @Override
    public String toString() {
      return "Node{" +
          "val=" + val +
          ", left=" + left +
          ", right=" + right +
          '}';
    }
  }
}
