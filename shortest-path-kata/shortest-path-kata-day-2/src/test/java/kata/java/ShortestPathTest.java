package kata.java;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class ShortestPathTest {

    @Test
    public void itShouldCreateShortestPath() throws Exception {
        EdgeWeightedDigraph digraph = EdgeWeightedDigraph.create();

        digraph.addEdge(DirectedEdge.create(1, 2, 1.0));

        ShortestPath.create(digraph, 1);
    }

    @Test(expected = RuntimeException.class)
    public void itShouldNotCreateShortestPathIfDigraphDoesNotContainVertex() throws Exception {
        EdgeWeightedDigraph digraph = EdgeWeightedDigraph.create();

        digraph.addEdge(DirectedEdge.create(1, 2, 1.0));

        ShortestPath.create(digraph, 4);
    }

    @Test
    public void itShouldHaveTwoEdgeInPath() throws Exception {
        EdgeWeightedDigraph digraph = EdgeWeightedDigraph.create();

        digraph.addEdge(DirectedEdge.create(1, 2, 1.0));
        digraph.addEdge(DirectedEdge.create(2, 3, 1.0));
        digraph.addEdge(DirectedEdge.create(1, 3, 4.0));

        ShortestPath paths = ShortestPath.create(digraph, 1);

        assertThat(paths.pathTo(3), contains(DirectedEdge.create(1, 2, 1.0), DirectedEdge.create(2, 3, 1.0)));
    }

    @Test
    @Ignore
    public void itShouldHaveThreeEdgeInPath() throws Exception {
        EdgeWeightedDigraph digraph = EdgeWeightedDigraph.create();

        digraph.addEdge(DirectedEdge.create(1, 2, 1.0));
        digraph.addEdge(DirectedEdge.create(2, 3, 1.0));
        digraph.addEdge(DirectedEdge.create(3, 4, 1.0));
        digraph.addEdge(DirectedEdge.create(1, 4, 4.0));

        ShortestPath paths = ShortestPath.create(digraph, 1);

        assertThat(paths.pathTo(4), contains(DirectedEdge.create(1, 2, 1.0), DirectedEdge.create(2, 3, 1.0), DirectedEdge
                .create(3, 4, 1.0)));
    }
}
