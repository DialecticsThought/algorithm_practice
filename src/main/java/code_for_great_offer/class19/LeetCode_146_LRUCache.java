package code_for_great_offer.class19;

import java.util.HashMap;

/**
 * 设计LRU缓存结构， 该结构在构造时确定大小， 假设大小为K， 并有如下两个功能。
 * set(key,value):将记录(key,value)插入该结构。
 * get(key):返回key对应的value值。
 * [要求]
 * 1.set和get方法的时间复杂度为O(1)
 * 2.某个key的set或get操作一旦发生， 认为这个key的记录成了最常使用的
 * 3.当缓存的大小超过K时， 移除最不经常使用的记录， 即set或get最久远的
 * [举例]
 * 假设缓存结构的实例是cache， 大小为3， 并依次发生如下行为
 * 1.cache.set("A",1)。 最常使用的记录为("A",1)
 * 2.cache.set("B",2)。 最常使用的记录为("B",2)， ("A",1)变为最不常使用的
 * 3.cache.set("C",3)。 最常使用的记录为("C",2)， ("A",1)还是最不常使用的
 * 4.cache.get("A")。   最常使用的记录为("A",1)， ("B",2)变为最不常使用的
 * 5.cache.set("D",4)。 大小超过了3， 所以移除此时最不常使用的记录("B",2)， 加入记录
 * ("D",4)， 并且为最常使用的记录， 然后("C",2)变为最不常使用的记录。
 * 本题测试链接 : https://leetcode.cn/problems/lru-cache/
 * 提交时把类名和构造方法名改成 : LRUCache
 * TODO
 * eg1:
 * 假设 有个cache 最多缓存3条信息
 * 有条信息 key=7 val=3 时间点1
 * <<7 3> 1>
 * 有条信息 key=6 val=1000 时间点2
 * <<7 3> 1>
 * <<6 100> 2>
 * 有条信息 key=4 val=60 时间点3
 * <<7 3> 1>
 * <<6 100> 2>
 * <<4 60> 3>
 * 有条信息 key=8 val=16 时间点4
 * <<6 100> 2>
 * <<4 60> 3>
 * <<8 16> 4>
 * eg2：
 * 有一个双向链表 一个hashmap
 * 1.有条信息 key=A val=3 时间点1 执行add()
 * 生成Info <A,3> 此时这个Info是链表的第一个节点
 * hashmap也存了 <A,<A,3>>
 *  链表：
 *      <A,3>
 *  hashmap:
 *      <A,<A,3>>
 * 2.有条信息 key=B val=7 时间点2 执行add()
 *  链表：
 *      <A,3> -> <B,7> -> null
 *  hashmap:
 *      <A,<A,3>> <B,<B,7>>
 * 3.有条信息 key=C val=17 时间点3  执行add()
 *  链表：
 *      <A,3> -> <B,7> -> <C,17> -> null
 *  hashmap:
 *      <A,<A,3>> <B,<B,7>> <C,<C,17>>
 * 4.有条信息 key=B val=23 时间点4  执行update() B的info 放在了链表的最后面  B的entry放在map的最后
 *  链表：
 *      <A,3> -> <C,17> -> <B,23>  -> null
 *  hashmap:
 *      <A,<A,3>>  <C,<C,17>>  <B,<B,23>>
 * 5.执行get方法 得到A  A的info 放在了链表的最后面  A的entry放在map的最后
 *  链表：
 *      <C,17> -> <B,23> ->  <A,3>  -> null
 *  hashmap:
 *      <C,<C,17>> <B,<B,23>> <A,<A,3>>
 * 6.有条信息 key=D val=15 时间点6  执行add()
 *  链表：
 *      <B,23> -> <A,3> -> <D,15> -> null
 *  hashmap:
 *      <B,<B,23>> <A,<A,3>> <D,<D,15>>
 * ......
 */
public class LeetCode_146_LRUCache {

    public LeetCode_146_LRUCache(int capacity) {
        cache = new MyCache<>(capacity);
    }

    private MyCache<Integer, Integer> cache;

    public int get(int key) {
        Integer ans = cache.get(key);
        return ans == null ? -1 : ans;
    }

    public void put(int key, int value) {
        cache.set(key, value);
    }

    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> pre;//上一个节点
        public Node<K, V> next;//下一个节点

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class NodeDoubleLinkedList<K, V> {
        private Node<K, V> head;//双向链表的头结点
        private Node<K, V> tail;//双向链表的为节点

        public NodeDoubleLinkedList() {
            head = null;
            tail = null;
        }

        // 现在来了一个新的node，请挂到尾巴上去
        public void addNode(Node<K, V> newNode) {
            if (newNode == null) {
                return;
            }
            //双向链表里面一个节点也没有
            if (head == null) {
                //头和尾都指向第一个进入的指针
                head = newNode;
                tail = newNode;
            } else {//双向链表里面有节点
                tail.next = newNode;
                newNode.pre = tail;
                tail = newNode;
            }
        }

        /**
         * TODO
         * node 入参，一定保证！node在双向链表里！
         * node原始的位置，左右重新连好，然后把node分离出来
         * 挂到整个链表的尾巴上
         */
        public void moveNodeToTail(Node<K, V> node) {
            if (tail == node) {
                return;
            }
            if (head == node) {//当前node是老的头部
                head = node.next;
                head.pre = null;
            } else {//当前node是中间一个节点（也就是非头结点）
                node.pre.next = node.next;
                node.next.pre = node.pre;
            }
            node.pre = tail;//新的最后一个节点 的pre指向之前的最后一个节点
            node.next = null;
            tail.next = node;
            tail = node;
        }

        public Node<K, V> removeHead() {
            if (head == null) {
                return null;
            }
            Node<K, V> res = head;
            if (head == tail) {//就一个节点
                head = null;
                tail = null;
            } else {//不只有一个节点
                head = res.next;//head指针 移到要删除的节点的下一个节点上
                res.next = null;//断掉要删节点的联系
                head.pre = null;
            }
            return res;
        }

    }

    public static class MyCache<K, V> {
        private HashMap<K, Node<K, V>> keyNodeMap;//key对应的数据区域
        private NodeDoubleLinkedList<K, V> nodeList;//记录key的相对次序
        private final int capacity;//黑盒的大小

        public MyCache(int cap) {
            keyNodeMap = new HashMap<K, Node<K, V>>();
            nodeList = new NodeDoubleLinkedList<K, V>();
            capacity = cap;
        }

        public V get(K key) {
            if (keyNodeMap.containsKey(key)) {//是否包含这个key
                Node<K, V> res = keyNodeMap.get(key);
                nodeList.moveNodeToTail(res);//把当前访问的节点 放到链表最后
                return res.value;
            }
            return null;
        }

        /**
         * TODO
         * set(Key, Value)
         * 新增  更新value的操作
         */
        public void set(K key, V value) {
            if (keyNodeMap.containsKey(key)) {
                Node<K, V> node = keyNodeMap.get(key);
                node.value = value;
                nodeList.moveNodeToTail(node);
            } else { // 新增！
                //创建一个新节点
                Node<K, V> newNode = new Node<>(key, value);
                keyNodeMap.put(key, newNode);//放入到map
                nodeList.addNode(newNode);
                if (keyNodeMap.size() == capacity + 1) {//容量超过了 就是LRU
                    removeMostUnusedCache();
                }
            }
        }

        private void removeMostUnusedCache() {
            Node<K, V> removeNode = nodeList.removeHead();
            keyNodeMap.remove(removeNode.key);
        }

    }

}
