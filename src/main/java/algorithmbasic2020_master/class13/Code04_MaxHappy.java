package algorithmbasic2020_master.class13;

import java.util.ArrayList;
import java.util.List;

public class Code04_MaxHappy {
/*
*TODO
* 派对的最大快乐值
* 公司的每个员工都符合Employee类的描述。整个公司的人员结构可以看作是一棵标准的、没有环的多叉树。树的头节点是公司唯一的老板。除老板之外的每个员工都有唯一的直接上级。叶节点是没有任何下属的基层员工
* (subordinates列表为空)，除基层员工外，每个员工都有一个或多个直接下级。
* 派对的最大快乐值
* 这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，
* 规则:
* 1.如果某个员工来了，那么这个员工的所有直接下级都不能来 （直接上级来 直接下级不能来）
* 2.派对的整体快乐值是所有到场员工快乐值的累加
* 3.你的目标是让派对的整体快乐值尽量大
* 给定一棵多叉树的头节点boss，请返回派对的最大快乐值。
* */
	public static class Employee {
		public int happy;
		public List<Employee> nexts;

		public Employee(int h) {
			happy = h;
			nexts = new ArrayList<>();
		}

	}

	public static int maxHappy1(Employee boss) {
		if (boss == null) {
			return 0;
		}
		return process1(boss, false);
	}

	/*
	*TODO
	*  当前来到的节点叫cur，
	*  up表示cur的上级是否来，
	*  该函数含义：
	*  如果up为true，表示在cur上级已经确定来，的情况下，cur整棵树能够提供最大的快乐值是多少？
	*  如果up为false，表示在cur上级已经确定不来，的情况下，cur整棵树能够提供最大的快乐值是多少？
	*/
	public static int process1(Employee cur, boolean up) {
		if (up) { //TODO  如果cur的上级来的话，cur没得选，只能不来
			int ans = 0;
			for (Employee next : cur.nexts) {
				ans += process1(next, false);
			}
			return ans;
		} else { //TODO 如果cur的上级不来的话，cur可以选，可以来也可以不来
			int p1 = cur.happy;
			int p2 = 0;
			for (Employee next : cur.nexts) {
				p1 += process1(next, true);
				p2 += process1(next, false);
			}
			return Math.max(p1, p2);
		}
	}

	public static int maxHappy2(Employee head) {
		Info allInfo = process(head);
		return Math.max(allInfo.no, allInfo.yes);
	}

	public static class Info {
		public int no;//TODO 头结点不来的话 整棵树的最大快乐值
		public int yes;//TODO 头结点来的话 整棵树的最大快乐值

		public Info(int n, int y) {
			no = n;
			yes = y;
		}
	}

	public static Info process(Employee x) {
		/*if (x == null) {
			return new Info(0, 0);
		}*/
		if(x.nexts.isEmpty()){//TODO 表示x是基层员工的时候
			return new Info(0, 0);
		}
		int no = 0;
		int yes = x.happy;
		/*
		* for 循环 是因为 不是二叉树 x可以有多个子节点
		* */
		for (Employee next : x.nexts) {
			Info nextInfo = process(next);
			/*
			*TODO
			* no表示如果x节点没有得到邀请 x节点的所有子节点都有可以来 和 不来 两种情况
			* 所以比较 x节点的子节点 不来 整棵树的happy值大 还是 来 正颗树的happy值大
			* 最大的值 和 当前 x不来的时候 的happy值 相加
			* */
			no += Math.max(nextInfo.no, nextInfo.yes);
			/*
			*TODO
			* yes表示 x决定来的话  x节点来的happy+下一级节点不来的happy最大值 因为当前节点x 下一层节点全是no
			* （也就是 x节点的所有子节点都不来 每一颗子树在头结点不来的情况下的最大happy值）
			* 一开始 上一级节点是根节点
			* */
			yes += nextInfo.no;

		}
		return new Info(no, yes);
	}

	// for test
	public static Employee genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
		if (Math.random() < 0.02) {
			return null;
		}
		Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
		genarateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
		return boss;
	}

	// for test
	public static void genarateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
		if (level > maxLevel) {
			return;
		}
		int nextsSize = (int) (Math.random() * (maxNexts + 1));
		for (int i = 0; i < nextsSize; i++) {
			Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
			e.nexts.add(next);
			genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
		}
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxNexts = 7;
		int maxHappy = 100;
		int testTimes = 100000;
		for (int i = 0; i < testTimes; i++) {
			Employee boss = genarateBoss(maxLevel, maxNexts, maxHappy);
			if (maxHappy1(boss) != maxHappy2(boss)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
