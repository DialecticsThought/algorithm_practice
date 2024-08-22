package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>随机数</h3>
 */
public class MyRandom {

    // 线性同余法
    private final int a;
    private final int c;
    private final int m;
    private int seed;

    public MyRandom(int a, int c, int m, int seed) {
        this.a = a;
        this.c = c;
        this.m = m;
        this.seed = seed;
    }

    public int nextInt() {
        seed = (seed * a + c) % m;
        return seed;
    }

    public static void main(String[] args) {
        MyRandom r = new MyRandom(7, 0, Integer.MAX_VALUE, 1);
        for (int i = 0; i < 20; i++) {
            System.out.println(r.nextInt());
        }
    }
}
