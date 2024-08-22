package algorithmbasic2020_master.class23;

public class Code0_F {
    /*
    * 斐波那契数列 Fibonacci sequence
    * 暴力递归里面有大量的重复运算
    * eg n = 7
    * f(7) = f(6)+f(5)
    * f(6) = f(5)+f(4)
    * f(5) = f(4)+f(3)
    * */
    public static int f(int n){
        if(n==1){
            return 1;
        }else if (n==2){
            return 2;
        }else {
            return f(n-1)+f(n-2);
        }
    }

    public static void main(String[] args) {
        System.out.println(f(7));
    }
}
