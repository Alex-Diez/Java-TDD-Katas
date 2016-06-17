package kata.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdgeWeightedDigraph {
    private Map<Integer, List<DirectedWeightedEdge>> edges = new HashMap<>();

    private EdgeWeightedDigraph() {
    }

    public static EdgeWeightedDigraph create() {
        return new EdgeWeightedDigraph();
    }

    public void addEdge(DirectedWeightedEdge edge) {
        edges.computeIfAbsent(edge.from(), ArrayList::new).add(edge);
        edges.computeIfAbsent(edge.to(), ArrayList::new);
    }

    public int numberOfVertex() {
        return edges.size();
    }

    public boolean containsVertex(int sourceVertex) {
        return edges.containsKey(sourceVertex);
    }

    public Iterable<Integer> vertices() {
        return edges.keySet();
    }

    public Iterable<DirectedWeightedEdge> adjacentTo(int vertex) {
        return edges.get(vertex);
    }
}
