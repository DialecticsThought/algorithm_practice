package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

@FunctionalInterface
public interface TriConsumer {

    void accept(int popValue, int popIndex, int peekIndex);
}
