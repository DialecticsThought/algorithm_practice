package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * 按出现频率排序 数据范围在 [-100, 100] 内
 */
public class Code_1636_E02 {
    public int[] frequencySort(int[] nums) {
        // 1. 统计出现频率
        int[] count = new int[201];
        for (int i : nums) {
            count[i + 100]++;
        }
        // 2. 比较器 按频率升序、再按数值降序
        return Arrays.stream(nums).boxed().sorted((a, b) -> {
            int af = count[a + 100];
            int bf = count[b + 100];
            if (af < bf) {
                return -1;
            } else if (af > bf) {
                return 1;
            } else {
                return b - a;
            }
        }).mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 3, 2};
        System.out.println(Arrays.toString(new Code_1636_E02().frequencySort(nums)));
    }
}
