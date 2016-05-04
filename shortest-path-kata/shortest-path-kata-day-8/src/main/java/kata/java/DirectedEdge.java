package kata.java;

import java.util.Objects;

public class DirectedEdge<V> {
    final V from;
    final V to;
    final double weight;

    private DirectedEdge(V from, V to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public static <V> DirectedEdge create(V from, V to, double weight) {
        return new DirectedEdge<>(from, to, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DirectedEdge edge = (DirectedEdge) o;
        return from == edge.from &&
                to == edge.to &&
                weight == edge.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }

    @Override
    public String toString() {
        return "[ Directed edge from - " + from + " to - " + to + " weight - " + weight + " ]";
    }
}
