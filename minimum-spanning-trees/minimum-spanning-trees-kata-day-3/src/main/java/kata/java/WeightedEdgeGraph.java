package kata.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedEdgeGraph {
    private final Map<Integer, List<WeightedEdge>> edges = new HashMap<>();

    public int numberOfVertices() {
        return edges.size();
    }

    public void addEdge(WeightedEdge edge) {
        int v = edge.either();
        int w = edge.other(v);
        edges.computeIfAbsent(v, ArrayList::new).add(edge);
        edges.computeIfAbsent(w, ArrayList::new).add(edge);
    }

    public int firstVertex() {
        return edges.keySet().iterator().next();
    }

    public List<WeightedEdge> adjacentTo(int vertex) {
        return edges.get(vertex);
    }
}
