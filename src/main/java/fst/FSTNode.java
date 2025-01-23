package fst;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author veritas
 * @Data 2025/1/6 19:13
 * <p>
 * 查询 'c' 的倒排列表：[Doc1, Doc2, Doc3, Doc4, Doc5, Doc6, Doc7, Doc8, Doc9, Doc10]
 * 查询 'a' 的倒排列表：[Doc1, Doc2, Doc3, Doc4, Doc5]
 * 查询 't' 的倒排列表：[Doc1, Doc2, Doc3, Doc4, Doc5, Doc6, Doc7, Doc8, Doc9, Doc10]
 * 查询单词 'cat' 的最终倒排列表：[Doc1, Doc2, Doc3, Doc4, Doc5, Doc6, Doc7, Doc8, Doc9, Doc10]
 * 查询单词 'cap' 的最终倒排列表：[Doc1, Doc2, Doc3, Doc4, Doc5]
 * 查询单词 'car' 的最终倒排列表：[Doc1, Doc2, Doc3]
 * <p>
 * 对于c a t
 * c、a、t 的 edgeOutputs 存储的内容和单词 cat 的倒排索引是一样的，但具体存储的内容是有层次和路径的。
 * 倒排列表在路径上的存储方式：
 * 在路径的每一层（c、a、t），存储的 edgeOutputs 是到达下一个节点所包含的文档 ID。
 * 最终的完整倒排列表（例如 cat -> [Doc1, Doc2, ..., Doc10]）只会存储在最终状态的 finalOutput 中
 * <p>
 * 1. 根节点 root 的 edgeOutputs
 * root.edgeOutputs.get('c') 存储从根节点到 c 的所有文档。
 * 对于单词集合 {cat, cap, car}：
 * c 是所有单词的开头，因此 root.edgeOutputs.get('c') = [Doc1, Doc2, ..., Doc10]。
 * 它包含了所有以 c 开头的单词的倒排列表。
 * 2. 节点 state1（对应字符 c）的 edgeOutputs
 * state1.edgeOutputs.get('a') 存储从 c 到 a 的所有文档。
 * 对于单词集合 {cat, cap, car}：
 * a 是所有单词的前缀 ca 的一部分，因此 state1.edgeOutputs.get('a') = [Doc1, Doc2, ..., Doc10]。
 * 3. 节点 state2（对应字符 a）的 edgeOutputs
 * state2.edgeOutputs.get('t') 存储从 a 到 t 的所有文档。
 * 对于单词 cat：
 * t 是 cat 的最后一个字符，因此 state2.edgeOutputs.get('t') = [Doc1, Doc2, ..., Doc10]。
 * 4. 节点 state3_t（对应字符 t）的 finalOutput
 * state3_t.finalOutput 存储的是完整单词 cat 的倒排列表。
 * 对于单词 cat：
 * finalOutput = [Doc1, Doc2, ..., Doc10]
 * <p>
 * <p>
 * 是的，c、a、t 的 edgeOutputs 存储的内容和单词 cat 的倒排索引是一样的，但具体存储的内容是有层次和路径的。
 * <p>
 * 倒排列表在路径上的存储方式：
 * 在路径的每一层（c、a、t），存储的 edgeOutputs 是到达下一个节点所包含的文档 ID。
 * 最终的完整倒排列表（例如 cat -> [Doc1, Doc2, ..., Doc10]）只会存储在最终状态的 finalOutput 中。
 * 更详细的存储逻辑
 * 1. 根节点 root 的 edgeOutputs
 * root.edgeOutputs.get('c') 存储从根节点到 c 的所有文档。
 * 对于单词集合 {cat, cap, car}：
 * c 是所有单词的开头，因此 root.edgeOutputs.get('c') = [Doc1, Doc2, ..., Doc10]。
 * 它包含了所有以 c 开头的单词的倒排列表。
 * 2. 节点 state1（对应字符 c）的 edgeOutputs
 * state1.edgeOutputs.get('a') 存储从 c 到 a 的所有文档。
 * 对于单词集合 {cat, cap, car}：
 * a 是所有单词的前缀 ca 的一部分，因此 state1.edgeOutputs.get('a') = [Doc1, Doc2, ..., Doc10]。
 * 3. 节点 state2（对应字符 a）的 edgeOutputs
 * state2.edgeOutputs.get('t') 存储从 a 到 t 的所有文档。
 * 对于单词 cat：
 * t 是 cat 的最后一个字符，因此 state2.edgeOutputs.get('t') = [Doc1, Doc2, ..., Doc10]。
 * 4. 节点 state3_t（对应字符 t）的 finalOutput
 * state3_t.finalOutput 存储的是完整单词 cat 的倒排列表。
 * 对于单词 cat：
 * finalOutput = [Doc1, Doc2, ..., Doc10]。
 * 存储内容对比
 * 以下是每一层节点的存储内容的对比：
 * <p>
 * 节点	字段	存储内容
 * 根节点 root	edgeOutputs.get('c')	[Doc1, Doc2, ..., Doc10] （所有以 c 开头的文档）
 * 节点 state1	edgeOutputs.get('a')	[Doc1, Doc2, ..., Doc10] （所有以 ca 开头的文档）
 * 节点 state2	edgeOutputs.get('t')	[Doc1, Doc2, ..., Doc10] （所有以 cat 开头的文档）
 * 节点 state3_t	finalOutput	[Doc1, Doc2, ..., Doc10] （完整单词 cat 的文档）
 * 为什么每层的内容看似一样？
 * 在这个例子中，单词 cat 的倒排列表确实在每一层路径上都有存储（c -> a -> t）。
 * 原因：
 * 每一层的存储是为了在路径查询过程中，能够快速找到与当前路径相关的倒排列表。
 * 只有在最终状态（state3_t）时，才将完整的倒排列表视为单词 cat 的结果。
 * <p>
 * 注意：每层的存储用途不同
 * 虽然看起来 c、a、t 的 edgeOutputs 和 cat 的最终倒排索引一样，但它们的用途不同：
 * <p>
 * 中间节点的 edgeOutputs：
 * <p>
 * 中间节点的 edgeOutputs 是为路径上的动态查询设计的，表示到下一个节点的所有可能文档。
 * 比如：
 * 查询以 ca 开头的所有文档，可以快速通过 state1.edgeOutputs.get('a') 得到 [Doc1, Doc2, ..., Doc10]。
 * 最终节点的 finalOutput：
 * <p>
 * 只有最终节点存储的 finalOutput 才表示单词本身的倒排索引，是最终查询结果。
 * 比如：
 * 查询单词 cat 的倒排索引，会直接访问 state3_t.finalOutput
 * <p>
 * 扩展：优化存储
 * 为了优化存储，可以减少每个节点上倒排列表的冗余
 * 使用引用共享
 * 如果中间节点和最终节点的倒排列表是一样的，可以让 edgeOutputs 指向 finalOutput 的引用，而不是复制完整列表。
 * 优点：
 * 节省存储空间。
 * 提高查询效率
 */
public class FSTNode<T> {
    private Map<Character, FSTNode<T>> transitions; // 转移规则
    private Map<Character, T> edgeOutputs;         // 边输出值（可以存储倒排列表等信息）
    private boolean isFinal;                                // 是否为最终状态
    private T finalOutput;                                   // 最终状态的输出值
    private int pass;                                           // 经过当前节点的路径数量

    public FSTNode() {
        this.transitions = new HashMap<>();

        this.edgeOutputs = new HashMap<>();
        this.isFinal = false;
        this.finalOutput = null;
        this.pass = 0;
    }

    public FSTNode(Map<Character, FSTNode<T>> transitions, Map<Character, T> edgeOutputs, boolean isFinal, T finalOutput, int pass) {
        this.transitions = transitions;
        this.edgeOutputs = edgeOutputs;
        this.isFinal = isFinal;
        this.finalOutput = finalOutput;
        this.pass = pass;
    }

    // Getter 和 Setter 方法
    public Map<Character, FSTNode<T>> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<Character, FSTNode<T>> transitions) {
        this.transitions = transitions;
    }

    public Map<Character, T> getEdgeOutputs() {
        return edgeOutputs;
    }

    public void setEdgeOutputs(Map<Character, T> edgeOutputs) {
        this.edgeOutputs = edgeOutputs;
    }

    public boolean getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public T getFinalOutput() {
        return finalOutput;
    }

    public void setFinalOutput(T finalOutput) {
        this.finalOutput = finalOutput;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    // 添加转移规则
    public void addTransition(char c, FSTNode<T> nextState, T output) {
        transitions.put(c, nextState);
        edgeOutputs.put(c, output);
    }


}
