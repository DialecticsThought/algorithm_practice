package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.Arrays;

public class Leetcode_283_MoveZeroes {
    static void moveZeroes(int[] nums) {
        int i = 0;
        int j = 0;
        while (j < nums.length) {
            if (nums[j] != 0) {
                int t = nums[i];
                nums[i] = nums[j];
                nums[j] = t;
                i++;
            }
            j++;
        }
    }

    /**
     * <pre>
     *
     *  核心想法：
     *      fast 从左到右扫描
     *      slow 指向“下一个应该放非0的位置”
     * nums = [0,1,0,3,12]
     * 初始: slow=0
     * fast=0: nums[0]=0 -> 跳过
     *
     * fast=1: nums[1]=1 -> nums[slow]=1 => nums[0]=1, slow=1
     *
     * fast=2: nums[2]=0 -> 跳过
     *
     * fast=3: nums[3]=3 -> nums[1]=3, slow=2
     *
     * fast=4: nums[4]=12 -> nums[2]=12, slow=3
     *
     * 此时前面变成: [1,3,12  |  3,12]（后面先别管）
     * 再把 slow=3 之后填 0 => [1,3,12,0,0]
     * </pre>
     *
     * @param nums
     */
    public void func(int[] nums) {
        // 当前遍历的指针
        int fast = 0;
        // TODO 下一个应该放非0的位置
        int slow = 0;
        for (fast = 0; fast < nums.length; fast++) {
            // 判nums[fast]是不是0
            if (nums[fast] != 0) {
                // 当前遍历的数字 赋值到slow
                nums[slow] = nums[fast];
                slow++;
            }
        }
        for (int i = 1; i <= slow; i++) {
            nums[nums.length - i] = 0;
        }
    }



    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }
}
