package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class LazyMinimumSpanningTreeTest {
    private WeightedEdgeGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = new WeightedEdgeGraph();
    }

    @Test(expected = RuntimeException.class)
    public void itShould_notCreateALazySpinningTree_fromEmptyGraph() throws Exception {
        new LazyMinimumSpanningTree(graph);
    }

    @Test
    public void itShould_createALazySpinningTree() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));

        new LazyMinimumSpanningTree(graph);
    }

    @Test
    public void itShould_beEdgeFrom1To2_and_edgeFrom2To3() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));
        graph.addEdge(new WeightedEdge(2, 3, 1.0));
        graph.addEdge(new WeightedEdge(1, 3, 5.0));

        LazyMinimumSpanningTree mst = new LazyMinimumSpanningTree(graph);

        assertThat(mst.tree(), contains(new WeightedEdge(1, 2, 1.0), new WeightedEdge(2, 3, 1.0)));
    }

    @Test
    public void itShould_beEdgeFrom1To2_and_edgeFrom2To3_and_edgeFrom3To4() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));
        graph.addEdge(new WeightedEdge(2, 3, 1.0));
        graph.addEdge(new WeightedEdge(3, 4, 1.0));
        graph.addEdge(new WeightedEdge(1, 4, 5.0));

        LazyMinimumSpanningTree mst = new LazyMinimumSpanningTree(graph);

        assertThat(mst.tree(), contains(new WeightedEdge(1, 2, 1.0), new WeightedEdge(2, 3, 1.0), new WeightedEdge(3, 4, 1.0)));
    }
}
