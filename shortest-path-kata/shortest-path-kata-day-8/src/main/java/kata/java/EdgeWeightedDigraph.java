package kata.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class EdgeWeightedDigraph<V> {
    private final Map<V, List<DirectedEdge<V>>> edges = new HashMap<>();

    private EdgeWeightedDigraph() {
    }

    public static <V> EdgeWeightedDigraph<V> create() {
        return new EdgeWeightedDigraph<>();
    }

    public void addEdge(DirectedEdge<V> edge) {
        edges.computeIfAbsent(edge.from, (k) -> new ArrayList<>()).add(edge);
        edges.computeIfAbsent(edge.to, (k) -> new ArrayList<>());
    }

    public boolean contains(V vertex) {
        return edges.containsKey(vertex);
    }

    public Iterable<V> vertices() {
        return edges.keySet();
    }

    public Stream<DirectedEdge<V>> adjacentTo(V vertex) {
        return edges.get(vertex).stream();
    }
}
