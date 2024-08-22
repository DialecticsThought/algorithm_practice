package other.basic.basicStudy8.mine;

/**
 * @Author zzz
 * @Date 2021/12/2 14:39
 * @Version 1.0
 */
public class printAllSub {
    public static void solution(char[] chars,int i ,String res){
        if (i == chars.length){
            System.out.println(res);
            return;
        }
        solution(chars, i+1, res+"");
        solution(chars, i+1, res+chars[i]);
    }

    public static void main(String[] args) {
        String s = "zhao";
        solution(s.toCharArray(), 0, "");
    }
}
