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
    final String serialized = serializer.serialize(null);
    assertThat(serialized).isEqualTo("[-]");
    assertThat(serializer.deserialize(serialized)).isEqualTo(null);
  }

  @Test
  void oneLevelTree() {
    final String serialized = serializer.serialize(new Node(1));
    assertThat(serialized).isEqualTo("[1, -, -]");
    assertThat(serializer.deserialize(serialized)).isEqualTo(new Node(1));
  }

  @Test
  void twoLevelsTree_onlyLeftNode() {
    final String serialized = serializer.serialize(new Node(1, new Node(2)));
    assertThat(serialized).isEqualTo("[1, 2, -, -, -]");
    assertThat(serializer.deserialize(serialized))
        .isEqualTo(
            new Node(1, new Node(2))
        );
  }

  @Test
  void twoLevelsTree_onlyRightNode() {
    final String serialized = serializer.serialize(new Node(1, null, new Node(3)));
    assertThat(serialized).isEqualTo("[1, -, 3, -, -]");
    assertThat(serializer.deserialize(serialized))
        .isEqualTo(
            new Node(1, null, new Node(3))
        );
  }

  @Test
  void twoLevelsTree_allNodes() {
    final String serialized = serializer.serialize(new Node(1, new Node(2), new Node(3)));
    assertThat(serialized).isEqualTo("[1, 2, -, -, 3, -, -]");
    assertThat(serializer.deserialize(serialized))
        .isEqualTo(
            new Node(1, new Node(2), new Node(3))
        );
  }
}
