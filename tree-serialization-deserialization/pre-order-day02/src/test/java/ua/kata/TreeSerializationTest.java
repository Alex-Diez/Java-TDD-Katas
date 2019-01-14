package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.kata.TreeSerializer.Node;

class TreeSerializationTest {

  private TreeSerializer treeSerializer;

  @BeforeEach
  void setUp() {
    treeSerializer = new TreeSerializer();
  }

  @Test
  void emptyTree() {
    final String serialized = treeSerializer.serialize(null);
    assertThat(serialized).isEqualTo("[-]");
    assertThat(treeSerializer.deserialize(serialized)).isEqualTo(null);
  }

  @Test
  void oneLevelTree() {
    final String serialized = treeSerializer.serialize(new Node(1));
    assertThat(serialized).isEqualTo("[1, -, -]");
    assertThat(treeSerializer.deserialize(serialized)).isEqualTo(new Node(1));
  }

  @Test
  void twoLevelsTree_onlyLeftNode() {
    final String serialized = treeSerializer.serialize(new Node(1, new Node(2)));
    assertThat(serialized).isEqualTo("[1, 2, -, -, -]");
    assertThat(treeSerializer.deserialize(serialized)).isEqualTo(new Node(1, new Node(2)));
  }

  @Test
  void twoLevelsTree_onlyRightNode() {
    final String serialized = treeSerializer.serialize(new Node(1, null, new Node(3)));
    assertThat(serialized).isEqualTo("[1, -, 3, -, -]");
    assertThat(treeSerializer.deserialize(serialized)).isEqualTo(new Node(1, null, new Node(3)));
  }

  @Test
  void treeLevelsTree_onlyLeftNodes() {
    final String serialized = treeSerializer.serialize(new Node(1, new Node(2, new Node(4))));
    assertThat(serialized).isEqualTo("[1, 2, 4, -, -, -, -]");
    assertThat(treeSerializer.deserialize(serialized)).isEqualTo(new Node(1, new Node(2, new Node(4))));
  }
}
