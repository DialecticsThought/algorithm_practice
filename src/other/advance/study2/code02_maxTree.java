package other.advance.study2;

import java.util.HashMap;
import java.util.Stack;

/**
 * @Author zzz
 * @Date 2021/12/5 21:53
 * @Version 1.0
 */
public class code02_maxTree {
    public static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node() {

        }

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node getMaxTree(int[] arr){
        Node[] nArr = new Node[arr.length];
        for (int i = 0; i !=arr.length; i++) {
            nArr[i] = new Node(arr[i]);
        }
        Stack<Node> stack = new Stack<Node>();
        HashMap<Node,Node> lBigMap = new HashMap<Node,Node>();
        HashMap<Node,Node> rBigMap = new HashMap<Node,Node>();
        for (int i = 0; i != nArr.length; i++) {
            Node curNode = nArr[i];
            while ((!stack.isEmpty()) && stack.peek().value <curNode.value){
                popStackSetMap(stack,lBigMap);
            }
            stack.push(curNode);
        }
        while (!stack.isEmpty()){
            popStackSetMap(stack,lBigMap);
        }
        for (int i = nArr.length -1;i!=-1; i--) {
            Node curNode = nArr[i];
            while ((!stack.isEmpty()) && stack.peek().value<curNode.value){
                popStackSetMap(stack, rBigMap);
            }
            stack.push(curNode);
        }
        while (!stack.isEmpty()){
            popStackSetMap(stack,rBigMap);
        }
        Node head = null;
        for (int i = 0; i != nArr.length; i++) {
            Node curNode = nArr[i];
            Node left = lBigMap.get(curNode);
            Node right = rBigMap.get(curNode);
            if (left == null && right==null){
                head = curNode;
            }else if (left == null){
                if (right.left == null){
                    right.left = curNode;
                }else {
                    right.right = curNode;
                }
            }else if (right == null){
                if (left.left == null){
                    left.left = curNode;
                }else {
                    left.right = curNode;
                }
            }else {
                Node parent = left.value< right.value?left:right;
                if (parent.left == null){
                    parent.left = curNode;
                }else {
                    parent.right = curNode;
                }
            }
        }
        return head;
    }

    public static void popStackSetMap(Stack<Node> stack, HashMap<Node, Node> map) {
        Node popNode = stack.pop();
        if (stack.isEmpty()){
            map.put(popNode,null);
        }else{
            map.put(popNode,stack.peek());
        }
    }

    public static void printPreOrder(Node head){
        if (head == null){
            return;
        }
        System.out.print(head.value+"\t");
        printPreOrder(head.left);
        printPreOrder(head.right);
    }

    public static void printInOrder(Node head){
        if (head == null){
            return;
        }
        printPreOrder(head.left);
        System.out.print(head.value+"\t");
        printPreOrder(head.right);
    }

    public static void main(String[] args) {
        int[] arr = {3,4,5,1,2};
        Node head = getMaxTree(arr);
        printPreOrder(head);
        System.out.println();
        printInOrder(head);
    }
}
