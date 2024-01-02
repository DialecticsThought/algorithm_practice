package heima_data_structure.test.other;


import heima_data_structure.java.src.main.java.com.itheima.algorithm.sort.QuickSortHoare;
import heima_data_structure.java.src.main.java.com.itheima.algorithm.sort.QuickSortLomuto;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.Random;

public class SortingComparison {
    public static void main(String[] args) {
        int[] arr = generateRandomArray(1000000, 10000000);
        int[] arrCopy1 = Arrays.copyOf(arr, arr.length);
        int[] arrCopy2 = Arrays.copyOf(arr, arr.length);
        int[] arrCopy3 = Arrays.copyOf(arr, arr.length);


        StopWatch sw = new StopWatch();
        sw.start("QuickSortLomuto");
        QuickSortLomuto.sort(arrCopy1);
        sw.stop();

        sw.start("QuickSortHoare");
        QuickSortHoare.sort(arrCopy2);
        sw.stop();

        sw.start("QuickSortHoare2");
        QuickSortHoare2.sort(arrCopy3);
        sw.stop();

        System.out.println(sw.prettyPrint());
    }

    private static int[] generateRandomArray(int length, int max) {
        Random random = new Random();
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt(max);
        }
        return arr;
    }
}
