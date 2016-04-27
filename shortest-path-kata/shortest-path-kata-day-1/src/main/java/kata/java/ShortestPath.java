package kata.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ShortestPath {
    private final Map<Integer, DirectedWeightedEdge> paths;

    private ShortestPath(Map<Integer, DirectedWeightedEdge> paths) {
        this.paths = paths;
    }

    public static ShortestPath create(EdgeWeightedDigraph digraph, int sourceVertex) {
        if (digraph.numberOfVertex() < 1) {
            throw new RuntimeException();
        }
        if (!digraph.containsVertex(sourceVertex)) {
            throw new RuntimeException();
        }
        return new ShortestPath(new AcyclicShortestPathBuilder(digraph, sourceVertex).buildPath());
    }

    public Iterable<DirectedWeightedEdge> pathTo(int vertex) {
        Stack<DirectedWeightedEdge> path = new Stack<>();
        for (DirectedWeightedEdge e = paths.get(vertex); e != null; e = paths.get(e.from())) {
            path.push(e);
        }
        return path;
    }

    private static class AcyclicShortestPathBuilder {
        private final EdgeWeightedDigraph digraph;
        private final int sourceVertex;
        private final Map<Integer, DirectedWeightedEdge> edges = new HashMap<>();
        private final Map<Integer, Double> distances = new HashMap<>();

        AcyclicShortestPathBuilder(EdgeWeightedDigraph digraph, int sourceVertex) {
            this.digraph = digraph;
            this.sourceVertex = sourceVertex;
        }

        Map<Integer, DirectedWeightedEdge> buildPath() {
            for (int v : digraph.vertices()) {
                distances.put(v, Double.POSITIVE_INFINITY);
            }
            distances.put(sourceVertex, 0.0);

            Topological top = new Topological(digraph);
            for (int v : top.order()) {
                relax(v);
            }
            return edges;
        }

        private void relax(int vertex) {
            for (DirectedWeightedEdge edge : digraph.adjacentTo(vertex)) {
                int w = edge.to();
                if (distances.get(w) >= distances.get(vertex) + edge.weight) {
                    distances.put(w, distances.get(vertex) + edge.weight);
                    edges.put(w, edge);
                }
            }
        }

    }

    private static class Topological {
        private Iterable<Integer> order;

        Topological(EdgeWeightedDigraph digraph) {
            EdgeWeightedDirectedCycle cycle = new EdgeWeightedDirectedCycle(digraph);
            if (!cycle.hasCycle()) {
                DepthFirstOrder order = new DepthFirstOrder(digraph);
                this.order = order.reversePost();
            }
        }

        Iterable<Integer> order() {
            return order;
        }
    }

    private static class EdgeWeightedDirectedCycle {
        private final Set<Integer> marked = new HashSet<>();
        private final Set<Integer> onStack = new HashSet<>();
        private final EdgeWeightedDigraph digraph;
        private boolean hasCycle = false;

        EdgeWeightedDirectedCycle(EdgeWeightedDigraph digraph) {
            this.digraph = digraph;
            for (int v : digraph.vertices()) {
                if (!marked.contains(v)) {
                    depthFirstSearch(v);
                }
            }
        }

        void depthFirstSearch(int vertex) {
            onStack.add(vertex);
            marked.add(vertex);
            for (DirectedWeightedEdge edge : digraph.adjacentTo(vertex)) {
                int w = edge.to();
                if (hasCycle) return;
                if (!marked.contains(w)) {
                    depthFirstSearch(w);
                }
                else if (onStack.contains(w)) {
                    hasCycle = true;
                }
            }
            onStack.remove(vertex);
        }

        boolean hasCycle() {
            return false;
        }
    }

    private static class DepthFirstOrder {
        private final Collection<Integer> post = new ArrayList<>();
        private final EdgeWeightedDigraph digraph;
        private final Set<Integer> marked = new HashSet<>();

        DepthFirstOrder(EdgeWeightedDigraph digraph) {
            this.digraph = digraph;
        }

        Iterable<Integer> reversePost() {
            for (int v : digraph.vertices()) {
                if (!marked.contains(v)) {
                    depthFirstSearch(v);
                }
            }
            Stack<Integer> stack = new Stack<>();
            for (int v : post) {
                stack.push(v);
            }
            return stack;
        }

        private void depthFirstSearch(int v) {
            marked.add(v);
            for (DirectedWeightedEdge edge : digraph.adjacentTo(v)) {
                if (!marked.contains(edge.to())) {
                    depthFirstSearch(edge.to());
                }
            }
            post.add(v);
        }
    }
}
