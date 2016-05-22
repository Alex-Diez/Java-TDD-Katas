package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class LazySpanningTreeTest {

    private EdgeWeightedGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = EdgeWeightedGraph.create();
    }

    @Test
    public void itShouldCreateALazySpanningTree() throws Exception {
        graph.addEdge(WeightedEdge.create(1, 2, 1.0));

        LazySpanningTree.create(graph);
    }

    @Test(expected = RuntimeException.class)
    public void itShouldNotCreateALazySpanningTree() throws Exception {
        LazySpanningTree.create(graph);
    }

    @Test
    public void itShouldHaveTreeWithEdgesFromOneToTwoFromTwoToThree() throws Exception {
        graph.addEdge(WeightedEdge.create(1, 2, 1.0));
        graph.addEdge(WeightedEdge.create(2, 3, 1.0));
        graph.addEdge(WeightedEdge.create(1, 3, 4.0));

        LazySpanningTree mst = LazySpanningTree.create(graph);

        assertThat(mst.tree(), contains(WeightedEdge.create(1, 2, 1.0), WeightedEdge.create(2, 3, 1.0)));
    }

    @Test
    public void itShouldHaveTreeWithEdgesFromOneToTwoFromTwoToThreeFromThreeToFour() throws Exception {
        graph.addEdge(WeightedEdge.create(1, 2, 1.0));
        graph.addEdge(WeightedEdge.create(2, 3, 1.0));
        graph.addEdge(WeightedEdge.create(3, 4, 1.0));
        graph.addEdge(WeightedEdge.create(1, 4, 5.0));

        LazySpanningTree mst = LazySpanningTree.create(graph);

        assertThat(mst.tree(), contains(WeightedEdge.create(1, 2, 1.0), WeightedEdge.create(2, 3, 1.0), WeightedEdge.create(3, 4, 1.0)));
    }
}
