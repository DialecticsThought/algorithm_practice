import java.util.*;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/7 12:13
 */
public class LeetCode_126_WordLadder2 {


    /**
     * 利用 BFS 构造单词接龙的树，并在每一层打印当前树的结构。
     *
     * @param beginWord 起始单词
     * @param endWord   目标单词
     * @param wordList  单词字典
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        // 将 wordList 转换为 Set，加速判断是否存在
        Set<String> wordSet = new HashSet<>(wordList);
        // parentMap 记录每个单词的所有父节点（即它可以从哪些单词转换而来）
        // 父节点单词 如果在第n-1层 那么子节点单词在第n层
        Map<String, List<String>> parentMap = new HashMap<>();
        // 如果目标单词不在字典中，直接返回空结果
        if (!wordSet.contains(endWord)) {
            return res;
        }
        // currentLevel 存储当前 BFS 层的单词
        Set<String> currentLevel = new HashSet<>();
        currentLevel.add(beginWord);
        // visited 用于记录所有之前层次中出现过的单词，防止重复搜索
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        // 标记是否在当前层中找到了 endWord
        boolean found = false;
        /**
         * <pre>
         * eg:
         * beginWord: "hit"
         * endWord: "cog"
         * wordList: ["hot", "dot", "dog", "lot", "log", "cog"]
         * 字典（wordSet）为：{"hot", "dot", "dog", "lot", "log", "cog"}
         * 全局集合：
         *      visited：记录所有之前层中出现过的单词，初始包含 "hit"
         *      parentMap：记录每个单词的前驱（父节点），初始为空
         *      found：是否在某一层中找到了 endWord，初始为 false
         *
         * 我们使用一个集合 currentLevel 存储当前层的单词。
         * 初始时：currentLevel = {"hit"}
         * 树的状态：hit
         * Step 1: 处理 Level 0
         *  当前层：
         *      currentLevel = {"hit"}
         *      visited：{"hit"}
         *  处理 "hit"：
         *      将 "hit" 转换为字符数组：['h','i','t']
         *      遍历每个位置：
         *          位置 0（字母 'h'）：
         *              替换为 'a' → 生成 "ait" → 不在字典中
         *              替换为 'b' → "bit" → 不在字典
         *              …
         *              替换所有字母后，没有生成有效单词。
         *          位置 1（字母 'i'）：
         *              替换为 'a' → 生成 "hat" → 不在字典
         *              替换为 'b' → "hbt" → 不在字典
         *              替换为 'o' → 生成 "hot" → "hot" 在字典中
         *                  检查：visited 中还没有 "hot"
         *                  如果本层第一次出现 "hot"（localVisited 中也没有），则：
         *                      将 "hot" 加入 nextLevel 和 localVisited
         *                      在 parentMap 中记录："hot" 的前驱包含 "hit"
         *                      因为 "hot" ≠ "cog"，found 保持 false
         *              继续尝试其它字母，均不生成新有效单词。
         *          位置 2（字母 't'）：
         *              替换为其它字符，例如 'a' → "hia"、'b' → "hib"，均不在字典中
         *  处理完 "hit"后，本层生成的 nextLevel 为：
         *          nextLevel = {"hot"}
         *  同时，localVisited = {"hot"}，并且更新全局 visited 后：
         *          visited = {"hit", "hot"}
         *  将 currentLevel 更新为 nextLevel：
         *          currentLevel = {"hot"}
         * 树的状态：
         *      hit
         *       └─ hot
         * Step 2: 处理 Level 1
         *  当前层：
         *      currentLevel = {"hot"}
         *      visited：{"hit", "hot"}
         *      处理 "hot"：字符数组：['h','o','t']
         *          位置 0（字母 'h'）：
         *              替换为 'a' → "aot" → 不在字典
         *              替换为 'b' → "bot" → 不在字典
         *              …
         *              替换为 'd' → "dot" → 在字典中
         *                  "dot" 不在 visited，也未在本层出现过
         *                      添加 "dot" 到 nextLevel 和 localVisited
         *                      记录：parentMap.put("dot", ["hot"])
         *                      "dot" ≠ "cog"，found 保持 false
         *              替换其它字母均不生成有效单词。
         *          位置 1（字母 'o'）：
         *              尝试替换：例如 'a' → "hat" → 不在字典
         *              … 无有效单词生成
         *          位置 2（字母 't'）：
         *              替换为 'g' → "hog" → 不在字典
         *              替换为 't' 自身跳过
         *              替换为其它字母均不在字典
         *              此处注意：其实，针对 "hot" 位置 2，再没有其它有效单词
         *          另外：对于 "hot" 位置 0，除了产生 "dot"，还可以产生 "lot"：
         *              例如，当位置 0 替换为 'l' → "lot"
         *                  "lot" 在字典中
         *                  "lot" 不在 visited
         *                  添加 "lot" 到 nextLevel 和 localVisited
         *                  记录：parentMap.put("lot", ["hot"])
         *  处理完 "hot"后，本层生成的 nextLevel 为：nextLevel = {"dot", "lot"}
         *                 更新 visited：{"hit", "hot", "dot", "lot"}
         *                 更新 currentLevel：{"dot", "lot"}
         * 树的状态：
         *      hit
         *       └─ hot
         *           ├─ dot
         *           └─ lot
         * Step 3: 处理 Level 2
         *  当前层：
         *      currentLevel = {"dot", "lot"}
         *      visited：{"hit", "hot", "dot", "lot"}
         *      处理 "dot"：
         *          字符数组：`['d','o','t']`
         *          位置 0（字母 `'d'`）：
         *              替换为 `'h'` → `"hot"` → 已在 visited
         *              替换为其他字母，不生成有效单词
         *          位置 1（字母 `'o'`）：
         *              替换不生成新有效单词
         *          位置 2（字母 `'t'`）：
         *              替换为 `'g'` → `"dog"` → 在字典中
         *              "dog" 未出现在 visited
         *              添加 "dog" 到 nextLevel 和 localVisited
         *              记录：parentMap.put("dog", ["dot"])
         *              "dog" ≠ "cog"
         *   处理完本层后：
         *          nextLevel = {"dog", "log"}
         *          visited = {"hit", "hot", "dot", "lot", "dog", "log"}
         *          currentLevel = {"dog", "log"}
         * 树的状态：
         *      hit
         *       └─ hot
         *           ├─ dot
         *           │    └─ dog
         *           └─ lot
         *                └─ log
         * Step 4: 处理 Level 3
         *  当前层：
         *      currentLevel = {"dog", "log"}
         *      visited：{"hit", "hot", "dot", "lot", "dog", "log"}
         *      处理 "dog"：
         *          字符数组：['d','o','g']
         *      位置 0（字母 'd'）：
         *          替换为 'c' → "cog" → 在字典中
         *          "cog" 不在 visited
         *          添加 "cog" 到 nextLevel 和 localVisited
         *          记录：parentMap.put("cog", ["dog"])
         *          此时 "cog" 等于 endWord，因此 found 标记为 true
         *          其它位置不再影响结果（可能也不会产生其它有效单词）
         *      处理 "log"：
         *          字符数组：['l','o','g']
         *      位置 0（字母 'l'）：
         *          替换为 'c' → "cog" → 在字典中
         *          注意：虽然 "cog"已经在本层通过 "dog"添加过，但这里依然检查：
         *          如果 "cog" 已在当前层（localVisited中已存在），则只更新父节点：
         *          更新 parentMap：为 "cog" 添加另一个父节点 "log"
         *          "cog" 等于 endWord，found 依然为 true
         *  处理完本层后：
         *          nextLevel = {"cog"}
         *          visited = {"hit", "hot", "dot", "lot", "dog", "log", "cog"}
         *          currentLevel = {"cog"}
         *  因为此时 found 为 true，所以 BFS 循环结束。
         *  树的最终状态：
         *          hit
         *          └─ hot
         *              ├─ dot
         *              │    └─ dog
         *              │         └─ cog
         *              └─ lot
         *                   └─ log
         *                        └─ cog
         * </pre>
         *
         * BFS 每一层代表从 beginWord 经过一次字符变换得到的所有单词。也就是说：
         *      第 0 层只有 beginWord 本身（0 次变换）。
         *      第 1 层是从 beginWord 经过 1 次变换得到的所有单词。
         *      第 n 层则是从 beginWord 经过 n 次变换得到的单词集合。
         * 每一层的变动方式如下：
         *      对于第 n 层的所有单词，它们是从第 n-1 层的单词经过 一次字符变换 产生的。
         *      具体来说，对于第 n-1 层的每个单词，从索引 0 到最后一个字符，尝试用 'a' 到 'z' 替换该字符，然后检查新单词是否存在于单词表（wordList/wordSet）中。
         *      只有在单词表中的单词才会被加入到第 n 层。
         */
        while (!currentLevel.isEmpty() && !found) {
            // 用于存储下一层的单词
            Set<String> nextLevel = new HashSet<>();
            // 记录当前层新发现的单词，防止本层重复加入
            Set<String> localVisited = new HashSet<>();
            // 遍历当前层的所有单词
            for (String word : currentLevel) {
                char[] charArray = word.toCharArray();
                // 遍历 该单词的 每一个字符
                for (int i = 0; i < charArray.length; i++) {
                    // 把该字符 替换掉成其他a~z的字符
                    // 先得到原始字符 方便还原
                    char original = charArray[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (original == c) {
                            continue;
                        }
                        //真正替换
                        charArray[i] = c;
                        String newWord = new String(charArray);
                        // 替换之后 查看是否该字符串在字典中出现
                        if (wordSet.contains(newWord)) { // 确保 newWord 在字典中
                            if (!visited.contains(newWord)) { // 避免重复访问
                                // 如果本层首次发现 newWord
                                if (!localVisited.contains(newWord)) {
                                    nextLevel.add(newWord);
                                    localVisited.add(newWord);
                                }
                            }
                            // 在 parentMap 记录 newWord 的前驱为当前单词
                            parentMap.computeIfAbsent(newWord, value -> new ArrayList<>()).add(word);
                            // 如果当前生成的单词是目标单词
                            if (newWord.equals(endWord)) {
                                found = true;
                            }
                        }
                    }
                    // 和上面对应 还原 尝试下一个字符
                    charArray[i] = original;
                }
            }
            // 将本层发现的单词加入全局 visited，避免在更深层重复
            visited.addAll(localVisited); // 这一层的所有新发现的单词标记为访问过
            // 进入下一层
            currentLevel = new HashSet<>(nextLevel);
            nextLevel.clear(); // 清空，避免累积
        }
        // 如果找到了目标单词，使用 DFS 回溯构造所有最短路径
        if (found) {
            List<String> path = new ArrayList<>();
            backtrack(res, path, endWord, beginWord, parentMap);
        }
        return res;
    }

    /**
     * DFS 递归回溯，构造所有从 beginWord 到 endWord 的最短路径
     * TODO
     *  自底向上回溯 树就是parentMap 已经构造好了
     *  回溯的终点就是beginword
     *  一定有从endword到beginword的路径,把所有路径找到
     *
     * @param res       存储所有符合要求的路径
     * @param path      当前构造的路径（从 endWord 向 beginWord 回溯）
     * @param word      当前回溯到的单词
     * @param beginWord 起始单词
     * @param parentMap 父节点映射（记录每个单词的前驱单词列表）
     */
    public static void backtrack(List<List<String>> res, List<String> path,
                                 String word, String beginWord, Map<String, List<String>> parentMap) {
        // 当前遍历到word
        // 把遍历到的放入路径集合中
        path.add(word);
        // 回溯到起始单词，说明构造了一条完整路径 这就是base case
        if (word.equals(beginWord)) {
            // 由于构造的路径是从目标到起点，所以反转后存入结果
            // TODO 不能 直接把path 放入res
            List<String> temp = new ArrayList<>(path);
            Collections.reverse(temp);
            res.add(new ArrayList<>(temp));  // ✅ 深拷贝，防止后续修改
        } else {
            // 否则继续回溯，对当前单词的所有前驱单词递归调用
            // 找到 该word的所有父节点
            if (parentMap.containsKey(word)) {
                for (String parent : parentMap.get(word)) {
                    //对父节点执行相同的操作
                    backtrack(res, path, parent, beginWord, parentMap);
                }
            }
        }
        //TODO 回溯的经典操作，恢复现场
        path.remove(path.size() - 1);
        //TODO 恢复完现场 返回到上层
    }
}
