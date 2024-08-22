package code_for_great_offer.class39;


/*
 *TODO 来自百度
 * 给定一个字符串str，和一个正数k
 * str子序列的字符种数必须是k种，返回有多少子序列满足这个条件
 * 已知str中都是小写字母
 * 原始是取mod
 * 本节在尝试上，最难的
 * 搞出桶来，组合公式
 * eg: arr [a a b c] k=3
 * [a b c]  [a b c]  [a a b c] => 3种
 *  0 2 3    1 2 3    0 1 2 3
 * eg： arr[ a a b b c c] k=3 => 27种
 * [a b c] [a b c] [a b c]  [a b c]
 *  0 2 4   0 2 5   0 3 4    0 3 5
 * 并且 顺序无关 eg: arr [a b b a c c] 也是27种
 * eg3: a有10个 b有9个 c有6个 d有15个 e有30个 k=3
 * 意思就是 凑出3种不同的字符
 * 那么先建立一个词频表数组
 * [10,9,6,15,30] 分别对应 [a b c d e]
 * 每个位置只有2中选择
 * 1.要
 * 2.不要
 * 从左往右的尝试模型
 * */
public class Code03_SequenceKDifferentKinds {

    /*
     *TODO
     * bu ->
     * {6,7,0,0,6,3}  对应的词频
     *  a b c d e f   对应的字符
     *  0 1 2 3 4 5   对应的下标
     * 在桶数组bu[index]~bu[N] 一定要凑出rest种来！请问几种方法！
     * 来到index位置
     * 一共有N个字符  因为N=arr.len-1
     * 你来到index位置 做出选择要不要bu[index]对应的字符 ，你当前还剩下rest字符要选
     * */
    public static int f(int[] bu, int index, int rest) {
        if (index == bu.length) {
            return rest == 0 ? 1 : 0;
        }
        //TODO 最后形成的子序列，一个index代表的字符也没有!
        int case1 = f(bu, index + 1, rest);
        //TODO 最后形成的子序列，一定要包含index代表的字符，几个呢？(所有可能性都要算上！)
        int case2 = 0;
        if (rest > 0) {
            /*
            *TODO
            * 剩余的种数，没耗尽，可以包含当前桶的字符
            * 假设当前来到 index=f位置 bu[f]有10个对应的字符
            * 假设 选择要这个字符 ，只要1个，那么就是 10C1 个方法
            * 只要2个，那么就是 10C2 个方法 , ... 只要2个，那么就是 10C10个方法
            * 也就是 10C1 + 10C2 + ...+10C10
            *TODO
            * 也就说 bu[index] = n的话 case = nC1 + nC2 + ...+nCn = 2 ^ bu[index] - 1
            * */
            case2 = (1 << bu[index] - 1) * f(bu, index + 1, rest - 1);
        }
        return case1 + case2;
    }

    public static int nums(String s, int k) {
        char[] str = s.toCharArray();
        int[] counts = new int[26];
        for (char c : str) {
            counts[c - 97]++;
        }
        return ways(counts, 0, k);
    }

    public static int ways(int[] c, int i, int r) {
        if (r == 0) {
            return 1;
        }
        if (i == c.length) {
            return 0;
        }
        // math(n) -> 2 ^ n -1
        return math(c[i]) * ways(c, i + 1, r - 1) + ways(c, i + 1, r);
    }

    // n个不同的球
    // 挑出1个的方法数 + 挑出2个的方法数 + ... + 挑出n个的方法数为:
    // C(n,1) + C(n,2) + ... + C(n,n) == (2 ^ n) -1
    public static int math(int n) {
        return (1 << n) - 1;
    }

    public static void main(String[] args) {
        String str = "acbbca";
        int k = 3;
        System.out.println(nums(str, k));
    }

}
