package kata.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ShortestPaths {private final Map<Integer, DirectedEdge> paths;

    private ShortestPaths(Map<Integer, DirectedEdge> paths) {
        this.paths = paths;
    }

    public static ShortestPaths create(EdgeWeightedDigraph digraph, int sourceVertex) {
        if (digraph.numberOfVertex() < 1) {
            throw new RuntimeException();
        }
        if (!digraph.contains(sourceVertex)) {
            throw new RuntimeException();
        }
        return new ShortestPaths(new AcyclicShortestPathBuilder(digraph, sourceVertex).buildPath());
    }

    public Iterable<DirectedEdge> pathTo(int vertex) {
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = paths.get(vertex); e != null; e = paths.get(e.from)) {
            path.push(e);
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

        Map<Integer, DirectedEdge> buildPath() {
            for (int v : digraph.vertices()) {
                distances.put(v, Double.POSITIVE_INFINITY);
            }
            distances.put(sourceVertex, 0.0);

            Topological top = new Topological(digraph);
            for (int vertex : top.order()) {
                for (DirectedEdge edge : digraph.adjacentTo(vertex)) {
                    relax(edge);
                }
            }
            return edges;
        }

        private void relax(DirectedEdge edge) {
            if (Double.isInfinite(distances.get(edge.to))) {
                distances.compute(edge.from, (k, distance) -> edge.weight);
                edges.put(edge.to, edge);
            }
            else if (distances.get(edge.to) > distances.get(edge.from) + edge.weight) {
                distances.compute(edge.from, (k, distance) -> distance + edge.weight);
                edges.put(edge.to, edge);
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
            for (int v : digraph.vertices()) {
                if (!marked.contains(v)) {
                    depthFirstSearch(v);
                }
            }
        }

        Iterable<Integer> reversePost() {
            Stack<Integer> stack = new Stack<>();
            post.forEach(stack::push);
            return stack;
        }

        private void depthFirstSearch(int v) {
            marked.add(v);
            for (DirectedEdge edge : digraph.adjacentTo(v)) {
                if (!marked.contains(edge.to)) {
                    depthFirstSearch(edge.to);
                }
            }
            post.add(v);
        }
    }
}
