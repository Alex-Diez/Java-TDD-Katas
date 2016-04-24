package kata.java;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class MinimumSpanningTreeTest {

    @Test
    public void itShouldCreateAMinimumSpanningTree() throws Exception {
        EdgeWeightedGraph graph = EdgeWeightedGraph.create();

        graph.addEdge(WeightedEdge.create(1, 2, 1.0));

        MinimumSpanningTree.create(graph);
    }

    @Test(expected = RuntimeException.class)
    public void itShouldNotCreateAMinimumSpanningTreeFromEmptyGraph() throws Exception {
        EdgeWeightedGraph graph = EdgeWeightedGraph.create();

        MinimumSpanningTree.create(graph);
    }

    @Test
    public void itShouldHaveTwoEdge() throws Exception {
        EdgeWeightedGraph graph = EdgeWeightedGraph.create();

        graph.addEdge(WeightedEdge.create(1, 2, 1.0));
        graph.addEdge(WeightedEdge.create(2, 3, 1.0));
        graph.addEdge(WeightedEdge.create(1, 3, 5.0));

        assertThat(
                MinimumSpanningTree.create(graph),
                contains(WeightedEdge.create(1, 2, 1.0), WeightedEdge.create(2, 3, 1.0))
        );
    }

    @Test
    public void itShouldHaveTreeEdge() throws Exception {
        EdgeWeightedGraph graph = EdgeWeightedGraph.create();

        graph.addEdge(WeightedEdge.create(1, 2, 1.0));
        graph.addEdge(WeightedEdge.create(2, 3, 1.0));
        graph.addEdge(WeightedEdge.create(3, 4, 1.0));
        graph.addEdge(WeightedEdge.create(1, 4, 5.0));

        assertThat(
                MinimumSpanningTree.create(graph),
                contains(WeightedEdge.create(1, 2, 1.0), WeightedEdge.create(2, 3, 1.0), WeightedEdge.create(3, 4, 1.0))
        );
    }
}
