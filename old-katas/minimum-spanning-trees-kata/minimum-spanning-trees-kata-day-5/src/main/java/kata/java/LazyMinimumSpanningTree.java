package kata.java;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class LazyMinimumSpanningTree {

    private final Iterable<WeightedEdge> tree;

    public LazyMinimumSpanningTree(Iterable<WeightedEdge> tree) {
        this.tree = tree;
    }

    public static LazyMinimumSpanningTree create(EdgeWeightedGraph graph) {
        if (graph.numberOfVertices() < 1) {
            throw new RuntimeException();
        }
        Set<Integer> marked = new HashSet<>();
        Queue<WeightedEdge> tree = new ArrayDeque<>();
        Queue<WeightedEdge> priority = new PriorityQueue<>();
        visit(graph, graph.startingVertex(), priority, marked);
        while (!priority.isEmpty()) {
            WeightedEdge edge = priority.poll();
            int v = edge.either();
            int w = edge.other(v);
            if (!marked.contains(v) || !marked.contains(w)) {
                tree.add(edge);
                if (!marked.contains(v)) visit(graph, v, priority, marked);
                if (!marked.contains(w)) visit(graph, w, priority, marked);
            }
        }
        return new LazyMinimumSpanningTree(tree);
    }

    private static void visit(EdgeWeightedGraph graph, int vertex, Queue<WeightedEdge> priority, Set<Integer> marked) {
        marked.add(vertex);
        for (WeightedEdge edge : graph.adjacentTo(vertex)) {
            if (!marked.contains(edge.other(vertex))) priority.add(edge);
        }
    }

    public Iterable<WeightedEdge> tree() {
        return tree;
    }
}
