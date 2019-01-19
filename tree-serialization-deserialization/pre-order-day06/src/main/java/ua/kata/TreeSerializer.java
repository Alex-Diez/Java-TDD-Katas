package ua.kata;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TreeSerializer {
  String serialize(Node root) {
    Deque<Optional<Node>> stack = new ArrayDeque<>();
    Deque<Optional<Node>> queue = new ArrayDeque<>();
    stack.push(Optional.ofNullable(root));
    while (!stack.isEmpty()) {
      Optional<Node> node = stack.pop();
      queue.addLast(node);
      node.ifPresent(n -> n.children().map(Optional::ofNullable).forEach(stack::push));
    }
    return "[" + queue.stream()
        .map(o -> o.map(Node::serializedValue).orElse("-"))
        .collect(Collectors.joining(", ")) + "]";
  }

  Node deserialize(String serialized) {
    return innerDeserialization(new Walker(serialized.substring(1, serialized.length() - 1).split(", ")));
  }

  private Node innerDeserialization(Walker walker) {
    if ("-".equals(walker.nodes[walker.index])) {
      return null;
    } else {
      Node node = new Node(Integer.parseInt(walker.nodes[walker.index]));
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

    String serializedValue() {
      return String.valueOf(value);
    }

    Stream<Node> children() {
      return Stream.of(right, left);
    }
  }
}
