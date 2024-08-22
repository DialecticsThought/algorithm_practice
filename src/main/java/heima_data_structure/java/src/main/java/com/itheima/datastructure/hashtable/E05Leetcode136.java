package heima_data_structure.java.src.main.java.com.itheima.datastructure.hashtable;

import java.util.HashSet;

/**
 * 找出出现一次的数字
 * <p>除了某个元素只出现一次以外，其余每个元素均出现两次。</p>
 */
public class E05Leetcode136 {
    /*
        输入：nums = [2,2,1]
        输出：1        1

        输入：nums = [4,1,2,1,2]
        输出：4        4

        思路2
        1. 任何相同的数字异或，结果都是 0
        2. 任何数字与 0 异或，结果是数字本身
     */
    public int singleNumber(int[] nums) {
        int num = nums[0];
        for (int i = 1; i < nums.length; i++) {
            num = num ^ nums[i];
        }
        return num;
    }
    /*
        思路1

        1. 准备一个 Set 集合，逐一放入数组元素
        2. 遇到重复的，则删除
        3. 最后留下来的，就是那个没有重复的数字

     */
    public int singleNumber1(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                set.remove(num);
            }
        }
        return set.toArray(new Integer[0])[0];
    }

    public static void main(String[] args) {
        E05Leetcode136 e06 = new E05Leetcode136();
        System.out.println(e06.singleNumber(new int[]{2, 2, 1}));
        System.out.println(e06.singleNumber(new int[]{4, 1, 2, 1, 2}));
    }
}
