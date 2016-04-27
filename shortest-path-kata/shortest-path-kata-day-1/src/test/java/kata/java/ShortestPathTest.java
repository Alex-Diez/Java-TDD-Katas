package kata.java;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class ShortestPathTest {

    private EdgeWeightedDigraph digraph;

    @Before
    public void setUp() throws Exception {
        digraph = EdgeWeightedDigraph.create();
    }

    @Test
    public void itShouldCreateAShortestPath() throws Exception {
        digraph.addEdge(DirectedWeightedEdge.create(1, 2, 1.0));

        ShortestPath.create(digraph, 1);
    }

    @Test(expected = RuntimeException.class)
    public void itShouldNotCreateAShortestPathFromAnEmptyDigraph() throws Exception {
        ShortestPath.create(digraph, 1);
    }

    @Test(expected = RuntimeException.class)
    public void itShouldNotCreateAShortestPathIfDigraphDoesNotContainSourceVertex() throws Exception {
        digraph.addEdge(DirectedWeightedEdge.create(1, 2, 1.0));

        ShortestPath.create(digraph, 3);
    }

    @Test
    public void itShouldHaveTwoEdgeInPath() throws Exception {
        digraph.addEdge(DirectedWeightedEdge.create(1, 2, 1.0));
        digraph.addEdge(DirectedWeightedEdge.create(2, 3, 1.0));
        digraph.addEdge(DirectedWeightedEdge.create(1, 3, 4.0));

        assertThat(ShortestPath.create(digraph, 1).pathTo(3), contains(DirectedWeightedEdge.create(1, 2, 1.0), DirectedWeightedEdge.create(2, 3, 1.0)));
    }

    @Test
    @Ignore
    public void itShouldHaveThreeEdgeInPath() throws Exception {
        digraph.addEdge(DirectedWeightedEdge.create(1, 2, 1.0));
        digraph.addEdge(DirectedWeightedEdge.create(2, 3, 1.0));
        digraph.addEdge(DirectedWeightedEdge.create(3, 4, 1.0));
        digraph.addEdge(DirectedWeightedEdge.create(1, 4, 5.0));

        assertThat(ShortestPath.create(digraph, 1).pathTo(4), contains(DirectedWeightedEdge.create(1, 2, 1.0), DirectedWeightedEdge.create(2, 3, 1.0), DirectedWeightedEdge.create(3, 4, 1.0)));
    }
}
