package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>最长公共前缀</h3>
 * <pre>
 * case1:  遇到不同
 *  初始状态
 *     i
 *  j [f l o w e r]
 *    [f l o w    ]
 *    [f l i g h t]
 *    [f l y      ]
 * 先针对 第0列 比较
 * 1. 查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 * 2.查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 * 3.查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 *  到底了 换列 i++
 * 1. 查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 * 2.查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 * 3.查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 *  到底了 换列 i++
 * 1. 查看 [j]和[j+1]字符是否相同 o == o
 *  j++
 * 2.查看 [j]和[j+1]字符是否相同 o != i
 *  结束
 *  那么共同前缀是 i[0 ~ 1]
 * case2: 遇到空
 *  初始状态
 *     i
 *  j [f l o w e r]
 *    [f l o w    ]
 *    [f l        ]
 *    [f l i g h t]
 *    [f l y      ]
 * 先针对 第0列 比较
 * 1. 查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 * 2.查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 * 3.查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 * 4.查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 *  到底了 换列 i++
 * 1. 查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 * 2.查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 * 3.查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 * 4.查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 *  到底了 换列 i++
 * 1. 查看 [j]和[j+1]字符是否相同 o == o
 *  j++
 * 2.查看 [j]和[j+1]字符是否相同 o != null
 *  结束
 *  那么共同前缀是 i[0 ~ 1]
 * case3: 第一个字符串比较短
 *  初始状态
 *     i
 *  j [f l        ]
 *    [f l o w    ]
 *    [f l o w e r]
 *    [f l i g h t]
 *    [f l y      ]
 * 先针对 第0列 比较
 * 1. 查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 * 2.查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 * 3.查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 * 4.查看 [j]和[j+1]字符是否相同 f == f
 *  j++
 *  到底了 换列 i++
 * 1. 查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 * 2.查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 * 3.查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 * 4.查看 [j]和[j+1]字符是否相同 l == l
 *  j++
 *  到底了 换列 i++
 *  发现 [j][i] == null 结束循环
 * </pre>
 */
public class Leetcode_14_LCP {
    static String longestCommonPrefix(String[] strings) {
        /**
         * 情况1：比较某一列时，遇到不同字符，i 之前的字符就是解
         * 情况2：比较某一列时，遇到字符串长度不够，i 之前的字符就是解
         * 情况3：i 循环自然结束，此时第一个字符串就是解
         */
        char[] first = strings[0].toCharArray(); // 第一个字符串
        for (int i = 0; i < first.length; i++) {
            // 拿出第一个字符串的第i个字符
            char ch = first[i];
            for (int j = 1; j < strings.length; j++) {
                // strings[j].charAt(i) 字符数组j行的第i个字符 或者叫做 第i列
                // strings[j].length() == i 情况2: 长度就是i
                // ch != strings[j].charAt(i) 情况i: 长度就是i
                if (strings[j].length() == i || ch != strings[j].charAt(i)) {
                    // 返回 first[0 ~ i]
                    return new String(first, 0, i);
                }
            }
        }
        // 执行代码到这里 就是只剩下情况3
        return strings[0];
    }

    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"})); // fl
        System.out.println(longestCommonPrefix(new String[]{"dog", "racecar", "car"})); // 空串
        System.out.println(longestCommonPrefix(new String[]{"ab", "a"})); // a
        System.out.println(longestCommonPrefix(new String[]{"dog", "dogaa", "dogbb"})); // dog
    }
}
