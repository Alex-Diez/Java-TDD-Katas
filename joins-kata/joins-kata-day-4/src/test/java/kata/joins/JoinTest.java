package kata.joins;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(HierarchicalContextRunner.class)
public class JoinTest {

    private NestedLoopJoin nestedLoopJoin;
    private HashJoin hashJoin;
    private MergeJoin mergeJoin;

    @Before
    public void setUp() throws Exception {
        nestedLoopJoin = new NestedLoopJoin();
        hashJoin = new HashJoin();
        mergeJoin = new MergeJoin();
    }

    public class Inner {
        List<Triple<Integer, Integer, Integer>> result = Arrays.asList(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(2, 2, 2),
                new ImmutableTriple<>(3, 3, 3),
                new ImmutableTriple<>(4, 4, 4)
        );

        List<Pair<Integer, Integer>> view = Arrays.asList(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(3, 3),
                new ImmutablePair<>(4, 4)
        );

        List<Triple<Integer, Integer, Integer>> leftRight = Arrays.asList(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(2, 2, 2),
                new ImmutableTriple<>(4, 4, 4)
        );

        List<Pair<Integer, Integer>> left = Arrays.asList(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(4, 4)
        );

        List<Pair<Integer, Integer>> right = Arrays.asList(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(4, 4),
                new ImmutablePair<>(5, 5)
        );

        @Test
        public void itShouldJoinTwoSameViewsByNestedLoopJoin() throws Exception {
            assertThat(nestedLoopJoin.innerJoin(view, view), is(result));
        }

        @Test
        public void itShouldIntersectTwoViewsWithDifferentDataSetByNestedLoopJoin() throws Exception {
            assertThat(nestedLoopJoin.innerJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinTwoSameViewsByHashJoin() throws Exception {
            assertThat(hashJoin.innerJoin(view, view), is(result));
        }

        @Test
        public void itShouldIntersectTwoViewsWithDifferentDataSetByHashJoin() throws Exception {
            assertThat(hashJoin.innerJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinTwoSameViewsByMergeJoin() throws Exception {
            assertThat(mergeJoin.innerJoin(view, view), is(result));
        }

        @Test
        public void itShouldIntersectTwoViewsWithDifferentDataSetByMergeJoin() throws Exception {
            assertThat(mergeJoin.innerJoin(left, right), is(leftRight));
        }
    }

    public class LeftJoin {
        List<Triple<Integer, Integer, Integer>> leftRight = Arrays.asList(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(2, 2, null),
                new ImmutableTriple<>(4, 4, null)
        );

        List<Triple<Integer, Integer, Integer>> rightLeft = Arrays.asList(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(3, 3, null),
                new ImmutableTriple<>(5, 5, null)
        );

        List<Pair<Integer, Integer>> left = Arrays.asList(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(4, 4)
        );

        List<Pair<Integer, Integer>> right = Arrays.asList(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(3, 3),
                new ImmutablePair<>(5, 5)
        );

        @Test
        public void itShouldJoinLeftViewWithRightByNestedLoopJoin() throws Exception {
            assertThat(nestedLoopJoin.leftJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinRightViewWithLeftByNestedLoopJoin() throws Exception {
            assertThat(nestedLoopJoin.leftJoin(right, left), is(rightLeft));
        }

        @Test
        public void itShouldJoinLeftViewWithRightByHashJoin() throws Exception {
            assertThat(hashJoin.leftJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinRightViewWithLeftByHashJoin() throws Exception {
            assertThat(hashJoin.leftJoin(right, left), is(rightLeft));
        }

        @Test
        public void itShouldJoinLeftViewWithRightByMergeJoin() throws Exception {
            assertThat(mergeJoin.leftJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinRightViewWithLeftByMergeJoin() throws Exception {
            assertThat(mergeJoin.leftJoin(right, left), is(rightLeft));
        }
    }
}
