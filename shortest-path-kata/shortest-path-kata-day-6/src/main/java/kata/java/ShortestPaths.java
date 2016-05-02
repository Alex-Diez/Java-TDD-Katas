package kata.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ShortestPaths {
    private final Map<Integer, DirectedEdge> paths;

    private ShortestPaths(Map<Integer, DirectedEdge> paths) {
        this.paths = paths;
    }

    public static ShortestPaths create(EdgeWeightedDigraph digraph, int startVertex) {
        if (!digraph.contains(startVertex)) {
            throw new RuntimeException();
        }
        return new ShortestPaths(new AcyclicShortestPathsBuilder(digraph, startVertex).buildPaths());
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

    private static class AcyclicShortestPathsBuilder {
        private final EdgeWeightedDigraph digraph;
        private final int startVertex;
        private final Map<Integer, Double> distances = new HashMap<>();
        private final Map<Integer, DirectedEdge> paths = new HashMap<>();

        private AcyclicShortestPathsBuilder(EdgeWeightedDigraph digraph, int startVertex) {
            this.digraph = digraph;
            this.startVertex = startVertex;
        }

        private Map<Integer, DirectedEdge> buildPaths() {
            digraph.vertices().filter(v -> v != startVertex).forEach(v -> distances.put(v, Double.POSITIVE_INFINITY));
            distances.put(startVertex, 0.0);
            for(int v : new DepthFirstSearch(digraph).preOrder()) {
                digraph.adjacentTo(v)
                        .filter(e -> distances.get(e.to) > distances.get(e.from) + e.weight)
                        .forEach(
                                e -> {
                                    distances.put(e.to, distances.get(e.from) + e.weight);
                                    paths.put(e.to, e);
                                }
                        );
            }
            return paths;
        }
    }

    private static class DepthFirstSearch {
        private final EdgeWeightedDigraph digraph;
        private final Set<Integer> marked = new HashSet<>();

        private DepthFirstSearch(EdgeWeightedDigraph digraph) {
            this.digraph = digraph;
        }

        private Iterable<Integer> preOrder() {
            Collection<Integer> preOrder = new ArrayList<>();
            depthFirstSearch(digraph.vertices(), preOrder);
            return preOrder;
        }

        private void depthFirstSearch(Stream<Integer> vertices, Collection<Integer> preOrder) {
            vertices.filter(v -> !marked.contains(v)).forEach(v -> depthFirstOrder(v, preOrder));
        }

        private void depthFirstOrder(int vertex, Collection<Integer> preOrder) {
            marked.add(vertex);
            preOrder.add(vertex);
            depthFirstSearch(digraph.adjacentTo(vertex).mapToInt(e -> e.to).boxed(), preOrder);
        }
    }
}
