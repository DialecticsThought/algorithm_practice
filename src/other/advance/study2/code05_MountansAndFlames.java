package other.advance.study2;

import java.util.Scanner;
import java.util.Stack;

/**
 * @Author zzz
 * @Date 2021/12/6 12:51
 * @Version 1.0
 */
public class code05_MountansAndFlames {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()){
            int size = in.nextInt();
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = in.nextInt();
            }
            System.out.println(communications(arr));
        }
        in.close();
    }

    //循环数组找下一个
    public static  int nextIndex(int size,int i){
        return i<(size-1)?(i+1):0;
    }

    //相同高度山峰的相互可见数
    public static long getInternalSum(int n){
        return n==1L?0L:(long) n* (long) (n-1) /2L;
    }

    //定义结构体
    public static class Pair{
        public int value;
        public int times;

        public Pair(int value) {
            this.value = value;
            this.times = 1;
        }
    }

    public static  long communications(int[] arr){
        if (arr == null | arr.length<2){
            return 0;
        }
        int size = arr.length;
        int maxIndex = 0;
        //最大值下标
        for (int i = 0; i < size; i++) {
            maxIndex = arr[maxIndex]<arr[i]?i:maxIndex;
        }
        int value = arr[maxIndex];
        int index= nextIndex(size,maxIndex);
        long res = 0L;
        Stack<Pair> stack = new Stack<Pair>();
        stack.push(new Pair(value));
        //遍历过程中结算山峰对+入栈
        while (index!=maxIndex){
            value = arr[index];
            while (!stack.isEmpty() && stack.peek().value<value){
                int times = stack.pop().times;
                //这两种写法一样的，因为最大值打底了
//                res += getInternalSum(times)+times;
//                res += stack.isEmpty()?0:times;
                res += getInternalSum(times)+2*times;
            }
            if (!stack.isEmpty() && stack.peek().value == value){
                stack.peek().times++;
            }else {
                stack.push(new Pair(value));
            }
            index = nextIndex(size,index);
        }
        //最后还剩下栈底->顶 从大到小的数据，需要再计算剩下的山峰对
        while (!stack.isEmpty()){
            int times = stack.pop().times;
            res += getInternalSum(times);
            if (!stack.isEmpty()){
                res += times;
                if (stack.size()>1){
                    res += times;
                }else {
                    res += stack.peek().times>1?times:0;
                }
            }
        }
        return res;
    }
}
