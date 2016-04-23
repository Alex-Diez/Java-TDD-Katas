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
    public void itShouldCreateALazySpanningTree() throws Exception {
        graph.addEdge(WeightEdge.create(1, 2, 1.0));

        MinimumSpanningTree.create(graph);
    }

    @Test(expected = RuntimeException.class)
    public void itShouldNotCreateALazySpanningTreeFromAnEmptyGraph() throws Exception {
        MinimumSpanningTree.create(graph);
    }

    @Test
    public void itShouldHaveTwoEdgesInTheTree() throws Exception {
        graph.addEdge(WeightEdge.create(1, 2, 1.0));
        graph.addEdge(WeightEdge.create(2, 3, 1.0));
        graph.addEdge(WeightEdge.create(1, 3, 4.0));

        assertThat(
                MinimumSpanningTree.create(graph),
                contains(WeightEdge.create(1, 2, 1.0), WeightEdge.create(2, 3, 1.0))
        );
    }

    @Test
    public void itShouldHaveThreeEdgesInTheTree() throws Exception {
        graph.addEdge(WeightEdge.create(1, 2, 1.0));
        graph.addEdge(WeightEdge.create(2, 3, 1.0));
        graph.addEdge(WeightEdge.create(3, 4, 1.0));
        graph.addEdge(WeightEdge.create(1, 4, 4.0));

        assertThat(
                MinimumSpanningTree.create(graph),
                contains(WeightEdge.create(1, 2, 1.0), WeightEdge.create(2, 3, 1.0), WeightEdge.create(3, 4, 1.0))
        );
    }
}
