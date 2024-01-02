package algorithmbasic2020_master.class09;

/**
 *TODO
 * 常见面试题
 * 将单向链表按某值划分成左边小、中间相等、右边大的形式
 * 1）把链表放入数组里，在数组上做partition(笔试用)
 * 2）分成小、中、大三部分，再把各个部分之间串起来（面试用)
 * 将单向链表按某值划分成左边小、中间相等、右边大的形式
 *  【题目】给定一个单链表的头节点head，节点的值类型是整型，再给定一个整数pivot。
 *  实现一个调整链表的函数，
 *  将链表调整为左部分都是值小于pivot的节点，
 *  中间部分都是值等于pivot的节点，右部分都是值大于pivot的节点。
 *  3 -> 5 -> 2 -> 6 -> 1 -> 3 -> 7 -> 0   V = 3
 *  小于区域 分别有sh st  等于区域 分别有eh et  大于区域 分别 bh bt
 *  从头开始遍历 头节点 == 3
 *  发现 value == 3
 *  发现 等于区域 et == eh == null 说明区域为空  所以 et == eh 指向 3
 *  来到节点5  value < 5
 *  发现 大于区域 bt == bh == null 说明区域为空  所以 bt == bh 指向 5
 *  来到节点2  value > 2
 *  发现 大于区域 st == sh == null 说明区域为空  所以 st == sh 指向 2
 *  来到节点6  value < 6
 *  发现 大于区域 bt == bh == 5 说明区域不为空  所以 bh 不变  5指向6  bt 指向 6
 *  来到节点1  value > 1
 *  发现 大于区域 st == sh == 2 说明区域不为空  所以 sh 不变  2指向1  st 指向 1
 *  发现 value == 3
 *  发现 等于区域 et == eh == 3 说明区域不为空  所以 eh 不变 老的3指向新的3  et 指向新的3
 *  来到节点7  value < 7
 *  发现 大于区域 bt == 6 bh == 5 说明区域不为空  所以 bh 不变  6指向7  bt 指向 7
 *  来到节点1  value > 1
 *  发现 大于区域 st == 1 sh == 2 说明区域不为空  所以 sh 不变  1指向0  st 指向 0
 *  也就是
 *  小于区域： sh -> 2 -> 1 -> 0 <- st
 *  等于区域： eh -> 3 -> 3 <- et
 *  大于区域： bh -> 5 -> 6 -> 7 <- bt
 *  小于区域的st连接等于区域的eh,et链接大于区域的bh
 */
public class Code03_SmallerEqualBigger {

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node listPartition1(Node head, int pivot) {
        if (head == null) {
            return head;
        }
        Node cur = head;
        int i = 0;
        while (cur != null) {
            i++;
            cur = cur.next;
        }
        Node[] nodeArr = new Node[i];//申请一个数组空间
        i = 0;
        cur = head;
        //把所有链表上的节点放入数组中
        for (i = 0; i != nodeArr.length; i++) {
            nodeArr[i] = cur;
            cur = cur.next;
        }
        //把链表放入数组里，在数组上做partition
        arrPartition(nodeArr, pivot);
        for (i = 1; i != nodeArr.length; i++) {
            nodeArr[i - 1].next = nodeArr[i];
        }
        nodeArr[i - 1].next = null;
        return nodeArr[0];
    }

    public static void arrPartition(Node[] nodeArr, int pivot) {
        int small = -1;
        int big = nodeArr.length;
        int index = 0;
        while (index != big) {
            if (nodeArr[index].value < pivot) {
                swap(nodeArr, ++small, index++);
            } else if (nodeArr[index].value == pivot) {
                index++;
            } else {
                swap(nodeArr, --big, index);
            }
        }
    }

    public static void swap(Node[] nodeArr, int a, int b) {
        Node tmp = nodeArr[a];
        nodeArr[a] = nodeArr[b];
        nodeArr[b] = tmp;
    }

    /*
     * 用三个链表换分成3个区域
     * */
    public static Node listPartition2(Node head, int pivot) {
        Node sH = null; // small head
        Node sT = null; // small tail
        Node eH = null; // equal head
        Node eT = null; // equal tail
        Node mH = null; // big head
        Node mT = null; // big tail
        Node next = null; // save next node
        // every node distributed to three lists
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.value < pivot) {
                if (sH == null) {
                    //因为一开始某一个区域只有一个节点
                    sH = head;
                    sT = head;
                } else {//如果当前链表已经有节点的话
                    sT.next = head;
                    sT = head;
                }
            } else if (head.value == pivot) {
                //因为一开始某一个区域只有一个节点
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {//如果当前链表已经有节点的话
                    eT.next = head;
                    eT = head;
                }
            } else {
                //因为一开始某一个区域只有一个节点
                if (mH == null) {
                    mH = head;
                    mT = head;
                } else {//如果当前链表已经有节点的话
                    mT.next = head;
                    mT = head;
                }
            }
            head = next;
        }
        // 小于区域的尾巴，连等于区域的头，等于区域的尾巴连大于区域的头
        if (sT != null) { // 如果有小于区域
            sT.next = eH;//小于区域的尾巴，连等于区域的头
            //如果等于区域是空的话 小于区域的尾巴去连接大于区域的头 相当于eT是sT
            eT = eT == null ? sT : eT; // 下一步，谁去连大于区域的头，谁就变成eT
        }
        /**
         * 下一步，一定是需要用eT 去接 大于区域的头
         * 有等于区域，eT 就是 等于区域的尾结点
         * 无等于区域，eT 就是 小于区域的尾结点
         * eT 尽量不为空的尾巴节点
         * 如果没有小于区域 只有等于区域
         * 那么上面if分支不经过 et还是等于区域的尾巴
         * 如果有小于区域 没有等于区域，上面if分支经过，那么et是小于区域的尾巴
         */
        if (eT != null) { // 如果小于区域和等于区域，不是都没有
            eT.next = mH;
        }
        /**
         * sH小于区域的头不为空 就返回sH小于区域的头 sH为空 说明没有小于区域 返回eH等于区域的头
         * eH等于区域的头为空的话 说明没有等于区域 mH返回大于区域的头
         * */
        return sH != null ? sH : (eH != null ? eH : mH);
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
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);
        // head1 = listPartition1(head1, 4);
        head1 = listPartition2(head1, 5);
        printLinkedList(head1);

    }

}
