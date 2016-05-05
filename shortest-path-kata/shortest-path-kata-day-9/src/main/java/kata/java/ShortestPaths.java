package kata.java;

import java.util.ArrayList;
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

    public static ShortestPaths create(EdgeWeightedDigraph digraph, int startVertex) {
        if (!digraph.contains(startVertex)) {
            throw new RuntimeException();
        }
        return new ShortestPaths(new AcyclicShortestPathBuilder(digraph, startVertex).buildPaths());
    }

    public Iterable<DirectedEdge> pathTo(int vertex) {
        List<DirectedEdge> reversePath = new ArrayList<>();
        for (DirectedEdge edge = paths.get(vertex); edge != null; edge = paths.get(edge.from)) {
            reversePath.add(edge);
        }
        List<DirectedEdge> path = new ArrayList<>();
        for (int i = reversePath.size() - 1; i > -1; i--) {
            path.add(reversePath.get(i));
        }
        return path;
    }

    private static class AcyclicShortestPathBuilder {
        private final EdgeWeightedDigraph digraph;
        private final int startVertex;

        private AcyclicShortestPathBuilder(EdgeWeightedDigraph digraph, int startVertex) {
            this.digraph = digraph;
            this.startVertex = startVertex;
        }

        private Map<Integer, DirectedEdge> buildPaths() {
            Map<Integer, DirectedEdge> paths = new HashMap<>();
            Map<Integer, Double> distances = new HashMap<>();
            digraph.vertices().forEach(v -> distances.put(v, Double.POSITIVE_INFINITY));
            distances.put(startVertex, 0.0);
            new DepthFirstOrder(digraph)
                    .preOrder()
                    .flatMap(digraph::adjacentTo)
                    .filter(edge -> distances.get(edge.to) > distances.get(edge.from) + edge.weight)
                    .forEach(edge -> {
                                distances.put(edge.to, distances.get(edge.from) + edge.weight);
                                paths.put(edge.to, edge);
                            }
                    );
            return paths;
        }
    }

    private static class DepthFirstOrder {
        private final EdgeWeightedDigraph digraph;

        private DepthFirstOrder(EdgeWeightedDigraph digraph) {
            this.digraph = digraph;
        }

        private Stream<Integer> preOrder() {
            Set<Integer> marked = new HashSet<>();
            List<Integer> preOrder = new ArrayList<>();
            digraph.vertices()
                    .filter(v -> !marked.contains(v))
                    .forEach(v -> depthFirstSearch(v, marked, preOrder));
            return preOrder.stream();
        }

        private void depthFirstSearch(int vertex, Set<Integer> marked, List<Integer> preOrder) {
            marked.add(vertex);
            preOrder.add(vertex);
            digraph.adjacentTo(vertex)
                    .mapToInt(e -> e.to)
                    .filter(to -> !marked.contains(to))
                    .forEach(to -> depthFirstSearch(to, marked, preOrder));
        }
    }
}
