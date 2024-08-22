package heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy;

import java.util.*;

public class SetCoverProblem {
    public static void main(String[] args) {
        Set<String> cities = new HashSet<>(Set.of("北京", "长沙", "重庆", "成都", "武汉", "南京", "上海", "杭州"));
        Map<String, Set<String>> map = new LinkedHashMap<>();
        map.put("1", Set.of("北京", "上海", "南京"));
        map.put("2", Set.of("重庆", "北京", "成都"));
        map.put("3", Set.of("杭州", "上海", "武汉"));
        map.put("4", Set.of("上海", "南京"));
        map.put("5", Set.of("武汉", "长沙"));

        /*
            暴力解法，找到所有的组合，共有 2^n - 1 种，n 为集合数
            1   2   3   4   5                           C(5,1) 取一个来组合   5!/1!/(5-1)! = 5
            12  13  14  15  23  24  25  34  35  45      C(5,2) 取两个来组合   5!/2!/(5-2)! = 10
            123 124 125 134 135 145 234 235 245 345     C(5,3) 取三个来组合   5!/3!/(5-3)! = 10
            1234    1235    1245    1345    2345        C(5,4) 取四个来组合   5!/4!/(5-4)! = 5
            12345                                       C(5,5) 取五个来组合   5!/5!/(5-5)! = 1
         */

        /*
            贪心解法
                1. cities 包含了所有的城市
                2. 当 cities 不为空时，循环执行下面的操作
                    1) 找一个集合，它的元素与 cities 交集最大
                    2) 集合加入最终结果，下一轮不用再考虑此集合
                    3) cities 中移除此集合内容

             过程分析
             第一轮
                cities = "北京", "长沙", "重庆", "成都", "武汉", "南京", "上海", "杭州"
                cities & Set.of("北京", "上海", "南京") 3
                cities & Set.of("重庆", "北京", "成都") 3
                cities & Set.of("杭州", "上海", "武汉") 3
                cities & Set.of("上海", "南京") 2
                cities & Set.of("武汉", "长沙") 2
             选择了 1->"北京", "上海", "南京"
             第二轮
                cities = "长沙", "重庆", "成都", "武汉", "杭州"
                cities & Set.of("重庆", "北京", "成都") 2
                cities & Set.of("杭州", "上海", "武汉") 2
                cities & Set.of("上海", "南京") 0
                cities & Set.of("武汉", "长沙") 2
             选择了 2->"重庆", "北京", "成都"
             第三轮
                cities = "长沙", "武汉", "杭州"
                cities & Set.of("杭州", "上海", "武汉") 2
                cities & Set.of("上海", "南京") 0
                cities & Set.of("武汉", "长沙") 2
             选择了 3->"杭州", "上海", "武汉"
             第四轮
                cities = "长沙"
                cities & Set.of("上海", "南京") 0
                cities & Set.of("武汉", "长沙") 1
             选择了 5->"武汉", "长沙"
         */
        System.out.println("剩余:" + cities);
        List<String> result = new ArrayList<>();
        while (!cities.isEmpty()) {
            Map.Entry<String, Set<String>> choose = null;
            int max = 0;
            for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
                Set<String> set = new HashSet<>(entry.getValue());
                set.retainAll(cities);
                if (choose == null || set.size() > max) {
                    choose = entry;
                    max = set.size();
                }
            }
            result.add(choose.getKey());
            cities.removeAll(choose.getValue());
            System.out.println("剩余:" + cities);
            map.remove(choose.getKey());
        }
        System.out.println(result);
    }
}
