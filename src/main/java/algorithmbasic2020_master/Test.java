package algorithmbasic2020_master;

/**
 * @Description
 * @Author veritas
 * @Data 2024/9/20 16:07
 */
public class Test {


    public int f(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        return f(i - 1) + f(i - 2);
    }

}
