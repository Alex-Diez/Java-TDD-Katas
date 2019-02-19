package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ua.kata.TreeSerializer.Node;

import static org.assertj.core.api.Assertions.assertThat;

class TreeSerializationTest {

  private TreeSerializer serializer;

  @BeforeEach
  void setUp() {
    serializer = new TreeSerializer();
  }

  @Test
  void emptyTree() {
    String serialized = serializer.serialize(null);
    assertThat(serialized).isEqualTo("[-]");
    assertThat(serializer.deserialize(serialized)).isEqualTo(null);
  }

  @Test
  void oneItemTree() {
    String serialized = serializer.serialize(new Node(10));
    assertThat(serialized).isEqualTo("[10, -, -]");
    assertThat(serializer.deserialize(serialized)).isEqualTo(new Node(10));
  }

  @Test
  void rootNode_withLeftChild() {
    String serialized = serializer.serialize(new Node(10, new Node(20)));
    assertThat(serialized).isEqualTo("[10, 20, -, -, -]");
    assertThat(serializer.deserialize(serialized)).isEqualTo(new Node(10, new Node(20)));
  }

  @Test
  void fullTwoLevelsTree() {
    String serialized = serializer.serialize(new Node(10, new Node(20), new Node(30)));
    assertThat(serialized).isEqualTo("[10, 20, -, -, 30, -, -]");
    assertThat(serializer.deserialize(serialized)).isEqualTo(new Node(10, new Node(20), new Node(30)));
  }
}
