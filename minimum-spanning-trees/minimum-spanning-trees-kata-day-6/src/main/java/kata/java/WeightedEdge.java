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
        if (vertex == w) return v;
        else return w;
    }

    @Override
    public int compareTo(WeightedEdge o) {
        if (o.weight > weight) return -1;
        else if (o.weight < weight) return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return "[ Edge v - " + v + " w - " + w + " weight - " + weight + " ]";
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
        if (object != null && object.getClass().equals(getClass())) {
            WeightedEdge edge = (WeightedEdge)object;
            return edge.v == v
                    && edge.w == w
                    && edge.weight == weight;
        }
        return false;
    }
}
