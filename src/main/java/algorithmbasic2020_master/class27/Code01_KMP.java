package algorithmbasic2020_master.class27;

public class Code01_KMP {
    /**
     * <pre>
     * str1 = a a b a a b a a f
     * str2 = a a b a a f
     * 先求出str2的前缀表 即next[]
     * str2 = a a b a a f  的
     * 前缀 	   后缀
     * a         f  	str[0]的子串的前后缀相同的长度=0
     * aa       af		str[0~1]的子串的前后缀相同的长度=1
     * aab     aaf	    str[0~2]的子串的前后缀相同的长度=0
     * aaba   baaf      str[0~3]的子串的前后缀相同的长度=1
     * aabaa  abaaf     str[0~4]的子串的前后缀相同的长度=2
     * next[] = 0 1 0 1 2 0
     * 因为str1[5]!=str2[5]
     * 所以查看str2[0~4]的最长前后缀及其长度
     * 查看前缀表 next[4]=2
     * 说明有一个len=2的后缀 与一个len=2的前缀相匹配
     * 那么就是str1[5]与 str2[next[4]]向匹配
     * 即
     * a a b a a b a a f
     *       a a b a a f
     * </pre>
     **/
    public static int getIndexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() < 1 || s1.length() < s2.length()) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int x = 0;
        int y = 0;
        // O(M) m <= n
        int[] next = getNextArray(str2);
        // O(N)
        /*
         * y越界说明了str1的某一个开头可以匹配str2了
         * x越界说明了str1的任何一个开头都没有匹配出str2
         * */
        while (x < str1.length && y < str2.length) {
            if (str1[x] == str2[y]) {//2个位置的字符相同的话 2个位置分别后移
                x++;
                y++;
            } else if (next[y] == -1) {
                // y == 0 来到了0位置 因为规定nextArray数组中的索引为0的元素是-1
                //说明str2没有办法向前跳了 还是没有办法与str1匹配的话 只能让str1后移一个位置
                x++;
            } else {//str1的位置不动 str2还能往前跳的话 往前跳
                y = next[y];
            }
        }
        //y == str2.length表示i2越界了 说明str1的某一个开头可以匹配str2
        return y == str2.length ? x - y : -1;
    }

    public static int[] getNextArray(char[] str2) {
        if (str2.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str2.length];
        //人为规定
        next[0] = -1;
        next[1] = 0;
        int i = 2; // 目前在哪个位置上求next数组的值

        /**
         * 代表当前位置上的信息是什么
         * 也代表当前是哪个位置的值再和i-1位置的字符比较
         **/
        int cn = 0;
        while (i < next.length) {
            if (str2[i - 1] == str2[cn]) { // 配成功的时候

                /**
                 * 任何一个位置i都要使用前一个位置i-1
                 * next[i] = cn+1 //当前位置的值是
                 * i++ //这里是要求下一个位置
                 **/
                next[i++] = ++cn;
            } else if (cn > 0) {//当前跳到cn位置的字符 和i-1位置的字符配不上

                /**
                 cn位置的字符不等于i-1位置的字符的时候
                 * cn往前跳 跳到下标为cn的位置
                 **/
                cn = next[cn];
            } else {//往前跳到底了 也就是第一个位置了还是比对不上
                next[i] = 0;
                i++;
            }
        }
        return next;
    }

    //这个函数是从s中找到t，如果存在返回t出现的位置，如果不存在返回-1
    public static int getIndexOf2(String s, String t) {
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        if (str2.length == 0) {
            return 0;
        }
        int[] next = getNextArray2(t.toCharArray());
        int j = 0;
        for (int i = 0; i < str1.length; i++) {
            while (j > 0 && str1[i] != str2[j]) {
                j = next[j - 1];
            }
            if (str1[i] == str2[j]) {
                j++;
            }
            if (j == str2.length) {
                return i - str2.length + 1;
            }
        }
        return -1;
    }

    public static int[] getNextArray2(char[] str) {
        if (str.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str.length];
        /**
         * 比较前缀和后缀对应的字符是否相同
         * i就从下标1开始
         * j代表了子串的最长相等前后缀的长度
         * j也表示前缀的末尾
         * i表示了后缀的末尾
         * */

        // 初始化
        int j = 0;
        next[0] = 0;
        /**
         * str2= a a b a a f
         * 2种情况
         * 1.前后缀不同
         * 2.前后缀相同
         * */
        for (int i = 1; i < str.length; i++) {
            /**
             * 1
             * eg: i=2 j=1, 也就是str2的子串"aab"
             * j表示前缀的末尾 前缀是aa
             * i表示后缀的末尾 前缀是ab
             * 需要回退 直到 回到0位置
             * 这里就是回到0位置
             * next[2]=j=0;
             */
            while (j > 0 && str[i] != str[j]) {
                j = next[j - 1];
            }
            if (str[i] == str[j]) {
                /**
                 * 2
                 * 前后缀相同
                 * eg: i=1 j=0, 也就是str2的子串"aa"
                 * j表示前缀的末尾 前缀是a
                 * i表示后缀的末尾 前缀是a
                 * 也就是查看j的前一位，即j-1的对应next的值 => next[j-1]
                 *
                 * j也代表包括i以及之前的整个子串的最长相同的前后缀的长度
                 * 因为i是后缀的末尾 也就说子串是[0]~[i]范围 即 aa
                 * 那么就是说 对于"aa"而言  整个子串的最长相同的前后缀的长度 =1
                 * 也就是 前缀是"a" 后缀是"a"
                 * 那么 j++
                 * 那么next[1]=1 =j++
                 * */
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);


    }

    public static void main(String[] args) {
        /*int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");*/
        char[] ans = new char[]{'a', 'a', 'b', 'a', 'a', 'f'};
        for (int i : getNextArray(ans)) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i : getNextArray2(ans)) {
            System.out.print(i + " ");
        }
        String str1 = "aabaabaaf";
        String str2 = "aabaaf";
        System.out.println(getIndexOf(str1, str2) == getIndexOf2(str1, str2));
    }

}
