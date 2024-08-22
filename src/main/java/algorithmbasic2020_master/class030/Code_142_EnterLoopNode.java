package algorithmbasic2020_master.class030;

/***
 *测试链接：https://leetcode.com/problems/linked-list-cycle-ii
 * TODO
 *					 ↙	9 <- 8 ↖
 *  1 -> 2 -> 3 -> 4 			7
 *  				 ↘ 5 -> 6 ↗
 * 1.
 *	slow
 *	↓				 ↙	9 <- 8 ↖
 *  1 -> 2 -> 3 -> 4 			7
 *  ↑				 ↘ 5 -> 6 ↗
 *  fast
 * 2.
 *		slow
 *		 ↓			 ↙	9 <- 8 ↖
 *  1 -> 2 -> 3 -> 4 			7
 *  		  ↑	 	 ↘ 5 -> 6 ↗
 *  		fast
 * 3.
 *			slow
 *		 	  ↓		 ↙ 9 <- 8 ↖
 *  1 -> 2 -> 3 -> 4 			7
 *  		  	     ↘ 5 -> 6 ↗
 *  		  	       ↑
 *  				fast
 * 4.
 *				 slow
 *		 	  	   ↓ ↙ 9 <- 8 ↖
 *  1 -> 2 -> 3 -> 4 			7
 *  		  	     ↘ 5 -> 6 ↗ ↑
 *  		  	       		  fast
 * 5.
 *  		  	      fast
 *				   	  ↓
 *		 	  	     ↙ 9 <- 8 ↖
 *  1 -> 2 -> 3 -> 4 			7
 *  		  	     ↘ 5 -> 6 ↗
 *  		  	       ↑
 *  		  	      slow
 * 6.
 *		 	  	     ↙ 9 <- 8 ↖
 *  1 -> 2 -> 3 -> 4 			7
 *  		  	     ↘ 5 -> 6 ↗
 *  				   ↑	↑
 *  				fast   slow
 * 7.
 *		 	  	     ↙ 9 <- 8 ↖
 *  1 -> 2 -> 3 -> 4 			7 ← slow / fast
 *  		  	     ↘ 5 -> 6 ↗
 */
public class Code_142_EnterLoopNode {

    // 这个类不用提交
    public static class ListNode {
        public int val;
        public ListNode next;
    }

    // 只提交以下的代码
    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

}
