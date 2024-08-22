package algorithmbasic2020_master.class09;

import java.util.Stack;

/**
 *TODO
 * 给定一个单链表的头节点head，请判断该链表是否为回文结构。
 * 1)哈希表方法特别简单（笔试用)
 * 2）改原链表的方法就需要注意边界了（面试用)
 * 方法1：用栈
 * eg:
 * 1 → 2 → 3 → 2 → 1 → null
 * 1.把上面的链表节点遍历一遍病房与一个栈里面
 * 栈的结构，从底到顶 1,2,3,2,1
 * 2.再从头遍历一遍，此时遍历到一个节点就弹出栈顶，比较是否相同
 * 方法2：
 * 1 → 2 → 3 → 2 → 1 → null
 * 找到链表的中点，如果是偶数个的话就是上中点
 * 执行操作：中点的next==null
 * 1 → 2 → 3   2 → 1 → null
 *         ↓
 *        null
 * 中点之后的节点 全部倒着指向
 * 1 → 2 → 3 ← 2 ← 1
 *         ↓
 *        null
 * 链头和链尾分别有L和R指向
 * 1 → 2 → 3 ← 2 ← 1
 * ↑       ↓       ↑
 * L      null     R
 * 如果L和R的值相同，L++，R--
 *
 */
public class Code02_IsPalindromeList {

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // need n extra space
    public static boolean isPalindrome1(Node head) {
        Stack<Node> stack = new Stack<Node>();
        Node cur = head;
        /*
         * 把一个链表里的全部节点放入到栈中
         * */
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        /*
         * 再把栈中的元素一个个弹出来作比对
         * */
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // need n/2 extra space
    public static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node right = head.next;
        Node cur = head;
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }
        //把链表右半部分全部放入到栈中 形成右半部的逆序
        //然后一个个弹出 与链表从头比对
        Stack<Node> stack = new Stack<Node>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }
        while (!stack.isEmpty()) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // need O(1) extra space
    public static boolean isPalindrome3(Node head) {
        if (head == null || head.next == null) {//链表为空或者只有1个 一定是回文
            return true;
        }
        Node n1 = head;
        Node n2 = head;
        while (n2.next != null && n2.next.next != null) { // find mid node
            n1 = n1.next; // n1 -> mid 每次走一步
            n2 = n2.next.next; // n2 -> end 每次走两步
        }
        // n1 奇数是中点 偶数是上重点
        n2 = n1.next; // n2 -> right part first node
        n1.next = null; // mid.next -> null
        Node n3 = null;
        /*
         * eg： 1->2->3->2->1
         * 变成 1->2->3<-2<-1<-n3  n3指向最后一个
         * n2一开始指向最左边 n1指向最右边 两个指针同时向中间走
         * */
        while (n2 != null) { // right part convert 右半部分逆序
            n3 = n2.next; // n3 -> save next node
            n2.next = n1; // next of right node convert
            n1 = n2; // n1 move
            n2 = n3; // n2 move
        }
        n3 = n1; // n3 -> save last node
        n2 = head;// n2 -> left first node
        boolean res = true;
        while (n1 != null && n2 != null) { // check palindrome
            //if表示如果n1和n2指向的两个节点不同的话 跳出循环 表示不是回文链表
            if (n1.value != n2.value) {
                res = false;
                break;
            }
            n1 = n1.next; // left to mid
            n2 = n2.next; // right to mid
        }
        /*
         * 下面的代码是翻转一下后半部分的方向
         * 本来是1->2->3<-2<-1
         * 翻转过来是 1->2->3->2->1
         * */
        n1 = n3.next;
        n3.next = null;
        while (n1 != null) { // recover list
            n2 = n1.next;
            n1.next = n3;
            n3 = n1;
            n1 = n2;
        }
        return res;
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

    }

}
