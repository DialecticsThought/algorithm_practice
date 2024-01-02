package code_for_great_offer.class10;

import java.util.*;

// 本题测试链接：https://www.lintcode.com/problem/top-k-frequent-words-ii/
// 以上的代码不要粘贴, 把以下的代码粘贴进java环境编辑器
// 把类名和构造方法名改成TopK, 可以直接通过
public class Code02_TopK {
	private Node[] heap;//堆就是一个数组
	private int heapSize;//实际堆的大小
	// 词频表   key  abc   value  (abc,7)
	//TODO 词频表 key就是某个str value就是<某个str,词频>
	private HashMap<String, Node> strNodeMap;
	//TODO 索引表 key是node value表示堆中的位置
	private HashMap<Node, Integer> nodeIndexMap;
	//TODO 比较器
	private NodeHeapComp comp;
	private TreeSet<Node> treeSet;//存放的堆一样的数据 单纯用来排序输出

	public Code02_TopK(int K) {
		heap = new Node[K];
		heapSize = 0;
		strNodeMap = new HashMap<String, Node>();
		nodeIndexMap = new HashMap<Node, Integer>();
		comp = new NodeHeapComp();
		treeSet = new TreeSet<>(new NodeTreeSetComp());
	}

	public static class Node {
		public String str;
		public int times;

		public Node(String s, int t) {
			str = s;
			times = t;
		}
	}

	public static class NodeHeapComp implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o1.times != o2.times ? (o1.times - o2.times) : (o2.str.compareTo(o1.str));
		}
	}

	public static class NodeTreeSetComp implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o1.times != o2.times ? (o2.times - o1.times) : (o1.str.compareTo(o2.str));
		}
	}
	//TODO 添加字符串
	public void add(String str) {
		if (heap.length == 0) {
			return;
		}
		//TODO  str  找到的对应节点  curNode 如果没有对应节点说明第一次进入堆
		Node curNode = null;
		//TODO  找到的对应节点  curNode  在堆上的位置  如果没有对应节点说明第一次进入堆
		int preIndex = -1;
		if (!strNodeMap.containsKey(str)) {//TODO 词频表 不包含这个str
			//TODO 创建节点
			curNode = new Node(str, 1);
			//TODO 放入词频表
			strNodeMap.put(str, curNode);
			//TODO 把节点注册到索引表
			nodeIndexMap.put(curNode, -1);
		} else {
			//TODO 直接得到当前节点
			curNode = strNodeMap.get(str);
			// 要在time++之前，先在treeSet中删掉
			// 原因是因为一但times++，curNode在treeSet中的排序就失效了
			// 这种失效会导致整棵treeSet出现问题
			if (treeSet.contains(curNode)) {
				treeSet.remove(curNode);//TODO 这里删除 下面会加入
			}
			curNode.times++;//TODO 当前str‘的词频+1
			//TODO 从索引表得到 当前节点的位置
			preIndex = nodeIndexMap.get(curNode);
		}
		if (preIndex == -1) {//TODO 说明之前没有进入堆 说明 堆满了只有两情况 堆满了 堆没有满
			if (heapSize == heap.length) {//堆满了
				//TODO 让堆顶节点 和当前节点 做比较
				if (comp.compare(heap[0], curNode) < 0) {
					treeSet.remove(heap[0]);
					treeSet.add(curNode);
					//TODO 索引表中 原先的堆顶节点 被删除
					nodeIndexMap.put(heap[0], -1);
					//TODO 索引表中 当前节点加入
					nodeIndexMap.put(curNode, 0);
					//TODO 再真正放入堆
					heap[0] = curNode;
					heapify(0, heapSize);//从0位置开始往后heapify
				}
			} else {//TODO 堆没有满 直接把节点放入堆的最后  再上浮
				treeSet.add(curNode);
				nodeIndexMap.put(curNode, heapSize);
				heap[heapSize] = curNode;
				heapInsert(heapSize++);
			}
		} else {
			treeSet.add(curNode);
			heapify(preIndex, heapSize);
		}
	}
	//返回 从次数最多 到次数最少
	public List<String> topk() {
		ArrayList<String> ans = new ArrayList<>();
		for (Node node : treeSet) {
			ans.add(node.str);
		}
		return ans;
	}

	private void heapInsert(int index) {
		while (index != 0) {
			int parent = (index - 1) / 2;
			if (comp.compare(heap[index], heap[parent]) < 0) {
				swap(parent, index);
				index = parent;
			} else {
				break;
			}
		}
	}

	private void heapify(int index, int heapSize) {
		int l = index * 2 + 1;
		int r = index * 2 + 2;
		int smallest = index;
		while (l < heapSize) {
			if (comp.compare(heap[l], heap[index]) < 0) {
				smallest = l;
			}
			if (r < heapSize && comp.compare(heap[r], heap[smallest]) < 0) {
				smallest = r;
			}
			if (smallest != index) {
				swap(smallest, index);
			} else {
				break;
			}
			index = smallest;
			l = index * 2 + 1;
			r = index * 2 + 2;
		}
	}

	private void swap(int index1, int index2) {
		/*
		* 索引表 和 堆都要交换
		* */
		nodeIndexMap.put(heap[index1], index2);
		nodeIndexMap.put(heap[index2], index1);
		Node tmp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = tmp;
	}

}
