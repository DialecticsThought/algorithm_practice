package heima_data_structure.java.src.main.java.com.itheima.datastructure.graph;

import java.util.Arrays;

/**
 * <h3>不相交集合（并查集合）优化2：union by size</h3>
 */
public class DisjointSetUnionBySize {
    int[] s;
    // union合并的时候 使用到
    int[] size;

    public DisjointSetUnionBySize(int size) {
        s = new int[size];
        this.size = new int[size];
        for (int i = 0; i < size; i++) {
            s[i] = i;
            this.size[i] = 1;
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
     * @param x
     * @return
     */
    public int find(int x) { // x = 2
        if (x == s[x]) {
            return x;
        }
        /**
         *  int i = find(s[x]);
         *  s[x] = i;
         *  return i;
         */
        return s[x] = find(s[x]); // 0    s[2]=0
    }

    /**
     * union 是让两个集合“相交”，即选出新老大，x、y 是原老大索引
     * x所在的集合 和 y所在的集合 合并在一起
     * 本质: x所在的集合的根节点 对应的索引 位置 存放着  x所在的集合的根节点的索引 ，也可以反着来
     * 这里的话 优先让个数少的集合 合并到个数大的集合
     * @param x
     * @param y
     */
    public void union(int x, int y) {
        /*if (size[x] < size[y]) {
            // y 老大  x 小弟
            s[x] = y;
            size[y] = size[x] + size[y]; // 更新老大元素个数
        } else {
            // x 新老大 y 新小弟
            s[y] = x;
            size[x] = size[x] + size[y]; // 更新老大元素个数
        }*/
        // 查看x的集合的个数 是否小于 y的集合的个数
        // 这里的意思是 不管 x < y 还是 y > x 最终都是 x作为老大
        // 前提是 如果  y > x 的话 把 旧 y 看成 新的 x , 旧 x 看成 新的 y 也就是交换
        if (size[x] < size[y]) {
            int t = x;
            x = y;
            y = t;
        }
        s[y] = x;
        size[x] = size[x] + size[y]; // 更新老大元素个数
    }

    @Override
    public String toString() {
        return "内容："+Arrays.toString(s) + "\n大小：" + Arrays.toString(size);
    }

    public static void main(String[] args) {
        DisjointSetUnionBySize set = new DisjointSetUnionBySize(5);

        set.union(1, 2);
        set.union(3, 4);
        set.union(1, 3);
        System.out.println(set);

        set.union(1, 0);
        System.out.println(set);
    }


}
