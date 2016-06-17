package kata.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class WeightedEdgeGraph {
    private Map<Integer, Collection<WeightedEdge>> edges = new HashMap<>();

    public int numberOfVertices() {
        return edges.size();
    }

    public int numberOfEdges() {
        return edges.values().stream().flatMapToInt(c -> IntStream.of(c.size())).sum() / 2;
    }

    public void addEdge(WeightedEdge edge) {
        int v = edge.either();
        int w = edge.other(v);
        edges.computeIfAbsent(v, ArrayList::new).add(edge);
        edges.computeIfAbsent(w, ArrayList::new).add(edge);
    }

    public Collection<WeightedEdge> adjacentTo(int v) {
        return edges.get(v);
    }

    int firstVertex() {
        return edges.keySet().iterator().next();
    }
}
