package kata.java;

import java.util.Objects;

public class DirectedEdge {
    final int from;
    final int to;
    final double weight;

    private DirectedEdge(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public static DirectedEdge create(int from, int to, double weight) {
        return new DirectedEdge(from, to, weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DirectedEdge edge = (DirectedEdge) obj;
        return from == edge.from &&
                to == edge.to &&
                Double.compare(edge.weight, weight) == 0;
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
