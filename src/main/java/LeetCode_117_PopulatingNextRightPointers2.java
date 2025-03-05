/**
 * @Description
 * @Author veritas
 * @Data 2025/3/5 17:22
 */
public class LeetCode_117_PopulatingNextRightPointers2 {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    /**
     * <pre>
     *          1
     *         / \
     *        2   3
     *       /     \
     *      4       5
     *      从上到下、逐层处理，每层利用前一层建立的 next 指针顺序遍历当前层，
     *      然后通过一个“哑节点”（dummy node）构建下一层的链表。接下来详细解释每一步操作
     *
     *  初始化
     *      检查根节点：如果根节点为空，直接返回空。这里根节点是 1，不为空。
     *      设置当前层起始节点：令 head = 1，表示当前处理的是第 0 层（根层）。
     *      定义一个层计数变量（例如 level = 0），方便理解每一层的处理（这仅用于解释，不影响算法本身）。
     *
     *  处理第 0 层（只有节点 1）
     *      1.新建哑节点：为构建下一层链表，新建一个 dummy 节点。dummy 只是个占位符，初始时 dummy.next 为 null。
     *      2.设置当前构建指针：令 curr 指向 dummy。curr 用来连接当前层中所有将要加入下一层的节点。
     *      3.遍历当前层节点：
     *          当前层只有一个节点：节点 1。
     *          访问节点 1：
     *              检查左子节点：节点 1 的左子节点是节点 2，不为空。
     *                  将 curr.next 指向节点 2（即 dummy.next 指向节点 2），并更新 curr 为节点 2。
     *                  操作说明：将节点 2 “挂到” dummy 后面，标志着下一层的第一个节点为 2。
     *              检查右子节点：节点 1 的右子节点是节点 3，不为空。
     *                  将 curr.next 指向节点 3，并更新 curr 为节点 3。
     *                  操作说明：在节点 2 后面连接节点 3，此时 dummy.next 形成的链表为：2 → 3。
     *      4.结束本层遍历：遍历完当前层后，dummy.next 指向节点 2，这正是下一层的起点。
     *      5.更新 head：令 head = dummy.next，即 head 指向节点 2。
     *      6.层次结束：第 0 层处理完毕。
     *  总结：经过这一层，连接操作已把节点 1 的孩子（节点 2 和 3）用链表顺序排列起来（2 → 3）。
     *
     *  处理第 1 层（节点 2 和 3，通过上一层的构建得到）
     *      1.新建哑节点：为构建下一层，再次新建一个 dummy 节点，初始 dummy.next 为 null。
     *      2.设置当前构建指针：令 curr 指向新的 dummy 节点。
     *      3.遍历当前层节点：
     *          当前层的起点是节点 2，接着通过节点 1 建立的 next 指针，节点 2 的 next 指向节点 3。
     *          处理节点 2：
     *              检查左子节点：节点 2 的左子节点是节点 4，不为空。
     *                  将 curr.next 指向节点 4，并更新 curr 为节点 4。
     *                  操作说明：将节点 4连接到 dummy 后面，标志下一层的第一个节点为 4。
     *              检查右子节点：节点 2 没有右子节点，故跳过。
     *                  处理节点 3（通过节点 2 的 next 指针到达）：
     *                  检查左子节点：节点 3 没有左子节点，跳过。
     *                  检查右子节点：节点 3 的右子节点是节点 5，不为空。
     *                      将 curr.next 指向节点 5，并更新 curr 为节点 5。
     *                      操作说明：在节点 4 后面连接节点 5，此时 dummy.next 链表为：4 → 5。
     *       4.结束本层遍历：遍历完节点 2 和 3后，dummy.next 指向节点 4。
     *       5.更新 head：令 head = dummy.next，即 head 指向节点 4。
     *       6.层次结束：第 1 层处理完毕。
     *  总结：经过这一层，节点 2 和 3的孩子被连接起来，形成下一层的链表：4 → 5。
     *
     *  处理第 2 层（节点 4 和 5，通过上一层的构建得到）
     *  1.新建哑节点：同样，新建一个 dummy 节点，用于构建下一层链表。
     *  2.设置当前构建指针：令 curr 指向 dummy 节点。
     *  3.遍历当前层节点：
     *      当前层起点是节点 4，通过上一层 next 指针，节点 4 的 next 指向节点 5。
     *      处理节点 4：
     *          检查左子节点：节点 4 没有左子节点。
     *          检查右子节点：节点 4 没有右子节点。
     *      处理节点 5：
     *          检查左子节点：节点 5 没有左子节点。
     *          检查右子节点：节点 5 没有右子节点。
     *  4.结束本层遍历：本层所有节点都没有子节点，dummy.next 仍为 null。
     *  5.更新 head：令 head = dummy.next，此时 head 为 null，表示下一层为空。
     *  6.层次结束：第 2 层处理完毕，算法结束。
     *  总结：节点 4 和 5均无子节点，说明树已到达叶子层，下一层为空，整个连接过程终止。
     *
     *  最终结果
     *  通过上述逐层操作，树中每个节点的 next 指针已正确建立，结果如下：
     *  第 0 层：节点 1 → null
     *  第 1 层：节点 2 → 节点 3 → null
     *  第 2 层：节点 4 → 节点 5 → null
     * </pre>
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        // base case
        if (root == null) {
            return null;
        }
        // 初始化
        // head 指向当前层的第一个节点
        Node head = root;
        // head用来遍历每一个层 ，每一个层内的遍历是内部for循环
        while (head != null) {
            // dummy 是构建下一层链表的哑节点
            Node dummy = new Node(-1);
            // TODO curr 指向当前在下一层构建链表的尾节点 ，
            //  初始情况:下一层还没构造，指向dummy
            // TODO 在某种程度上 current也是遍历一层 只不过是当前层的下一层
            Node current = dummy;
            // 有一个辅助节点auxiliary
            // 遍历当前层 从第一个节点开始 真正的遍历是里面的for循环
            // TODO 当前层 是由上一层遍历的时候构造得到的
            for (Node auxiliary = head; auxiliary != null; auxiliary = auxiliary.next) {
                // 当前被遍历到的节点是否有左孩子
                if (auxiliary.left != null) {
                    // 用来指向当前层的下一层的尾节点的指针 更新
                    current.next = auxiliary.left;
                    current = current.next;
                }
                // 当前被遍历到的节点是否有左孩子
                if (auxiliary.right != null) {
                    // 用来指向当前层的下一层的尾节点的指针 更新
                    current.next = auxiliary.right;
                    current = current.next;
                }
            }
            // 更新 head 到下一层的第一个节点
            // dummy用来存放下一层的首节点
            head = dummy.next;
        }
        return root;
    }
}
