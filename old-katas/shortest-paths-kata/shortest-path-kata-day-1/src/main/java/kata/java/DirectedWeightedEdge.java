package kata.java;

public class DirectedWeightedEdge {
    private final int v;
    private final int w;
    public final double weight;

    private DirectedWeightedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public static DirectedWeightedEdge create(int v, int w, double weight) {
        return new DirectedWeightedEdge(v, w, weight);
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    @Override
    public int hashCode() {
        return v + w + Double.hashCode(weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass().equals(getClass())) {
            DirectedWeightedEdge edge = (DirectedWeightedEdge)obj;
            return edge.weight == weight
                    && edge.w == w
                    && edge.v == v;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ Directed Edge v - " + v + " w - " + w + " weight - " + weight + " ]";
    }
}
