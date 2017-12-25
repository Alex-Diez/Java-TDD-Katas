package kata.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdgeWeightedDigraph {
    private Map<Integer, List<DirectedEdge>> edges = new HashMap<>();

    private EdgeWeightedDigraph() {
    }

    public static EdgeWeightedDigraph create() {
        return new EdgeWeightedDigraph();
    }

    public void addEdge(DirectedEdge edge) {
        edges.computeIfAbsent(edge.from, ArrayList::new).add(edge);
        edges.computeIfAbsent(edge.to, ArrayList::new);
    }

    public int numberOfVertex() {
        return edges.size();
    }

    public boolean contains(int vertex) {
        return edges.containsKey(vertex);
    }

    public Iterable<Integer> vertices() {
        return edges.keySet();
    }

    public Iterable<DirectedEdge> adjacentTo(int vertex) {
        return edges.get(vertex);
    }
}
