package kata.joins;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(HierarchicalContextRunner.class)
public class JoinTest {

    private NestedLoopJoin<Integer, Integer> nestedLoopJoin;
    private HashJoin<Integer, Integer> hashJoin;
    private MergeJoin<Integer, Integer> mergeJoin;

    @Before
    public void setUp() throws Exception {
        nestedLoopJoin = new NestedLoopJoin<>();
        hashJoin = new HashJoin<>();
        mergeJoin = new MergeJoin<>();
    }

    public class InnerJoin {
        List<Triple<Integer, Integer, Integer>> result = List.of(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(2, 2, 2),
                new ImmutableTriple<>(3, 3, 3),
                new ImmutableTriple<>(4, 4, 4)
        );

        List<Pair<Integer, Integer>> view = List.of(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(3, 3),
                new ImmutablePair<>(4, 4)
        );

        List<Triple<Integer, Integer, Integer>> leftRight = List.of(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(2, 2, 2),
                new ImmutableTriple<>(4, 4, 4)
        );

        List<Pair<Integer, Integer>> left = List.of(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(4, 4)
        );

        List<Pair<Integer, Integer>> right = List.of(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(4, 4),
                new ImmutablePair<>(5, 5)
        );

        @Test
        public void itShouldJoinTwoSameViewsByNestedLoop() throws Exception {
            assertThat(nestedLoopJoin.innerJoin(view, view), is(result));
        }

        @Test
        public void itShouldJoinTwoViewWithDifferentDataSet() throws Exception {
            assertThat(nestedLoopJoin.innerJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinTwoSameViewsByHashJoin() throws Exception {
            assertThat(hashJoin.innerJoin(view, view), is(result));
        }

        @Test
        public void itShouldJoinTwoViewWithDifferentDataSetByHashJoin() throws Exception {
            assertThat(hashJoin.innerJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinTwoSameViewsByMergeJoin() throws Exception {
            assertThat(mergeJoin.innerJoin(view, view), is(result));
        }

        @Test
        public void itShouldJoinTwoViewWithDifferentDataSetByMergeJoin() throws Exception {
            assertThat(mergeJoin.innerJoin(left, right), is(leftRight));
        }
    }

    public class LeftJoin {

        @Test
        public void itShouldJoinLeftViewWithRightByNestedLoop() throws Exception {
            List<Triple<Integer, Integer, Integer>> result = List.of(
                    new ImmutableTriple<>(1, 1, 1),
                    new ImmutableTriple<>(2, 2, null),
                    new ImmutableTriple<>(3, 3, 3),
                    new ImmutableTriple<>(4, 4, null)
            );

            List<Pair<Integer, Integer>> left = List.of(
                    new ImmutablePair<>(1, 1),
                    new ImmutablePair<>(2, 2),
                    new ImmutablePair<>(3, 3),
                    new ImmutablePair<>(4, 4)
            );

            List<Pair<Integer, Integer>> right = List.of(
                    new ImmutablePair<>(1, 1),
                    new ImmutablePair<>(3, 3)
            );

            assertThat(nestedLoopJoin.leftJoin(left, right), is(result));
        }

        @Test
        public void itShouldJoinLeftViewWithRightByHashJoin() throws Exception {
            List<Triple<Integer, Integer, Integer>> result = List.of(
                    new ImmutableTriple<>(1, 1, 1),
                    new ImmutableTriple<>(2, 2, null),
                    new ImmutableTriple<>(3, 3, 3),
                    new ImmutableTriple<>(4, 4, null)
            );

            List<Pair<Integer, Integer>> left = List.of(
                    new ImmutablePair<>(1, 1),
                    new ImmutablePair<>(2, 2),
                    new ImmutablePair<>(3, 3),
                    new ImmutablePair<>(4, 4)
            );

            List<Pair<Integer, Integer>> right = List.of(
                    new ImmutablePair<>(1, 1),
                    new ImmutablePair<>(3, 3)
            );

            assertThat(hashJoin.leftJoin(left, right), is(result));
        }
    }
}
