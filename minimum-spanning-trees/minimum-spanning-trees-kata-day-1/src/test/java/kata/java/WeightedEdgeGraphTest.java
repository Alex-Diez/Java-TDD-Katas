package kata.java;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WeightedEdgeGraphTest {

    private WeightedEdgeGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = new WeightedEdgeGraph();
    }

    @Test
    public void createANewEmptyGraph() throws Exception {

        assertThat(graph.numberOfVertices(), is(0));
        assertThat(graph.numberOfEdges(), is(0));
    }

    @Test
    public void addAnEdgeToAGraph() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));

        assertThat(graph.numberOfEdges(), is(1));
        assertThat(graph.numberOfVertices(), is(2));
    }

    @Test
    public void itShouldAddEdgesToAGraph() throws Exception {
        graph.addEdge(new WeightedEdge(1, 2, 1.0));
        graph.addEdge(new WeightedEdge(2, 3, 4.0));
        graph.addEdge(new WeightedEdge(1, 4, 5.0));

        assertThat(graph.numberOfVertices(), is(4));
        assertThat(graph.numberOfEdges(), is(3));
    }

    @Test
    public void itShouldBeAdjacentToEachOther() throws Exception {
        WeightedEdge edge = new WeightedEdge(1, 2, 1.0);
        graph.addEdge(edge);

        assertThat(graph.adjacentTo(1), is(Collections.singletonList(edge)));
        assertThat(graph.adjacentTo(2), is(Collections.singletonList(edge)));
    }
}
