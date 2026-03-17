

import heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist.ListNode;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author veritas
 * @Data 2024/10/8 12:09
 */
public class Test {


    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    public ListNode mergeTwoLists(ListNode p1, ListNode p2) {
        ListNode dummy = new ListNode(0, null);
        ListNode current = dummy;
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                current.next = p1;
                p1 = p1.next;
            } else {
                current.next = p2;
                p2 = p2.next;
            }
            current = current.next;
        }
        if (p1 != null) {
            current.next = p1;
        }
        if (p2 != null) {
            current.next = p2;
        }
        return dummy.next;
    }

    public ListNode split(ListNode[] lists, int i, int j) {

        if (i == j) {
            return lists[i];
        }
        int m = (i + j) / 2;
        ListNode left = split(lists, i, m);
        ListNode right = split(lists, m + 1, j);
        return mergeTwoLists(left, right);
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        return split(lists, 0, lists.length - 1);
    }
}
