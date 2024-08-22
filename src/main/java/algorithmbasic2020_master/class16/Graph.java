package algorithmbasic2020_master.class16;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
	public HashMap<Integer, Node> nodes;//点的编号 和点
	public HashSet<Edge> edges;//边的集合

	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}
}
