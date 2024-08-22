package heima_data_structure.java.src.main.java.com.itheima.temp.recursion;

import java.util.Arrays;

public class Test2 {
    public static void main(String[] args) {
        int[] children = {1, 2, 3, 4, 5, 0};
        int index = 2;
        int keyNumber = 5;
        System.out.println(Arrays.toString(children));
        System.arraycopy(children, index, children, index + 1, keyNumber - index);
        System.out.println(Arrays.toString(children));
    }
}
