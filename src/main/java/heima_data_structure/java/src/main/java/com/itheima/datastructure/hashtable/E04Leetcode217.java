package heima_data_structure.java.src.main.java.com.itheima.datastructure.hashtable;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 判断有没有重复元素
 */
public class E04Leetcode217 {
    /*
        输入：nums = [1,2,3,1]
        输出：true

        输入：nums = [1,2,3,4]
        输出：false

        [1,2,3]
     */
    public boolean containsDuplicate1(int[] nums) { // 6ms
        HashMap<Integer, Object> map = new HashMap<>(nums.length * 2);
        Object value = new Object();
        for (int key : nums) {
            Object put = map.put(key, value);
            if (put != null) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDuplicate(int[] nums) { // 5ms
        HashSet<Integer> set = new HashSet<>();
        for (int key : nums) {
            if (!set.add(key)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        E04Leetcode217 e04 = new E04Leetcode217();
        System.out.println(e04.containsDuplicate(new int[]{1, 2, 3, 1}));
        System.out.println(e04.containsDuplicate(new int[]{1, 2, 3, 4}));
    }
}
