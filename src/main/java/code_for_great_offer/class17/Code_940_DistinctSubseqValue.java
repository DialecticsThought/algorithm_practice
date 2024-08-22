package code_for_great_offer.class17;

import java.util.HashMap;

/**
 * TODO 本题测试链接 : https://leetcode.com/problems/distinct-subsequences-ii/
 * 1.没有重复的字符
 * S: [1,2,3] all:∅
 * 有一个新的集合{} 表示s的char什么都不用的字面值 此时all:{}
 * 遍历到1的时候，之前的新的集合加1 变成新的集合{1}，此时新的all:{},{1}
 * 遍历到2的时候，之前的1的新的集合的每一个元素加2就是新的集合{2},{1,2},，此时新的all:{},{1},{2},{1,2}
 * 遍历到3的时候，之前的2的新的集合的每一个元素加3就是新的集合{3},{1,2,3}{2,3},此时新的all:{},{1},{2},{1,2},{3},{1,2,3},{2,3}
 * 2.有重复的字符
 * S: [1,2,1] all:∅
 * 有一个集合{} 表示s的char什么都不用的字面值 此时all:{}
 * 遍历到1的时候，之前的新all的集合加1 变成新的集合{1}，此时新的all:{},{1}
 * 遍历到2的时候，之前的1的all的集合的每一个元素加2就是新的集合{2},{1,2},，此时新的all:{},{1},{2},{1,2}
 * 遍历到1的时候，之前的2的all的集合的每一个元素加3就是新的集合{1},{1,1}{2,1},{1,2,1}此时新的all:{},{1},{2},{1,1},{2,1},{1,2,1}
 * 这个all有个核心的操作是因为 需要修正 也就是减去重复 因为已经有{1}了,
 * 不能再算进去,要减去上一次同样以某个char的all的个数减去1(表示空集)
 * S: [1,1,1] all:∅
 * 有一个集合{} 表示s的char什么都不用的字面值 此时all:{}
 * 遍历到1的时候，之前的新的集合加1 变成新的集合{1}，此时新的all:{},{1}
 * 遍历到1的时候，之前的1的all的集合的每一个元素加1就是新的集合{1},{1,1}(和上一轮上面的all的元素数量相同),此时新的all:{},{1},{1,1},{1}
 *      要减去上一次以1结尾的除去空集的集合数1,也就是{1} 所以all:{},{1},{1,1}
 * 遍历到1的时候，之前的1的all的集合的每一个元素加1就是新的集合{1},{1,1},{1,1,1}(和上一轮上面的all的元素数量相同)，此时新的all:{},{1},{1,1},{1},{1,1},{1,1,1}
 *      要减去上一次以1结尾的除去空集的all集合数2,也就是{1},{1,1} 所以all:{},{1},{1,1},{1,1,1}
 * 抽象化 s = .....X ...B*X
 * 假设
 * 来到第1个X 以当前X结尾的all集合数量 = 100个
 * 来到第2个X all的集合 = B的all数量+此时第2次X带来的新集合数 - 99
 * 如果只有字面值0~9
 * 记录
 * 之前有多少个字面值必须以1结尾
 * 之前有多少个字面值必须以2结尾
 * 之前有多少个字面值必须以3结尾
 * 之前有多少个字面值必须以4结尾
 * ......
 * 之前有多少个字面值必须以9结尾
 */
public class Code_940_DistinctSubseqValue {

    public static int distinctSubseqII(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // 用来取模
        int m = 1000000007;
        char[] str = s.toCharArray();
        int[] count = new int[26];
        int all = 1; // 算空集
        for (char x : str) {
            int add = (all - count[x - 'a'] + m) % m;
            all = (all + add) % m;
            count[x - 'a'] = (count[x - 'a'] + add) % m;
        }
        return all - 1;
    }

    /**
     * 这个方法是算空集的
     *
     * @param s
     * @return
     */
    public static int zuo(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int m = 1000000007;
        char[] str = s.toCharArray();
        /**
         * 记录 上次key出现时候，以key结尾的集合数量
         * str = "b c c" all:∅  也就是 all = 1
         * {b} , b:{} {b}   <b,2>  all = 1 + 1 - 0
         * {c},{b,c} , c:{} {b} {c} {b,c}   <c,2> all = 2 + 2 - 0
         * {c},{b,c},{c,c},{b,c,c} , c:{} {c} {b,c}, {c,c} {b,c,c}   <c,3>   all = 4 + 4 - 2 = 6
         */
        HashMap<Character, Integer> map = new HashMap<>();
        int all = 1; // 一个字符也没遍历的时候，有空集
        for (char x : str) {
            //当前x字符产生的新的集合数量
            int newAdd = all;
            //上一步的all+ 新加的 - 修正（如果map 记录过 该字符结尾的集合数量, 是修正  ）

            int curAll = all;
            curAll = (curAll + newAdd) % m;
            /**
             * 如果包含了了x，也就是说当前遍历到的x是之前遍历过的
             * int curAll = all + newAdd - (map.containsKey(x) ? map.get(x) : 0);
             * curAll - (map.containsKey(x) ? map.get(x) : 0) 可能是负数 所欲取模之前 先加上 模数、
             * 因为有两个数 a和b
             * 如果a%m - b%m 可能就会变成负数 因为都去了摸
             * 那么 (a%m - b%m+m)%m
             */
            curAll = (curAll - (map.containsKey(x) ? map.get(x) : 0) + m) % m;
            all = curAll;
            //更新x的所对应的all集合数量
            map.put(x, newAdd);
        }
        return all;
    }

    public static void main(String[] args) {
        String s = "bccaccbaabbc";
        System.out.println(distinctSubseqII(s) + 1);
        System.out.println(zuo(s));
    }

}
