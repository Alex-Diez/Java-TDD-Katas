package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class MinimumSpanningTreeTest {

    private EdgeWeightGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = EdgeWeightGraph.create();
    }

    @Test
    public void itShouldCreateAMinimumSpanningTree() throws Exception {
        graph.addEdge(WeightEdge.create(1, 2, 1.0));

        MinimumSpanningTree.create(graph);
    }

    @Test(expected = RuntimeException.class)
    public void itShouldNotCreateAMinimumSpanningTreeFromAnEmptyGraph() throws Exception {
        MinimumSpanningTree.create(graph);
    }

    @Test
    public void itShouldHaveTwoEdgesInSpanningTree() throws Exception {
        graph.addEdge(WeightEdge.create(1, 2, 1.0));
        graph.addEdge(WeightEdge.create(2, 3, 1.0));
        graph.addEdge(WeightEdge.create(1, 3, 5.0));

        assertThat(MinimumSpanningTree.create(graph), contains(WeightEdge.create(1, 2, 1.0), WeightEdge.create(2, 3, 1.0)));
    }

    @Test
    public void itShouldHaveThreeEdgesInSpanningTree() throws Exception {
        graph.addEdge(WeightEdge.create(1, 2, 1.0));
        graph.addEdge(WeightEdge.create(2, 3, 1.0));
        graph.addEdge(WeightEdge.create(3, 4, 1.0));
        graph.addEdge(WeightEdge.create(4, 1, 5.0));

        assertThat(MinimumSpanningTree.create(graph), contains(WeightEdge.create(1, 2, 1.0), WeightEdge.create(2, 3, 1.0), WeightEdge.create(3, 4, 1.0)));
    }
}
