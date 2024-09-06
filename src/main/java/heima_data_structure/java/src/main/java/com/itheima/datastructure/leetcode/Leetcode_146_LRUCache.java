package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.HashMap;

/**
 * <h3>设计 LRU 缓存</h3>
 */
public class Leetcode_146_LRUCache {

    static class LRUCache {

        static class Node {
            Node prev;
            Node next;
            int key;
            int value;

            public Node() {
            }

            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        static class DoublyLinkedList {
            Node head;
            Node tail;

            public DoublyLinkedList() {
                head = tail = new Node();
                head.next = tail;
                tail.prev = head;
            }

            // 头部添加     head<->tail
            public void addFirst(Node newFirst) { // O(1)
                Node oldFirst = head.next;
                newFirst.prev = head;
                newFirst.next = oldFirst;
                head.next = newFirst;
                oldFirst.prev = newFirst;
            }

            // 已知节点删除
            public void remove(Node node) { // O(1)
                Node prev = node.prev;
                Node next = node.next;
                prev.next = next;
                next.prev = prev;
            }

            // 尾部删除
            public Node removeLast() { // O(1)
                Node last = tail.prev;
                remove(last);
                return last;
            }
        }

        private final HashMap<Integer, Node> map = new HashMap<>();
        private final DoublyLinkedList list = new DoublyLinkedList();
        private int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) { // O(1)
            if (!map.containsKey(key)) {
                return -1;
            }
            Node node = map.get(key);
            list.remove(node);
            list.addFirst(node);
            return node.value;
        }

        public void put(int key, int value) { // O(1)
            if(map.containsKey(key)) { // 更新
                Node node = map.get(key);
                node.value = value;
                list.remove(node);
                list.addFirst(node);
            } else { // 新增
                Node node = new Node(key, value);
                map.put(key, node);
                list.addFirst(node);
                if (map.size() > capacity) {
                    Node removed = list.removeLast();
                    map.remove(removed.key);
                }
            }
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1)); // 1
        cache.put(3, 3);
        System.out.println(cache.get(2)); // -1
        cache.put(4, 4);
        System.out.println(cache.get(1)); // -1
        System.out.println(cache.get(3)); // 3
    }
}
