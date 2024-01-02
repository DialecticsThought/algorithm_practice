package heima_data_structure.java.src.main.java.com.itheima.datastructure.graph;

import java.util.List;
import java.util.Objects;

/**
 * <h3>顶点</h3>
 */
public class Vertex {
    String name;
    public List<Edge> edges;

    boolean visited; // 是否被访问过，用在 BFS 和 DFS
    int inDegree; // 入度，用在拓扑排序
    int status; // 状态 0-未访问 1-访问中 2-访问过，用在拓扑排序

    int dist = INF; // 距离
    static final Integer INF = Integer.MAX_VALUE;
    Vertex prev = null; // 记录从何而来

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String n = name;
        if (visited) {
            n = "\u001B[31m" + name + "\u001B[0m";
        }
        return n + '(' + (dist == Integer.MAX_VALUE ? "∞" : String.valueOf(dist)) + ") <- " + (prev == null ? "null" : prev.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
