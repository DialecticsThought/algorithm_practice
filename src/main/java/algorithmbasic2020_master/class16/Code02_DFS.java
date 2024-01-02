package algorithmbasic2020_master.class16;

import java.util.HashSet;
import java.util.Stack;

public class Code02_DFS {

    public static void dfs(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        //set能避免重复 因为某一个节点可以重复进队列 但是不能重复打印
        HashSet<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        //进栈的时候打印
        System.out.println(node.value);
        /**
         * DFS就是一路走到通 走到底了 么就会回到上一个节点 看看还有没有直接邻居节点没有被访问
         * 没有被访问就 向没有访问的邻居走 没有访问的邻居有多个 就随机选一个走
         * 没有访问的邻居走到低底了 回上一个节点 看看还有没有直接邻居节点没有被访问
         * 如果没有 就一直向上回 直到DFS结束
         * */
        while (!stack.isEmpty()) {
            Node cur = stack.pop();//弹出栈顶
            for (Node next : cur.nexts) {//遍历栈顶节点的直接邻居
                if (!set.contains(next)) {//如果set里面没有 这一轮遍历到的直接邻居
                    stack.push(cur);//把刚刚弹出的栈顶节点 压入栈
                    stack.push(next);//把栈顶节点的直接邻居压入栈
                    set.add(next);//把这个直接邻居放入到set里面  避免重复打印处理
                    System.out.println(next.value);//进栈的时候打印
                    break;
                }
            }
        }
    }


}
