package heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TravellingSalesmanProblem {

    public static void main(String[] args) {
        Vertex v0 = new Vertex("北京");
        Vertex v1 = new Vertex("上海");
        Vertex v2 = new Vertex("西安");
        Vertex v3 = new Vertex("武汉");

        v0.edges = List.of(
                new Edge(v0, v1, 1), /* 北京 -> 上海 */
                new Edge(v0, v2, 2), /* 北京 -> 西安 */
                new Edge(v0, v3, 3)  /* 北京 -> 武汉 */
        );

        v1.edges = List.of(
                new Edge(v1, v0, 1), /* 上海 -> 北京 */
                new Edge(v1, v2, 6), /* 上海 -> 西安 */
                new Edge(v1, v3, 4)  /* 上海 -> 武汉 */
        );

        v2.edges = List.of(
                new Edge(v2, v0, 2), /* 西安 -> 北京 */
                new Edge(v2, v1, 6), /* 西安 -> 上海 */
                new Edge(v2, v3, 5)  /* 西安 -> 武汉 */
        );

        v3.edges = List.of(
                new Edge(v3, v0, 3),  /* 武汉 -> 北京 */
                new Edge(v3, v1, 4),  /* 武汉 -> 上海 */
                new Edge(v3, v2, 5)   /* 武汉 -> 西安 */
        );

        LinkedHashSet<Vertex> visited = new LinkedHashSet<>();
        Vertex start = v0; // start=v3 不是最优解
        Vertex curr = start;
        int dist = 0;
        while (true) {
            visited.add(curr);
            Optional<Edge> first = curr.edges.stream().filter(e -> !visited.contains(e.to)).sorted().findFirst();
            if (first.isEmpty()) {
                break;
            }
            Edge edge = first.get();
            curr = edge.to;
            dist += edge.weight;
        }
        ArrayList<Vertex> x = new ArrayList<>(visited);
        dist += x.get(x.size() - 1).edges.stream().filter(b -> b.to == start).findFirst().get().weight;
        x.add(start);
        System.out.println(x.stream().map(v -> v.name).collect(Collectors.joining("->")) + " " + dist);
    }
}
