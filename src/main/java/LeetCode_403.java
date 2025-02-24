/**
 * @Description
 * @Author veritas
 * @Data 2025/2/23 19:30
 */
public class LeetCode_403 {
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

    ;

    public Node flatten(Node head) {
        return dfs(head);
    }

    public Node dfs(Node node) {
        //记录 当前遍历到的最新的节点 也就是最后的一个节点
        Node last = null;
        // 记录 当前节点的下一个
        Node cur = node;

        while (cur != null) {
            // TODO 提前记录
            Node curNext = cur.next;
            // 检查 当前节点不是有child
            if (cur.child != null) {
                // 返回  遍历 子链表得到的最后一个节点
                Node childLast = dfs(cur.child);
                // TODO  上面的代码执行完 说明 要处理当前这一层链表了

                // TODO 重要 需要恢复
                curNext = cur.next;
                // 当前节点 和 当前节点子节点 关联在一起
                cur.next = cur.child;
                cur.child.prev = cur;
                // 返回  遍历 子链表得到的最后一个节点 和 当前节点 的下一个节点 关联在一起
                if (curNext != null) {
                    childLast.next = curNext;
                    curNext.prev = childLast;
                }
                // 此时最新的节点就是遍历 子链表得到的最后一个节点
                last = childLast;
                // 遍历过子链表之后 删去
                cur.child = null;
            } else {
                // 当前节点没有子节点
                // 说明 当前节点就是最后一个节点
                last = cur;
            }
            // 当前层遍历的话 来到下一个
            cur = curNext;
        }
        return last;
    }
}
