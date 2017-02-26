package kata.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class EdgeWeightedDigraph {
    private final Map<Integer, List<DirectedEdge>> edges = new HashMap<>();

    private EdgeWeightedDigraph() {
    }

    public static EdgeWeightedDigraph create() {
        return new EdgeWeightedDigraph();
    }

    public void addEdge(DirectedEdge edge) {
        edges.computeIfAbsent(edge.from, ArrayList::new).add(edge);
        edges.computeIfAbsent(edge.to, ArrayList::new);
    }

    public boolean contains(int vertex) {
        return edges.containsKey(vertex);
    }

    public Stream<Integer> vertices() {
        return edges.keySet().stream();
    }

    public Stream<DirectedEdge> adjacentTo(int vertex) {
        return edges.get(vertex).stream();
    }
}
