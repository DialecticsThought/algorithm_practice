package algorithmbasic2020_master.class36;

import java.util.ArrayList;

public class Code02_SkipListMap {

    // 跳表的节点定义
    public static class SkipListNode<K extends Comparable<K>, V> {
        public K key;
        public V val;

        public ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K k, V v) {
            key = k;
            val = v;
            nextNodes = new ArrayList<SkipListNode<K, V>>();
        }


        /**
         * 遍历的时候，如果是往右遍历到的null(next == null), 遍历结束
         * 头(null), 头节点的null，认为最小
         * node  -> 头，node(null, "")  node.isKeyLess(!null)  true
         * node里面的key是否比otherKey小，true，不是false
         *
         * @param otherKey
         * @return
         */
        public boolean isKeyLess(K otherKey) {
            //  otherKey == null -> false
            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
        }

        public boolean isKeyEqual(K otherKey) {
            return (key == null && otherKey == null)
                    || (key != null && otherKey != null && key.compareTo(otherKey) == 0);
        }

    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        private static final double PROBABILITY = 0.5; // < 0.5 继续做，>=0.5 停
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel;//TODO 最大高度

        public SkipListMap() {
            head = new SkipListNode<K, V>(null, null);
            head.nextNodes.add(null); //TODO 0 初始情况 有第0层链表 并且该链表指向null
            size = 0;
            maxLevel = 0;
        }

        /**
         * TODO 从最高层开始，一路找下去，
         * 最终，找到第0层的<key的最右的节点
         */
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) { // 从上层跳下层
                /**
                 *  TODO cur  level  -> level-1
                 *   在遍历到的这一层里 <= key 最右节点 之后
                 *   下一层开始 继续 从 上一层得到 <= key 最右节点 开始 向右找 下一层 <= key 最右节点
                 *   直到 层数为0
                 * */
                cur = mostRightLessNodeInLevel(key, cur, level--);
            }
            return cur;
        }

        /**
         * TODO 在level层里，如何往右移动
         * 现在来到的节点是cur，来到了cur的level层，在level层上，找到 <=key 最后一个节点并返回
         */
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key,
                                                            SkipListNode<K, V> cur,
                                                            int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);//TODO 不停地往有走
            }
            return cur;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key);
        }

        // 新增、改value
        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            /**
             * 0层上，最右一个，< key 的Node -> >key
             *  因为是最底层的 最右一个，< key 的Node  这个node 叫做 less
             *  每个key 在最底层都有值
             *  只要检查 less 的右侧 一个节点 （该节点名字叫做 find） 是不是等于 key
             *  等于key  说明之前加入过了 修改
             *  不等于key 追加
             * */
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> find = less.nextNodes.get(0);
            if (find != null && find.isKeyEqual(key)) {
                find.val = value;
            } else { // find == null   8   7   9
                size++;
                int newNodeLevel = 0;
                //TODO 这是 随机 得出新增节点的层数
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }
                // newNodeLevel
                while (newNodeLevel > maxLevel) {//新增节点的层数 > 原有跳表的最高层
                    /*
                     * TODO 不断增加 链表 这个链表 初始指向null
                     *   直到 层数 == newNodeLevel
                     * */
                    head.nextNodes.add(null);
                    maxLevel++;
                }
                SkipListNode<K, V> newNode = new SkipListNode<K, V>(key, value);
                for (int i = 0; i <= newNodeLevel; i++) {
                    newNode.nextNodes.add(null);
                }
                /**
                 * TODO 从最高层开始 最左节点开始
                 * */
                int level = maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    // 在本 level 层中，找到最右的 < key 的节点
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    if (level <= newNodeLevel) {//TODO 只有当前遍历到的level<= 新增节点的最高层
                        // 在level层 pre的原有右侧 变成 新增节点的右侧
                        newNode.nextNodes.set(level, pre.nextNodes.get(level));
                        // 在level层 把节点 放在pre的右侧
                        pre.nextNodes.set(level, newNode);
                    }
                    level--;
                }
            }
        }

        /**
         * TODO 从最高层 开始找 找到该层的最右边节点
         *  再到该节点的下一层 往右走 再次找到 下一层的最右边节点
         *  不断循环
         */
        public V get(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.val : null;
        }

        public void remove(K key) {
            if (containsKey(key)) {
                size--;
                /**
                 * TODO 从最高层 初始节点开始找
                 * */
                int level = maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    SkipListNode<K, V> next = pre.nextNodes.get(level);
                    /**
                     *TODO
                     * 1）在这一层中，pre下一个就是key
                     * 2）在这一层中，pre的下一个key是>要删除key
                     *
                     * */
                    if (next != null && next.isKeyEqual(key)) {
                        /*
                         * free delete node memory -> C++
                         * level : pre -> next(key) -> ...
                         * TODO 被删除节点的 前一个节点 指向 被删除节点的后一个节点
                         * */
                        pre.nextNodes.set(level, next.nextNodes.get(level));
                    }
                    /**
                     * TODO 在level层只有一个节点了，就是默认节点head
                     *  如果被删节点在最高层 并且只有这一个节点在最高层 这个节点所在层也要删除
                     * */
                    if (level != 0 && pre == head && pre.nextNodes.get(level) == null) {
                        head.nextNodes.remove(level);
                        maxLevel--;
                    }
                    level--;
                }
            }
        }

        public K firstKey() {
            return head.nextNodes.get(0) != null ? head.nextNodes.get(0).key : null;
        }

        public K lastKey() {
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                while (next != null) {
                    cur = next;
                    next = cur.nextNodes.get(level);
                }
                level--;
            }
            return cur.key;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null ? next.key : null;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.key : less.key;
        }

        public int size() {
            return size;
        }

    }

    // for test
    public static void printAll(SkipListMap<String, String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            SkipListNode<String, String> cur = obj.head;
            while (cur.nextNodes.get(i) != null) {
                SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.key + " , " + next.val + ") ");
                cur = next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipListMap<String, String> test = new SkipListMap<>();
        printAll(test);
        System.out.println("======================");
        test.put("A", "10");
        printAll(test);
        System.out.println("======================");
        test.remove("A");
        printAll(test);
        System.out.println("======================");
        test.put("E", "E");
        test.put("B", "B");
        test.put("A", "A");
        test.put("F", "F");
        test.put("C", "C");
        test.put("D", "D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.containsKey("B"));
        System.out.println(test.containsKey("Z"));
        System.out.println(test.firstKey());
        System.out.println(test.lastKey());
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
        System.out.println("======================");
        test.remove("D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));


    }

}
