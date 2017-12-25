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

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException();
    }

    @Override
    public int compareTo(WeightedEdge other) {
        if (weight < other.weight) return 1;
        else if (weight > other.weight) return -1;
        else return 0;
    }
}
