package ua.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import ua.kata.PathSum.TreeNode;

import static org.assertj.core.api.Assertions.assertThat;

class PathSumTest {

  private PathSum pathSum;

  @BeforeEach
  void setUp() {
    pathSum = new PathSum();
  }

  @Test
  void emptyPath_whenEmptyTree() throws Exception {
    assertThat(pathSum.computePaths(null, 10)).isEmpty();
  }

  @Test
  void onlyRoot_sumEqRootVal() throws Exception {
    assertThat(pathSum.computePaths(new TreeNode(10), 10)).containsExactly(List.of(10));
  }

  @Test
  void onlyRoot_sumNotEqRootVal() throws Exception {
    assertThat(pathSum.computePaths(new TreeNode(20), 10)).isEmpty();
  }

  @Test
  void twoLevelsTree_leftNodeInPath() throws Exception {
    assertThat(pathSum.computePaths(new TreeNode(3, new TreeNode(4), new TreeNode(5)), 7))
      .containsExactly(List.of(3, 4));
  }

  @Test
  void twoLevelsTree_bothNodeInPath() throws Exception {
    assertThat(pathSum.computePaths(new TreeNode(1, new TreeNode(-1), new TreeNode(-1)), 0))
      .containsExactly(List.of(1, -1), List.of(1, -1));
  }

  @Test
  void stopComputingOnLeaves() throws Exception {
    assertThat(pathSum.computePaths(new TreeNode(0, new TreeNode(1, new TreeNode(-1))), 0))
      .containsExactly(List.of(0, 1, -1));
  }

  @Test
  void computeBigTree() throws Exception {
    assertThat(
      pathSum.computePaths(
        new TreeNode(
          5,
          new TreeNode(
            4,
            new TreeNode(
              11,
              new TreeNode(7),
              new TreeNode(2)
            )
          ),
          new TreeNode(
            8,
            new TreeNode(13),
            new TreeNode(
              4,
              new TreeNode(5),
              new TreeNode(1)
            )
          )
        ),
        22
      )
    )
      .containsExactly(List.of(5, 4, 11, 2), List.of(5, 8, 4, 5));
  }
}
