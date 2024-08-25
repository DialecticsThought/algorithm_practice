import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author veritas
 * @Data 2024/8/24 17:09
 */
public class Code_30_SubstringWithConcatenationOfAllWords {
    /**
     * String s = "barfoofoobarthefoobarman";
     * String[] words = {"bar", "foo", "the"};
     * 初始化：
     * wordLen = 3 （每个单词的长度）
     * wordCount = 3 （单词数量）
     * totalLen = 9 （所有单词串联起来的总长度 = 3 * 3）
     * wordMap = {"bar": 1, "foo": 1, "the": 1} （构建单词频率表）
     *
     * 外层循环：
     * 外层循环遍历i从0到2，尝试不同的起始点。
     * 第一轮：起始点 i = 0
     * left = 0
     * currentMap = {}（当前窗口的单词频率表）
     * count = 0（当前窗口的单词数量）
     * 内层循环（滑动窗口从j = 0开始，每次跳跃wordLen = 3）：
     * 第一次迭代 (j = 0):
     * String s = "b a r f o o f o o b a r t h e f o o b a r m a n";
     *             ↑↑  ↑
     *          left   right
     * 提取子串word = "bar"
     * "bar"在wordMap中，加入currentMap = {"bar": 1}，count = 1
     * 因为count = 3，并不等于wordCount = 3，继续下一步
     *
     * 第二次迭代 (j = 3):
     * String s = "b a r f o o f o o b a r t h e f o o b a r m a n";
     *             ↑     ↑   ↑
     *             left   right
     * 提取子串word = "foo"
     * "foo"在wordMap中，加入currentMap = {"bar": 1, "foo": 1}，count = 2
     * 因为count = 2，并不等于wordCount = 3，继续下一步
     *
     * 第三次迭代 (j = 6)
     * String s = "b a r f o o f o o b a r t h e f o o b a r m a n";
     *             ↑           ↑   ↑
     *            left            right
     * 提取子串word = "foo"
     * "foo"在wordMap中，加入currentMap = {"bar": 1, "foo": 2}，count = 3
     * "foo"的频率超过wordMap中的频率，窗口左边界left = 0右移：
     * 删除left = "bar"，更新currentMap = {"bar": 0, "foo": 2}，count = 2 ★★★★★★★★
     * left = 3
     * 继续下一步
     *
     * 第四次迭代 (j = 9):
     * String s = "b a r f o o f o o b a r t h e f o o b a r m a n";
     *                   ↑           ↑   ↑
     *                   left           right
     * 提取子串word = "bar"
     * "bar"在wordMap中，加入currentMap = {"bar": 1, "foo": 2}，count = 3
     * "bar"的频率与wordMap匹配，且count = 3，添加起始索引left = 6到结果中
     * 窗口左边界left = 3右移：
     * 删除left = "foo"，更新currentMap = {"bar": 1, "foo": 1}，count = 2
     * left = 6
     * 继续下一步
     *
     * 第五次迭代 (j = 12):
     * String s = "b a r f o o f o o b a r t h e f o o b a r m a n";
     *                         ↑           ↑   ↑
     *                       left              right
     * 提取子串word = "the"
     * "the"在wordMap中，加入currentMap = {"bar": 1, "foo": 1, "the": 1}，count = 3
     * currentMap与wordMap匹配，且count = 3，添加起始索引left = 9到结果中
     * 窗口左边界left = 6右移：
     * 删除left = "bar"，更新currentMap = {"bar": 0, "foo": 1, "the": 1}，count = 2
     * left = 9
     * 继续下一步
     *
     * 第六次迭代 (j = 15):
     * String s = "b a r f o o f o o b a r t h e f o o b a r m a n";
     *                         ↑                 ↑   ↑
     *                       left                   right
     * 提取子串word = "foo"
     * "foo"在wordMap中，加入currentMap = {"bar": 0, "foo": 2, "the": 1}，count = 3
     * "foo"的频率超过wordMap中的频率，窗口左边界left = 9右移：
     * 删除left = "foo"，更新currentMap = {"bar": 0, "foo": 1, "the": 1}，count = 2
     * left = 12
     * 继续下一步
     *
     * 第七次迭代 (j = 18):
     * String s = "b a r f o o f o o b a r t h e f o o b a r m a n";
     *                         ↑                       ↑   ↑
     *                       left                        right
     * 提取子串word = "bar"
     * "bar"在wordMap中，加入currentMap = {"bar": 1, "foo": 1, "the": 1}，count = 3
     * currentMap与wordMap匹配，且count = 3，添加起始索引left = 12到结果中
     *
     * 第二轮：起始点 i = 1
     * 初始化：
     * left = 1
     * currentMap = {}（当前窗口的单词频率表）
     * count = 0（当前窗口的单词数量）
     * 内层循环（滑动窗口从 j = 1 开始，每次跳跃 wordLen = 3）：
     *
     * 第一次迭代 (j = 1)：
     * 提取子串 word = "arf"
     * String s = "b a r f o o f o o b a r t h e f o o b a r m a n";
     *               ↑↑  ↑
     *              left   right
     * "arf" 不在 wordMap 中，窗口重置。
     * left = 4 （窗口左边界移动到下一个可能的起始位置）
     * ........
     *
     *
     * 第三轮：起始点 i = 2
     * 初始化：
     * left = 2
     * currentMap = {}（当前窗口的单词频率表）
     * count = 0（当前窗口的单词数量）
     * 内层循环（滑动窗口从 j = 2 开始，每次跳跃 wordLen = 3）：
     *
     * 第一次迭代 (j = 2)：
     * String s = "b a r f o o f o o b a r t h e f o o b a r m a n";
     *                 ↑↑  ↑
     *                left   right
     * 提取子串 word = "rfo"
     * "rfo" 不在 wordMap 中，窗口重置。
     * left = 5 （窗口左边界移动到下一个可能的起始位置）
     *
     * ........
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res;
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            res = result;
        } else {// 单词长度
            int wordLen = words[0].length();// 单词数量
            int wordCount = words.length;// 所有单词的总长度
            int totalLen = wordLen * wordCount;// 构建单词频率表
            Map<String, Integer> wordMap = new HashMap<>();
            for (String word : words) {
                wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
            }// 遍历每个可能的起始点（0 到 wordLen - 1）

            // 通过i从0到wordLen - 1，尝试不同的起点。这样可以确保从每个可能的位置开始都能覆盖所有可能的串联情况
            for (int i = 0; i < wordLen; i++) {
                // 当前窗口的左边界
                int left = i;
                // 记录当前窗口内单词的数量
                int count = 0;
                // 记录当前窗口中单词的频率
                Map<String, Integer> currentMap = new HashMap<>();
                /**
                 * 窗口滑动：
                 * 使用j按单词长度逐步移动窗口，每次截取一个word。
                 * 如果word在wordMap中，则将其加入currentMap并增加count。
                 * 如果currentMap中某个单词的频率超过了wordMap中的频率，则需要缩小窗口，即移动left，直到窗口内的频率恢复正常。
                 */
                // 移动滑动窗口
                for (int j = i; j <= s.length() - wordLen; j += wordLen) {
                    String word = s.substring(j, j + wordLen);
                    if (wordMap.containsKey(word)) {
                        currentMap.put(word, currentMap.getOrDefault(word, 0) + 1);
                        count++;
                        // 如果当前单词的频率超过了所需频率，缩小窗口
                        while (currentMap.get(word) > wordMap.get(word)) {
                            String leftWord = s.substring(left, left + wordLen);
                            currentMap.put(leftWord, currentMap.get(leftWord) - 1);
                            count--;
                            left += wordLen;
                        }
                        // 如果窗口内单词数目与wordCount相同，说明找到了一个有效起点
                        if (count == wordCount) {
                            result.add(left);
                        }
                    } else {
                        // 如果碰到不在wordMap中的单词，重置窗口
                        currentMap.clear();
                        count = 0;
                        left = j + wordLen;
                    }
                }
            }
            res = result;
        }

        return res;
    }

    public static void main(String[] args) {
        String s = "barfoothefoobarman";
        String[] words = {"foo", "bar"};
    }
}
