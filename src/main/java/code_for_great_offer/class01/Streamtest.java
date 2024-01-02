package code_for_great_offer.class01;

import java.util.*;
import java.util.stream.Stream;

public class Streamtest {
    public static void main(String[] args) {
        List list = new ArrayList<Integer>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }

        Stream stream = list.stream();

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Optional<Integer> reduce = integers.stream().reduce((x, y) -> {
            return x + y;
        });
        System.out.println(reduce);
    }

    public void leftJoin(){
        //生成两组测试数据（这里故意打乱顺序）
        List<Map<String, String>> list1 = new ArrayList<>();
        List<Map<String, String>> list2 = new ArrayList<>();
        for (int i = 0; i <= 50000; i++) {
            Map<String, String> map = new HashMap<>(); map.put("value", i + "w"); list1.add(map);
        }
        for (int i = 50000; i >= 0; i--) {
            Map<String, String> map = new HashMap<>(); map.put("value", i + "w"); list2.add(map);
        }
        long start = System.currentTimeMillis();//记录开始时间
        //1、两组数据使用相同的排序方式
        Collections.sort(list1, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                return o1.get("value").compareTo(o2.get("value"));
            }
        });
        Collections.sort(list2, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                return o1.get("value").compareTo(o2.get("value"));
            }
        });
        for (Map<String, String> m1 : list1) {
            for (Map<String, String> m2 : list2) {
                if (m1.get("value").equals(m2.get("value"))) {
                    list2.remove(m2);//2、副数组 使用过后去除元素
                    break;//3、确定元素后 跳过其余元素
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end - start) + "ms");

    }
}
