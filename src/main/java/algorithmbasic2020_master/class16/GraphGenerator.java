package algorithmbasic2020_master.class16;

public class GraphGenerator {

	// matrix 所有的边
	// N*3 的矩阵
	// [weight权重, from节点上面的值，to节点上面的值]
	//
	// [ 5 , 0 , 7]
	// [ 3 , 0,  1]
	//
	public static Graph createGraph(int[][] matrix) {
		Graph graph = new Graph();
		for (int i = 0; i < matrix.length; i++) {
			 // 拿到每一条边， matrix[i]
			int weight = matrix[i][0];
			int from = matrix[i][1];
			int to = matrix[i][2];
			//graph.node存放的是点的集合
			if (!graph.nodes.containsKey(from)) {//如果点集里面没有from节点的编号
				graph.nodes.put(from, new Node(from));//创建from节点并和编号放入map
			}
			if (!graph.nodes.containsKey(to)) {//如果点集里面没有to节点的编号
				graph.nodes.put(to, new Node(to));//创建to节点并和编号放入map
			}
			//根据编号从map中获得 from/to 节点
			Node fromNode = graph.nodes.get(from);
			Node toNode = graph.nodes.get(to);
			//创建from 到 to 这两个节点之间的边 边的权值是weight
			Edge newEdge = new Edge(weight, fromNode, toNode);
			//因为from和to间接相连 把 to节点放入from节点的直接邻居集合里面
			fromNode.nexts.add(toNode);
			//from ---->  to 所以 from的出度+1 to的入度+1
			fromNode.out++;
			toNode.in++;
			//from 到 to 这两个节点之间的边 放入到from的边集合里面
			fromNode.edges.add(newEdge);
			//from 到 to 这两个节点之间的边 放入graph的边集合里面
			graph.edges.add(newEdge);
		}
		return graph;
	}

}
