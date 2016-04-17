package kata.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class LazyMinimumSpanningTree {
    private Set<Integer> marked;
    private Queue<WeightedEdge> priority;
    private double weight;

    public LazyMinimumSpanningTree(WeightedEdgeGraph graph) {
        priority = new PriorityQueue<>();
        marked = new HashSet<>();
        Collection<WeightedEdge> mst = new ArrayList<>();
        visit(graph, graph.edges.keySet().iterator().next());
        while (!priority.isEmpty()) {
            WeightedEdge edge = priority.poll();
            int v = edge.either();
            int w = edge.other(v);
            if (marked.contains(v) && marked.contains(w)) continue;
            mst.add(edge);
            if (!marked.contains(v)) visit(graph, v);
            if (!marked.contains(w)) visit(graph, w);
        }
        weight = mst.stream().mapToDouble(WeightedEdge::weight).sum();
    }

    private void visit(WeightedEdgeGraph graph, int v) {
        marked.add(v);
        for (WeightedEdge edge : graph.adjacentTo(v)) {
            if (!marked.contains(edge.other(v))) {
                priority.add(edge);
            }
        }
    }

    public double weight() {
        return weight;
    }
}
