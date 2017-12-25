package kata.java;

public class WeightedEdge implements Comparable<WeightedEdge> {
    private final int v;
    private final int w;
    private final double weight;

    public static WeightedEdge create(int v, int w, double weight) {
        return new WeightedEdge(v, w, weight);
    }

    private WeightedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        else return v;
    }

    @Override
    public int compareTo(WeightedEdge that) {
        if (that.weight > weight) return -1;
        else if (that.weight < weight) return 1;
        else return 0;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(v) + Integer.hashCode(w) + Double.hashCode(weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass().equals(getClass())) {
            WeightedEdge edge = (WeightedEdge)obj;
            return edge.weight == weight
                    && edge.v == v
                    && edge.w == w;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ Edge v - " + v + " w - " + w + " weight - " + weight + " ]";
    }
}
