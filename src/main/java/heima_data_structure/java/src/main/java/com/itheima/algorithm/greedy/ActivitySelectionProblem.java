package heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy;

import java.util.*;

/**
 * <h3>活动选择问题 - 贪心解法</h3>
 * <p>Leetcode 435 无重叠区间本质就是活动选择问题</p>
 */
public class ActivitySelectionProblem {

    /*
        要在一个会议室举办 n 个活动
        - 每个活动有它们各自的起始和结束时间
        - 找出在时间上互不冲突的活动组合，能够最充分利用会议室（举办的活动次数最多）

        例1
            0   1   2   3   4   5   6   7   8   9
                |-------)
                    |-------)
                        |-------)
        例2
            0   1   2   3   4   5   6   7   8   9
                |---)
                        |---)
            |-----------------------)
                                |-------)
                                            |---)
                                |---------------)

        几种贪心策略
        1. 优先选择持续时间最短的活动
            0   1   2   3   4   5   6   7   8   9
        1       |---------------)                      选中
        2                   |-------)
        3                       |---------------)      选中

        2. 优先选择冲突最少的活动
        编号 0   1   2   3   4   5   6   7   8   9
        1   |-------)                                       3   选中
        2       |-------)                                   4
        3       |-------)                                   4
        4       |-------)                                   4
        5           |-------)                               4   选中
        6               |-------)                           2
        7                   |-----------)                   4   选中
        8                           |-------)               4
        9                           |-------)               4
       10                           |-------)               4
       11                               |-------)           3   选中

        3. 优先选择最先开始的活动
            0   1   2   3   4   5   6   7   8   9
        2       |---)                               选中
        3           |---)                           选中
        4               |---)                       选中
        1   |-----------------------------------)

        4. 优先选择最先结束的活动
     */

    static class Activity {
        int index;
        int start;
        int finish;

        public Activity(int index, int start, int finish) {
            this.index = index;
            this.start = start;
            this.finish = finish;
        }

        public int getFinish() {
            return finish;
        }

        @Override
        public String toString() {
            return "Activity(" + index + ")";
        }
    }

    public static void main(String[] args) {
        Activity[] activities = new Activity[]{
                new Activity(0, 1, 3),
                new Activity(1, 2, 4),
                new Activity(2, 3, 5)
        };
        /*
            例1
        编号 0   1   2   3   4   5   6   7   8   9
          0     |-------)         选中
          1         |-------)
          2             |-------) 选中
          3                 |-------)
         */
        Arrays.sort(activities, Comparator.comparingInt(Activity::getFinish));
        System.out.println(Arrays.toString(activities));
//        Activity[] activities = new Activity[]{
//                new Activity(0, 1, 2),
//                new Activity(1, 3, 4),
//                new Activity(2, 0, 6),
//                new Activity(3, 5, 7),
//                new Activity(4, 8, 9),
//                new Activity(5, 5, 9)
//        };
        select(activities, activities.length);
    }

    public static void select(Activity[] activities, int n) {
        List<Activity> result = new ArrayList<>();
        Activity prev = activities[0]; // 上次被选中的活动
        result.add(prev);
        for (int i = 1; i < n; i++) {
            Activity curr = activities[i]; // 正在处理的活动
            if (curr.start >= prev.finish) {
                result.add(curr);
                prev = curr;
            }
        }
        for (Activity activity : result) {
            System.out.println(activity);
        }
    }

}
