package other.basic.basicStudy8.mine;

/**
 * @Author zzz
 * @Date 2021/12/2 15:08
 * @Version 1.0
 */
public class cow {
    public static int solution(int n){
        if (n<=3){
            return n;
        }else {
            return solution(n-1)+solution(n-3);
        }
    }

    public static int solution1(int n){
        if (n<=3){
            return n;
        }
        int[] cows = new int[n];
        for (int i = 0; i < 3; i++) {
            cows[i] = i+1;
        }
        for (int i = 3; i < n; i++) {
            cows[i] = cows[i-1]+cows[i-3];
        }
        return cows[n-1];
    }

    public static void main(String[] args) {
        System.out.println(solution(14));
        System.out.println(solution(15));
        System.out.println(solution(16));
        System.out.println(solution1(14));
        System.out.println(solution1(15));
        System.out.println(solution1(16));
    }
}
