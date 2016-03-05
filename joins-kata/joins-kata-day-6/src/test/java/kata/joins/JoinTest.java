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

    private NestedLoopJoin nestedLoopJoin;
    private HashJoin hashJoin;
    private MergeJoin mergeJoin;

    @Before
    public void setUp() throws Exception {
        nestedLoopJoin = new NestedLoopJoin();
        hashJoin = new HashJoin();
        mergeJoin = new MergeJoin();
    }

    public class InnerJoin {
        private List<Triple<Integer, Integer, Integer>> result = List.of(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(2, 2, 2),
                new ImmutableTriple<>(3, 3, 3),
                new ImmutableTriple<>(4, 4, 4)
        );
        private List<Pair<Integer, Integer>> view = List.of(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(3, 3),
                new ImmutablePair<>(4, 4)
        );
        private List<Triple<Integer, Integer, Integer>> leftRight = List.of(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(2, 2, 2),
                new ImmutableTriple<>(4, 4, 4)
        );

        private List<Pair<Integer, Integer>> left = List.of(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(4, 4),
                new ImmutablePair<>(6, 6)
        );

        private List<Pair<Integer, Integer>> right = List.of(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(4, 4),
                new ImmutablePair<>(5, 5)
        );

        @Test
        public void itShouldJoinTwoViewWithSameDataSetByNestedLoopJoin() throws Exception {
            assertThat(nestedLoopJoin.innerJoin(view, view), is(result));
        }

        @Test
        public void itShouldJoinTwoViewWithDifferentDataSetByNestedLoopJoin() throws Exception {
            assertThat(nestedLoopJoin.innerJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinTwoViewWithSameDataSetByHashJoin() throws Exception {
            assertThat(hashJoin.innerJoin(view, view), is(result));
        }

        @Test
        public void itShouldJoinTwoViewWithDifferentDataSetByHashJoin() throws Exception {
            assertThat(hashJoin.innerJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinTwoViewWithSameDataSetByMergeJoin() throws Exception {
            assertThat(mergeJoin.innerJoin(view, view), is(result));
        }

        @Test
        public void itShouldJoinTwoViewWithDifferentDataSetByMergeJoin() throws Exception {
            assertThat(mergeJoin.innerJoin(left, right), is(leftRight));
        }
    }

    public class OuterLeftJoin {

        private List<Triple<Integer, Integer, Integer>> leftRight = List.of(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(2, 2, null),
                new ImmutableTriple<>(4, 4, null)
        );

        private List<Triple<Integer, Integer, Integer>> rightLeft = List.of(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(3, 3, null),
                new ImmutableTriple<>(5, 5, null)
        );

        private List<Pair<Integer, Integer>> left = List.of(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(4, 4)
        );

        private List<Pair<Integer, Integer>> right = List.of(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(3, 3),
                new ImmutablePair<>(5, 5)
        );

        @Test
        public void itShouldJoinLeftWithRightByNestedLoopJoin() throws Exception {
            assertThat(nestedLoopJoin.leftJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinRightWithLeftByNestedLoopJoin() throws Exception {
            assertThat(nestedLoopJoin.leftJoin(right, left), is(rightLeft));
        }

        @Test
        public void itShouldJoinLeftWithRightByHashJoin() throws Exception {
            assertThat(hashJoin.leftJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinRightWithLeftByHashJoin() throws Exception {
            assertThat(hashJoin.leftJoin(right, left), is(rightLeft));
        }

        @Test
        public void itShouldJoinLeftWithRightByMergeJoin() throws Exception {
            assertThat(mergeJoin.leftJoin(left, right), is(leftRight));
        }

        @Test
        public void itShouldJoinRightWithLeftByMergeJoin() throws Exception {
            assertThat(mergeJoin.leftJoin(right, left), is(rightLeft));
        }
    }

    public class OuterRight {

        private List<Triple<Integer, Integer, Integer>> rightLeft = List.of(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(2, 2, null),
                new ImmutableTriple<>(4, 4, null)
        );

        private List<Triple<Integer, Integer, Integer>> leftRight = List.of(
                new ImmutableTriple<>(1, 1, 1),
                new ImmutableTriple<>(3, 3, null),
                new ImmutableTriple<>(5, 5, null)
        );

        private List<Pair<Integer, Integer>> right = List.of(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(2, 2),
                new ImmutablePair<>(4, 4)
        );

        private List<Pair<Integer, Integer>> left = List.of(
                new ImmutablePair<>(1, 1),
                new ImmutablePair<>(3, 3),
                new ImmutablePair<>(5, 5)
        );

        @Test
        public void itShouldJoinLeftWithRightByNestedLoopJoin() throws Exception {
            assertThat(nestedLoopJoin.rightJoin(left, right), is(rightLeft));
        }

        @Test
        public void itShouldJoinRightWithLeftByNestedLoopJoin() throws Exception {
            assertThat(nestedLoopJoin.rightJoin(right, left), is(leftRight));
        }

        @Test
        public void itShouldJoinLeftWithRightByHashJoin() throws Exception {
            assertThat(hashJoin.rightJoin(left, right), is(rightLeft));
        }

        @Test
        public void itShouldJoinRightWithLeftByHashJoin() throws Exception {
            assertThat(hashJoin.rightJoin(right, left), is(leftRight));
        }

        @Test
        public void itShouldJoinLeftWithRightByMergeJoin() throws Exception {
            assertThat(mergeJoin.rightJoin(left, right), is(rightLeft));
        }

        @Test
        public void itShouldJoinRightWithLeftByMergeJoin() throws Exception {
            assertThat(mergeJoin.rightJoin(right, left), is(leftRight));
        }
    }
}
