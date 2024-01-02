package algorithmbasic2020_master.class28;

/**
 *TODO
 * Manacher算法解决的问题
 * 字符串str中，最长回文子串的长度如何求解?
 * 如何做到时间复杂度0(N)完成?
 * 回文直径
 * eg: a b c 1 2 3 2 1 d e f
 * 1 2 3 2 1 是回文str
 * 回文直径 = 5 回文半径 =(5+1) / 2 = 3
 * 最右回文右边界  初始情况：int R = -1
 * 回文半径数组：
 * 以0位置为中心 向左右2侧扩展 最长回文是......
 * 以1位置为中心 向左右2侧扩展 最长回文是......
 * 以2位置为中心 向左右2侧扩展 最长回文是......
 * ....
 * 上面的答案记录到arr[]中
 * 举例：
 * # 1 # 1 # 2 # 1 # 1 # k ........
 * 0 1 2 3 4 5 6 7 8 9 10 11
 * 不管是哪一个位置为中心 向2侧扩展，只要扩展到最右边界，就记录下来
 * 以1位置为中心 向左右2侧扩展 直到2位置  R = -1 => R = 2 ,C = 1
 * 以2位置为中心 向左右2侧扩展 直到4位置  R = 2 => R = 4 ,C = 2
 * 以3位置为中心 向左右2侧扩展 直到4位置  R =4 不变
 * 以5位置为中心 向左右2侧扩展 直到4位置  R =4 => R = 10 ,C = 5
 * .....
 * 如果来到i位置
 * 1.R没有把i位置罩住 => R > i 这种情况无法优化
 * eg: # 1 # 2 # 2 初始：R = -1,i = 0
 * 2.R把i位置罩住 => R <= i
 * |     eg: # 1 # 2 # 2 # 1 =>  i=3的时候 R = 4
 * |    一定是：?  ?  ?  ?  ?   i`是以c位置为中心 i位置对应的位置，L是以c位置为中心 R位置对应的位置
 * |      下标:L  i` c  i  R
 * |     可能是：?  ?  ?     i和R是同一个位置 ，i`和L是同一个位置
 * |           L  c  R
 * |           i`    i
 * |    还可以在细分,根据i`位置扩展的回文半径再细分,因为记录过再arr[]中
 * |   2.1 i`位置为中心2侧扩展的区域在L~R区域里面
 * |      eg:
 * |          a  b  c  d  c  k  s  t  s  k  c  d  c  b  e
 * |          L     (  i` )        c           i        R
 * |        这种情况下，i位置不用想2侧扩展了，因为扩展的区域和i`位置的区域相同
 * |        以i`位置为中心，向2边扩展得到的区域甲，以i位置为中心，向2侧扩展得到的区域乙，乙和甲长度相同
 * |        乙和甲的关系是逆序关系 => 甲因为是回文 ，那么乙也是
 * |        甲区域左侧的char是b，右侧的char是k,这2个char不同
 * |        因为关于c位置对称，所以甲右侧的char和乙左侧的char是相同的，都是k ==> 同理甲左侧的1个cahr == 乙右侧的1个char
 * |        => 甲和乙是i`和i位置为中心向2侧扩展的最大区域
 * |   2.2 i`位置为中心2侧扩展的区域在L~R区域外面
 * |      eg: a  b  c  d  e  d  c  b  a  t  s  t  a  b  c  d  e  d  c  f
 * |          L`    L     i`          R`    c                 i     R
 * |        这种情况下，i位置不用想2侧扩展了，i的回文半径是i~R
 * |      类似
 * |          (  [  o  ]  )   o   [  o  ]
 * |             L  i` L`     c   R` i  R
 * |          L和L`分别和i中心对称，R和R`分别和i中心对称
 * |      证明R~R`这段是回文
 * |      R~R`的子str(称之为甲)对应L~L`的子str(称之为乙)  因为关于c对称
 * |      甲和乙是在大的回文串，且与c对称 => 甲和乙是逆序
 * |      此外，甲是i`为中心的小回文串 ==> 甲一定是回文 ==> 乙一定是回文
 * |      (  a [  o  ] b  )    o    x [  o  ] y ....
 * |           L  i` L`        c      R` i  R
 * |      把R`左侧的char称为x  R的右侧char称为y
 * |      同理L的左侧char称为a  L`的右侧称为b
 * |      a和b相同char，因为i`为中心的回文串 保住了a和b
 * |      在以c中心的回文串  b == x 因为b与x对称
 * |      但是以c为中心的回文串 左边界是L 右边界是R,y是R的右侧一个char ==> x != y
 * |   2.3 i`的回文区域的左边界与L重合
 * |     类似
 * |         (
 * |         [  i  )  o    i`  ]
 * |         L        c        R
 * |      x  [  a  b  c  b  a  s  t  s  a  b  c  b  a  ] y
 * |         L        i`          c           i     R
 * |     这种情况：i的回文区域大小  == i`的回文区域
 * |     此外  i为中心的回文区域的有边界至少是R 可能更右边
 * 总结：
 * i在R的内部
 * 1.i`的回文区域在L~R内部,那么i的回文半径就是i`的回文半径  且 i`的回文半径 < i到R的距离
 * 2.i的回文区域超出了L~R,那么i的回文半径的有边界就是k => R`__ i __ R 且 i的回文半径 > i到R的距离
 * 3.i的回文区域的左边界是L,那么i的回文半径的左边界至少是k
 */
public class Code01_Manacher {

    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // "12132" -> "#1#2#1#3#2#"
        char[] str = manacherString(s);
        // 回文半径的大小
        int[] pArr = new int[str.length];
        int C = -1;
        /*
         *TODO
         * R的区别
         * 讲述中：R代表最右的扩成功的位置
         * coding：最右的扩成功位置的，再下一个位置
         * */
        int R = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) { // 0 1 2
            /*
             *TODO
             * 现在R第一个违规的位置，那么就是i>= R ，R在i外部
             * R > i 才称之为R在i内部
             * i位置扩出来的答案，i位置扩的区域，至少是多大。
             * */
            if (R > i) {
                /*
                 *TODO
                 * 2 * C - i 就是i'
                 * i'的回文半径长度 和R到i的距离 哪个区域小 哪个区域就是不用校验的区域
                 *TODO i在R内部的话 3种情况
                 * i'的回文区域在L~R内 => i的回文半径 就是 i'的回文区域 并且i'的回文区域长度 < R-i的距离
                 * i'的回文区域在L~R外 => i的回文半径 的右边界 就是R本身 并且i'的回文区域长度 > R-i的距离
                 * i'的回文区域的左边界就是L => i的回文半径 的右边界 至少是R（不确定） R的右侧还要检测
                 * */
                pArr[i] = Math.min(pArr[2 * C - i], R - i);
            } else {
                ///至少的回文半径长度是1
                pArr[i] = 1;
            }
            //pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            /*
             *TODO
             * i+不用检验的区域 不越界
             * i-不用检验的区域 不越界
             * */
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {//右边要验证的字符和左边要验证的字符不能越界
                //如果 再往右的char ==  再往左的char
                if (str[i + pArr[i]] == str[i - pArr[i]])
                    //回文半径++
                    pArr[i]++;
                else {
                    //失败
                    break;
                }
            }
            if (i + pArr[i] > R) {//如果更往右了
                //更新R和C
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        //回文半径-1就是原始str的最大回文子串长度
        return max - 1;
    }

    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
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
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
