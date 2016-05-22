package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class LazyMinimumSpanningTreeTest {

    private EdgeWeightedGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = new EdgeWeightedGraph();
    }

    @Test(expected = RuntimeException.class)
    public void itShouldNotCreateASpinningTreeFromAnEmptyGraph() throws Exception {
        new LazyMinimumSpinningTree(graph);
    }

    @Test
    public void itShouldCreateASpinningTree() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 2.0));

        new LazyMinimumSpinningTree(graph);
    }

    @Test
    public void itShouldHaveTreeWithEdgesFromOneToTwoFromTwoToThree() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));
        graph.addEdge(new WeightedEdge(2, 3, 1.0));
        graph.addEdge(new WeightedEdge(3, 1, 5.0));

        LazyMinimumSpinningTree mst = new LazyMinimumSpinningTree(graph);

        assertThat(mst.tree(), contains(new WeightedEdge(1, 2, 1.0), new WeightedEdge(2, 3, 1.0)));
    }

    @Test
    public void itShouldHaveTreeWithEdgesFromOneToTwoFromTwoToThreeFromThreeToFour() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));
        graph.addEdge(new WeightedEdge(2, 3, 1.0));
        graph.addEdge(new WeightedEdge(3, 4, 1.0));
        graph.addEdge(new WeightedEdge(4, 1, 5.0));

        LazyMinimumSpinningTree mst = new LazyMinimumSpinningTree(graph);

        assertThat(mst.tree(), contains(new WeightedEdge(1, 2, 1.0), new WeightedEdge(2, 3, 1.0), new WeightedEdge(3, 4, 1.0)));
    }
}
