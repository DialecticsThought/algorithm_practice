package code_for_great_offer.class47;

import java.util.Arrays;

public class Problem_0475_Heaters {
	/**
	 *TODO 所有供暖站有统一的半径 ☆☆☆☆☆
	 * eg0:
	 * 比如地点是7, 9, 14
	 * 供暖点的位置: 1 3 4 5 13 15 17
	 * 先看地点7
	 * 由1供暖，半径是6    由3供暖，半径是4
	 * 由4供暖，半径是3    由5供暖，半径是2
	 * 由13供暖，半径是6
	 * 由此可知，地点7应该由供暖点5来供暖，半径是2
	 * 再看地点9
	 * 供暖点不回退
	 * 由5供暖，半径是4	由13供暖，半径是4		由15供暖，半径是6
	 * 由此可知，地点9应该由供暖点13来供暖，半径是4
	 * 为什么是13而不是5？因为接下来的地点都会更靠右，所以半径一样的时候，就应该选更右的供暖点
	 * 再看地点14
	 * 供暖点不回退
	 * 由13供暖，半径是1		由15供暖，半径是1		由17供暖，半径是3
	 * 由此可知，地点14应该由供暖点15来供暖，半径是1
	 * 以此类推
	 * eg1: 地点：[0,10] 供暖器 [5]
	 * 那么半径就是5
	 * eg2: 地点 [0,10] 供暖器[1,11]
	 * 那么半径 就是1
	 * eg3: 地点 d[1,13,26,100,500] 供暖器 h[-5,-3,2,9,11,17,27,29....]
	 * 地点和供暖器都要先从小到大排序
	 * 从h[0]和d[0]开始试
	 * h[0]想要覆盖到d[0] 需要半径5 ， h[1]想要覆盖到d[0] 需要半径4
	 * 说明 给d[0]供暖的最优不知道是不是h[1] 但是一定不是h[0]，而且后面所有地点都不是h[0]=-4供暖
	 * 继续尝试 h[2]想要覆盖到d[0] 需要半径1
	 * 说明 给d[0]供暖的最优不知道是不是h[2] 但是一定不是h[1] ，而且后面所有地点都不是h[1]=-3供暖
	 * 继续尝试 h[3]想要覆盖到d[0] 需要半径8
	 * 说明 给d[0]供暖的最优一定不是9 ，而且h[3]后面所有地点都不能d[0]供暖 因为越来越远
	 * ===> 得到结论 给d[0]供暖的最优 = h[2]
	 * h[2]想要覆盖到d[1] 需要半径11， h[3]想要覆盖到d[0] 需要半径4
	 * 说明 给d[1]供暖的最优不知道是不是h[3] 但是一定不是h[2]，而且后面所有地点都不是h[2]供暖
	 * 继续尝试 h[4]想要覆盖到d[1] 需要半径2
	 * 继续尝试 h[5]想要覆盖到d[1] 需要半径4
	 * 说明 给d[1]供暖的最优一定不是h[5] ，而且h[5]后面所有地点都不能d[1]供暖 因为越来越远
	 * ===> 得到结论 给d[1]供暖的最优 = h[4]
	 * h[4]想要覆盖到d[2] 需要半径15， h[5]想要覆盖到d[2] 需要半径9
	 * 说明 给d[2]供暖的最优不知道是不是h[5] 但是一定不是h[4]，而且后面所有地点都不是h[4]供暖
	 * 继续尝试 h[6]想要覆盖到d[2] 需要半径1
	 * 说明 给d[2]供暖的最优不知道是不是h[6] 但是一定不是h[5]，而且后面所有地点都不是h[5]供暖
	 * 继续尝试 h[7]想要覆盖到d[2] 需要半径3
	 * 说明 给d[2]供暖的最优一定不是h[7] ，而且h[7]后面所有地点都不能d[2]供暖 因为越来越远
	 * ===> 得到结论 给d[2]供暖的最优 = h[6]
	 * ......
	 * 不断重复的操作
	 * 得到所有的 最优 的集合中求个max
	 *TODO
	 * 如果遇到一种情况 d[i] ,h[j] ,h[j+1]
	 * |d[i]-h[j]| == |d[i]-h[j+1]|
	 * 这个时候肯定选择左边 h[j]  ，因为下一个d[i+1]不适合h[j]的话 也会跳过
	 * 如果选了右边 会有如下情况
	 * d: [3,100]  h:[1,1,1,1,1,5,6]
	 * 对于d[0]而言, h[0]和h[1]一样 可以选择不跳
	 * 但是对于d[1]而言, h[0]和h[1]一样 但是不跳过的话 就一直在那里
	 * @param houses
	 * @param heaters
	 * @return
	 */
	public static int findRadius(int[] houses, int[] heaters) {
		Arrays.sort(houses);
		Arrays.sort(heaters);
		int ans = 0;
		// 时间复杂度O(N)
		// i是地点，j是供暖点
		for (int i = 0, j = 0; i < houses.length; i++) {
			while (!best(houses, heaters, i, j)) {
				j++;
			}
			ans = Math.max(ans, Math.abs(heaters[j] - houses[i]));
		}
		return ans;
	}

	/**
	 * 这个函数含义：
	 * 当前的地点houses[i]由heaters[j]来供暖是最优的吗？
	 * 当前的地点houses[i]由heaters[j]来供暖，产生的半径是a
	 * 当前的地点houses[i]由heaters[j + 1]来供暖，产生的半径是b
	 * 如果a < b, 说明是最优，供暖不应该跳下一个位置
	 * 如果a >= b, 说明不是最优，应该跳下一个位置
	 * @param houses
	 * @param heaters
	 * @param i
	 * @param j
	 * @return
	 */
	public static boolean best(int[] houses, int[] heaters, int i, int j) {
		return j == heaters.length - 1
				|| Math.abs(heaters[j] - houses[i]) < Math.abs(heaters[j + 1] - houses[i]);
	}

	// 下面这个方法不对，你能找出原因嘛？^_^
	public static int findRadius2(int[] houses, int[] heaters) {
		Arrays.sort(houses);
		Arrays.sort(heaters);
		int ans = 0;
		// 时间复杂度O(N)
		// i是地点，j是供暖点
		for (int i = 0, j = 0; i < houses.length; i++) {
			while (!best2(houses, heaters, i, j)) {
				j++;
			}
			ans = Math.max(ans, Math.abs(heaters[j] - houses[i]));
		}
		return ans;
	}

	public static boolean best2(int[] houses, int[] heaters, int i, int j) {
		return j == heaters.length - 1 || Math.abs(heaters[j] - houses[i]) <= Math.abs(heaters[j + 1] - houses[i]);
	}

	// 为了测试
	public static int[] randomArray(int len, int v) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int len = 5;
		int v = 10;
		int testTime = 10000;
		for (int i = 0; i < testTime; i++) {
			int[] a = randomArray(len, v);
			int[] b = randomArray(len, v);
			int ans1 = findRadius(a, b);
			int ans2 = findRadius2(a, b);
			if (ans1 != ans2) {
				System.out.println("A : ");
				for (int num : a) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println("B : ");
				for (int num : b) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
	}

}
