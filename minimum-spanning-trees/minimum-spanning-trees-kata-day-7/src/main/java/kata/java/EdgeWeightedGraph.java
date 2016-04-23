package kata.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EdgeWeightedGraph {
    private Map<Integer, List<WeightEdge>> edges = new HashMap<>();

    public static EdgeWeightedGraph create() {
        return new EdgeWeightedGraph();
    }

    private EdgeWeightedGraph() {
    }

    public void addEdge(WeightEdge edge) {
        int v = edge.either();
        int w = edge.other(v);
        edges.computeIfAbsent(v, ArrayList::new).add(edge);
        edges.computeIfAbsent(w, ArrayList::new).add(edge);
    }

    public int numberOfEdges() {
        return edges.values().stream().flatMapToInt(l -> IntStream.of(l.size())).sum() / 2;
    }

    public int startingVertex() {
        return edges.keySet().iterator().next();
    }

    public Stream<WeightEdge> adjacentTo(int vertex) {
        return edges.get(vertex).stream();
    }
}
