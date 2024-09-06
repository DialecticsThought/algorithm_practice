package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.Arrays;

public class Leetcode_344_ReverseString {
    public static void main(String[] args) {
        char[] array = "abcde".toCharArray();
        reverseString(array);
        System.out.println(Arrays.toString(array));
    }

    static void reverseString(char[] s) {
        recursion(s, 0, s.length - 1);
    }

    public static void recursion(char[] array, int i, int j) {
        if (i >= j) {
            return;
        }
        swap(array, i, j);
        recursion(array, ++i, --j);
    }

    public static void swap(char[] array, int i, int j) {
        char c = array[i];
        array[i] = array[j];
        array[j] = c;
    }
}
