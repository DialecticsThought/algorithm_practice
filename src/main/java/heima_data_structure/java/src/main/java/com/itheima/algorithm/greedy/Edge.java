package heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy;

import java.util.Objects;

public class Edge implements Comparable<Edge> {

    public Vertex from;
    public Vertex to;
    public int weight;

    public Edge(Vertex from, Vertex to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.weight, o.weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        return Objects.equals(to.name, edge.to.name);
    }

    @Override
    public int hashCode() {
        return to.name != null ? to.name.hashCode() : 0;
    }
}