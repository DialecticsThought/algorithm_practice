package code_for_great_offer.class31;
//https://leetcode.cn/problems/sort-list/submissions/
public class leetCode_0148_SortList {

	public static class ListNode {
		int val;
		ListNode next;

		public ListNode(int v) {
			val = v;
		}
	}

	// 链表的归并排序
	// 时间复杂度O(N*logN), 因为是链表所以空间复杂度O(1)
	public static ListNode sortList1(ListNode head) {
		int N = 0;
		ListNode cur = head;
		while (cur != null) {
			N++;
			cur = cur.next;
		}
		ListNode h = head;
		ListNode teamFirst = head;
		ListNode pre = null;
		//TODO 每次步长乘2
		for (int len = 1; len < N; len  <<= 1) {
			while (teamFirst != null) {
				// 左组从哪到哪 ls le
				// 右组从哪到哪 rs re
				// 左 右 next
				ListNode[] hthtn = hthtn(teamFirst, len);
				// ls...le rs...re -> merge去
				// 整体的头、整体的尾
				ListNode[] mhmt = merge(hthtn[0], hthtn[1], hthtn[2], hthtn[3]);
				if (h == teamFirst) {
					h = mhmt[0];
					pre = mhmt[1];
				} else {
					pre.next = mhmt[0];
					pre = mhmt[1];
				}
				teamFirst = hthtn[4];
			}
			teamFirst = h;
			pre = null;
		}
		return h;
	}
	/*
	*TODO
	* merge的前置步骤
	* 找到新组（左子组+右子组）的
	* 2个子组的第一个元素和最后一个元素和next
	* 前置知识
	* eg: 1->2->2->3  2->2->3->5
	* h1指针指向链表1的头结点 h2指针指向链表2的头结点
	* 一开始 h1 h2哪个小 那个是头部
	* 1 < 2 h1指向的1是头部 即 ： 1
	* h1 右移
	* h1 == h2 那么 h1 优先
	* 即 ： 1->2
	* ...
	* 所以最后 1->2->2->2->2->3->5
	*TODO
	* 用非递归的形式
	* 3->2->1->7->5->6->4->3 步长=1
	* 从头开始数 数够一个步长 算作一部分
	* 3是左部分 2是右部分 3和2合并 => 2->3 并改回原来的链表
	* 从1开始数步长 =>  1是左部分 7是右部分 1和7合并 => 1->7 并改回原来的链表
	* 从5开始数步长 =>  5是左部分 6是右部分 1和7合并 => 5->6 并改回原来的链表
	* 从4开始数步长 =>  4是左部分 3是右部分 4和3合并 => 3->4 并改回原来的链表
	* 最后 2->3->1->7->5->6->3->4
	* 开始 步长 = 2
	* 2->3是左部分 1->7是右部分 ls指针指向2 rs指针指向1 合并 =>1->2->3->7 并改回原来的链表
	* 5->6是左部分 3->4是右部分 ls指针指向5 rs指针指向3 合并 =>3->4->5->6 并改回原来的链表
	* 最后 1->2->3->7->3->4->5->6
	* 开始 步长 = 4
	* 1->2->3->7是左部分 3->4->5->6是右部分
	* ls指针指向1 rs指针指向3 合并 => 1->2->3->3->4->5->6->7
	 * */
	public static ListNode[] hthtn(ListNode teamFirst, int len) {
		/*
		*TODO
		* 因为每一次循环 根据步长要分成左组和右组  左组和右组构成一个新组
		* 先得到左组的第一个元素 和 最后一个元素（就是第一个元素开始数步长个元素）
		* 左组的最后一个元素的下一个元素就是右组的第一元素
		* 右组的最后1个元素（就是第一个元素开始数步长个元素）
		* TODO
		*  除此之外 上一个右组的尾巴 要和 新的左组的头部连接
		* */
		ListNode ls = teamFirst;//TODO 左组的第一个元素
		ListNode le = teamFirst;//TODO 左组的最后一个元素
		ListNode rs = null;//TODO 右组的第一个元素
		ListNode re = null;//TODO 右组的最后一个元素
		ListNode next = null;//TODO 下一个新组的第一个元素（下一个新组的左组的第一个元素）
		int pass = 0;
		while (teamFirst != null) {
			pass++;
			if (pass <= len) {
				le = teamFirst;
			}
			if (pass == len + 1) {
				rs = teamFirst;
			}
			if (pass > len) {
				re = teamFirst;
			}
			if (pass == (len << 1)) {
				break;
			}
			teamFirst = teamFirst.next;
		}
		le.next = null;
		if (re != null) {
			next = re.next;
			re.next = null;
		}
		return new ListNode[] { ls, le, rs, re, next };
	}

	public static ListNode[] merge(ListNode ls, ListNode le, ListNode rs, ListNode re) {
		if (rs == null) {
			return new ListNode[] { ls, le };
		}
		ListNode head = null;
		ListNode pre = null;
		ListNode cur = null;
		ListNode tail = null;
		while (ls != le.next && rs != re.next) {
			if (ls.val <= rs.val) {
				cur = ls;
				ls = ls.next;
			} else {
				cur = rs;
				rs = rs.next;
			}
			if (pre == null) {
				head = cur;
				pre = cur;
			} else {
				pre.next = cur;
				pre = cur;
			}
		}
		if (ls != le.next) {
			while (ls != le.next) {
				pre.next = ls;
				pre = ls;
				tail = ls;
				ls = ls.next;
			}
		} else {
			while (rs != re.next) {
				pre.next = rs;
				pre = rs;
				tail = rs;
				rs = rs.next;
			}
		}
		return new ListNode[] { head, tail };
	}

	// 链表的快速排序
	// 时间复杂度O(N*logN), 空间复杂度O(logN)
	public static ListNode sortList2(ListNode head) {
		int n = 0;
		ListNode cur = head;
		while (cur != null) {
			cur = cur.next;
			n++;
		}
		return process(head, n).head;
	}

	public static class HeadAndTail {
		public ListNode head;
		public ListNode tail;

		public HeadAndTail(ListNode h, ListNode t) {
			head = h;
			tail = t;
		}
	}

	public static HeadAndTail process(ListNode head, int n) {
		if (n == 0) {
			return new HeadAndTail(head, head);
		}
		int index = (int) (Math.random() * n);
		ListNode cur = head;
		while (index-- != 0) {
			cur = cur.next;
		}
		Record r = partition(head, cur);
		HeadAndTail lht = process(r.lhead, r.lsize);
		HeadAndTail rht = process(r.rhead, r.rsize);
		if (lht.tail != null) {
			lht.tail.next = r.mhead;
		}
		r.mtail.next = rht.head;
		return new HeadAndTail(lht.head != null ? lht.head : r.mhead, rht.tail != null ? rht.tail : r.mtail);
	}

	public static class Record {
		public ListNode lhead;
		public int lsize;
		public ListNode rhead;
		public int rsize;
		public ListNode mhead;
		public ListNode mtail;

		public Record(ListNode lh, int ls, ListNode rh, int rs, ListNode mh, ListNode mt) {
			lhead = lh;
			lsize = ls;
			rhead = rh;
			rsize = rs;
			mhead = mh;
			mtail = mt;
		}
	}

	public static Record partition(ListNode head, ListNode mid) {
		ListNode lh = null;
		ListNode lt = null;
		int ls = 0;
		ListNode mh = null;
		ListNode mt = null;
		ListNode rh = null;
		ListNode rt = null;
		int rs = 0;
		ListNode tmp = null;
		while (head != null) {
			tmp = head.next;
			head.next = null;
			if (head.val < mid.val) {
				if (lh == null) {
					lh = head;
					lt = head;
				} else {
					lt.next = head;
					lt = head;
				}
				ls++;
			} else if (head.val > mid.val) {
				if (rh == null) {
					rh = head;
					rt = head;
				} else {
					rt.next = head;
					rt = head;
				}
				rs++;
			} else {
				if (mh == null) {
					mh = head;
					mt = head;
				} else {
					mt.next = head;
					mt = head;
				}
			}
			head = tmp;
		}
		return new Record(lh, ls, rh, rs, mh, mt);
	}

}
