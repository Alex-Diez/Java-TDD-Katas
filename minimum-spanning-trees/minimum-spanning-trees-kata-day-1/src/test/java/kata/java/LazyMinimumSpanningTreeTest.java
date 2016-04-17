package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LazyMinimumSpanningTreeTest {

    private WeightedEdgeGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = new WeightedEdgeGraph();
    }

    @Test
    public void itShouldHaveWeightOfThree() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));
        graph.addEdge(new WeightedEdge(1, 3, 1.0));
        graph.addEdge(new WeightedEdge(1, 4, 1.0));

        LazyMinimumSpanningTree mst = new LazyMinimumSpanningTree(graph);

        assertThat(mst.weight(), is(3.0));
    }

    @Test
    public void itShouldHaveWeightOfTwo() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));
        graph.addEdge(new WeightedEdge(1, 3, 1.0));
        graph.addEdge(new WeightedEdge(2, 3, 1.0));

        LazyMinimumSpanningTree mst = new LazyMinimumSpanningTree(graph);

        assertThat(mst.weight(), is(2.0));
    }
}
