package kata.java;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LazyMinimumSpanningTreeTest {

    private WeightedEdgeGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = new WeightedEdgeGraph();
    }

    @Test
    public void itShouldCreateAMST() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 3.0));

        new LazyMinimumSpanningTree(graph);
    }

    @Test(expected = RuntimeException.class)
    public void itShouldNotCreateMSTFromAnEmptyGraph() throws Exception {
        new LazyMinimumSpanningTree(graph);
    }

    @Test
    @Ignore
    public void itShouldHaveWeightOfTwo() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));
        graph.addEdge(new WeightedEdge(1, 3, 1.0));
        graph.addEdge(new WeightedEdge(2, 3, 5.0));

        LazyMinimumSpanningTree mst = new LazyMinimumSpanningTree(graph);

        assertThat(mst.weight(), is(2.0));
    }

    @Test
    @Ignore
    public void itShouldHaveWeightOfThree() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));
        graph.addEdge(new WeightedEdge(2, 3, 1.0));
        graph.addEdge(new WeightedEdge(3, 4, 1.0));
        graph.addEdge(new WeightedEdge(1, 4, 5.0));

        LazyMinimumSpanningTree mst = new LazyMinimumSpanningTree(graph);

        assertThat(mst.weight(), is(3.0));
    }
}
