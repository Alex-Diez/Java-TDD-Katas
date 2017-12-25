package kata.java;

public class WeightedEdge implements Comparable<WeightedEdge> {
    private final int v;
    private final int w;
    private final double weight;

    public WeightedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return v + w + Double.hashCode(weight);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object != null && getClass().equals(object.getClass())) {
            WeightedEdge edge = (WeightedEdge)object;
            return edge.v == v
                    && edge.w == w
                    && edge.weight == weight;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ Edge from " + v + " to " + w + " weight " + weight + " ]";
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == w) return v;
        else return w;
    }

    @Override
    public int compareTo(WeightedEdge o) {
        if (o.weight < weight) return 1;
        else if (o.weight > weight) return -1;
        else return 0;
    }
}
