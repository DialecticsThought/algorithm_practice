import org.checkerframework.checker.units.qual.K;

/**
 * @Description
 * @Author veritas
 * @Data 2024/10/8 12:09
 */
public class Test {



    class Node<K,V>{
        K key;
        V value;
        Node<K,V> next;
        Node<K,V> prev;

        public Node(K key, V value, Node<K, V> next, Node<K, V> prev) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    class DoubleLinkedList{
        Node head;
        Node tail;
    }
}
