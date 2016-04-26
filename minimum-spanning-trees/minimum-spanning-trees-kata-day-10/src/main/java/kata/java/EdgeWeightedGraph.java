package kata.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class EdgeWeightedGraph {
    private Map<Integer, List<WeightedEdge>> edges = new HashMap<>();

    private EdgeWeightedGraph() {
    }

    public static EdgeWeightedGraph create() {
        return new EdgeWeightedGraph();
    }

    public void addEdge(WeightedEdge edge) {
        int v = edge.either();
        int w = edge.other(v);
        edges.computeIfAbsent(v, ArrayList::new).add(edge);
        edges.computeIfAbsent(w, ArrayList::new).add(edge);
    }

    public int numberOfEdges() {
        return edges.values().stream().mapToInt(List::size).sum() / 2;
    }

    public int startingVertex() {
        return edges.keySet().iterator().next();
    }

    public Stream<WeightedEdge> adjacentTo(int vertex) {
        return edges.get(vertex).stream();
    }
}
