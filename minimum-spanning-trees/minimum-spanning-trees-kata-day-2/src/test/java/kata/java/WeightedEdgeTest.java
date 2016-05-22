package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

public class WeightedEdgeTest {

    private WeightedEdge edge;

    @Before
    public void setUp() throws Exception {
        edge = new WeightedEdge(1, 2, 3.0);
    }

    @Test
    public void itShouldCreateAWeightedEdgeWithWeightOfThree() throws Exception {
        assertThat(edge.weight(), is(3.0));
    }

    @Test
    public void itShouldBeComparableByWeight() throws Exception {
        WeightedEdge smaller = new WeightedEdge(1, 2, 5.6);
        WeightedEdge bigger = new WeightedEdge(1, 3, 3.9);

        assertThat(bigger.compareTo(smaller), greaterThan(0));
        assertThat(smaller.compareTo(bigger), lessThan(0));
    }

    @Test
    public void itShouldBeSameWithTheSameWeight() throws Exception {
        WeightedEdge edge1 = new WeightedEdge(1, 5, 1);
        WeightedEdge edge2 = new WeightedEdge(4, 3, 1);

        assertThat(edge1.compareTo(edge2), is(0));
    }

    @Test
    public void itShouldReturnTheOtherVertex() throws Exception {
        int v = edge.either();
        assertThat(v, is(1));
        assertThat(edge.other(v), is(2));
        assertThat(edge.other(edge.other(v)), is(v));
    }

    @Test(expected = RuntimeException.class)
    public void itShouldThrownException_whenEdgeDoesNotHaveVertex() throws Exception {
        edge.other(10);
    }
}
