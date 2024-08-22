package algorithmbasic2020_master.class059;

import java.util.ArrayList;

public class Code07_Sum {

    public static void equalSum(int sum, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            int count = 0;
            for (int j = i; j >= 0; j--) {
                count = count + arr[j];
                if (sum == count) {
                    for (int k = j; k <= i; k++) {
                        list.add(arr[k]);
                    }
                }
                if(list.size() != 0){
                    System.out.println(list);
                }
            }
        }
    }

    public static int equalSum1(int sum, int[] arr) {
        int count2 = 0;
        for (int i = 0; i < arr.length; i++) {
            int count = 0;
            for (int j = i; j >= 0; j--) {
                count = count + arr[j];
                if (sum == count) {
                    count2++;
                }
            }
        }
        return count2;
    }

    public static void main(String[] args) {
        equalSum(5, new int[]{5, -2, 2, 0, 5});
        System.out.println(equalSum1(5, new int[]{5, -2, 2, 0, 5}));


    }
}
