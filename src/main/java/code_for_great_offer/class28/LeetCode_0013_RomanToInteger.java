package code_for_great_offer.class28;

public class LeetCode_0013_RomanToInteger {

	public static int romanToInt(String s) {
		/*
		* eg:
		* C   M     X   C    I   X
		* 100 1000  10  100  1   10
		* */
		int nums[] = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case 'M':
				nums[i] = 1000;
				break;
			case 'D':
				nums[i] = 500;
				break;
			case 'C':
				nums[i] = 100;
				break;
			case 'L':
				nums[i] = 50;
				break;
			case 'X':
				nums[i] = 10;
				break;
			case 'V':
				nums[i] = 5;
				break;
			case 'I':
				nums[i] = 1;
				break;
			}
		}
		int sum = 0;
		for (int i = 0; i < nums.length - 1; i++) {
			//TODO 只要当前位的数字 比下一位的数字小 就是负的
			if (nums[i] < nums[i + 1]) {
				sum -= nums[i];
			} else {
				sum += nums[i];
			}
		}
		//因为 for循环 没有遍历最后一位
		return sum + nums[nums.length - 1];
	}

}
