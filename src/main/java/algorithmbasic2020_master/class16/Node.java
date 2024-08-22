package algorithmbasic2020_master.class16;

import java.util.ArrayList;

// 点结构的描述
public class Node {
	public int value;//点的编号
	public int in;//入度
	public int out;//出度
	public ArrayList<Node> nexts;//直接邻居
	public ArrayList<Edge> edges;//从该节点出发的边 会存在这个集合里面

	public Node(int value) {
		this.value = value;
		in = 0;
		out = 0;
		nexts = new ArrayList<>();
		edges = new ArrayList<>();
	}
}
