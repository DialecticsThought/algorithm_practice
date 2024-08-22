package other.mid.class07;

import java.util.ArrayList;
import java.util.List;

public class Code09_InsertInterval {

    public static class Interval {
    int start;
    int end;
        Interval() {
            start = 0;
            end = 0;
        }
        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    // items中，区间从小到大出现的，无重复区域
    //
    public List<Interval> insert(List<Interval> items,Interval newItems) {
        // i结尾了;要么，items .get(i).end >= newItems.start 某个区间的右部分 侵入到 添加的区间的左部分了
        //while 就是找 添加的区间所影响的所有区间
        /*
         * [-2 -1] [0,1] [2,4] [5,6] [8,10]  [11,13]
         * 现在添加[3,9]
         * 第一个while循环 把不被影响到的区间加入
         * 加入[-2 -1] [0,1]
         * 第二个while循环 items.get(i).start <= newItems.end 就是把哪个区间的左部分比9小 就加入
         *  [2,4] [5,6] [8,10]
         * 并且循环每一轮都会求出合并区间的左部分 和右部分  形成[2,10]
         * 第三个while循环 把不被影响到的区间加入
         * */
        List<Interval> result = new ArrayList<>();
        int i = 0;
        //左侧不会被影响到的区间，原封不动放入结果序列中
        while (i < items.size() && items.get(i).end < newItems.start) {
            result.add(items.get(i++));
        }
        /*
        * 一个区间的结束为止进入了另一个区间的开始位置
        * i 结尾；要么，item.get(i).end>=newItem.start
        * */
        while (i < items.size() && items.get(i).start <= newItems.end) {
            newItems.start = Math.min(newItems.start, items.get(i).start);
            newItems.end = Math.max(newItems.end, items.get(i).end);
            i++;
        }
        result.add(newItems);

        while (i < items.size()) {
            result.add(items.get(i++));
        }
        return result;
    }
}
