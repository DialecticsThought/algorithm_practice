package leetcode;

// algorithmbasic2020_master.class43
public class LeetCode_464_CanIWin {

    /**
     * TODO
     * 1~choose 拥有的数字
     * total 一开始的剩余
     * 返回先手会不会赢
     */
    public static boolean canIWin0(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        int[] arr = new int[choose];
        for (int i = 0; i < choose; i++) {
            arr[i] = i + 1;
        }
        // arr[i] != -1 表示arr[i]这个数字还没被拿走
        // arr[i] == -1 表示arr[i]这个数字已经被拿走
        // 集合，arr，1~choose
        return process(arr, total);
    }

    /**
     * 当前轮到先手拿，
     * 先手只能选择在arr中还存在的数字，
     * 还剩rest这么值，
     * 返回先手会不会赢
     */
    public static boolean process(int[] arr, int rest) {
        if (rest <= 0) {
            return false;
        }
        //先手去尝试所有的情况
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {//如果当前数字没哟被拿走
                int cur = arr[i];//尝试拿走这个数
                arr[i] = -1;
                //当前这一轮的先手 变成下一轮的后手 如果下一轮的先手输掉的话 就是当前这一轮的先手赢
                //可以理解为 如果子过程的后手赢了的话，就是当前的先手赢 是同一个人
                boolean next = process(arr, rest - cur);
                arr[i] = cur;//尝试完后 把数字复原  类似于深度优先遍历
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }

    // 这个是暴力尝试，思路是正确的，超时而已
    public static boolean canIWin1(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        return process1(choose, 0, total);
    }

    /**
     * TODO
     * 当前轮到先手拿，
     * 先手可以拿1~choose中的任何一个数字 这个choose 一定小于32
     * status   i位如果为0，代表没拿，当前可以拿
     * i位为1，代表已经拿过了，当前不能拿
     * 还剩rest这么值，
     * 返回先手会不会赢
     * choose是固定参数 status和rest是可变参数
     * status 可以决定 rest的大小
     * 所以一维动态规划表就可以了
     */
    public static boolean process1(int choose, int status, int rest) {
        if (rest <= 0) {
            return false;//先手输
        }
        //先手执行 i~choose所有可能的数 但是只有第i位置是0的数 才能执行  因为0表示没有拿
        for (int i = 1; i <= choose; i++) {
            if (((1 << i) & status) == 0) { // i 这个数字，是此时先手的决定！
                //(status | (1 << i)) 实现 i位的数 0 -> 1表示使用过了
                if (!process1(choose, (status | (1 << i)), rest - i)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 暴力尝试改动态规划而已
    public static boolean canIWin2(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        //+1 是因为 最低位的0 不使用 第二位表示1 第三位表示2 ......
        int[] dp = new int[1 << (choose + 1)];
        // dp[status] == 1 那么说明过程算过了 返回 true
        // dp[status] == -1 那么说明过程算过了 返回  false
        // dp[status] == 0  process(status) 没算过！去算！
        return process2(choose, 0, total, dp);
    }

    /**
     * TODO
     * 为什么明明status和rest是两个可变参数，却只用status来代表状态(也就是dp)
     * 因为选了一批数字之后，得到的和一定是一样的，所以rest是由status决定的，所以rest不需要参与记忆化搜索
     * 对 status做缓存
     */
    public static boolean process2(int choose, int status, int rest, int[] dp) {
        if (dp[status] != 0) {//表示算过了 命中缓存
            return dp[status] == 1 ? true : false;
        }
        //开始算
        boolean ans = false;
        if (rest > 0) {
            for (int i = 1; i <= choose; i++) {
                if (((1 << i) & status) == 0) {
                    //(status | (1 << i)) 实现 i位的数 0 -> 1表示使用过了
                    if (!process2(choose, (status | (1 << i)), rest - i, dp)) {
                        ans = true;
                        break;
                    }
                }
            }
        }
        dp[status] = ans ? 1 : -1;//算完把结果放入缓存
        return ans;//返回结果
    }

}
