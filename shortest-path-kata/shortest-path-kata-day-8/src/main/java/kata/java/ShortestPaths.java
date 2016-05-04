package kata.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class ShortestPaths<V> {
    private final Map<V, DirectedEdge<V>> paths;

    private ShortestPaths(Map<V, DirectedEdge<V>> paths) {
        this.paths = paths;
    }

    public static <V> ShortestPaths create(EdgeWeightedDigraph<V> digraph, V startingVertex) {
        if (!digraph.contains(startingVertex)) {
            throw new RuntimeException();
        }
        return new ShortestPaths<>(new AcyclicShortestPathBuilder<>(digraph, startingVertex).buildPaths());
    }

    public Iterable<DirectedEdge<V>> pathTo(V vertex) {
        List<DirectedEdge<V>> reversePath = new ArrayList<>();
        for (DirectedEdge<V> edge = paths.get(vertex); edge != null; edge = paths.get(edge.from)) {
            reversePath.add(edge);
        }
        List<DirectedEdge<V>> path = new ArrayList<>();
        for (int i = reversePath.size() - 1; i > -1; i--) {
            path.add(reversePath.get(i));
        }
        return path;
    }

    private static class AcyclicShortestPathBuilder<V> {
        private final EdgeWeightedDigraph<V> digraph;
        private final V startingVertex;

        private AcyclicShortestPathBuilder(EdgeWeightedDigraph<V> digraph, V startingVertex) {
            this.digraph = digraph;
            this.startingVertex = startingVertex;
        }

        private Map<V, DirectedEdge<V>> buildPaths() {
            Map<V, DirectedEdge<V>> paths = new HashMap<>();
            Map<V, Double> distances = new HashMap<>();
            for (V v : digraph.vertices()) {
                distances.put(v, Double.POSITIVE_INFINITY);
            }
            distances.put(startingVertex, 0.0);
            new DepthFirstOrder<>(digraph).preOrder().flatMap(digraph::adjacentTo).forEach(e -> relax(e, distances, paths));
            return paths;
        }

        private void relax(DirectedEdge<V> edge, Map<V, Double> distances, Map<V, DirectedEdge<V>> paths) {
            if (distances.get(edge.to) > distances.get(edge.from) + edge.weight) {
                distances.put(edge.to, distances.get(edge.from) + edge.weight);
                paths.put(edge.to, edge);
            }
        }
    }

    private static class DepthFirstOrder<V> {
        private final EdgeWeightedDigraph<V> digraph;

        private DepthFirstOrder(EdgeWeightedDigraph<V> digraph) {
            this.digraph = digraph;
        }

        private Stream<V> preOrder() {
            Set<V> marked = new HashSet<>();
            Collection<V> preOrder = new ArrayList<>();
            for (V v : digraph.vertices()) {
                if (!marked.contains(v)) {
                    depthFirstOrder(v, marked, preOrder);
                }
            }
            return preOrder.stream();
        }

        private void depthFirstOrder(V v, Set<V> marked, Collection<V> preOrder) {
            marked.add(v);
            preOrder.add(v);
            digraph.adjacentTo(v).map(e -> e.to).filter(to -> !marked.contains(to)).forEach(to -> depthFirstOrder(to, marked, preOrder));
        }
    }
}
