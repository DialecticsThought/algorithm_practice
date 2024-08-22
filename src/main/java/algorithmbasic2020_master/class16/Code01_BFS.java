package algorithmbasic2020_master.class16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Code01_BFS {

	// 从node出发，进行宽度优先遍历
	public static void bfs(Node start) {
		if (start == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		//set能避免重复 因为某一个节点可以重复进队列 但是不能重复打印
		HashSet<Node> set = new HashSet<>();
		/*
		* 一开始把 第一个节点放入到队列和set中
		* */
		queue.add(start);
		set.add(start);
		while (!queue.isEmpty()) {//只要队列不为空
			Node cur = queue.poll();//弹出队首
			System.out.println(cur.value);//打印队首
			for (Node next : cur.nexts) {//遍历队首的直接邻居
				if (!set.contains(next)) {//队首的直接邻居不在set里面说明没有打印
					//把没有打印的邻居节点放入set和队列中
					set.add(next);
					queue.add(next);
				}
			}
		}
	}

}
