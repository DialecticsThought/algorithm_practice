import java.util.Arrays;

/**
 * code_for_great_offer.class12
 * 本题测试链接 : https://leetcode.com/problems/permutation-in-string/
 */
public class Leetcode_567_ContainAllCharExactly {

    /**
     * https://www.bilibili.com/video/BV1aS6oB2EiT
     * 本质可以看438这道题目
     *
     * @param s
     * @param p
     * @return
     */
    public static int func(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        //base case
        if (sLen < pLen) {
            return -1;
        }
        // 存放p字符的计数器，每一个下标就是代表了一个字母，对应的元素就是该字母出现的次数
        int[] need = new int[26];
        // 存放当前窗口的计数器 每一个下标就是代表了一个字母，对应的元素就是该字母出现的次数
        int[] window = new int[26];
        // 1) 统计 need，并计算 needCount（p里有多少种不同字符） 要和命中次数作比较
        int needCount = 0;
        for (int i = 0; i < pLen; i++) {
            //标就是代表了一个字母，对应的元素就是该字母出现的次数
            int index = s.charAt(i) - 'a';
            if (need[index] == 0) {
                needCount++;
            }
            need[index]++;
        }
        // 2) valid 表示：窗口中有多少个字符的频次 “刚好等于 need” 也就是命中次数
        int valid = 0;
        for (int i = 0; i < sLen; i++) {
            int in = p.charAt(i) - 'a';
            window[in]++;
            if (need[in] > 0) {
                // 如果 当前字符读入 两个数量相同 就达标
                if (window[in] == need[in]) {
                    valid++;
                }
                // 如果 当前字符读入 比 作比较的字符串中的该字符多一个
                if (window[in] == need[in] + 1) {
                    valid--;
                }
            }
            // ---- 左边弹出（当窗口长度 > 作比较的字符串的长度）----
            if (i > pLen) {
                // 需要被弹出的字符在window中的下标
                int out = s.charAt(i - pLen) - 'a';
                //先判断当前字符是否在作比较的字符串里面
                if (need[out] > 0) {
                    // 弹出前超标1个，弹出后会刚好达标
                    if (need[out] + 1 == window[out]) {
                        valid++;
                    }
                    // 弹出前刚好达标，弹出后会变不达标
                    if (need[out] == window[out]) {
                        valid--;
                    }
                }
                window[out]--;
            }
            // 判定：只要有一个窗口满足即可
            if (i >= pLen - 1 && valid == needCount) return 1;
        }
        return -1;
    }


    public static int containExactly1(String s, String a) {
        if (s == null || a == null || s.length() < a.length()) {
            return -1;
        }
        char[] aim = a.toCharArray();
        Arrays.sort(aim);
        String aimSort = String.valueOf(aim);
        for (int L = 0; L < s.length(); L++) {
            for (int R = L; R < s.length(); R++) {
                char[] cur = s.substring(L, R + 1).toCharArray();
                Arrays.sort(cur);
                String curSort = String.valueOf(cur);
                if (curSort.equals(aimSort)) {
                    return L;
                }
            }
        }
        return -1;
    }

    public static int containExactly2(String s, String a) {
        if (s == null || a == null || s.length() < a.length()) {
            return -1;
        }
        char[] str = s.toCharArray();
        char[] aim = a.toCharArray();
        for (int L = 0; L <= str.length - aim.length; L++) {
            if (isCountEqual(str, L, aim)) {
                return L;
            }
        }
        return -1;
    }

    public static boolean isCountEqual(char[] str, int L, char[] aim) {
        int[] count = new int[256];
        for (int i = 0; i < aim.length; i++) {
            count[aim[i]]++;
        }
        for (int i = 0; i < aim.length; i++) {
            if (count[str[L + i]]-- == 0) {
                return false;
            }
        }
        return true;
    }

    public static int containExactly3(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return -1;
        }
        char[] str2 = s2.toCharArray();
        int M = str2.length;
        //记录的是字母和对应的词频 相当于欠账表
        int[] count = new int[256];//可以用该数组表示 也可以是hashmap
        for (int i = 0; i < M; i++) {
            count[str2[i]]++;
        }
        int all = M;//初始的all 就是str2的长度
        char[] str1 = s1.toCharArray();
        int R = 0;
        // 0~M-1
        for (; R < M; R++) { //这个循环 最早的M个字符，让其窗口初步形成
            if (count[str1[R]]-- > 0) {
                all--;
            }
        }
        /*
         * 窗口初步形成了，并没有判断有效无效，决定下一个位置一上来判断
         * 接下来的过程，窗口右进一个，左吐一个
         * 到达n位置 对于n位置 查看的是n-1为右边界的窗口是否满足要求
         * */
        for (; R < str1.length; R++) {
            if (all == 0) { //此时的窗口的右边界是 R-1
                return R - M;//表示R-M~R的范围可以找到str2的变形词
            }
            if (count[str1[R]]-- > 0) {// 词频--后还是>0 说明是一次有效的还款
                all--;
            }
            //如果++是>=0的 那就让all++
            if (count[str1[R - M]]++ >= 0) {//窗口右移 那么要突吐出R-M位置的字符
                all++;
            }
        }
        //最后的窗口的时候 查询all
        return all == 0 ? R - M : -1;
    }

    // for test
    public static String getRandomString(int possibilities, int maxSize) {
        char[] ans = new char[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strMaxSize = 20;
        int aimMaxSize = 10;
        int testTimes = 500000;
        System.out.println("test begin, test time : " + testTimes);
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strMaxSize);
            String aim = getRandomString(possibilities, aimMaxSize);
            int ans1 = containExactly1(str, aim);
            int ans2 = containExactly2(str, aim);
            int ans3 = containExactly3(str, aim);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
                System.out.println(str);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("test finish");

    }

}
