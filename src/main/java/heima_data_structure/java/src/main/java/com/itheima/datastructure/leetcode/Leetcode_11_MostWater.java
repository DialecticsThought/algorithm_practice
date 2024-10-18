package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>盛最多水的容器</h3>
 */
public class Leetcode_11_MostWater {
    /**
     * 初始
     * [1, 8, 6, 2, 5, 4, 8, 3, 7]
     *  i                       j
     *  先固定 i
     *  一开始 容量是 1*8  因为j-i=8
     *  j--
     *  容量是 1*7  因为j-i=7
     *  ...
     *  发现距离变短的情况下，改动了最高的1挡板 容量一定减少
     *  所以 不需要这么尝试
     *
     *  回到初始
     *  [1, 8, 6, 2, 5, 4, 8, 3, 7]
     *   i                       j
     *  一开始 容量是 1*8  因为j-i=8
     *  发现 arr[i]<arr[j]
     *  i++
     *  容量是 7*7  因为j-i=7  7=math.min(arr[i],arr[j])
     *  .....
     *  两侧挡板都应该往中间缩减距离 ，每一次调整的都是最小的那一个挡板  ☆☆☆☆☆☆☆☆☆☆
     * @param height
     * @return
     */
    static int maxArea(int[] height) {
        int i = 0;
        int j = height.length - 1;
        // 最终结果  初始值 = 0
        int max = 0;
        while (i < j) {
            if (height[i] < height[j]) {
                // 得到当前高度
                int area = (j - i) * height[i];
                // 比大小
                max = Math.max(max, area);
                // height[i] < height[j] 移动较小的挡板 i
                i++;
            } else {
                // 得到当前高度
                int area = (j - i) * height[j];
                max = Math.max(max, area);
                // height[i] > height[j] 移动较小的挡板 j
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
