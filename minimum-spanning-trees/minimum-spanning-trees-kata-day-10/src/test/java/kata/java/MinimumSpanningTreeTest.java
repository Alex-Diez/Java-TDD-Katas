package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class MinimumSpanningTreeTest {

    private EdgeWeightedGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = EdgeWeightedGraph.create();
    }

    @Test
    public void itShouldCreateAMinimumSpanningTree() throws Exception {
        graph.addEdge(WeightedEdge.create(1, 2, 1.0));

        MinimumSpanningTree.create(graph);
    }

    @Test(expected = RuntimeException.class)
    public void itShouldNotCreateAMinimumSpanningTreeFromAnEmptyGraph() throws Exception {
        MinimumSpanningTree.create(graph);
    }

    @Test
    public void itShouldHaveTwoEdgesInSpanningTree() throws Exception {
        graph.addEdge(WeightedEdge.create(1, 2, 1.0));
        graph.addEdge(WeightedEdge.create(2, 3, 1.0));
        graph.addEdge(WeightedEdge.create(3, 1, 5.0));

        assertThat(MinimumSpanningTree.create(graph), contains(WeightedEdge.create(1, 2, 1.0), WeightedEdge.create(2, 3, 1.0)));
    }

    @Test
    public void itShouldHaveThreeEdgesInSpanningTree() throws Exception {
        graph.addEdge(WeightedEdge.create(1, 2, 1.0));
        graph.addEdge(WeightedEdge.create(2, 3, 1.0));
        graph.addEdge(WeightedEdge.create(3, 4, 1.0));
        graph.addEdge(WeightedEdge.create(1, 4, 5.0));

        assertThat(MinimumSpanningTree.create(graph), contains(WeightedEdge.create(1, 2, 1.0), WeightedEdge.create(2, 3, 1.0), WeightedEdge.create(3, 4, 1.0)));
    }
}
