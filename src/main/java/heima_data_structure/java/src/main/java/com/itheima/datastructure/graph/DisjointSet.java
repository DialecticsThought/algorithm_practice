package heima_data_structure.java.src.main.java.com.itheima.datastructure.graph;

import java.util.Arrays;

/**
 * <h3>不相交集合（并查集合）</h3>
 */
public class DisjointSet {
    int[] s;

    /**
     * 索引对应顶点
     * 元素是用来表示与之有关系的顶点
     * <p>
     * 索引  0  1  2  3  4  5  6
     * 元素 [0, 1, 2, 3, 4, 5, 6] 表示一开始顶点直接没有联系（只与自己有联系）
     *
     * @param size
     */
    public DisjointSet(int size) {
        s = new int[size];
        for (int i = 0; i < size; i++) {
            s[i] = i;
        }
    }

    /**
     * find 是找到老大
     * 本质就是找到树的根节点
     * 根节点的特征: 节点x的对应的数组s中存的就是节点x自己
     * 如果x不是根节点,节点x的对应的数组s中存的就是节点x的父节点
     * <p>
     * 优化1：路径压缩
     * 现在是让这个属性结构扁平化 子节点指向的不是父节点 而是根节点 除非父节点是根节点
     */
    public int find(int x) {
        // base case
        if (x == s[x]) {//节点对应的索引的存储单元就是自己
            return x;
        }
        //return find(s[x]);
        // 路径压缩的话 要把非根节点的节点对应的索引存储单元全部写成根节点的索引
        int i = find(s[x]);

        s[x] = i;

        return i;
    }

    /**
     * union 是让两个集合“相交”，即选出新老大，
     * x所在的集合 和 y所在的集合 合并在一起
     * 本质: x所在的集合的根节点 对应的索引 位置 存放着  x所在的集合的根节点的索引 ，也可以反着来
     *
     * @param x
     * @param y
     */
    public void union(int x, int y) {
        //也可以 s[x] = y
        s[y] = x;
    }

    @Override
    public String toString() {
        return Arrays.toString(s);
    }

    public static void main(String[] args) {
        DisjointSet set = new DisjointSet(7);
        System.out.println(set);
        // 1. 演示 Kruskal 算法执行流程，下面是边按权重排序顺序
        /*
            0 <--> 3
            5 <--> 6
            0 <--> 1
            2 <--> 3
            1 <--> 3
            0 <--> 2
            3 <--> 6
            2 <--> 5
            4 <--> 6
            3 <--> 4
            3 <--> 5
            1 <--> 4
        */
        /*
        int x = set.find(0);
        int y = set.find(3);
        System.out.println("老大分别是：" + x + " " + y);
        if (x != y) {
            set.union(x, y);
            System.out.println(set);
        }

        x = set.find(5);
        y = set.find(6);
        System.out.println("老大分别是：" + x + " " + y);
        if (x != y) {
            set.union(x, y);
            System.out.println(set);
        }

        x = set.find(0);
        y = set.find(1);
        System.out.println("老大分别是：" + x + " " + y);
        if (x != y) {
            set.union(x, y);
            System.out.println(set);
        }

        x = set.find(2);
        y = set.find(3);
        System.out.println("老大分别是：" + x + " " + y);
        if (x != y) {
            set.union(x, y);
            System.out.println(set);
        }

        x = set.find(1);
        y = set.find(3);
        System.out.println("老大分别是：" + x + " " + y);
        if (x != y) {
            set.union(x, y);
            System.out.println(set);
        }

        x = set.find(0);
        y = set.find(2);
        System.out.println("老大分别是：" + x + " " + y);
        if (x != y) {
            set.union(x, y);
            System.out.println(set);
        }

        x = set.find(3);
        y = set.find(6);
        System.out.println("老大分别是：" + x + " " + y);
        if (x != y) {
            set.union(x, y);
            System.out.println(set);
        }

        x = set.find(2);
        y = set.find(5);
        System.out.println("老大分别是：" + x + " " + y);
        if (x != y) {
            set.union(x, y);
            System.out.println(set);
        }

        x = set.find(4);
        y = set.find(6);
        System.out.println("老大分别是：" + x + " " + y);
        if (x != y) {
            set.union(x, y);
            System.out.println(set);
        }*/

        // 2. 演示最糟情况(实际不可能出现,仅为演示)
        set.union(0, 1);
        set.union(1, 2);
        set.union(2, 3);
        set.union(3, 4);
        set.union(4, 5);
        set.union(5, 6);
        System.out.println(set);
        set.find(6);
        System.out.println(set);
    }


}
