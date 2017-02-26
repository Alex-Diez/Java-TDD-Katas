package kata.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class EdgeWeightedDigraph {
    private final Map<Integer, List<DirectedEdge>> vertices = new HashMap<>();

    private EdgeWeightedDigraph() {
    }

    public static EdgeWeightedDigraph create() {
        return new EdgeWeightedDigraph();
    }

    public Stream<Integer> vertices() {
        return vertices.keySet().stream();
    }

    public void addEdge(DirectedEdge edge) {
        vertices.computeIfAbsent(edge.from, ArrayList::new).add(edge);
        vertices.computeIfAbsent(edge.to, ArrayList::new);
    }

    public boolean contains(int vertex) {
        return vertices.containsKey(vertex);
    }

    public Stream<DirectedEdge> adjacentTo(int vertex) {
        return vertices.get(vertex).stream();
    }
}
