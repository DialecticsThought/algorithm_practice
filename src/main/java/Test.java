


import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author veritas
 * @Data 2024/10/8 12:09
 */
public class Test {


    public void heapSort(int[] arr) {
        heapify(arr, arr.length);
        for (int right = arr.length - 1; right > 0; right--) {
            swap(arr, 0, right);
            down(arr, 0, right);
        }
    }

    public void heapify(int[] arr, int size) {
        for (int i = (size / 2) - 1; i >= 0; i--) {
            down(arr, i, size);
        }
    }


    public void down(int[] arr, int parent, int heapSize) {
        for (; ; ) {
            int left = parent * 2 + 1;
            int right = left + 1;
            int max = parent;
            if (left < heapSize && arr[left] > arr[max]) {
                max = left;
            }
            if (right < heapSize && arr[right] > arr[max]) {
                max = right;
            }
            if (max == parent) {
                break;
            }
            swap(arr, left, max);

            parent = max;
        }
    }


    public static int[] nethertLandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, R};
        }
        int less = L - 1;
        int more = R;
        int index = L;

        while (index < more) {
            if (arr[index] == arr[R]) {
                index = index + 1;
            }
            if (arr[index] < arr[R]) {
                swap(arr, index, less + 1);
                less = less + 1;
                index = index + 1;
            }
            if (arr[index] > arr[R]) {
                more = more - 1;
                swap(arr, index, more - 1);
            }
        }

        swap(arr, more, R);
        return new int[]{less + 1, more};
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
