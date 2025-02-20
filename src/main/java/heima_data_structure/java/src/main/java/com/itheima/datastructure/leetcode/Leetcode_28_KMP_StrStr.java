package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * <h3>字符串匹配 - Knuth Morris Pratt 算法</h3>
 * <pre>
 * TODO 目标 让pattern的指针少回退，利用之前匹配成功的子串 来算出 pattern的指针回退到哪里
 * 找 origin中有没有pattern的子串
 * eg1:
 * 1.
 * pattern = a a a a b
 *           j
 * origin  = a a a a a a a a a a a b
 *           i
 * origin[i] = pattern[j]
 * i++ j++
 * 2.
 * pattern = a a a a b
 *             j
 * origin  = a a a a a a a a a a a b
 *             i
 * origin[i] = pattern[j]
 * i++ j++
 * 3.
 * pattern = a a a a b
 *               j
 * origin  = a a a a a a a a a a a b
 *               i
 * origin[i] = pattern[j]
 * i++ j++
 * 4.
 * pattern = a a a a b
 *                 j
 * origin  = a a a a a a a a a a a b
 *                 i
 * origin[i] = pattern[j]
 * i++ j++
 * 5.
 * pattern = a a a a b
 *                   j
 * origin  = a a a a a a a a a a a b
 *                   i
 * origin[i] != pattern[j]
 * 此时 j不会回到索引0 根据之前匹配到的公共字符串"a a a a" 的前后缀
 * 对于 pattern而言 求 "a a a a" 的 前缀 也就是 a a a
 * 对于 origin 而言 求 "a a a a" 的 后缀 也就是 a a a
 * 即：
 * pattern = a a a a b
 *           _ _ _   j
 * origin  = a a a a a a a a a a a b
 *             _ _ _ i
 * 上面带有下划线的部分不用再重复对比了
 * 那么就是说 pattern的指针j移动到 第4个a上 然后 j和i继续比对
 * pattern =   a a a a b
 *                   j
 * origin  = a a a a a a a a a a a b
 *                   i
 * origin[i] = pattern[j]
 * i++ j++
 * 6.
 * pattern =   a a a a b
 *                     j
 * origin  = a a a a a a a a a a a b
 *                     i
 * origin[i] != pattern[j]
 * 此时 j不会回到索引0 根据之前匹配到的公共字符串"a a a a" 的前后缀
 * 对于 pattern而言 求 "a a a a" 的 前缀 也就是 a a a
 * 对于 origin 而言 求 "a a a a" 的 后缀 也就是 a a a
 * 即：
 * pattern = a a a a b
 *           _ _ _   j
 * origin  = a a a a a a a a a a a b
 *               _ _ _ i
 * 上面带有下划线的部分不用再重复对比了
 * 那么就是说 pattern的指针j移动到 第4个a上 然后 j和i继续比对
 * pattern =     a a a a b
 *                     j
 * origin  = a a a a a a a a a a a b
 *                     i
 * origin[i] = pattern[j]
 * i++ j++
 * 7.
 * ......
 *
 *
 *
 * eg2:
 * TODO pattern和origin比较时候，永远看的是origin与pattern相同的字符串的部分
 *      也就是说本质是看pattern字符串，求lps也就是求 pattern的
 * pattern = a b a b a c a
 * origin = a b a b a b a b c a b a c a c a b a b a c a
 * 初始:
 * a b a b a c a
 * i
 * a b a b a b a b c a b a c a c a b a b a c a
 * j
 * pattern的第一个字符a 和 origin 的第一个字符a 开始比较
 * ....
 * 不断地找 找到了这里
 * a b a b a c a
 *           j
 * a b a b a b a b c a b a c a c a b a b a c a
 *           i
 * 需要pattern字符串的前缀 和origin字符串的后缀
 * 前面有5个共同的字符的时候
 * 公共 字符串 a b a b a 的 最长公共前后缀  a b a
 * 也就是说
 * a b a b a c a
 * _ _ _     j
 * a b a b a b a b c a b a c a c a b a b a c a
 *     _ _ _ i
 * 公共 字符串 a b a b a 的 最长公共前后缀 a b a
 * 之后找的话,可以加速
 * 相当于 pattern索引在 从头开始 遍历 "a b a" 之后来到的位置 这里是 b
 *     a b a b a c a
 *           j
 * a b a b a b a b c a b a c a c a b a b a c a
 *           i
 * pattern[i] = origin[j]
 * i++,j++
 *
 *     a b a b a c a
 *             j
 * a b a b a b a b c a b a c a c a b a b a c a
 *             i
 * pattern[i] = origin[j]
 * i++,j++
 *
 *     a b a b a c a
 *     _ _ _     j
 * a b a b a b a b c a b a c a c a b a b a c a
 *         _ _ _ i
 * 公共 字符串 a b a b a 的 最长公共前后缀 a b a
 * 之后找的话,可以加速
 * 相当于 pattern索引在 从头开始 遍历 "a b a" 之后来到的位置 这里是 b
 *         a b a b a c a
 *               j
 * a b a b a b a b c a b a c a c a b a b a c a
 *               i
 * pattern[i] = origin[j]
 * i++,j++
 *
 *         a b a b a c a
 *         _ _     j
 * a b a b a b a b c a b a c a c a b a b a c a
 *             _ _ i
 * 公共 字符串 a b a b 的 最长公共前后缀 a b
 * 之后找的话,可以加速
 *             a b a b a c a
 *             _ _ j
 * a b a b a b a b c a b a c a c a b a b a c a
 *             _ _ i
 * 公共 字符串 a b 的 最长公共前后缀 没有
 * 这种情况下 i和j 从头开始,不能加速
 * 之后找的话 pattern的索引j从0开始，origin的索引i不动  ☆ ☆ ☆ ☆ ☆ ☆
 *                 a b a b a c a
 *                 j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                 i
 * origin[i] != pattern[j]
 * 没有公共字符串
 * 直接origin的索引i++ , pattern的索引j不动
 *                   a b a b a c a
 *                   j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                   i
 * origin[i] = pattern[j]
 * j++ and i++
 *                   a b a b a c a
 *                     j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                     i
 * origin[i] = pattern[j]
 * j++ and i++
 *                   a b a b a c a
 *                       j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                       i
 * origin[i] = pattern[j]
 * j++ and i++
 *                   a b a b a c a
 *                         j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                         i
 * origin[i] != pattern[j]
 * 公共 字符串 a b a 的 最长公共前后缀 a
 * 之后找的话
 * 相当于 pattern索引在 从头开始 遍历 "a " 之后来到的位置 这里是 b
 *                       a b a b a c a
 *                         j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                         i
 * origin[i] != pattern[j]
 * 公共 字符串 a 的 最长公共前后缀 无
 * 之后找的话 pattern的索引j从0开始，origin的索引i不动  ☆ ☆ ☆ ☆ ☆ ☆
 *                         a b a b a c a
 *                         j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                         i
 * 没有公共字符串
 * 直接origin的索引i++ , pattern的索引j不动
 *                           a b a b a c a
 *                           j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                           i
 * origin[i] = pattern[j]
 * j++ and i++
 *                             a b a b a c a
 *                             j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                             i
 * 公共 字符串 a 的 最长公共前后缀 无
 * 直接origin的索引i++ , pattern的索引j不动
 *                               a b a b a c a
 *                               j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                               i
 * ....
 * </pre>
 * 本质:
 * 求公共字符串的前后缀只和pattern有关
 * pattern
 */
class Leetcode_28_KMP_StrStr {
    static int strStr(String str1, String str2) {
        char[] origin = str1.toCharArray();     // 原始
        char[] pattern = str2.toCharArray();    // 模式
        int[] lps = lps(pattern);       // 最长前后缀数组
        int i = 0;
        int j = 0;
        /**
         * while 的 退出循环条件 不是 i < origin.length
         * <pre>
         *                                    a b a b a c a
         *                                          j
         *  a b a b a b a b c a b a c a c a b a b a b a
         *                                          i
         * 这种情况不需要比对了
         * 因为模式字符串剩下3个字符   3 = pattern.length - j
         * 但是原始字符串只剩下1个字符  1 = origin.length - i
         * </pre>
         */
        while (pattern.length - j <= origin.length - i) {
            if (origin[i] == pattern[j]) {
                /**
                 * <pre>
                 *  1. 匹配成功，i++ j++，直到 j==模式字符串长度
                 *  eg:
                 *  a b a b a c a
                 *  j
                 *  a b a b a b a b c a b a c a c a b a b a b a
                 *  i
                 *  匹配成功
                 * </pre>
                 * 2. 匹配失败
                 * j != 0 跳过最长前后缀字符，继续匹配
                 * j == 0 则 i++
                 */
                i++;
                j++;
                // 没有这个分支就是匹配失败
            } else if (j == 0) {
                /**
                 * <pre>
                 * eg:
                 * origin[i] != pattern[j]
                 *                 a b a b a c a
                 *                 j
                 * a b a b a b a b c a b a c a c a b a b a b a
                 *                 i
                 * 这种情况之前没有匹配好的字符
                 * 只需要 i++ 即：
                 *                   a b a b a c a
                 *                   j
                 * a b a b a b a b c a b a c a c a b a b a b a
                 *                   i
                 * </pre>
                 */
                i++;
            } else {
                /**
                 * <pre>
                 * a b a b a c a
                 *           j
                 * a b a b a b a b c a b a c a c a b a b a b a
                 *           i
                 * a b a b a 的公共前后缀 = a b a 长度是3
                 * 直接查lps[j-1]=3
                 * j跳到的位置 是 3
                 * i不会退
                 *     a b a b a c a
                 *           j
                 * a b a b a b a b c a b a c a c a b a b a b a
                 *           i
                 * </pre>
                 */
                j = lps[j - 1];
            }
            if (j == pattern.length) {
                /**
                 * 当模式字符串到最后一个位置的时候找到解了
                 * <pre>
                 *                               a b a b a c a
                 *                                           j
                 * a b a b a b a b c a b a c a c a b a b a b a
                 *                                           i
                 * </pre>
                 * 其索引位置 就是 i-j
                 */
                return i - j;
            }
        }
        return -1;
    }

    /**
     * 最长前后缀数组：只跟模式字符串相关
     * LPS 数组的每个元素 lps[j] 代表的是字符串的前 j+1 个字符的最长前缀和后缀的长度 ☆☆☆☆☆☆☆
     * 或者说前 i 个字符的最长前缀和后缀的长度 因为 i = j + 1 ，用来求后缀， j是用来求前缀
     * 1. 索引：使用了模式字符串前 j 个字符串 - 1
     * 2. 值：最长前后缀的长度（恰好是j要跳转的位置）
     * 方法简述：
     * 遇到相同字符：
     * 记录共同前后缀长度
     * 长度即为 j+1
     * 长度记录至数组 i 索引处
     * 然后 i++  j++
     * 遇到不同字符：
     * 前面没有共同部分（j=0）
     * 前面有共同部分，j向回找
     * 无需对比的地方，可以跳过
     * 无需对比的字符个数，之前已计算过
     * <p>
     * 前缀：字符串的前缀是从起点开始的连续子串，不包括最后一个字符。
     * 例如，字符串 abcde 的前缀有：a, ab, abc, abcd。
     * 后缀：字符串的后缀是从末尾开始的连续子串，不包括第一个字符。
     * 例如，字符串 abcde 的后缀有：e, de, cde, bcde。
     * <p>
     * 本来只有一个模式字符串  ，现在再复制一份
     * <pre>
     * eg: str = a a a a b
     * 对于 str 子串 的 前后缀 一一写出
     * 1.
     * 对于 str 的子串 a 而言
     * 前缀：无 后缀：无 公共前后缀长度 = 0
     * 2.
     * 对于 str 的子串 a a 而言
     * 前缀：a 后缀：a 公共前后缀长度 = 1
     * 3.
     * 对于 str 的子串 a a a而言
     * 前缀：        后缀：
     * a               a
     * a a           a a <== 看这个
     * 公共前后缀长度 = 2
     * 4.
     * 对于 str 的子串 a a a a而言
     * 前缀：        后缀：
     * a               a
     * a a           a a
     * a a a       a a a  <== 看这个
     * 公共前后缀长度 = 3
     * 5.
     * 对于 str 的子串 a a a a b而言
     * 前缀：        后缀：
     * a               a
     * a a           a a
     * a a a       a a a
     * a a a a   a a a b  <== 看这个
     * 公共前后缀长度 = 0
     * 现在的套路:
     * TODO 复制一份相同的str数组，对比写出lps
     * TODO 上面的字符串代表前缀 下面的字符串代表后缀
     * TODO 查看后缀是 1~i ,i是因为后缀不会包含第0个字符  前缀是 0~j ,j是因为前缀不会包含第str.length-1个字符
     * 初始情况:
     * i=1 j=0
     * 1.
     * 一开始 特例 ： 就一个字符 没有 前后缀
     * 求前缀:   a a a a b
     *          j
     * 求后缀: a a a a b
     *          i
     * 前缀: ""  后缀: ""  根据定义 没有公共前后缀  记录 lps[0]=0
     * 2. 求 "a a"的 前后缀  填写 lps[1]
     * 求前缀:   a a a a b
     *          j
     * 求后缀: a a a a b
     *          i
     * 前缀: a  后缀: a  ,有上面的图中可以看出 "a a" 有共同的前后缀 a 记录 lps[1]=1 j++ i++
     * 3. 求 "a a a"的 前后缀  填写 lps[2]
     * 求前缀:   a a a a b
     *            j
     * 求后缀: a a a a b
     *            i
     * 前缀: a a  后缀: a a  ,有上面的图中可以看出 "a a a" 有共同的前后缀 a a 记录 lps[2]=2  j++ i++
     * 4. 求 "a a a a"的 前后缀  填写 lps[3]
     * 求前缀:   a a a a b
     *              j
     * 求后缀: a a a a b
     *              i
     * 前缀: a a a 后缀: a a a 有上面的图中可以看出 "a a a a"有共同的前后缀 a a a 记录 lps[3]=3  j++ i++
     * 5. 求 "a a a a b"的 前后缀  填写 lps[4]
     * 求前缀:      a a a a b
     *                   j
     * 求后缀:      a a a a b
     *                     i
     * 前缀: a a a a 后缀: a a a b  不相同
     *  j向前 并且 直接跳过一些不需要对比的字符
     *  因为 前缀 和 后缀 的公共字符串是 a a a  ，求 共字符串 a a a 的公共前后缀 ,已经求过了 是 a a ， 查看  lps[2]=2
     *  TODO 类似于递归的思想：本来求的是"a a a a b"的前后缀，现在求a a a a的前后缀 不是求 "a a a b"的前后缀
     *  也就是
     *    a a a a b
     *    _ _   j
     *  a a a a b
     *      _ _ i
     *  那么 j = 2 i不动
     *  然后得到：
     *      a a a a b
     *          j
     *  a a a a b
     *          i
     *  前缀: a a a  后缀: a a b 不相同
     *  j向前
     *  并且直接跳过一写不需要对比的字符
     *  因为 前缀 和 后缀 的公共部分是 a a ，求a a 的公共前后缀 ,已经求过了 是 a 查看  lps[1]=1
     *  也就是
     *      a a a a b
     *      _   j
     *  a a a a b
     *        _ i
     *  那么  j = 1 i不动
     *  得到：
     *        a a a a b
     *          j
     *  a a a a b
     *          i
     *  因为 前缀 和 后缀 的公共部分是 a ，求a 的公共前后缀 ,已经求过了 是 ""  查看  lps[0]=0
     *  那么  j = 0 i不动
     *  得到：
     *          a a a a b
     *          j
     *  a a a a b
     *          i
     * 此时 说明b作为后缀 但是没有以b作为前缀的与之对应
     * 得到lps[4]=0
     * </pre>
     *
     * <pre>
     * 遇到了相同字符的case:
     * 求前缀:    a a a c a a a a a c
     *           j
     * 求后缀:  a a a c a a a a a c
     *           i
     * 遇到了相同字符 也就是 [i]==[j]  意味着找到了共同的前后缀 把共同前后缀的长度记录到数组中
     * 当前 i=1 那么把共同前后缀的长度 1 记录到lps[i]中
     * 公共前后缀的长度 和 j 的关系 ==> 公共前后缀的长度 = j + 1
     * 最后 i++ j++
     * 求前缀:    a a a c a a a a a c
     *             j
     * 求后缀:  a a a c a a a a a c
     *             i
     * 遇到了相同字符 也就是 [i]==[j]  意味着找到了共同的前后缀 把共同前后缀的长度记录到数组中
     * 当前 i=2 那么把共同前后缀的长度 2 记录到lps[i]中
     * 公共前后缀的长度 和 j 的关系 ==> 公共前后缀的长度 = j + 1
     * 最后 i++ j++
     * ......
     * </pre>
     *
     * <pre>
     * 1.遇到了不相同字符的case:
     *       a a a c a a a a a c
     *       j
     * a a a c a a a a a c
     *       i
     * 发现 j = 0 前面没公共部分
     * 所以 公共前后缀长度 = 0  lps[i] = 0
     * 最后 j不变, i++,因为要让 i++之后的字符 与j的字符比较
     * 2.遇到了不相同字符的case:
     *         a a a c a a a a a c
     *               j
     * a a a c a a a a a c
     *               i
     * 发现 前面有匹配过的公共部分
     * 需要让 j 掉头回去 向前找
     * 但是 之前共同的字符 不需要重新对比一遍
     * 这里比对的是
     * 找 "a a a" 的最长前缀  "a a"
     *         a a a c a a a a a c
     *         _ _   j
     * 找 "a a a" 的最长后缀  "a a"
     * a a a c a a a a a c
     *           _ _ i
     * 因为计算过 所以 没有必要重新比对
     * 直接 j调回去 再重新和i比较即可
     * 也就是
     *           a a a c a a a a a c
     *               j
     * a a a c a a a a a c
     *               i
     * 这里用到的是 当使用了 前3个字符 这里是 "a a a"的时候 应该跳过的字符个数 直接看 lps[2]即可
     * lps[2] 中的 索引 2 是因为 j 原来 = 3 , j - 1 = 2
     * lps[2]= 2 说明 j需要下次跳到 2 索引
     * </pre>
     */
    static int[] lps(char[] pattern) {
        int[] lps = new int[pattern.length];
        // 两个指针初始状态
        int pointForSuffix = 1; // 用来查后缀
        int pointForPrefix = 0; // 用来查前缀
        while (pointForSuffix < pattern.length) {
            // i 处的元素 和 j 处的元素 比较
            if (pattern[pointForSuffix] == pattern[pointForPrefix]) {// 遇到相同字符的处理逻辑
                lps[pointForSuffix] = pointForPrefix + 1;
                pointForSuffix++;
                pointForPrefix++;
            } else if (pointForPrefix == 0) {//类似kmp的做法
                // 如果 不匹配
                // 并且求前缀的指针是0说明 该指针不能向前移动了
                // 只能移动求后缀的指针
                pointForSuffix++;
            } else {//类似kmp
                // 如果 不匹配 那么 求前缀的指针向前移动
                // 相当于利用 0~pointForPrefix-1这个字符串的公共前后缀
                pointForPrefix = lps[pointForPrefix - 1];
            }
        }
        return lps;
    }

    public static void main(String[] args) {
//        System.out.println(strStr("ababababcabacacababaca", "ababaca"));
//        System.out.println("ababababcabacacababaca".indexOf("ababaca"));
        System.out.println(Arrays.toString(lps("ababaca".toCharArray())));
    }
}
