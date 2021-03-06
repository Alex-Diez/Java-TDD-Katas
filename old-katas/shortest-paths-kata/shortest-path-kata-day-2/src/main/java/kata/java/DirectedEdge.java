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

    public static DirectedEdge create(int v, int w, double weight) {
        return new DirectedEdge(v, w, weight);
    }

    @Override
    public int hashCode() {
        return from + to + Double.hashCode(weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass().equals(getClass())) {
            DirectedEdge edge = (DirectedEdge)obj;
            return edge.weight == weight
                    && edge.to == to
                    && edge.from == from;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ Directed Edge from - " + from + " to - " + to + " weight - " + weight + " ]";
    }
}
