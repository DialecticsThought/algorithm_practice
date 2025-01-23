package fst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2025/1/6 19:14
 */
public class FST<T> {
    private final FSTNode<T> root = new FSTNode<>(); // 根节点

    /*
     * 输入一组单词，逐个构建 FST。
     * 每个单词拆解为路径，插入节点，并更新 pass 和其他属性
     *
     * */
    public void buildFST(List<String> words) {
        for (String word : words) {
            addWord(word, null); // 假设没有倒排列表信息
        }
    }

    /*
     * 插入一个新单词，并更新路径上的节点和相关信息（如 pass、edgeOutputs 等）。
     * 如果单词已存在，可以选择覆盖或忽略
     * */
    public void addWord(String word, T value) {
        FSTNode<T> currentNode = root; // 从根节点开始
        for (char c : word.toCharArray()) {
            // 更新路径计数
            currentNode.setPass(currentNode.getPass() + 1);

            // 检查当前节点的转移是否存在
            if (!currentNode.getTransitions().containsKey(c)) {
                // 插入新节点
                FSTNode<T> newNode = new FSTNode<>();
                currentNode.getTransitions().put(c, newNode);

                // 更新 edgeOutputs，假设这里直接存储 value，或者自定义逻辑填充
                currentNode.getEdgeOutputs().put(c, value);
            } else {
                // 如果边已经存在，可以选择更新 edgeOutputs（例如累加统计信息）
                // 注意：这里假设 T 支持特定的操作，例如累加倒排列表等
                T existingValue = currentNode.getEdgeOutputs().get(c);
                if (existingValue instanceof PostingList && value instanceof PostingList) {
                    PostingList existingPosting = (PostingList) existingValue;
                    PostingList newPosting = (PostingList) value;

                    // 合并文档列表（例如去重合并）
                    List<String> mergedDocs = new ArrayList<>(existingPosting.getDocuments());
                    for (String doc : newPosting.getDocuments()) {
                        if (!mergedDocs.contains(doc)) {
                            mergedDocs.add(doc);
                        }
                    }
                    currentNode.getEdgeOutputs().put(c, (T) new PostingList(mergedDocs, mergedDocs.size()));
                }
            }

            // 移动到下一个节点
            currentNode = currentNode.getTransitions().get(c);
        }

        // 设置最终节点
        currentNode.setIsFinal(true); // 标记为最终节点
        currentNode.setFinalOutput(value); // 设置单词的最终输出值
    }


    public boolean deleteWord(String word) {
        return deleteWord(root, word, 0);
    }

    /*
     * 移除一个完整单词，更新路径上的 pass，并清理不再被使用的节点。
     * 如果某个节点的 pass == 0，可以安全删除该节点
     * */
    private boolean deleteWord(FSTNode<T> node, String word, int index) {
        if (index == word.length()) {
            if (!node.getIsFinal()) {
                return false; // 单词不存在
            }
            node.setIsFinal(false); // 取消最终状态
            node.setFinalOutput(null); // 清除最终输出
            return node.getTransitions().isEmpty(); // 如果没有子节点，返回 true（可以删除当前节点）
        }

        char c = word.charAt(index);
        FSTNode<T> nextNode = node.getTransitions().get(c);
        if (nextNode == null) {
            return false; // 路径不存在
        }

        boolean shouldDelete = deleteWord(nextNode, word, index + 1);
        if (shouldDelete) {
            node.getTransitions().remove(c); // 删除当前路径
        }

        // 更新 pass，并决定是否删除当前节点
        int pass = node.getPass();
        node.setPass(pass--);
        return !node.getIsFinal() && node.getPass() == 0 && node.getTransitions().isEmpty();
    }

    /*
     * 查询一个完整单词是否存在于 FST 中。
     * 返回对应的 finalOutput，如倒排列表
     *
     * */
    public T searchWord(String word) {
        FSTNode<T> currentNode = root; // 从当前节点（root）开始
        for (char c : word.toCharArray()) {
            if (!currentNode.getTransitions().containsKey(c)) {
                return null; // 单词不存在
            }
            currentNode = currentNode.getTransitions().get(c); // 移动到下一个节点
        }
        return currentNode.getIsFinal() ? currentNode.getFinalOutput() : null; // 返回最终输出
    }

    /*
     * 查询以某个前缀开头的所有单词或路径信息。
     * 返回路径上的统计信息或可能的结果列表
     *
     * */
    public int searchPrefix(String prefix) {
        FSTNode<T> currentNode = root; // 从当前节点（root）开始
        for (char c : prefix.toCharArray()) {
            if (!currentNode.getTransitions().containsKey(c)) {
                return 0; // 前缀不存在
            }
            currentNode = currentNode.getTransitions().get(c); // 移动到下一个节点
        }
        return currentNode.getPass(); // 返回前缀路径的统计次数
    }

    /*
     * 返回 FST 中的所有完整单词数量。
     * 遍历所有叶子节点，统计 isFinal == true 的节点数量
     * */
    private int countWords(FSTNode<T> node) {
        int count = node.getIsFinal() ? 1 : 0; // 如果是最终节点，计数 +1
        for (FSTNode<T> child : node.getTransitions().values()) {
            count += countWords(child); // 递归统计子节点
        }
        return count;
    }

    // 可视化整棵树
    public void visualizeFST() {
        visualizeFST(root, "");
    }

    private void visualizeFST(FSTNode<T> node, String prefix) {
        if (node.getIsFinal()) {
            System.out.println(prefix + " -> " + node.getFinalOutput());
        }
        for (var entry : node.getTransitions().entrySet()) {
            visualizeFST(entry.getValue(), prefix + entry.getKey());
        }
    }

    public static <T> int countDocuments(FSTNode<T> node) {
        int totalDocs = 0;

        // 遍历 edgeOutputs
        for (T output : node.getEdgeOutputs().values()) {
            if (output instanceof PostingList) {
                PostingList postingList = (PostingList) output;
                totalDocs += postingList.getDocuments().size();
            }
            // 可以扩展其他类型的处理逻辑
        }

        // 检查 finalOutput
        if (node.getIsFinal() && node.getFinalOutput() instanceof PostingList) {
            PostingList postingList = (PostingList) node.getFinalOutput();
            totalDocs += postingList.getDocuments().size();
        }

        return totalDocs;
    }

    public FSTNode<T> getRoot() {
        return root;
    }

    public static void main(String[] args) {
        // 创建 FST
        FST<PostingList> fst = new FST<>();
        // 创建一个 FSTNode 根节点
        FSTNode<PostingList> root = new FSTNode<>();


        // 添加单词及其倒排列表
        fst.addWord("cat", new PostingList(Arrays.asList("Doc1", "Doc2", "Doc3"), 3));
        fst.addWord("cap", new PostingList(Arrays.asList("Doc4", "Doc5"), 2));
        fst.addWord("car", new PostingList(Arrays.asList("Doc6", "Doc7", "Doc8"), 3));


        // 测试统计文档数量的方法
        System.out.println("统计根节点的文档数量（总共应该是 0，因为根节点没有倒排列表）：");
        System.out.println("根节点文档数量：" + countDocuments(fst.getRoot()));

        System.out.println("\n统计 'c' 节点的文档数量（总共应该是 8 个文档）：");
        FSTNode<PostingList> nodeC = fst.getRoot().getTransitions().get('c');
        System.out.println("节点 'c' 的文档数量：" + countDocuments(nodeC));

        System.out.println("\n统计 'ca' 节点的文档数量（总共应该是 8 个文档）：");
        FSTNode<PostingList> nodeCA = nodeC.getTransitions().get('a');
        System.out.println("节点 'ca' 的文档数量：" + countDocuments(nodeCA));

        System.out.println("\n统计 'cat' 节点的文档数量（总共应该是 3 个文档）：");
        FSTNode<PostingList> nodeCAT = nodeCA.getTransitions().get('t');
        System.out.println("节点 'cat' 的文档数量：" + countDocuments(nodeCAT));

        System.out.println("\n统计 'cap' 节点的文档数量（总共应该是 2 个文档）：");
        FSTNode<PostingList> nodeCAP = nodeCA.getTransitions().get('p');
        System.out.println("节点 'cap' 的文档数量：" + countDocuments(nodeCAP));

        System.out.println("\n统计 'car' 节点的文档数量（总共应该是 3 个文档）：");
        FSTNode<PostingList> nodeCAR = nodeCA.getTransitions().get('r');
        System.out.println("节点 'car' 的文档数量：" + countDocuments(nodeCAR));

        fst.visualizeFST();
    }
}
