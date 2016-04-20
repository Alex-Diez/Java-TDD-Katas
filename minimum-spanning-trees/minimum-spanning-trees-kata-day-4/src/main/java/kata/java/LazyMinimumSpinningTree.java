package kata.java;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class LazyMinimumSpinningTree {
    private Set<Integer> marked = new HashSet<>();
    private Queue<WeightedEdge> mst = new ArrayDeque<>();
    private Queue<WeightedEdge> priority = new PriorityQueue<>();

    public LazyMinimumSpinningTree(EdgeWeightedGraph graph) {
        if (graph.numberOfVertices() < 1) {
            throw new RuntimeException();
        }
        visit(graph, graph.startingVertex());
        while (!priority.isEmpty()) {
            WeightedEdge edge = priority.poll();
            int v = edge.either();
            int w = edge.other(v);
            if (marked.contains(v) && marked.contains(w)) continue;
            mst.add(edge);
            if (!marked.contains(v)) visit(graph, v);
            if (!marked.contains(w)) visit(graph, w);
        }
    }

    private void visit(EdgeWeightedGraph graph, int vertex) {
        marked.add(vertex);
        for (WeightedEdge edge : graph.adjacentTo(vertex)) {
            if (!marked.contains(edge.other(vertex))) priority.add(edge);
        }
    }

    public Collection<WeightedEdge> tree() {
        return mst;
    }
}
