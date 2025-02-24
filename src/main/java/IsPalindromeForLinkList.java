import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2025/2/23 18:45
 */
public class IsPalindromeForLinkList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    public boolean isPalindrome(ListNode head) {
        List<Integer> integers = new ArrayList<>();
        while (head != null) {
            integers.add(head.val);
            head = head.next;
        }
        for (int i = 0; i < integers.size() / 2; i++) {
            if (integers.get(i) != integers.get(integers.size() - 1 - i)) {
                return false;
            }
        }
        return true;
    }
}
