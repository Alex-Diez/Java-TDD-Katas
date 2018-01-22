package ua.kata;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.vavr.collection.List;
import reactor.test.StepVerifier;
import ua.kata.PathSum.TreeNode;

class PathSumTest {
  @Test
  void emptyPath_whenEmptyTree() throws Exception {
    new PathSum().computePath(null, 10)
        .as(StepVerifier::create)
        .verifyComplete();
  }

  @Test
  void onlyRoot_sumEqRootVal() throws Exception {
    new PathSum().computePath(new TreeNode(10), 10)
        .as(StepVerifier::create)
        .expectNext(List.of(10))
        .verifyComplete();
  }

  @Test
  void onlyRoot_sumNotEqRootVal() throws Exception {
    new PathSum().computePath(new TreeNode(20), 10)
        .as(StepVerifier::create)
        .verifyComplete();
  }

  @Test
  void twoLevelsTree_leftNodeInPath() throws Exception {
    new PathSum().computePath(new TreeNode(3, new TreeNode(4), new TreeNode(5)), 7)
        .as(StepVerifier::create)
        .expectNext(List.of(3, 4))
        .verifyComplete();
  }

  @Test
  void twoLevelsTree_bothNodesInPath() throws Exception {
    new PathSum().computePath(new TreeNode(1, new TreeNode(-1), new TreeNode(-1)), 0)
        .as(StepVerifier::create)
        .expectNext(List.of(1, -1))
        .expectNext(List.of(1, -1))
        .verifyComplete();
  }

  @Test
  void stopComputationOnLeaves() throws Exception {
    new PathSum().computePath(new TreeNode(0, new TreeNode(-1, new TreeNode(1))), 0)
        .as(StepVerifier::create)
        .expectNext(List.of(0, -1, 1))
        .verifyComplete();
  }
}
