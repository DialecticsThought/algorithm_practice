/**
 * @description
 * @author jiahao.liu
 * @date 2026/03/18 21:15
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author jiahao.liu
 * @Data 2026/3/18 21:15
 */
public class Leetcode_49 {
    /**
     * <pre>
     * LeetCode 49 : Group Anagrams
     *
     * 解法：字符计数法 + 哈希表
     *
     * 核心思想：
     * 1. 对每个字符串统计 26 个小写字母的出现次数
     * 2. 把这 26 个计数拼成一个字符串，作为 HashMap 的 key
     * 3. key 相同的字符串，说明字符频率分布相同，属于同一组
     *
     * 也就是说：
     * “不是直接拿原字符串做 key”
     * 而是拿“这个字符串的词频表序列化后的结果”做 key
     *
     * --------------------------------------------------
     * 例子：
     * strs = ["eat", "tea", "ate", "tan", "nat", "bat"]
     *
     * 目标分组结果：
     * [
     *   ["eat", "tea", "ate"],
     *   ["tan", "nat"],
     *   ["bat"]
     * ]
     *
     * 原因：
     * eat / tea / ate 的字符组成相同
     * tan / nat 的字符组成相同
     * bat 单独一组
     *
     * --------------------------------------------------
     * 以 "eat" 为例：
     *
     * 字符统计结果：
     * a b c d e f g h i j k l m n o p q r s t u v w x y z
     * 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0
     *
     * 可以拼成 key：
     * #1#0#0#0#1#0#0#0#0#0#0#0#0#0#0#0#0#0#0#1#0#0#0#0#0#0
     *
     * "tea" 的字符统计结果也是：
     * a b c d e f g h i j k l m n o p q r s t u v w x y z
     * 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0
     *
     * 所以 "tea" 的 key 和 "eat" 完全一样，
     * 因此它们会被放进 HashMap 的同一个桶中。
     *
     * --------------------------------------------------
     * 为什么要把词频表变成字符串作为 key？
     *
     * 因为在 Java 里，int[] 作为 HashMap 的 key 时，
     * 默认比较的是对象地址，而不是数组内容。
     *
     * 例如：
     * int[] a = {1, 0, 0};
     * int[] b = {1, 0, 0};
     *
     * 虽然内容一样，但 a 和 b 不是同一个数组对象，
     * 直接作为 key 使用时，不能按“内容相同”来分组。
     *
     * 所以要把词频数组转成字符串：
     * int[] count -> "#1#0#0#0#1#..."
     *
     * 这样 HashMap 才能正确地按照“字符频率分布”分组。
     *
     * --------------------------------------------------
     * 整个流程：
     *
     * 单词
     *   -> 统计 26 个字母出现次数
     *   -> 得到词频数组 count[]
     *   -> 把 count[] 拼成字符串 key
     *   -> 放入 map[key] 对应的列表中
     *
     * --------------------------------------------------
     * 处理过程演示：
     *
     * 初始：
     * map = {}
     *
     * 1) 处理 "eat"
     * 词频：
     * a b c d e f g h i j k l m n o p q r s t u v w x y z
     * 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0
     *
     * key =
     * #1#0#0#0#1#0#0#0#0#0#0#0#0#0#0#0#0#0#0#1#0#0#0#0#0#0
     *
     * map = {
     *   key1 : ["eat"]
     * }
     *
     * 2) 处理 "tea"
     * 词频和 "eat" 完全一样
     * 所以仍然进入 key1 对应的组
     *
     * map = {
     *   key1 : ["eat", "tea"]
     * }
     *
     * 3) 处理 "ate"
     * 词频仍然和 "eat"、"tea" 一样
     *
     * map = {
     *   key1 : ["eat", "tea", "ate"]
     * }
     *
     * 4) 处理 "tan"
     * 词频：
     * a b c d e f g h i j k l m n o p q r s t u v w x y z
     * 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0
     *
     * 这是一个新的 key
     *
     * map = {
     *   key1 : ["eat", "tea", "ate"],
     *   key2 : ["tan"]
     * }
     *
     * 5) 处理 "nat"
     * 词频与 "tan" 相同
     *
     * map = {
     *   key1 : ["eat", "tea", "ate"],
     *   key2 : ["tan", "nat"]
     * }
     *
     * 6) 处理 "bat"
     * 词频：
     * a b c d e f g h i j k l m n o p q r s t u v w x y z
     * 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0
     *
     * 又是一个新的 key
     *
     * map = {
     *   key1 : ["eat", "tea", "ate"],
     *   key2 : ["tan", "nat"],
     *   key3 : ["bat"]
     * }
     *
     * --------------------------------------------------
     * 最终结果：
     * [
     *   ["eat", "tea", "ate"],
     *   ["tan", "nat"],
     *   ["bat"]
     * ]
     *
     * --------------------------------------------------
     * 一句话总结：
     *
     * 把每个字符串压缩成“26 个字母出现次数的签名”，
     * 再把这个签名转成字符串作为 HashMap 的 key，
     * 相同 key 的字符串自然就会分到同一组。
     * </pre>
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> lists = new ArrayList<>();

        Map<String, List<String>> hashMap = new HashMap<>();

        for (int i = 0; i < strs.length; i++) {
            // 每一个单词的计数器
            int[] count = new int[26];
            String str = strs[i];
            // 统计每个字符出现次数
            for (int j = 0; j < str.length(); j++) {
                int position = str.charAt(j) - 'a';
                count[position]++;
            }
            // 把 count 数组转成唯一 key
            StringBuilder keyBuilder = new StringBuilder();
            for (int j = 0; j < count.length; j++) {
                keyBuilder.append("#").append(count[i]);
            }
            String key = keyBuilder.toString();

            hashMap.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }


        return new ArrayList<>(hashMap.values());
    }
}
