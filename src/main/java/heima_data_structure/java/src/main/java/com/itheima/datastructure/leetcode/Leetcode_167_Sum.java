package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.Arrays;

/**
 * <h3>2数之和</h3>
 */
public class Leetcode_167_Sum {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));
    }

    /*
        思路
         - 两个指针 i 和 j 分别指向最左侧和最右侧的数字
         - 它俩指向的数字和与 target 相比
            - 小于 target i++ 向右找
            - 大于 target j-- 向左找
            - 等于 target 找到
     */
    static public int[] twoSum(int[] numbers, int target) {
        int i = 0;
        int j = numbers.length - 1;
        while (i < j) {
            int sum = numbers[i] + numbers[j];
            if (sum < target) {
                i++;
            } else if (sum > target) {
                j--;
            } else {
                break;
            }
        }
        return new int[]{i + 1, j + 1};
    }


}
