package kata.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShortestPaths {
    private final Map<Integer, DirectedEdge> paths;

    private ShortestPaths(Map<Integer, DirectedEdge> paths) {
        this.paths = paths;
    }

    public static ShortestPaths create(EdgeWeightedDigraph digraph, int startingVertex) {
        if (!digraph.contains(startingVertex)) {
            throw new RuntimeException();
        }
        return new ShortestPaths(new AcyclicShortestPathsBuilder(digraph, startingVertex).buildPaths());
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

    private static class AcyclicShortestPathsBuilder {
        private final EdgeWeightedDigraph digraph;
        private final int startingVertex;
        private final Map<Integer, Double> distances = new HashMap<>();

        private AcyclicShortestPathsBuilder(EdgeWeightedDigraph digraph, int startingVertex) {
            this.digraph = digraph;
            this.startingVertex = startingVertex;
        }

        private Map<Integer, DirectedEdge> buildPaths() {
            digraph.vertices().forEach(v -> distances.put(v, Double.POSITIVE_INFINITY));
            distances.put(startingVertex, 0.0);
            Map<Integer, DirectedEdge> paths = new HashMap<>();
            new DepthFirstOrder(digraph).order().flatMap(digraph::adjacentTo).forEach(e -> relax(e, paths));
            return paths;
        }

        private void relax(DirectedEdge edge, Map<Integer, DirectedEdge> paths) {
            if (distances.get(edge.to) > distances.get(edge.from) + edge.weight) {
                distances.put(edge.to, distances.get(edge.from) + edge.weight);
                paths.put(edge.to, edge);
            }
        }
    }

    private static class DepthFirstOrder {
        private final EdgeWeightedDigraph digraph;
        private final Set<Integer> marked = new HashSet<>();

        private DepthFirstOrder(EdgeWeightedDigraph digraph) {
            this.digraph = digraph;
        }

        private Stream<Integer> order() {
            List<Integer> order = new ArrayList<>();
            digraph.vertices().filter(v -> !marked.contains(v)).forEach(v -> depthFirstSearch(v, order));
            return order.stream();
        }

        private void depthFirstSearch(int vertex, List<Integer> order) {
            order.add(vertex);
            marked.add(vertex);
            digraph.adjacentTo(vertex)
                    .mapToInt(e -> e.to)
                    .filter(v -> !marked.contains(v))
                    .collect(ArrayList::new, (l, v) -> depthFirstSearch(v, order), List::addAll);
        }
    }
}
