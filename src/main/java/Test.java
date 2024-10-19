/**
 * @Description
 * @Author veritas
 * @Data 2024/10/8 12:09
 */
public class Test {
    public static int[] lps(char[] arr) {
        int[] lps = new int[arr.length];
        int i = 0;
        int j = 1;
        while (i < arr.length) {
            if (arr[i] == arr[j]) {
                lps[i] = j;
                i++;
                j++;
            } else if (j == 0) {
                i++;
            } else {
                j = lps[j - 1];
            }
        }

        return lps;
    }


}
