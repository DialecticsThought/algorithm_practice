package algorithmbasic2020_master.class059;

// 来自网易
// 给定一个正数数组arr，表示每个小朋友的得分
// 任何两个相邻的小朋友，如果得分一样，怎么分糖果无所谓，但如果得分不一样，分数大的一定要比分数少的多拿一些糖果
// 假设所有的小朋友坐成一个环形，返回在不破坏上一条规则的情况下，需要的最少糖果数
public class Code02_CircleCandy {
    /**
     * [3,4,2,2] 小朋友对应下标是[0,1,2,3]
     * 0号小朋友给1个糖果 1号小朋友给2个糖果 2号小朋友给1个糖果 3好小朋友和2号小朋友得分一样 给1个糖果
     * 1号小朋友和0号小朋友是相邻的关系 并且1号小朋友得分比0号小朋友多 给2个是正常的
     * [1,1,1,3,2,1,1] 小朋友对应下标是[0,1,2,3,4,5,6]
     * 0号小朋友给1个糖果 1号小朋友和0号相邻 且 得分 相同 给1个糖果
     * 同理 2号小朋友给1个糖果
     * 6号小朋友给1个糖果 6号小朋友和5号相邻 且 得分 相同 给1个糖果
     * 4号小朋友和5号相邻 并且 4号小朋友得分高 5号给1个糖果   4号起码给2个
     * 3号小朋友和4 号相邻 并且 3号小朋友得分高 4号给2个糖果  3号起码给3个
     */
    public static int minCandy(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return 1;
        }
        int n = arr.length;
        int minIndex = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] <= arr[lastIndex(i, n)] && arr[i] <= arr[nextIndex(i, n)]) {
                minIndex = i;
                break;
            }
        }
        int[] nums = new int[n + 1];
        for (int i = 0; i <= n; i++, minIndex = nextIndex(minIndex, n)) {
            nums[i] = arr[minIndex];
        }
        int[] left = new int[n + 1];
        left[0] = 1;
        for (int i = 1; i <= n; i++) {
            left[i] = nums[i] > nums[i - 1] ? (left[i - 1] + 1) : 1;
        }
        int[] right = new int[n + 1];
        right[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            right[i] = nums[i] > nums[i + 1] ? (right[i + 1] + 1) : 1;
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.max(left[i], right[i]);
        }
        return ans;
    }

    public static int nextIndex(int i, int n) {
        return i == n - 1 ? 0 : (i + 1);
    }

    public static int lastIndex(int i, int n) {
        return i == 0 ? (n - 1) : (i - 1);
    }

    public static void main(String[] args) {
        int[] arr = {3, 4, 2, 3, 2};
        System.out.println(minCandy(arr));
    }

}
