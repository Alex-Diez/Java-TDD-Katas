package kata.java;

public class WeightEdge implements Comparable<WeightEdge> {
    private final int v;
    private final int w;
    private final double weight;

    private WeightEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public static WeightEdge create(int v, int w, double weight) {
        return new WeightEdge(v, w, weight);
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == w) return v;
        else return w;
    }

    @Override
    public int compareTo(WeightEdge edge) {
        if (edge.weight > weight) return -1;
        else if (edge.weight < weight) return 1;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass().equals(getClass())) {
            WeightEdge edge = (WeightEdge) obj;
            return edge.weight == weight
                    && edge.v == v
                    && edge.w == w;
        }
        return false;
    }
}
