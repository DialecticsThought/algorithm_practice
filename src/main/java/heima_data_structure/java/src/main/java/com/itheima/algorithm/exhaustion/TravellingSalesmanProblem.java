package heima_data_structure.java.src.main.java.com.itheima.algorithm.exhaustion;



import heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy.Edge;
import heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy.Vertex;

import java.util.LinkedHashSet;
import java.util.List;

public class TravellingSalesmanProblem {

    public static void main(String[] args) {
        Vertex v1 = new Vertex("北京");
        Vertex v2 = new Vertex("上海");
        Vertex v3 = new Vertex("西安");
        Vertex v4 = new Vertex("武汉");

        v1.edges = List.of(
                new Edge(v1, v2, 1210), /* 北京 -> 上海 */
                new Edge(v1, v3, 1070), /* 北京 -> 西安 */
                new Edge(v1, v4, 1150)  /* 北京 -> 武汉 */
        );

        v2.edges = List.of(
                new Edge(v2, v1, 1320), /* 上海 -> 北京 */
                new Edge(v2, v3, 1360), /* 上海 -> 西安 */
                new Edge(v2, v4, 790)   /* 上海 -> 武汉 */
        );

        v3.edges = List.of(
                new Edge(v3, v1, 1200), /* 西安 -> 北京 */
                new Edge(v3, v2, 1350), /* 西安 -> 上海 */
                new Edge(v3, v4, 680)   /* 西安 -> 武汉 */
        );

        v4.edges = List.of(
                new Edge(v4, v1, 1100), /* 武汉 -> 北京 */
                new Edge(v4, v2, 760),  /* 武汉 -> 上海 */
                new Edge(v4, v3, 650)   /* 武汉 -> 西安 */
        );

        List<Vertex> all = List.of(v1, v2, v3, v4);

        for (Vertex v : all) {
            LinkedHashSet<Vertex> visited = new LinkedHashSet<>();
            int sum = 0;
            visited.add(v);
            for (Edge e2 : v.edges) {
                visited.add(e2.to);
                sum += e2.weight;
                for (Edge e3 : e2.to.edges.stream().filter(x -> !visited.contains(x.to)).toList()) {
                    visited.add(e3.to);
                    sum += e3.weight;
                    for (Edge e4 : e3.to.edges.stream().filter(x -> !visited.contains(x.to)).toList()) {
                        visited.add(e4.to);
                        sum += e4.weight;
                        System.out.print(visited.stream().map(x -> x.name).toList());
                        System.out.println("\t" + sum);
                        sum -= e4.weight;
                        visited.remove(e4.to);
                    }
                    sum -= e3.weight;
                    visited.remove(e3.to);
                }
                sum -= e2.weight;
                visited.remove(e2.to);
            }
            visited.remove(v);
        }

        /*System.out.println("北京 -> 上海 -> 西安 -> 武汉");
        System.out.println(v1.edges.get(0).weight + v2.edges.get(1).weight + v3.edges.get(2).weight);
        System.out.println("北京 -> 上海 -> 武汉 -> 西安");
        System.out.println(v1.edges.get(0).weight + v2.edges.get(2).weight + v4.edges.get(2).weight);
        System.out.println("北京 -> 西安 -> 上海 -> 武汉");
        System.out.println(v1.edges.get(1).weight + v3.edges.get(1).weight + v2.edges.get(2).weight);
        System.out.println("北京 -> 西安 -> 武汉 -> 上海");
        System.out.println(v1.edges.get(1).weight + v3.edges.get(2).weight + v4.edges.get(1).weight);
        System.out.println("北京 -> 武汉 -> 上海 -> 西安");
        System.out.println(v1.edges.get(2).weight + v4.edges.get(1).weight + v2.edges.get(1).weight);
        System.out.println("北京 -> 武汉 -> 西安 -> 上海");
        System.out.println(v1.edges.get(2).weight + v4.edges.get(2).weight + v3.edges.get(1).weight);*/
    }
}
