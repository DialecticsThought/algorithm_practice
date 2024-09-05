package code_for_great_offer.class49;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeetCode_0527_WordAbbreviation {
	/*
	*TODO
	* eg: accct bccct 那么2个单词可以转成a3t b3t
	* 缩写必须保留开头和结尾 但是还要区分出来
	* eg: abct abck => a2t a2k
	* eg: abcccct abcdddt => ab4t ab4t
	* 那么需要写成 abccwt abcd2t 也就是只能只能通过增加前缀的方式
	* eg: abcdzt abcfyt
	* 那么需要写成 abcd1t abcf1t 但是缩写之后没有让字符变小
	* 那么就保留原始str
	* eg: 对于0位置的单词：akfffe -> a4e
	* 有个hashmap
	* 这个key是缩写 value是一个数组 记录缩写对应的单词下表
	* 也就是 <a4e,[0]>
	* 假设 有 <a4e,[0，3,13]>
	* 那么解决方法
	* 通过增加前缀的方式来判断是否可以区分多个单词
	* */
	public static List<String> wordsAbbreviation(List<String> words) {
		int len = words.size();
		List<String> res = new ArrayList<>();
		//TODO 下面的for循环 就是记录是所有单词的缩写
		HashMap<String, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < len; i++) {
			res.add(makeAbbr(words.get(i), 1));
			List<Integer> list = map.getOrDefault(res.get(i), new ArrayList<>());
			list.add(i);
			map.put(res.get(i), list);
		}

		int[] prefix = new int[len];
		for (int i = 0; i < len; i++) {
			if (map.get(res.get(i)).size() > 1) {
				List<Integer> indexes = map.get(res.get(i));
				map.remove(res.get(i));
				//TODO 通过增加前缀的方式来区分开单词
				for (int j : indexes) {
					prefix[j]++;
					res.set(j, makeAbbr(words.get(j), prefix[j]));
					List<Integer> list = map.getOrDefault(res.get(j), new ArrayList<>());
					list.add(j);
					map.put(res.get(j), list);
				}
				i--;
			}
		}
		return res;
	}

	public static String makeAbbr(String s, int k) {
		if (k >= s.length() - 2) {
			return s;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(s.substring(0, k));
		builder.append(s.length() - 1 - k);
		builder.append(s.charAt(s.length() - 1));
		return builder.toString();
	}

}
