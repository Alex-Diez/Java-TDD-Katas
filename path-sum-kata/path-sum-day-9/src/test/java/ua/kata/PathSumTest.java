package ua.kata;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import ua.kata.PathSum.TreeNode;

import static org.assertj.core.api.Assertions.assertThat;

class PathSumTest {
  @Test
  void emptyPath_whenEmptyTree() throws Exception {
    assertThat(new PathSum().computePath(null, 10).block()).isEmpty();
  }

  @Test
  void onlyRoot_sumEqRootVal() throws Exception {
    assertThat(new PathSum().computePath(new TreeNode(10), 10).block())
        .containsExactly(List.of(10));
  }

  @Test
  void onlyRoot_sumNotEqRootVal() throws Exception {
    assertThat(new PathSum().computePath(new TreeNode(20), 10).block()).isEmpty();
  }

  @Test
  void twoLevelsTree_leftNodeInPath() throws Exception {
    assertThat(new PathSum().computePath(new TreeNode(3, new TreeNode(4), new TreeNode(5)), 7).block())
        .containsExactly(List.of(3, 4));
  }

  @Test
  void twoLevelsTree_bothNodesInPath() throws Exception {
    assertThat(new PathSum().computePath(new TreeNode(1, new TreeNode(-1), new TreeNode(-1)), 0).block())
        .containsExactly(List.of(1, -1), List.of(1, -1));
  }

  @Test
  void computationStopsOnLeaves() throws Exception {
    assertThat(new PathSum().computePath(new TreeNode(0, new TreeNode(1, new TreeNode(-1))), 0).block())
        .containsExactly(List.of(0, 1, -1));
  }
}
