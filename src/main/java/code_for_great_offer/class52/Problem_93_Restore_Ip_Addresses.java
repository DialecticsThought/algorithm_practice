package code_for_great_offer.class52;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2023/4/6 16:50
 * https://leetcode.cn/problems/restore-ip-addresses/solution/dai-ma-sui-xiang-lu-93-fu-yuan-ip-di-zhi-pzjo/
 * https://leetcode.cn/problems/restore-ip-addresses/
 * 有效 IP 地址 正好由四个整数(每个整数位于 0 到 255 之间组成，且不能含有前导 0)
 * 整数之间用.分隔
 * 例如:"0.1.2.201"和“192.168.1.1”是 有效 地址,
 * 但是"0.011.255.245"、"192.168.1.312'和"192.168@1.1”是 无效 IP 地址。给定一个只包含数字的字符串 s ，
 * 用以表示一个 P 地址，返回所有可能的有效 P 地址这些地址可以通过在 5 中插入.来形成
 * 你不能重新排序或删除中的任何数字。你可以按任何顺序返回答案。
 */
public class Problem_93_Restore_Ip_Addresses {

    public static void main(String[] args) {
        String s="101023";
        System.out.println(restoreIpAddresses(s));
    }


    public static List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        String path = "";
        if (s.length() > 12) {
            return result; // 算是剪枝了
        }
        backTrack(s, 0, 0, result, path);
        return result;
    }

    //TODO startIndex: 搜索的起始位置， pointNum:添加逗点的数量
    private static void backTrack(String s, int startIndex, int pointNum, List<String> result, String path) {
        if (pointNum == 3) {//TODO 逗点数量为3时，分隔结束
            //TODO 判断第四段⼦字符串是否合法，如果合法就放进result中
            if (isValid(s, startIndex, s.length() - 1)) {
                result.add(s);
            }
            return;
        }
        String pathBak = path;
        for (int index = startIndex; index < s.length(); index++) {
            if (isValid(s, startIndex, index)) {//TODO 如果s[startIndex]~s[index]组成的数字合法
                s = s.substring(0, index + 1) + "." + s.substring(index + 1);  //TODO 在str的后⾯插⼊⼀个逗号
                pointNum++;
                backTrack(s, index + 2, pointNum, result, path);//TODO 插⼊逗点之后下⼀个⼦串的起始位置为i+2
                pointNum--;//TODO 回溯
                //TODO 回溯删掉逗号
                path = pathBak;
                s = s.substring(0, index + 1) + s.substring(index + 2);
            } else {
                break;
            }
        }
    }

    //TODO 判断字符串s在左闭右闭区间[start, end]所组成的数字是否合法
    private static Boolean isValid(String s, int start, int end) {
        if (start > end) {
            return false;
        }
        if (s.charAt(start) == '0' && start != end) { //TODO 0开头的数字不合法 但是只是一个'0’合法
            return false;
        }
        int num = 0;
        for (int i = start; i <= end; i++) {
            if (s.charAt(i) > '9' || s.charAt(i) < '0') { //TODO 遇到⾮数字字符不合法
                return false;
            }
            num = num * 10 + (s.charAt(i) - '0');
            if (num > 255) { //TODO 如果⼤于255了不合法
                return false;
            }
        }
        return true;
    }


}
