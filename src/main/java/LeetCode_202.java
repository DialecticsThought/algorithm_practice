/**
 * @description
 * @author jiahao.liu
 * @date 2026/03/18 21:27
 */

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author jiahao.liu
 * @Data 2026/3/18 21:27
 */
public class LeetCode_202 {

    public boolean isHappy(int n) {

        Set<Integer> set = new HashSet<>();

        while (n != 1) {
            // 中间数
            Integer num = 0;
            // 得到n的每一位
            while (n != 0) {
                int d = n % 10;
                num = num + d * d;
                n = n / 10;
            }
            if (set.contains(num)) {
                return false;
            } else {
                set.add(num);
                n = num;
            }
        }

        return true;
    }
}
