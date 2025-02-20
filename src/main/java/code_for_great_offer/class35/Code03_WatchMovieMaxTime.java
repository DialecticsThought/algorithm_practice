package code_for_great_offer.class35;

import java.util.Arrays;

/*
 *TODO 来自小红书
 * 一场电影开始和结束时间可以用一个小数组来表示["07:30","12:00"]
 * 已知有2000场电影开始和结束都在同一天，这一天从00:00开始到23:59结束
 * 一定要选3场完全不冲突的电影来观看，返回最大的观影时间
 * 如果无法选出3场完全不冲突的电影来观看，返回-1
 */
public class Code03_WatchMovieMaxTime {

    // 暴力方法，枚举前三场所有的可能全排列
    public static int maxEnjoy1(int[][] movies) {
        if (movies.length < 3) {
            return -1;
        }
        return process1(movies, 0);
    }

    public static int process1(int[][] movies, int index) {
        if (index == 3) {
            int start = 0;
            int watch = 0;
            for (int i = 0; i < 3; i++) {
                if (start > movies[i][0]) {
                    return -1;
                }
                watch += movies[i][1] - movies[i][0];
                start = movies[i][1];
            }
            return watch;
        } else {
            int ans = -1;
            for (int i = index; i < movies.length; i++) {
                swap(movies, index, i);
                ans = Math.max(ans, process1(movies, index + 1));
                swap(movies, index, i);
            }
            return ans;
        }
    }

    public static void swap(int[][] movies, int i, int j) {
        int[] tmp = movies[i];
        movies[i] = movies[j];
        movies[j] = tmp;
    }

    // 优化后的递归解
    public static int maxEnjoy2(int[][] movies) {
        //TODO 按照所有电影的开始时间 排序 从小到大 开始时间相同 的话 比较结束时间 从小到大
        Arrays.sort(movies, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] - b[1]));
        return process2(movies, 0, 0, 3);
    }

    /*
     *TODO
     * 参数1 ：现在来到的分钟 time
     * 参数2：还有几场电影要安排（0~3）rest
     * 参数3：当前来到第几号电影（0~1999） 从i位置往后考虑
     * 考虑电影的选择是从左往右的
     * */
    public static int process2(int[][] movies, int index, int time, int rest) {
        if (index == movies.length) {
            return rest == 0 ? 0 : -1;
        }
        //TODO case1：当前来到i号节点对应的电影 选择不看
        int p1 = process2(movies, index + 1, time, rest);
        int next = 0;
        if (movies[index][0] >= time && rest > 0) {
            //TODO case2：当前来到i号节点对应的电影 选择看
            next = process2(movies, index + 1, movies[index][1], rest - 1);
        } else {
            next = -1;
        }
        int p2 = 0;
        if (next != -1) {
            p2 = (movies[index][1] - movies[index][0] + next);
        } else {
            p2 = -1;
        }
        return Math.max(p1, p2);
    }
    // 记忆化搜索的动态规划

    // 为了测试
    public static int[][] randomMovies(int len, int time) {
        int[][] movies = new int[len][2];
        for (int i = 0; i < len; i++) {
            int a = (int) (Math.random() * time);
            int b = (int) (Math.random() * time);
            movies[i][0] = Math.min(a, b);
            movies[i][1] = Math.max(a, b);
        }
        return movies;
    }

    public static void main(String[] args) {
        int n = 10;
        int t = 20;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[][] movies = randomMovies(len, t);
            int ans1 = maxEnjoy1(movies);
            int ans2 = maxEnjoy2(movies);
            if (ans1 != ans2) {
                for (int[] m : movies) {
                    System.out.println(m[0] + " , " + m[1]);
                }
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束");
    }

}
