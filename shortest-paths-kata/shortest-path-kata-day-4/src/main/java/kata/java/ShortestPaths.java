package kata.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShortestPaths {
    private final Map<Integer, DirectedEdge> directedEdges;

    private ShortestPaths(Map<Integer, DirectedEdge> directedEdges) {
        this.directedEdges = directedEdges;
    }

    public static ShortestPaths create(EdgeWeightedDigraph digraph, int vertex) {
        if (!digraph.containsVertex(vertex)) {
            throw new RuntimeException();
        }
        return new ShortestPaths(new AcyclicShortestPathBuilder(digraph, vertex).buildPaths());
    }

    public Iterable<DirectedEdge> pathTo(int vertex) {
        List<DirectedEdge> stack = new ArrayList<>();
        for (DirectedEdge edge = directedEdges.get(vertex); edge != null; edge = directedEdges.get(edge.from)) {
            stack.add(edge);
        }
        List<DirectedEdge> path = new ArrayList<>();
        for (int i = stack.size() - 1; i > -1; i--) {
            path.add(stack.get(i));
        }
        return path;
    }

    private static class AcyclicShortestPathBuilder {
        private final EdgeWeightedDigraph digraph;
        private final int sourceVertex;
        private final Map<Integer, DirectedEdge> edges = new HashMap<>();
        private final Map<Integer, Double> distances = new HashMap<>();

        AcyclicShortestPathBuilder(EdgeWeightedDigraph digraph, int sourceVertex) {
            this.digraph = digraph;
            this.sourceVertex = sourceVertex;
        }

        private Map<Integer, DirectedEdge> buildPaths() {
            for (int vertex : digraph.vertices()) {
                distances.put(vertex, Double.POSITIVE_INFINITY);
            }
            distances.put(sourceVertex, 0.0);
            Topological topological = new Topological(digraph);
            for (int vertex : topological.order()) {
                for (DirectedEdge edge : digraph.adjacentTo(vertex)) {
                    relax(edge);
                }
            }
            return edges;
        }

        private void relax(DirectedEdge edge) {
            if (Double.isInfinite(distances.get(edge.to))) {
                distances.compute(edge.to, (k, distance) -> edge.weight);
                edges.put(edge.to, edge);
            }
            else if (distances.get(edge.to) > distances.get(edge.from) + edge.weight) {
                distances.compute(edge.to, (k, distance) -> distances.get(edge.from) + edge.weight);
                edges.put(edge.to, edge);
            }
        }
    }

    private static class Topological {
        private Iterable<Integer> order;

        private Topological(EdgeWeightedDigraph digraph) {
            EdgeWeightDirectedCycle cycle = new EdgeWeightDirectedCycle(digraph);
            if (!cycle.hasCycle) {
                DepthFirstOrder order = new DepthFirstOrder(digraph);
                this.order = order.pre();
            }
        }

        private Iterable<Integer> order() {
            return order;
        }
    }

    private static class EdgeWeightDirectedCycle {
        private final Set<Integer> marked = new HashSet<>();
        private final Set<Integer> onStack = new HashSet<>();
        private final EdgeWeightedDigraph digraph;
        private boolean hasCycle = false;

        private EdgeWeightDirectedCycle(EdgeWeightedDigraph digraph) {
            this.digraph = digraph;
            for (int vertex : digraph.vertices()) {
                if (!marked.contains(vertex)) {
                    depthFirstSearch(vertex);
                }
            }
        }

        private void depthFirstSearch(int vertex) {
            marked.add(vertex);
            onStack.add(vertex);
            for (DirectedEdge edge : digraph.adjacentTo(vertex)) {
                if (hasCycle) return;
                if (!marked.contains(edge.to)) {
                    depthFirstSearch(edge.to);
                }
                else if (onStack.contains(edge.to)) {
                    hasCycle = true;
                }
            }
            onStack.remove(vertex);
        }
    }

    private static class DepthFirstOrder {
        private final Collection<Integer> pre = new ArrayList<>();
        private final EdgeWeightedDigraph digraph;
        private final Set<Integer> marked = new HashSet<>();

        private DepthFirstOrder(EdgeWeightedDigraph digraph) {
            this.digraph = digraph;
            for (int vertex : digraph.vertices()) {
                if (!marked.contains(vertex)) {
                    depthFirstSearch(vertex);
                }
            }
        }

        private void depthFirstSearch(int vertex) {
            marked.add(vertex);
            pre.add(vertex);
            for (DirectedEdge edge : digraph.adjacentTo(vertex)) {
                if (!marked.contains(edge.to)) {
                    depthFirstSearch(edge.to);
                }
            }
        }

        private Iterable<Integer> pre() {
            return pre;
        }
    }
}
