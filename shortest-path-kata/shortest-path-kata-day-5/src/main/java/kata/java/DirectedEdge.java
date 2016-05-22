package kata.java;

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
    public int hashCode() {
        return from + to;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass().equals(getClass())) {
            DirectedEdge edge = (DirectedEdge)obj;
            return edge.from == from
                    && edge.to == to
                    && edge.weight == weight;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ Directed edge from - " + from + " to - " + to + " weight - " + weight + " ]";
    }
}
