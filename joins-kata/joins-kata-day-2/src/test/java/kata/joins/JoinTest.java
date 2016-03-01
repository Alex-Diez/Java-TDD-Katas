package kata.joins;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


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

        @SuppressWarnings("ALL")
        List<Triple<Integer, Integer, Integer>> sameSetJoint = List.of(
                new ImmutableTriple[]{
                        new ImmutableTriple(1, 1, 1),
                        new ImmutableTriple(2, 2, 2),
                        new ImmutableTriple(3, 3, 3),
                        new ImmutableTriple(4, 4, 4)
                }
        );

        @SuppressWarnings("ALL")
        List<Pair<Integer, Integer>> set = List.of(
                new ImmutablePair[]{
                        new ImmutablePair(1, 1),
                        new ImmutablePair(2, 2),
                        new ImmutablePair(3, 3),
                        new ImmutablePair(4, 4)
                }
        );

        @SuppressWarnings("ALL")
        List<Triple<Integer, Integer, Integer>> intersect = List.of(
                new ImmutableTriple[]{
                        new ImmutableTriple(1, 1, 1),
                        new ImmutableTriple(2, 2, 2)
                }
        );

        @SuppressWarnings("ALL")
        List<Pair<Integer, Integer>> left = List.of(
                new ImmutablePair[]{
                        new ImmutablePair(1, 1),
                        new ImmutablePair(2, 2),
                        new ImmutablePair(3, 3),
                        new ImmutablePair(7, 7),
                        new ImmutablePair(8, 8),
                }
        );

        @SuppressWarnings("ALL")
        List<Pair<Integer, Integer>> right = List.of(
                new ImmutablePair[]{
                        new ImmutablePair(1, 1),
                        new ImmutablePair(2, 2),
                        new ImmutablePair(4, 4),
                        new ImmutablePair(5, 5),
                        new ImmutablePair(6, 6),
                }
        );


        @Test
        public void itShouldJoinByNestedLoop() {
            assertThat(nestedLoopJoin.join(set, set), is(sameSetJoint));
        }

        @Test
        public void itShouldJoinByHashJoin() {
            assertThat(hashJoin.join(set, set), is(sameSetJoint));
        }

        @Test
        public void itShouldJoinDiffDataSetByNestedLoop() throws Exception {
            assertThat(nestedLoopJoin.join(left, right), is(intersect));
        }

        @Test
        public void itShouldJoinTwoViewWithDiffDataSetByHash() throws Exception {
            assertThat(hashJoin.join(left, right), is(intersect));
        }

        @Test
        public void itShouldJoinTwoViewByMergeJoin() throws Exception {
            assertThat(mergeJoin.join(set, set), is(sameSetJoint));
        }

        @Test
        public void itShouldJoinTwoViewWithDiffDataSetByMergeJoin() throws Exception {
            assertThat(mergeJoin.join(left, right), is(intersect));
        }
    }
}
