package code_for_great_offer.class34;

import java.util.HashMap;

// 测试链接 : https://leetcode.com/problems/insert-delete-getrandom-o1/
public class LeetCode_0380_InsertDeleteGetRandom {
	/*
	*TODO
	* 假设 [7,8,9]
	* keyIndexMap
	* <7,0>
	* <8,1>
	* <9,2>
	* indexKeyMap
	* <0,7>
	* <1,8>
	* <2,9>
	* 此时2张表 size = 3
	* 根据size-1 做random操作
	* 再去 indexKeyMap取对应的值
	* eg2：
	* keyIndexMap
	* <7,0>
	* <6,1>
	* <5,2>
	* <9,3>
	* indexKeyMap
	* <0,7>
	* <1,6>
	* <2,5>
	* <3,9>
	* 此时2张表 size = 4
	* 如果removeValue(6)
	* 为了删除元素之后 这2张表 的下标连续
	* 就让 最后一个元素 顶替它
	* keyIndexMap
	* <7,0>
	* <9,1>
	* <5,2>
	* indexKeyMap
	* <0,7>
	* <1,9>
	* <2,5>
	* */
	public class RandomizedSet {
		//这张表 是 v -> index
		private HashMap<Integer, Integer> keyIndexMap;
		//这张表 是 index -> v
		private HashMap<Integer, Integer> indexKeyMap;
		private int size;

		public RandomizedSet() {
			keyIndexMap = new HashMap<Integer, Integer>();
			indexKeyMap = new HashMap<Integer, Integer>();
			size = 0;
		}

		public boolean insert(int val) {
			if (!keyIndexMap.containsKey(val)) {
				keyIndexMap.put(val, size);
				indexKeyMap.put(size, val);
				size++;
				return true;
			}
			return false;
		}

		public boolean remove(int val) {
			if (keyIndexMap.containsKey(val)) {
				int deleteIndex = keyIndexMap.get(val);
				int lastIndex = size-1;
				int lastKey = indexKeyMap.get(lastIndex);
				keyIndexMap.put(lastKey, deleteIndex);
				indexKeyMap.put(deleteIndex, lastKey);
				keyIndexMap.remove(val);
				indexKeyMap.remove(lastIndex);
				return true;
			}
			return false;
		}

		public int getRandom() {
			if (size == 0) {
				return -1;
			}
			int randomIndex = (int) (Math.random() * size);
			return indexKeyMap.get(randomIndex);
		}
	}

}
