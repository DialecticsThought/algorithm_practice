package algorithmbasic2020_master.class32;

/**
 * TODO
 * https://www.bilibili.com/video/BV1rE411a7x9/
 * 有一个数组
 * arr = [a,b,c,d,e,f,g,h,i,j,k,l,m,n]
 * [1,10,11,100,101,110,111,1000,1001,1010,1011,1100,1101,1110]
 * 用lowbit函数求数组下标二进制的非0最低位所表示的值
 * [1,10,1,100,1,10,1,1000,1,10,1,100,1,10]
 * 转成十进制
 * [1,2,1,4,1,2,1,8,1,2,1,4,1,2]
 * 最高是8 说明整棵树 是4层 因为 2^3 ,3+1,
 * 最底层是lowbit=1 倒数第二层lowbit=2 倒数第三层lowbit=4  倒数第四层lowbit=8
 * 设arr[i] 就是a[i]
 * 树的每一个节点是c[i]，这个树用树状数组c表示
 * 也就是c的length和arr的length相同
 * arr = [a,b,c,d,e,f,g,h,i,j,k,l,m,n] 用树状数组表示
 * <pre>
 *                       h
 *              ↙                   ↘
 *          d                         l
 *       ↙     ↘                   ↙      ↘
 *    b           f             j             n
 *  ↙  ↘         ↙  ↘         ↙  ↘
 * a    c       e    g      i      k       m
 * </pre>
 * 对这颗树进行改造 每一个节点的值 c[i] = 原始的值 + 左子节点的值
 * <pre>
 *                      h
 *             ↙                   ↘
 *          d                           l
 *      ↙     ↘                     ↙     ↘
 *   a+b          e+f           i+j            m+n
 *  ↙   ↘        ↙   ↘         ↙   ↘         ↙
 * a     c      e     g      i       k      m
 * </pre>
 * <pre>
 *                      h
 *             ↙                    ↘
 *         a+b+d                       i+j+l
 *        ↙     ↘                     ↙     ↘
 *   a+b          e+f           i+j             m+n
 *   ↙  ↘         ↙  ↘         ↙  ↘           ↙
 * a     c      e     g      i       k      m
 * </pre>
 * 下面是树状数组的最终版本
 * <pre>
 *                   a+b+d+h+i+j+l
 *               ↙                    ↘
 *         a+b+d                       i+j+l
 *        ↙     ↘                     ↙     ↘
 *   a+b          e+f           i+j             m+n
 *   ↙  ↘         ↙  ↘         ↙  ↘           ↙
 * a     c      e     g      i       k      m
 * </pre>
 * 满足c[i]
 * = a[x]  x是奇数
 * = a[1] + a[2] + ... + a[x]  x是2的次方幂
 * 当节点x是左节点：其父节点 是 a[x]+ lowbit(x)
 * 当节点x是右节点：其父节点 是 a[x] - lowbit(x)
 * <p>
 * https://blog.csdn.net/ls2868916989/article/details/119268741
 * 当我们修改A数组中某个值时，应当如何更新C数组呢？
 * 回想一下，区间查询的过程，再看一下上文中列出的过程。
 * 这里声明一下：单点更新实际上是不修改A数组的，而是修改树状数组C，向上更新区间长度为lowbit(i)所代表的节点的值。
 * <pre>
 * eg:
 * a[1]     a[2]     a[3]    a[4]    a[5]    a[6]    a[7]    a[8]
 * 对应的树状数组
 *                                                          c[8]
 *                                   ↙                        ↓
 *                           c[4]                           xxxx
 *                ↙          ↓                    ↙           ↓
 *        c[2]              xxxx            c[6]           xxxx
 *      ↙   ↓            ↙   ↓            ↙   ↓           ↙   ↓
 * c[1]    xxxx     c[3]    xxxx    c[5]    xxxx    c[7]    xxxx
 * </pre>
 * 当在A[1]加上值val，即更新A[1]时，需要向上更新C[1],C[2],C[4],C[8]，
 * 这个时候只需将这4个节点每个节点的值加上val即可。
 * 这里为了方便大家理解，人为添加了个A数组表示每个叶子节点的值，
 * 事实上A数组并不用修改，实际运用中也可不设置A数组，单点更新只需修改树状数组C即可。
 * 下标写成二进制：C[(001)],C[(010)],C[(100)],C[(1000)]；
 * lowbit(1)=1(001) + lowbit(1)=2(010) C[2]+=val；
 * lowbit(2)=2(010) + lowbit(2)=4(100) C[4]+=val；
 * lowbit(4)=4(100) + lowbit(4)=8(1000) C[8]+=val；
 * 由于c[1] c[2] c[4] c[8] 都包含有A[1]，所以在更新A[1]时实际上就是更新每一个包含A[1]的节点。
 * 总结
 * 树状数组的重点就是利用二进制的变化，动态地更新树状数组。
 * <p>
 * 树状数组的每一个节点并不是代表原数组的值，而是包含了原数组多个节点的值。
 * <p>
 * 所以在更新A[1]时需要将所有包含A[1]的C[i]都加上val这也就利用到了二进制的神奇之处。
 * <p>
 * 如果是更新A[i]的值，则每一次对C[i] 中的 i 向上更新，即每次i+=lowbit(i),这样就能C[i] 以及C[i] 的所有父节点都加上val。
 * <p>
 * 反之求区间和也是和更新节点值差不多，只不过每次 i-=lowbit(i)。
 */
public class Code01_IndexTree {

    public static class IndexTree {

        private int[] tree;
        private int N;

        public IndexTree(int size) {
            N = size;
            tree = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            while (index > 0) {
                ret += tree[index];
                index -= index & -index;
            }
            return ret;
        }

        public void add(int index, int d) {
            while (index <= N) {
                tree[index] += d;
                index += index & -index;
            }
        }
    }

    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }

        public int lowbit(int i) {
            return i & -i;//或者是return i-(i&(i-1));表示求数组下标二进制的非0最低位所表示的值
        }
    }

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 2000000;
        IndexTree tree = new IndexTree(N);
        Right test = new Right(N);
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * V);
                tree.add(index, add);
                test.add(index, add);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test finish");
    }

}
