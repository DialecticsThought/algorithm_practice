package other.mid.class03;


public class Code03_Decompress_String {

	public static String decompress(String decompressStr) {
		char[] chs = decompressStr.toCharArray();
		return process(chs, 0).str;
	}

	public static class ReturnData {
		public String str;//遍历的这一段的转化结果
		public int end; //转化到了什么位置

		public ReturnData(String str, int nextIndex) {
			this.str = str;
			this.end = nextIndex;
		}
	}

	// str[index...结尾或者遇到右括号]这一段，字符串、以及处理到的位置返回
	public static ReturnData process(char[] str, int index) {
		StringBuilder res = new StringBuilder();
		int times = 0;
		while (index < str.length && str[index] != '}') {
			if (str[index] == '{') { //递归即将开始阶段3
				ReturnData returnData = process(str, index + 1);
				res.append(getTimesString(times, returnData.str));
				times = 0;
				index = returnData.end + 1;
			} else {// 没有遇到{之前
				if (str[index] >= '0' && str[index] <= '9') {
					times = times * 10 + str[index] - '0';
				}
				if (str[index] >= 'a' && str[index] <= 'z') {
					res.append(String.valueOf(str[index]));
				}
				index++;
			}
		}
		return new ReturnData(res.toString(), index);
	}

	public static String getTimesString(int times, String base) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < times; i++) {
			res.append(base);
		}
		return res.toString();
	}

	public static void main(String[] args) {
		String test1 = "3{a}2{bc}";
		String test2 = "3{a2{c}}";
		String test3 = "2{abc}3{cd}ef";
		System.out.println(decompress(test1));
		System.out.println(decompress(test2));
		System.out.println(decompress(test3));

	}

}
