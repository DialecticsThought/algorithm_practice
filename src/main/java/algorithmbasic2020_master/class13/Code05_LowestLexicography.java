package algorithmbasic2020_master.class13;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class Code05_LowestLexicography {

	public static String lowestString1(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		TreeSet<String> ans = process(strs);
		return ans.size() == 0 ? "" : ans.first();
	}

	/*
	* TODO 暴力解法
	*  strs中所有字符串全排列，返回所有可能的结果
	* */
	public static TreeSet<String> process(String[] strs) {
		TreeSet<String> ans = new TreeSet<>();
		if (strs.length == 0) {
			ans.add("");
			return ans;
		}
		/*
		* TODO
		*  每一个位置的元素 都可以成为第一个字符串 => for循环
		*/
		for (int i = 0; i < strs.length; i++) {
			String first = strs[i];
			//TODO 把i位置的字符串移除掉 那么在剩下的字符串中做拼接 得到所有可能的结果（递归）
			String[] nexts = removeIndexString(strs, i);
			TreeSet<String> next = process(nexts);
			/*
			* TODO
			*  已经做完剩下的字符串中做拼接 得到所有可能的结果 最后再拼接开头的部分 得到最终结果
			*  每个可能的结果放到treeSet
			* */
			for (String cur : next) {
				ans.add(first + cur);
			}
		}
		return ans;
	}

	/* {"abc", "cks", "bct"}
	   0 1 2
	   removeIndexString(arr , 1) -> {"abc", "bct"}
	*/
	public static String[] removeIndexString(String[] arr, int index) {
		int N = arr.length;
		String[] ans = new String[N - 1];
		int ansIndex = 0;
		for (int i = 0; i < N; i++) {
			if (i != index) {
				ans[ansIndex++] = arr[i];
			}
		}
		return ans;
	}
	/*
	* TODO 比较器
	* */
	public static class MyComparator implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			return (a + b).compareTo(b + a);
		}
	}
	/*
	* TODO 贪心算法
	* */
	public static String lowestString2(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		//TODO 利用比较器 排序
		Arrays.sort(strs, new MyComparator());
		String res = "";
		for (int i = 0; i < strs.length; i++) {
			res += strs[i];
		}
		return res;
	}

	// for test
	public static String generateRandomString(int strLen) {
		char[] ans = new char[(int) (Math.random() * strLen) + 1];
		for (int i = 0; i < ans.length; i++) {
			int value = (int) (Math.random() * 5);
			ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
		}
		return String.valueOf(ans);
	}

	// for test
	public static String[] generateRandomStringArray(int arrLen, int strLen) {
		String[] ans = new String[(int) (Math.random() * arrLen) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = generateRandomString(strLen);
		}
		return ans;
	}

	// for test
	public static String[] copyStringArray(String[] arr) {
		String[] ans = new String[arr.length];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = String.valueOf(arr[i]);
		}
		return ans;
	}

	public static void main(String[] args) {
		int arrLen = 6;
		int strLen = 5;
		int testTimes = 10000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String[] arr1 = generateRandomStringArray(arrLen, strLen);
			String[] arr2 = copyStringArray(arr1);
			if (!lowestString1(arr1).equals(lowestString2(arr2))) {
				for (String str : arr1) {
					System.out.print(str + ",");
				}
				System.out.println();
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
