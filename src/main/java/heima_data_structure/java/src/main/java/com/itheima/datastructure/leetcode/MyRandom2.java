package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class MyRandom2 {
    private static final long a = 0x5DEECE66DL;  // 7,097,364,492,799,003,750,039,552
    private static final long c = 0xBL;
    private static final long m = 1L << 48;

    private long seed;

    public MyRandom2(long seed) {
        this.seed = (seed ^ a) & (m - 1);
    }

    public int nextInt() {
        seed = (a * seed + c) & (m - 1);
        System.out.println(seed);
        return ((int) (seed >>> 16));
    }

    public static void main(String[] args) {
        Random r1 = new Random(-1);
        MyRandom2 r2 = new MyRandom2(1);
        System.out.println(Arrays.toString(IntStream.generate(r1::nextInt).limit(10).toArray()));
        System.out.println(Arrays.toString(IntStream.generate(r2::nextInt).limit(100).toArray()));
        System.out.println(Arrays.toString(DoubleStream.generate(r1::nextDouble).limit(10).toArray()));
    }
}
