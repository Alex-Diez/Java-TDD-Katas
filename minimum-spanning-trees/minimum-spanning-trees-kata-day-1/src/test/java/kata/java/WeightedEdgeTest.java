package kata.java;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;


public class WeightedEdgeTest {

    @Test
    public void itShouldCreateAnEdgeWithWeightOfThree() throws Exception {
        WeightedEdge edge = new WeightedEdge(1, 2, 3.0);

        assertThat(edge.weight(), is(3.0));
    }

    @Test
    public void itShouldReturnOtherVertex() throws Exception {
        WeightedEdge edge = new WeightedEdge(2, 3, 5.0);

        int v = edge.either();
        assertThat(v, is(2));
        assertThat(edge.other(v), is(3));
        assertThat(edge.other(edge.other(v)), is(v));
    }
    
    @Test(expected = RuntimeException.class)
    public void itShouldThrowException_ifNonEdgeVerticesWillAcquired() throws Exception {
        WeightedEdge edge = new WeightedEdge(1, 3, 2.0);

        edge.other(5);
    }

    @Test
    public void itShouldBeComparableByWeight() throws Exception {
        WeightedEdge smaller = new WeightedEdge(2, 3, 5.0);
        WeightedEdge bigger = new WeightedEdge(1, 3, 3.0);

        assertThat(smaller.compareTo(bigger), lessThan(0));
        assertThat(bigger.compareTo(smaller), greaterThan(0));

        WeightedEdge edge = new WeightedEdge(2, 4, 5.0);

        assertThat(edge.compareTo(smaller), is(0));
    }
}
