package heima_data_structure.java.src.main.java.com.itheima.datastructure.hashtable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 出现次数最多的单词
 * <p>答案会唯一</p>
 */
public class E08Leetcode819 {
    /*
        1. 将 paragraph 截取为一个个单词
        2. 将单词加入 map 集合，单词本身作为 key，出现次数作为 value，避免禁用词加入
        3. 在 map 集合中找到 value 最大的，返回它对应的 key 即可
     */
    public String mostCommonWord1(String paragraph, String[] banned) { // 14ms
        String[] split = paragraph.toLowerCase().split("[^A-Za-z]+");
        Set<String> set = Set.of(banned);
        HashMap<String, Integer> map = new HashMap<>();
        for (String key : split) {
            if (!set.contains(key)) {
                map.compute(key, (k, v) -> v == null ? 1 : v + 1);
            }
        }
        Optional<Map.Entry<String, Integer>> optional = map.entrySet().stream().max(Map.Entry.comparingByValue());
        return optional.map(Map.Entry::getKey).orElse(null);
    }

    public String mostCommonWord2(String paragraph, String[] banned) { // 12ms
        String[] split = paragraph.toLowerCase().split("[^A-Za-z]+");
        Set<String> set = Set.of(banned);
        HashMap<String, Integer> map = new HashMap<>();
        for (String key : split) {
            if (!set.contains(key)) {
                map.compute(key, (k, v) -> v == null ? 1 : v + 1);
            }
        }
        int max = 0;
        String maxKey = null;
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            Integer value = e.getValue();
            if (value > max) {
                max = value;
                maxKey = e.getKey();
            }
        }
        return maxKey;
    }

    public String mostCommonWord(String paragraph, String[] banned) { // 4ms
        // bob hit a ball, ...
        // bob
        Set<String> set = Set.of(banned);
        HashMap<String, Integer> map = new HashMap<>();
        char[] chars = paragraph.toLowerCase().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char ch : chars) {
            if (ch >= 'a' && ch <= 'z') {
                sb.append(ch);
            } else {
                String key = sb.toString();
                if (!set.contains(key)) {
                    map.compute(key, (k, v) -> v == null ? 1 : v + 1);
                }
//                sb = new StringBuilder();
                sb.setLength(0);
            }
        }
        if (sb.length() > 0) {
            String key = sb.toString();
            if (!set.contains(key)) {
                map.compute(key, (k, v) -> v == null ? 1 : v + 1);
            }
        }
        System.out.println(map);

        int max = 0;
        String maxKey = null;
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            Integer value = e.getValue();
            if (value > max) {
                max = value;
                maxKey = e.getKey();
            }
        }
        return maxKey;
    }

    public static void main(String[] args) {
        E08Leetcode819 e08 = new E08Leetcode819();
        String key = e08.mostCommonWord("Bob hit a ball, the hit BALL flew far after it was hit.", new String[]{"hit"});
//        String key = e08.mostCommonWord("Bob", new String[]{"hit"});
        System.out.println(key); // ball
    }
}
