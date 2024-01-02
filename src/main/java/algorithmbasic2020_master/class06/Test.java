package algorithmbasic2020_master.class06;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Test {
    public static class MyComp implements Comparator<Integer>{
        /*
        * 负数的时候 o1在前面
        * 整数的时候 o2在前面
        * */
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2-o1;
        }
    }
    public static void main(String[] args) {
        /*
        * 默认是小根堆  不是一个队列
        * */
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(5);
        heap.add(7);
        heap.add(3);
        heap.add(0);
        heap.add(2);
        heap.add(5);
        while (!heap.isEmpty()){
            System.out.println(heap.poll());
        }
        /*
        * 大根堆
        * 根据自己定义的比较器
        * */
        PriorityQueue<Integer> bigHeap = new PriorityQueue<>(new MyComp());
        bigHeap.add(5);
        bigHeap.add(7);
        bigHeap.add(3);
        bigHeap.add(0);
        bigHeap.add(2);
        bigHeap.add(5);
        while (!bigHeap.isEmpty()){
            System.out.println(bigHeap.poll());
        }
    }
}
