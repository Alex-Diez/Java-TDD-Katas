package kata.java;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LazySpanningTree {
    private final Iterable<WeightedEdge> tree;

    public LazySpanningTree(Iterable<WeightedEdge> tree) {
        this.tree = tree;
    }

    public static LazySpanningTree create(EdgeWeightedGraph graph) {
        if (graph.numberOfEdges() < 1) {
            throw new RuntimeException();
        }
        return new LazySpanningTree(new SpanningTreeBuilder(graph).createTree());
    }

    public Iterable<WeightedEdge> tree() {
        return tree;
    }

    private static class SpanningTreeBuilder {
        private final Set<Integer> marked = new HashSet<>();
        private final Queue<WeightedEdge> tree = new ArrayDeque<>();
        private final Queue<WeightedEdge> priority = new PriorityQueue<>();

        private SpanningTreeBuilder(EdgeWeightedGraph graph) {
            int vertex = graph.startingVertex();
            visit(graph.adjacentTo(vertex), vertex);
            walkOverGraph(graph);
        }

        private void walkOverGraph(EdgeWeightedGraph graph) {
            WeightedEdge edge;
            while ((edge = priority.poll()) != null) {
                int v = edge.either();
                int w = edge.other(v);
                if (!marked.contains(v) || !marked.contains(w)) {
                    tree.add(edge);
                }
                if (!marked.contains(v)) {
                    visit(graph.adjacentTo(v), v);
                }
                if (!marked.contains(w)) {
                    visit(graph.adjacentTo(w), w);
                }
            }
        }

        private void visit(Stream<WeightedEdge> adjacent, int vertex) {
            marked.add(vertex);
            priority.addAll(adjacent.filter(e -> !marked.contains(e.other(vertex))).collect(Collectors.toList()));
        }

        Queue<WeightedEdge> createTree() {
            return tree;
        }
    }
}
