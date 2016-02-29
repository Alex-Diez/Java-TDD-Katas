package kata.joins;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"ALL"})
public class NestedJoinTest {

    private NestedJoin join;

    @Before
    public void setUp() throws Exception {
        join = new NestedJoin();
    }

    @Test
    public void itShouldJoinTwoViews() throws Exception {
        List<Triple<Integer, Integer, Integer>> data = Arrays.<Triple<Integer, Integer, Integer>>asList(
                new ImmutableTriple[]{
                        new ImmutableTriple(1, 1, 1),
                        new ImmutableTriple(2, 2, 2),
                        new ImmutableTriple(3, 3, 3),
                        new ImmutableTriple(4, 4, 4)
                }
        );

        List<Pair<Integer, Integer>> view1 = Arrays.<Pair<Integer, Integer>>asList(
                new ImmutablePair[]{
                        new ImmutablePair(1, 1),
                        new ImmutablePair(2, 2),
                        new ImmutablePair(3, 3),
                        new ImmutablePair(4, 4)
                }
        );

        List<Pair<Integer, Integer>> view2 = Arrays.<Pair<Integer, Integer>>asList(
                new ImmutablePair[]{
                        new ImmutablePair(1, 1),
                        new ImmutablePair(2, 2),
                        new ImmutablePair(3, 3),
                        new ImmutablePair(4, 4)
                }
        );

        assertThat(join.innerJoin(view1, view2), is(data));
    }

    @Test
    public void itShouldHaveNoResult() throws Exception {

        List<Triple<Integer, Integer, Integer>> data = Arrays.<Triple<Integer, Integer, Integer>>asList(
                new ImmutableTriple[]{}
        );

        List<Pair<Integer, Integer>> view1 = Arrays.<Pair<Integer, Integer>>asList(
                new ImmutablePair[]{
                        new ImmutablePair(1, 1),
                        new ImmutablePair(2, 2)
                }
        );

        List<Pair<Integer, Integer>> view2 = Arrays.<Pair<Integer, Integer>>asList(
                new ImmutablePair[]{
                        new ImmutablePair(3, 3),
                        new ImmutablePair(4, 4)
                }
        );

        assertThat(join.innerJoin(view1, view2), is(data));
    }

    @Test
    public void itShouldJoinToLeftViewKeys() throws Exception {
        List<Triple<Integer, Integer, Integer>> leftRight = Arrays.<Triple<Integer, Integer, Integer>>asList(
                new ImmutableTriple[]{
                        new ImmutableTriple(1, 1, 1),
                        new ImmutableTriple(2, 2, 2),
                        new ImmutableTriple(4, 4, null)
                }
        );

        List<Triple<Integer, Integer, Integer>> rightLeft = Arrays.<Triple<Integer, Integer, Integer>>asList(
                new ImmutableTriple[]{
                        new ImmutableTriple(1, 1, 1),
                        new ImmutableTriple(2, 2, 2),
                        new ImmutableTriple(3, 3, null)
                }
        );

        List<Pair<Integer, Integer>> left = Arrays.<Pair<Integer, Integer>>asList(
                new ImmutablePair[]{
                        new ImmutablePair(1, 1),
                        new ImmutablePair(2, 2),
                        new ImmutablePair(4, 4)
                }
        );

        List<Pair<Integer, Integer>> right = Arrays.<Pair<Integer, Integer>>asList(
                new ImmutablePair[]{
                        new ImmutablePair(1, 1),
                        new ImmutablePair(2, 2),
                        new ImmutablePair(3, 3)
                }
        );

        assertThat(join.leftJoin(left, right), is(leftRight));
        assertThat(join.leftJoin(right, left), is(rightLeft));
    }

    @Test
    public void itShouldJoinToRightViewKeys() throws Exception {
        List<Triple<Integer, Integer, Integer>> leftRight = Arrays.<Triple<Integer, Integer, Integer>>asList(
                new ImmutableTriple[]{
                        new ImmutableTriple(1, 1, 1),
                        new ImmutableTriple(2, 2, 2),
                        new ImmutableTriple(4, null, 4)
                }
        );

        List<Triple<Integer, Integer, Integer>> rightLeft = Arrays.<Triple<Integer, Integer, Integer>>asList(
                new ImmutableTriple[]{
                        new ImmutableTriple(1, 1, 1),
                        new ImmutableTriple(2, 2, 2),
                        new ImmutableTriple(3, null, 3)
                }
        );

        List<Pair<Integer, Integer>> right = Arrays.<Pair<Integer, Integer>>asList(
                new ImmutablePair[]{
                        new ImmutablePair(1, 1),
                        new ImmutablePair(2, 2),
                        new ImmutablePair(4, 4)
                }
        );

        List<Pair<Integer, Integer>> left = Arrays.<Pair<Integer, Integer>>asList(
                new ImmutablePair[]{
                        new ImmutablePair(1, 1),
                        new ImmutablePair(2, 2),
                        new ImmutablePair(3, 3)
                }
        );

        assertThat(join.rightJoin(left, right), is(leftRight));
        assertThat(join.rightJoin( right, left ), is(rightLeft));
    }
}
