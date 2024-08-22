package code_for_great_offer.class23;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class Code04_FindKMajority {
    /**
     * 超级水王问题
     * 超级水王问题：给你一个数组，出现次数大于数组长度的一半的元素称之为水王数，怎么能快速找到水王数？
     * 内存限制：时间复杂度O(n)，额外空间复杂度O(1)——也就是遍历数组的次数为有限次，申请的变量数为有限个
     * 扩展1∶摩尔投票
     * 扩展2∶给定一个正数K，返回所有出现次数>N/K的数
     */
    public static void printHalfMajor(int[] arr) {
        int cand = 0;
        int HP = 0;
        for (int i = 0; i < arr.length; i++) {
            if (HP == 0) {
                cand = arr[i];
                HP = 1;
            } else if (arr[i] == cand) {
                HP++;
            } else {
                HP--;
            }
        }
        if (HP == 0) {
            System.out.println("no such number.");
            return;
        }
        HP = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == cand) {
                HP++;
            }
        }
        if (HP > arr.length / 2) {
            System.out.println(cand);
        } else {
            System.out.println("no such number.");
        }
    }

    //扩展2∶给定一个正数K，返回所有出现次数>N/K的数
    public static void printKMajor(int[] arr, int K) {
        if (K < 2) {
            System.out.println("the value of K is invalid.");
            return;
        }
        // 攒候选，cands，候选表，最多K-1条记录！ > N / K次的数字，最多有K-1个
        HashMap<Integer, Integer> cands = new HashMap<Integer, Integer>();
        for (int i = 0; i != arr.length; i++) {
            if (cands.containsKey(arr[i])) {
                cands.put(arr[i], cands.get(arr[i]) + 1);
            } else { // arr[i] 不是候选
                if (cands.size() == K - 1) { // 当前数肯定不要！，每一个候选付出1点血量，血量变成0的候选，要删掉！
                    allCandsMinusOne(cands);
                } else {
                    cands.put(arr[i], 1);
                }
            }
        }
        // 所有可能的候选，都在cands表中！遍历一遍arr，每个候选收集真实次数


        HashMap<Integer, Integer> reals = getReals(arr, cands);
        boolean hasPrint = false;
        for (Entry<Integer, Integer> set : cands.entrySet()) {
            Integer key = set.getKey();
            if (reals.get(key) > arr.length / K) {
                hasPrint = true;
                System.out.print(key + " ");
            }
        }
        System.out.println(hasPrint ? "" : "no such number.");
    }

    public static void allCandsMinusOne(HashMap<Integer, Integer> map) {
        List<Integer> removeList = new LinkedList<Integer>();
        //不能一边遍历一边删除 2个操作分开了
        for (Entry<Integer, Integer> set : map.entrySet()) {
            Integer key = set.getKey();
            Integer value = set.getValue();
            if (value == 1) {
                removeList.add(key);
            }
            map.put(key, value - 1);
        }
        for (Integer removeKey : removeList) {
            map.remove(removeKey);
        }
    }

    public static HashMap<Integer, Integer> getReals(int[] arr,
                                                     HashMap<Integer, Integer> cands) {
        HashMap<Integer, Integer> reals = new HashMap<Integer, Integer>();
        for (int i = 0; i != arr.length; i++) {
            int curNum = arr[i];
            if (cands.containsKey(curNum)) {
                if (reals.containsKey(curNum)) {
                    reals.put(curNum, reals.get(curNum) + 1);
                } else {
                    reals.put(curNum, 1);
                }
            }
        }
        return reals;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 1, 1, 2, 1};
        printHalfMajor(arr);
        int K = 4;
        printKMajor(arr, K);
    }

}
