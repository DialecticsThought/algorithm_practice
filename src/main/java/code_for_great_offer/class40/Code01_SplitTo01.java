package code_for_great_offer.class40;

import java.util.ArrayList;
import java.util.HashMap;

// 腾讯
//
/*
 *TODO
 * 分裂问题
 * 一个数n，可以分裂成一个数组[n/2, n%2, n/2] ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
 * 这个数组中哪个数不是1或者0，就继续分裂下去
 * 比如 n = 5，一开始分裂成[2, 1, 2]
 * [2, 1, 2]这个数组中不是1或者0的数，会继续分裂下去，比如两个2就继续分裂
 * [2, 1, 2] -> [1, 0, 1,   1,   1, 0, 1]
 * 那么我们说，5最后分裂成[1, 0, 1, 1, 1, 0, 1]
 * 如果 L=4 R=7 查询L~R上有几个1？ 1
 * 每一个数都可以这么分裂，在最终分裂的数组中，假设下标从1开始
 * 给定三个数n、l、r，返回n的最终分裂数组里[l,r]范围上有几个1
 * n <= 2 ^ 50，n是long类型
 * r - l <= 50000，l和r是int类型
 * 我们的课加个码:
 * n是long类型随意多大都行
 *  l和r也是long类型随意多大都行，但要保证l<=r
 * */
public class Code01_SplitTo01 {

    /*
     *TODO 前置
     *  一个数n，可以分裂成一个数组[n/2, n%2, n/2]
     * 如果 n/2裂变出的数组长度是a 那么 n裂变出的数组的长度 就是2a+1
     *TODO
     * 假设 n这个数的一半 n/2 ，n/2生成的数组长度是50的话
     * 那么n这个数对应的数组长度就是101
     * 知道了n的长度 就可以对在L~R范围上找出多少个1 这个任务做出分解 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
     * 如果要求 n这个数对应的数组的23~74范围上有几个1 那么可以用二分 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
     * 意思就是 因为 n 生成数组 [n/2, n%2, n/2]
     * 即 101 生成数组 [50, 50%2, 50]
     * 只要知道 左边这个50会生成几个1 那么右边的50也就是知道了 因为是相同的数
     * 那么50 有几个1 就要看对应的数组 [25, 25%2, 25]
     * 只要知道 左边这个25会生成几个1 那么右边的25也就是知道了 因为是相同的数
     * ....
     * 但是 要求的是23~74范围 也就是
     * 只要知道 左边这个50的23~50范围 会生成几个1
     * +
     * 只要知道 右边这个50的51~74范围 会生成几个1
     * */
    public static long len(long n, HashMap<Long, Long> lenMap) {
        //TODO base case 如果n=1或0 说明不再裂变
        if (n == 1 || n == 0) {
            lenMap.put(n, 1L);
            return 1;
        } else {//TODO 需要裂变
            //TODO n > 1 的话 对于n这个数的一半 即n/2,它的数组长度是len(n / 2, lenMap)
            long half = len(n / 2, lenMap);
            //TODO 这个n对应的数组的总长度就是 half*2+1
            long all = half * 2 + 1;
            lenMap.put(n, all);
            return all;
        }
    }



    /*
     *TODO  优化的后的方法2
     * n = 100
     * n = 100, 最终裂变的数组中，一共有几个1
     * n = 50, 最终裂变的数组，一共有几个1
     * n = 25, 最终裂变的数组，一共有几个1
     * ..
     * n = 1 ,最终裂变的数组，一共有几个1
     * 请都填写到onesMap中去！
     * 直到了2件事
     * n的数组长度 n/2的数组长度 n/4的数组长度 n/8的数组长度...
     * 都知道了
     * n的数组几个1 n/2的数组几个1 n/4的数组几个1 n/8的数组几个1...
     * 都知道了
     *TODO
     * n=31 有个多叉树
     * n                                31
     * n/2                15                              15
     * n/4        7       1       7               7       1       7
     * n/8   3    1    3     3    1   3      3    1    3     3    1   3
     *     1 0 1     1 0 1
     * 如果任务是 2~30范围多少个1求出来
     * 那么这个任务即要从31节点 向左发 也向右发 中间不用下发
     * 从左侧15节点 向左发 也向右发
     * 但是左侧节点15的右孩子节点7 可以不用向下发送 类似线段树 对于这个7节点先揽住这个任务
     * 因为之前求出了 n/4的数组有几个1  直接向上返回
     * 左侧节点15的左孩子节点7 继续下发
     * 右侧15节点的左孩子节点7 可以不用向下发送  因为之前求出了 n/4的数组有几个1  直接向上返回
     */
    public static long ones(long n, HashMap<Long, Long> lenMap) {
        //TODO base case 如果n=1或0 说明不再裂变
        if (n == 1 || n == 0) {
            lenMap.put(n, n);
            return n;
        } else {//TODO 需要裂变
            //TODO n > 1 的话 对于n这个数的一半 即n/2,它的数组长度是len(n / 2, lenMap)
            long half = ones(n / 2, lenMap);
            long mid = n % 2 == 1 ? 1 : 0;
            //TODO 这个n对应的数组的总长度就是 half*2+1
            long all = half * 2 + mid;
            lenMap.put(n, all);
            return all;
        }
    }


    /*
     *TODO 方法1
     * 相求 n最终裂变的数组里 从 l到r范围有几个1
     * 下标从1开始 根据题目要求
     * l~r一定是有效的范围
     * */
    public static long nums1(long n, long l, long r) {
        /*
         *TODO 为什么求n的长度 不算出n对应完整数组 也就可以多少个1 就是看这个basecase
         * 当递归函数 来到 n=1/n=0的时候 说明 被划分到了极点
         * */
        if (n == 1 || n == 0) {
            return n == 1 ? 1 : 0;
        }
        //当前 n/2的对应的数组的长度
        long half = size(n / 2);
        /*
         *TODO
         * 左侧有几个1被包含进了 [L~R]范围
         * 判断 是否当前n的对应的左侧数组(即 n/2 对应的数组 )的右边界（half） > L
         * 假设 n/2对应上数组的长度 是 1~100 正好 求的是 34~69
         * 34 < 100 && 69 < 100  此时 左侧数组的右边界（half） > r
         * 那么 直接在n/2对应上数组（左侧）查在34~69范围有几个1
         * 如果 求的是 64~154 此时 左侧数组的右边界（half） < r
         * 那么 还要在n/2对应上数组（左侧）查在64~100范围有几个1(这里只讨论 左侧 ，所以不写了)
         * 所以这是 l ~ half 还是 l ~ r 之间做选择
         * */
        long left = l > half ? 0 : nums1(n / 2, l, Math.min(half, r));
        /*
         *TODO
         * mid变量表示 对于 中间有几个1 被包含进了 [L~R]范围
         * 因为 n对应的数组长度 是 n/2对应的数组长度（左侧） + 1 + n/2对应的数组长度（右侧）
         * 所以计算 mid（也就是中间的那个 1） 是否被 L~R覆盖到
         * 没有被覆盖到 只有 R < 中间的那个1的下标 或 L > 中间的那个1的下标
         * 如果覆盖到 那么判断 n % 2 == 1 如果是0 就是没有1
         * */
        long mid = (l > half + 1 || r < half + 1) ? 0 : (n % 2 == 1 ? 1 : 0);
        /*
         *TODO
         * 右侧有几个1被包含进了 [L~R]范围
         * 判断 是否当前n的对应的右侧数组(即 n/2 对应的数组 )的左边界(即half + 1) < R
         * 假设 n/2对应上数组的长度 是 1~100
         * 求的是 64~156 说明要在左侧数组（1~100） 和 右侧数组（102~201）共同选择 但是 这里只考虑右侧的数组
         * 也就是说 求右侧的数组中 1（102-101） ~ 55（156~101）范围中有多少个1
         * 求的是 130~156 这个情况只考虑右侧的数组
         * 也就是说 求右侧的数组中 29（130-101） ~ 55（156~101）范围中有多少个1
         * */
        long right = r > half + 1 ? nums1(n / 2, Math.max(l - half - 1, 1), r - half - 1) : 0;
        return left + mid + right;
    }

    //TODO 求出这个数 n的长度
    public static long size(long n) {
        if (n == 1 || n == 0) {
            return 1;
        } else {
            long half = size(n / 2);
            return (half << 1) + 1;
        }
    }

    public static long nums2(long n, long l, long r) {
        HashMap<Long, Long> allMap = new HashMap<>();
        return dp(n, l, r, allMap);
    }

    /*
     *
     *
     * */
    public static long dp(long n, long l, long r, HashMap<Long, Long> allMap) {
        if (n == 1 || n == 0) {
            return n == 1 ? 1 : 0;
        }
        long half = size(n / 2);
        long all = (half << 1) + 1;
        long mid = n & 1;
        if (l == 1 && r >= all) {
            if (allMap.containsKey(n)) {
                return allMap.get(n);
            } else {
                long count = dp(n / 2, 1, half, allMap);
                long ans = (count << 1) + mid;
                allMap.put(n, ans);
                return ans;
            }
        } else {
            mid = (l > half + 1 || r < half + 1) ? 0 : mid;
            long left = l > half ? 0 : dp(n / 2, l, Math.min(half, r), allMap);
            long right = r > half + 1 ? dp(n / 2, Math.max(l - half - 1, 1), r - half - 1, allMap) : 0;
            return left + mid + right;
        }
    }

    /*
     *TODO
     * 为了测试
     * 彻底生成n的最终分裂数组返回
     * */
    public static ArrayList<Integer> test(long n) {
        ArrayList<Integer> arr = new ArrayList<>();
        process(n, arr);
        return arr;
    }

    public static void process(long n, ArrayList<Integer> arr) {
        if (n == 1 || n == 0) {
            arr.add((int) n);
        } else {
            process(n / 2, arr);
            arr.add((int) (n % 2));
            process(n / 2, arr);
        }
    }

    public static void main(String[] args) {
        long num = 671;
        ArrayList<Integer> ans = test(num);
        int testTime = 10000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * ans.size()) + 1;
            int b = (int) (Math.random() * ans.size()) + 1;
            int l = Math.min(a, b);
            int r = Math.max(a, b);
            int ans1 = 0;
            for (int j = l - 1; j < r; j++) {
                if (ans.get(j) == 1) {
                    ans1++;
                }
            }
            long ans2 = nums1(num, l, r);
            long ans3 = nums2(num, l, r);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("出错了!");
            }
        }
        System.out.println("功能测试结束");
        System.out.println("==============");

        System.out.println("性能测试开始");
        num = (2L << 50) + 22517998136L;
        long l = 30000L;
        long r = 800000200L;
        long start;
        long end;
        start = System.currentTimeMillis();
        System.out.println("nums1结果 : " + nums1(num, l, r));
        end = System.currentTimeMillis();
        System.out.println("nums1花费时间(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        System.out.println("nums2结果 : " + nums2(num, l, r));
        end = System.currentTimeMillis();
        System.out.println("nums2花费时间(毫秒) : " + (end - start));
        System.out.println("性能测试结束");
        System.out.println("==============");

        System.out.println("单独展示nums2方法强悍程度测试开始");
        num = (2L << 55) + 22517998136L;
        l = 30000L;
        r = 6431000002000L;
        start = System.currentTimeMillis();
        System.out.println("nums2结果 : " + nums2(num, l, r));
        end = System.currentTimeMillis();
        System.out.println("nums2花费时间(毫秒) : " + (end - start));
        System.out.println("单独展示nums2方法强悍程度测试结束");
        System.out.println("==============");

    }

}
