package kata.java;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WeightedEdgeGraphTest {

    private WeightedEdgeGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = new WeightedEdgeGraph();
    }

    @Test
    public void itShouldCreateANewEmptyGraph() throws Exception {
        assertThat(graph.numberOfVertices(), is(0));
        assertThat(graph.numberOfEdges(), is(0));
    }

    @Test
    public void itShouldAddAnEdgeToAGraph() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));

        assertThat(graph.numberOfEdges(), is(1));
        assertThat(graph.numberOfVertices(), is(2));
    }

    @Test
    public void itShouldAddEdgesToAGraph() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));
        graph.addEdge(new WeightedEdge(2, 3, 5.0));
        graph.addEdge(new WeightedEdge(1, 4, 4.0));

        assertThat(graph.numberOfEdges(), is(3));
        assertThat(graph.numberOfVertices(), is(4));
    }

    @Test
    public void itShouldBeAdjacentToEachOther() throws Exception {
        WeightedEdge edge = new WeightedEdge(1, 2, 1.0);
        graph.addEdge(edge);

        assertThat(graph.adjacentTo(1), is(Collections.singletonList(edge)));
        assertThat(graph.adjacentTo(2), is(Collections.singletonList(edge)));
    }
}
