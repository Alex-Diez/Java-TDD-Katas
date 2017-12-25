package kata.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class WeightedEdgeGraph {
    final Map<Integer, List<WeightedEdge>> edges = new HashMap<>();

    public int numberOfVertices() {
        return edges.size();
    }

    public int numberOfEdges() {
        return edges.values().stream().flatMapToInt(l -> IntStream.of(l.size())).sum() / 2;
    }

    public void addEdge(WeightedEdge edge) {
        int v = edge.either();
        edges.computeIfAbsent(v, ArrayList::new).add(edge);
        int w = edge.other(v);
        edges.computeIfAbsent(w, ArrayList::new).add(edge);
    }

    public Collection<WeightedEdge> adjacentTo(int vertex) {
        return edges.get(vertex);
    }
}
