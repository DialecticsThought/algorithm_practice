import org.checkerframework.checker.units.qual.K;

/**
 * @Description
 * @Author veritas
 * @Data 2024/10/8 12:09
 */
public class Test {


    class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        Node<K, V> prev;

        public Node(K key, V value, Node<K, V> next, Node<K, V> prev) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    class DoubleLinkedList {
        Node head;
        Node tail;

        public DoubleLinkedList() {
            this.head = null;
            this.tail = null;
        }

        public void addNode(Node node) {
            if (node == null) {
                return;
            }
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }
        }

        public void moveNodeToTail(Node node) {

            if (node == null) {
                return;
            }
            if (node == head) {// 如果节点是原始双向链表的头部
                head = head.next;
                head = null;
            } else {// 如果节点不是原始双向链表的头部

            }
            tail.next = node;
            node.prev = tail;
            tail = node;
            node.next = null;
        }
    }
}
