package code_for_great_offer.class16;

/**
 * 本题测试链接 : https://leetcode-cn.com/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof/
 * 约瑟夫环问题
 * 给定一个链表头节点head，和一个正数m从头开始，每次数到m就杀死当前节点
 * 然后被杀节点的下一个节点从1开始重新数，周而复始直到只剩一个节点,返回最后的节点
 *TODO
 * m=3
 * 1)
 *   1 ← 从这里开始 顺时针
 * ↗ 	↘
 * 5	2
 * ↑	↓
 * 4 ←	3 × 这个节点被删去
 * 2) 被删除节点后的状态
 * 1  → 2
 * ↑	↓
 * 5 ←	4 ← 从这里开始 顺时针
 * ↓ 这个节点被删去
 * ×
 * 1  → 2
 * ↑	↓
 * 5 ←	4 ← 从这里开始 顺时针
 * 3) 被删除节点后的状态
 * 5 -> -> 2 ← 从这里开始 顺时针
 * 	 ↖	↙
 * 	  4
 * ↓ 这个节点被删去
 * ×
 * 5 -> -> 2 ← 从这里开始 顺时针
 * 	 ↖	↙
 * 	  4
 * 4) 被删除节点后的状态
 * 4 <-> 2 ← 从这里开始 顺时针
 * 2 -> 4 -> 2 所以2被删去
 * 假设共有i个节点
 * 报数	节点编号
 * 1		1
 * 2		2
 * ...		...
 * i		i
 * i+1		1
 * ...		...
 * 2i		i
 * 总结
 * 编号 = (数字-1)%i+1 => s = (m-1)%i+1 ， m-1可以看成k(倍数) * i + r (余数)
 * 杀前 = (杀后 + (m-1)%i)+1
 *TODO
 * 假设 i = 7 m = 3 杀掉数到3的节点
 * 杀之前： 1 2 3 4 5 6 7
 * 杀之后： 5 6 x 1 2 3 4
 * 要用杀之后的编号 推出杀之前的编号
 * 之前
 *   ↑
 * 7 ↑          ↗
 * 6 ↑       ↗
 * 5 ↑    ↗
 * 4 ↑ ↗
 * 3 ↑                   ↗
 * 2 ↑                ↗
 * 1 ↑             ↗
 * 0  → → → → → → → → → → → 之后
 *     1  2  3  4  5  6  7
 * 抽象化
 *  如果杀死m 编号为s
 *  那杀之后的编号为1的就是杀之前的s+1，编号为2的是s+2....
 *  直到 i-s,i-s对应杀之前的是i
 *  杀之前编号 = (杀之后编号 - 1 + s)% i + 1，s就是长度为i的chain中数到m的编号
 */
public class Code05_JosephusProblem {

    // 提交直接通过
    // 给定的编号是0~n-1的情况下，数到m就杀
    // 返回谁会活？
    public int lastRemaining1(int n, int m) {
        return getLive(n, m) - 1;
    }

    // 课上题目的设定是，给定的编号是1~n的情况下，数到m就杀
    // 返回谁会活？
    public static int getLive(int n, int m) {
        if (n == 1) {
            return 1;
        }
        return (getLive(n - 1, m) + m - 1) % n + 1;
    }

    // 提交直接通过
    // 给定的编号是0~n-1的情况下，数到m就杀
    // 返回谁会活？
    // 这个版本是迭代版
    public int lastRemaining2(int n, int m) {
        int ans = 1;
        int r = 1;
        while (r <= n) {
            ans = (ans + m - 1) % (r++) + 1;
        }
        return ans - 1;
    }

    // 以下的code针对单链表，不要提交
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node josephusKill1(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node last = head;
        while (last.next != head) {
            last = last.next;
        }
        int count = 0;
        while (head != last) {
            if (++count == m) {
                last.next = head.next;
                count = 0;
            } else {
                last = last.next;
            }
            head = last.next;
        }
        return head;
    }

    public static Node josephusKill2(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node cur = head.next;
        int size = 1; // tmp -> list size
        while (cur != head) {
            size++;
            cur = cur.next;
        }
        int live = getLive(size, m); // tmp -> service node position
        while (--live != 0) {
            head = head.next;
        }
        head.next = head;
        return head;
    }

    public static void printCircularList(Node head) {
        if (head == null) {
            return;
        }
        System.out.print("Circular List: " + head.value + " ");
        Node cur = head.next;
        while (cur != head) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println("-> " + head.value);
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = head1;
        printCircularList(head1);
        head1 = josephusKill1(head1, 3);
        printCircularList(head1);

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = head2;
        printCircularList(head2);
        head2 = josephusKill2(head2, 3);
        printCircularList(head2);

    }

}
