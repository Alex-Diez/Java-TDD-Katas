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
    public int compareTo(WeightEdge o) {
        if (o.weight > weight) return -1;
        else if (o.weight < weight) return 1;
        else return 0;
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
            WeightEdge edge = (WeightEdge)object;
            return edge.weight == weight
                    && edge.w == w
                    && edge.v == v;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
