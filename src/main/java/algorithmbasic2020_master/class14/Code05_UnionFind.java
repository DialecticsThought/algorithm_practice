package algorithmbasic2020_master.class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code05_UnionFind {
    /*
     * 1)有若干个样本a、b、c、d…类型假设是V
     * 2)在并查集中一开始认为每个样本都在单独的集合里
     * 3)用户可以在任何时候调用如下两个方法:
     * boolean isSameSet(V x, V y):查询样本x和样本y是否属于一个集合
     * void union(V x,Vy):把x和y各自所在集合的所有样本合并成一个集合
     * 4) isSameSet和union方法的代价越低越好
     * */
    public static class Node<V> {
        V value;

        public Node(V v) {
            value = v;
        }
    }
    public static class UnionFind<V> {
        //拿一个value 去查对应的节点  这个表不会有改动
        public HashMap<V, Node<V>> nodes;
        /**
         * 把一个节点和该节点对应的父亲节点 放在一个map中
         * eg a指向a自己 b指向a c指向a  那么 有 <a,a> <b,a>  <c,a>
         * */
        public HashMap<Node<V>, Node<V>> parents;
        /**
         * 只有一个点 它是某个集合的代表点的情况下 才会放入这个map中
         * eg a指向a自己 b指向a c指向a a就是代表点  有一个记录 <a,3> 和 b与c没有关系
         * 也就是说这个map存放的是 代表点 和 代表点所对应的集合的大小
         * */
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionFind(List<V> values) {
            // 传入一个list列表 列表中的每一个元素都是value
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V value : values) {
                Node<V> node = new Node<>(value);// 针对value创建对应的节点
                nodes.put(value, node);// 把value和对应的节点放入map中
                // 因为一开始 每个样本所在的集合里面只有样本自己一个 所以 node的parent是node本身
                parents.put(node, node);
                // 每个点一开始都是代表点
                sizeMap.put(node, 1);
            }
        }
        // 给你一个节点value，请你从value对应的node往上到不能再往上，把代表点返回
        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            /*
            * 如果cur节点不等于自己的父节点 说明还能向上找
            * */
            while (cur != parents.get(cur)) {
                path.push(cur);//在找到最顶端的父节点的过程中，把每一个遍历到的cur都存到栈里面
                cur = parents.get(cur);//让cur的父节点代替掉cur 属于一种递归
            }
            /*
            * 上衣循环结束的时候 cur一定指向最顶端的父节点
            * */
            while (!path.isEmpty()) {
                /*
                * eg x指向a b指向b b指向c  c指向d d指向d自己 d是代表点
                * parent这个map中存放 <x,a> <a,b> <b,c> <c,d> <d,d>
                * 最终在这个while循环中变成 <x,d> <a,d> <b,d> <c,d>  实现扁平化
                * */
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        /*
         * 检查a样本和b样本是否是同一个集合
         * */
        public boolean isSameSet(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {//nodes这个map里面没有这两个节点 直接返回false
                return false;
            }
            /*
            * 判断a的代表点和b的代表点是否相同 相同的话 就是同一个集合
            * eg a指向a自己 b指向a c指向a  那么 有 <a,a> <b,a>  <c,a>
            * b和c都是 代表点 a
            * */
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            /**
            * 表示如果a的代表点和b的代表点都是同一个的话 就是一个集合
            * 只有不同才要union
            * */
            if (aHead != bHead) {
                /**
                * 得到 a和b 代表点所对应的集合的大小
                * */
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                /**
                * 两个集合 让小集合的代表点 由代表点指向自己 转到 指向大集合的代表点
                * eg: 集合1 <a,a> <b,a>  <c,a>  集合2 <d,d> <e,d>
                * 集合2和集合1合并  d指向a就可以了
                * 让big指针指向大的集合
                * */
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                /*
                * 如果 big指针指向大的集合 那么就让small指针指向小的集合
                * */
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);//修改原先两个集合中的大的那一个集合的大小
                sizeMap.remove(small);//删掉小的那个集合 因为被合并了
            }
        }

        public int sets() {
            return sizeMap.size();
        }

    }
}
