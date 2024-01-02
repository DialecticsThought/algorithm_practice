package other.advance.study2;

import java.util.LinkedList;

/**
 * @Author zzz
 * @Date 2021/12/5 10:11
 * @Version 1.0
 */
public class code01_SlidingWindowsMaxArray {
    public static  int[] getMaxWindow(int[] arr,int w){
        if (arr == null || w<1 || arr.length<2){
            return null;
        }
        LinkedList<Integer> qmax = new LinkedList<>();
        int n = arr.length;
        int[] res = new int[n-w+1];
        int index = 0;
        for (int i = 0; i < n; i++) {
            while (!qmax.isEmpty() && arr[qmax.peekLast()]<=arr[i]){
                qmax.pollLast();
            }
            qmax.addLast(i);
            if (qmax.peekFirst() == i-w){
                qmax.pollFirst();
            }
            if (i >=w-1){
                res[index++] = arr[qmax.peekFirst()];
            }
        }
        return res;
    }

    public static void main(String[] args) {

    }
}
