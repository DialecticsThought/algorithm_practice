package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <h3>基数排序 最低有效数字 LSD(Least significant digit)</h3>
 * 本质就是按 位 排序
 */
public class RadixSort {
    /**
     * 原始数组: 158  151  135  138  139 159  按 个位 排
     * 0
     * 1   151
     * 2
     * 3
     * 4
     * 5   135
     * 6
     * 7
     * 8   158 138
     * 9   139 159
     * 放入桶之后 再一次遍历桶取出数字 放入原数组
     * 151 135 158 138 139 159
     * <p>
     * 151 135 158 138 139 159 按 十位 排
     * 0
     * 1
     * 2
     * 3   135 138 139
     * 4
     * 5   151 158 159
     * 6
     * 7
     * 8
     * 9
     * 135 138 139 151 158 159  按十位排
     * 下面是从高位到低位 排序 是错误的例子
     * 针对 110 088 009 从高位到低位 依次排序
     * 这一轮是百位
     * 0   088 009
     * 1   110
     * 2
     * 3
     * 4
     * 5
     * 6
     * 7
     * 8
     * 9
     * 088 009 110 第一轮
     * 这一轮是十位
     * 0   009
     * 1   110
     * 2
     * 3
     * 4
     * 5
     * 6
     * 7
     * 8   088
     * 9
     * 009 110 088 第二轮
     * 这一轮是个位
     * 0   110
     * 1
     * 2
     * 3
     * 4
     * 5
     * 6
     * 7
     * 8   088
     * 9   009
     * 110 088 009
     * 原因： 越靠后排序 重要性越大
     * 也就是说这里个位是最后排序的 那么个位 是所有位中重要性 最大的 但是显然不是 ☆☆☆☆☆☆☆☆☆☆
     *
     * @param a      要排序的数组
     * @param length 字符串数组中最大的字符串的字符长度
     */
    public static void radixSort(String[] a, int length) {
        // 1. 准备128个桶
        // 桶是128个原因 是可以利用 ASCII码 排序
        ArrayList<String>[] buckets = new ArrayList[256];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        // 2. 进行多轮按位桶排序
        // 依次是 个位 百位 十位 .... 的排序
        // 扩展成ASCII码的话 从右到左的顺序逐位进行排序
        for (int i = length - 1; i >= 0; i--) {
            // 将字符串放入合适的桶
            // 根据 数字的字符的第i位 找到桶 然后放入桶
            for (String s : a) {
                // eg: 对于字符 'a'，其 ASCII 值为 97，那么就是buckets[97]
                // 得到该字符串 当前位的字符
                char c = s.charAt(i);
                // 当前字符 所属的桶 放入该字符串
                buckets[c].add(s);
            }
            // 重新取出排好序的字符串，放回原始数组
            int k = 0;
            // 当前所有的桶的所有元素就是 包含了String[] arr 的所有元素 所以可以覆盖原始数组arr
            // 遍历所有的桶
            for (ArrayList<String> bucket : buckets) {
                // 遍历桶中的每一个元素
                // 先放入桶中的元素的先被拿出来
                for (String s : bucket) {
                    a[k] = s;
                    k++;
                }
                // 遍历完当前的桶之后 清空 然后 给下一轮用
                bucket.clear();
            }
        }
    }


    public static void main(String[] args) {
        String[] phoneNumbers = new String[10];  // 0~127
        phoneNumbers[0] = "13812345678";  // int long
        phoneNumbers[1] = "13912345678";
        phoneNumbers[2] = "13612345678";
        phoneNumbers[3] = "13712345678";
        phoneNumbers[4] = "13512345678";
        phoneNumbers[5] = "13412345678";
        phoneNumbers[6] = "15012345678";
        phoneNumbers[7] = "15112345678";
        phoneNumbers[8] = "15212345678";
        phoneNumbers[9] = "15712345678";

        /*String[] phoneNumbers = new String[6];
        phoneNumbers[0] = "158";
        phoneNumbers[1] = "151";
        phoneNumbers[2] = "235";
        phoneNumbers[3] = "138";
        phoneNumbers[4] = "139";
        phoneNumbers[5] = "159";*/

        RadixSort.radixSort(phoneNumbers, 11);
        for (String phoneNumber : phoneNumbers) {
            System.out.println(phoneNumber);
        }
    }
}
