package kata.java;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class LazyMinimumSpanningTree {
    private final Set<Integer> marked = new HashSet<>();
    private final Queue<WeightedEdge> priority = new PriorityQueue<>();
    private final Queue<WeightedEdge> tree = new ArrayDeque<>();

    public LazyMinimumSpanningTree(WeightedEdgeGraph graph) {
        if (graph.numberOfVertices() < 1) {
            throw new RuntimeException();
        }
        visit(graph, graph.firstVertex());
        while (!priority.isEmpty()) {
            WeightedEdge edge = priority.poll();
            int v = edge.either();
            int w = edge.other(v);
            if (!marked.contains(v) || !marked.contains(w)) {
                tree.add(edge);
                if (!marked.contains(v)) visit(graph, v);
                if (!marked.contains(w)) visit(graph, w);
            }
        }
    }

    private void visit(WeightedEdgeGraph graph, int vertex) {
        marked.add(vertex);
        priority.addAll(graph.adjacentTo(vertex)
                .stream()
                .filter(e -> !marked.contains(e.other(vertex)))
                .collect(Collectors.toList()));
    }

    public Collection<WeightedEdge> tree() {
        return tree;
    }
}
