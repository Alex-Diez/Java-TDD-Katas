package kata.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class ShortestPaths {
    private final Map<Integer, DirectedEdge> paths;

    private ShortestPaths(Map<Integer, DirectedEdge> paths) {
        this.paths = paths;
    }

    public static ShortestPaths create(EdgeWeightedDigraph digraph, int vertex) {
        if (!digraph.contains(vertex)) {
            throw new RuntimeException();
        }
        return new ShortestPaths(new AcyclicPathBuilder(digraph, vertex).buildPaths());
    }

    public Iterable<DirectedEdge> pathTo(int vertex) {
        List<DirectedEdge> reversePath = new ArrayList<>();
        for (DirectedEdge edge = paths.get(vertex); edge != null; edge = paths.get(edge.from)) {
            reversePath.add(edge);
        }
        Collection<DirectedEdge> path = new ArrayList<>();
        for (int i = reversePath.size() - 1; i > -1; i--) {
            path.add(reversePath.get(i));
        }
        return path;
    }

    private static class AcyclicPathBuilder {
        private final EdgeWeightedDigraph digraph;
        private final int startVertex;
        private final Map<Integer, DirectedEdge> edges = new HashMap<>();
        private final Map<Integer, Double> distances = new HashMap<>();

        private AcyclicPathBuilder(EdgeWeightedDigraph digraph, int startVertex) {
            this.digraph = digraph;
            this.startVertex = startVertex;
        }

        private Map<Integer, DirectedEdge> buildPaths() {
            digraph.vertices().filter(v -> v != startVertex).forEach(v -> distances.put(v, Double.POSITIVE_INFINITY));
            distances.put(startVertex, 0.0);
            for (int v : new Topological(digraph).order()) {
                digraph.adjacentTo(v)
                        .filter(e -> distances.get(e.to) > distances.get(e.from) + e.weight)
                        .forEach(
                                e -> {
                                    distances.put(e.to, distances.get(e.from) + e.weight);
                                    edges.put(e.to, e);
                                }
                        );
            }
            return edges;
        }
    }

    private static class Topological {
        private final Collection<Integer> order;

        private Topological(EdgeWeightedDigraph digraph) {
            this.order = new DepthFirstOrder(digraph).buildOrder();
        }

        private Iterable<Integer> order() {
            return order;
        }
    }

    private static class DepthFirstOrder {
        private final Set<Integer> marked = new HashSet<>();
        private final EdgeWeightedDigraph digraph;

        private DepthFirstOrder(EdgeWeightedDigraph digraph) {
            this.digraph = digraph;
        }

        private Collection<Integer> buildOrder() {
            Collection<Integer> preOrder = new ArrayList<>();
            digraph.vertices().filter(v -> !marked.contains(v)).forEach(v -> depthFirstOrder(v, preOrder));
            return preOrder;
        }

        private void depthFirstOrder(int vertex, Collection<Integer> preOrder) {
            marked.add(vertex);
            preOrder.add(vertex);
            digraph.adjacentTo(vertex).filter(e -> !marked.contains(e.to)).forEach(e -> depthFirstOrder(
                    e.to,
                    preOrder
            ));
        }
    }
}
