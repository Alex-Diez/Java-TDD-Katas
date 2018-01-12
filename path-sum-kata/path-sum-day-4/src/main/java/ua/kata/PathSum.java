package ua.kata;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Predicate;

public class PathSum {
  public Single<List<List<Integer>>> computePath(TreeNode root, int sum) {
    return Observable.<Triple<TreeNode, Integer, List<Integer>>>create(
      emitter -> {
        recursiveEmission(emitter, root, sum, new ArrayList<>());
        emitter.onComplete();
      }
    ).filter(triple -> IS_LEAF.test(triple) && AS_SUM.test(triple))
      .map(Triple::getRight)
      .toList();
  }

  private void recursiveEmission(
    Emitter<Triple<TreeNode, Integer, List<Integer>>> emitter,
    TreeNode node,
    int currentSum,
    List<Integer> path) {
    if (node != null) {
      path.add(node.val);
      recursiveEmission(emitter, node.left, currentSum - node.val, new ArrayList<>(path));
      recursiveEmission(emitter, node.right, currentSum - node.val, new ArrayList<>(path));
      emitter.onNext(new ImmutableTriple<>(node, currentSum - node.val, path));
    }
  }

  private static final Predicate<Triple<TreeNode, Integer, List<Integer>>> IS_LEAF =
    triple -> triple.getLeft().isLeaf();
  private static final Predicate<Triple<TreeNode, Integer, List<Integer>>> AS_SUM =
    triple -> triple.getMiddle() == 0;

  static class TreeNode {

    private final int val;
    private final TreeNode left;
    private final TreeNode right;

    TreeNode(int val) {
      this(val, null, null);
    }

    TreeNode(int val, TreeNode left) {
      this(val, left, null);
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }

    private boolean isLeaf() {
      return left == null && right == null;
    }
  }
}
