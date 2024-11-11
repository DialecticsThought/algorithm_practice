
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiahao.liu
 * @description
 * @date 2024/11/11 14:10
 */
public class FlattenByJava {
    /**
     * 对外的入口方法，创建结果列表并开始递归
     *
     * @param originalList
     * @return
     */
    public static List<Object> flatten(List originalList) {
        // 创建一个新的空列表，用来存放结果
        List<Object> result = new ArrayList<Object>();
        // 调用 doFlatten 方法进行递归操作，传入原始列表和结果列表
        return doFlatten(originalList, result);
    }

    /**
     * 递归方法，接收原始列表和输出列表，递归处理嵌套结构
     *
     * @param orginalList
     * @param outputList
     * @return
     */
    public static List<Object> doFlatten(List orginalList, List<Object> outputList) {
        // 递归的“递”开始：如果原始列表为空，则不处理
        if (orginalList != null) {
            // 遍历 originalList 中的每个元素
            if (orginalList.size() != 0) {
                for (Object obj : orginalList) {
                    // 如果 obj 是一个 List 类型的嵌套列表
                    if (obj instanceof List) {
                        // 递归调用 doFlatten，传入当前的子列表，继续展开
                        // 递归的“递”：将子列表递归展开并合并到 outputList 中
                        doFlatten((List) obj, outputList);
                    } else {
                        // 如果 obj 不是一个列表元素，则直接将它添加到输出列表中
                        // 将非 List 元素添加到外部创建的 flattenedList 中
                        outputList.add(obj);// 归：将最终元素添加到输出列表中
                    }
                }
            }
        }
        // 返回最终展开后的结果列表
        return outputList;  // 归：递归到最底层，返回已展开的结果列表
    }

    public static void main(String[] args) {
        // 示例嵌套列表
        List<Object> nestedList = List.of(1, List.of(2, List.of(3, 4)), 5);

        // 调用 flatten 方法并打印展开后的结果
        List<Object> flattened = flatten(nestedList);
        // 输出: [1, 2, 3, 4, 5]
        System.out.println(flattened);
    }

}
