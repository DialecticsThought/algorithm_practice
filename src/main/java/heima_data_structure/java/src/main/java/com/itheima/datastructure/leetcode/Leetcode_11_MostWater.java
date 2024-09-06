package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>盛最多水的容器</h3>
 */
public class Leetcode_11_MostWater {
    static int maxArea(int[] height) {
        int i = 0;
        int j = height.length - 1;
        int max = 0;
        while (i < j) {
            if (height[i] < height[j]) {
                int area = (j - i) * height[i];
                max = Math.max(max, area);
                i++;
            } else {
                int area = (j - i) * height[j];
                max = Math.max(max, area);
                j--;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7})); // 49
//        System.out.println(maxArea(new int[]{2,1})); // 1
    }
}
