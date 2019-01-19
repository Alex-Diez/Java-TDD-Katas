package ua.kata;

import org.junit.jupiter.api.BeforeEach;
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
  void threeLevelsTree_onlyLeftMostNode() {
    final String serialized = treeSerializer.serialize(new Node(1, new Node(2, new Node(4)), new Node(3)));
    assertThat(serialized).isEqualTo("[1, 2, 4, -, -, -, 3, -, -]");
    assertThat(treeSerializer.deserialize(serialized)).isEqualTo(new Node(1, new Node(2, new Node(4)), new Node(3)));
  }

  @Test
  void fourLevelsTree() {
    final Node tree = new Node(
        1,
        new Node(
            2,
            new Node(
                4,
                new Node(8)
            ),
            new Node(
                5,
                null,
                new Node(11)
            )
        ),
        new Node(
            3,
            new Node(
                6,
                null,
                new Node(13)
            ),
            new Node(
                7,
                new Node(14)
            )
        )
    );
    final String serialized = treeSerializer.serialize(tree);
    assertThat(serialized)
        .isEqualTo("[1, 2, 4, 8, -, -, -, 5, -, 11, -, -, 3, 6, -, 13, -, -, 7, 14, -, -, -]");
    assertThat(treeSerializer.deserialize(serialized)).isEqualTo(tree);
  }
}
