package algorithmbasic2020_master.class14;

import java.util.HashSet;

/*
* 给定一个字符串str，只由X'和∵'两种字符构成。×'表示墙，不能放灯，也不需要点亮
* "∵'表示居民点，可以放灯，需要点亮
* 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
*
* */
public class Code01_Light {

	public static int minLight1(String road) {
		if (road == null || road.length() == 0) {
			return 0;
		}
		//index = 0 表示 从头开始放灯 还没开始做
		return process(road.toCharArray(), 0, new HashSet<>());
	}

	/* str[index....]位置，自由选择放灯还是不放灯
	   str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights这个set里面
	   1.要求选出能照亮所有.的方案，
	  2.并且在这些有效的方案中，返回最少需要几个灯
	*/
	public static int process(char[] str, int index, HashSet<Integer> lights) {
		if (index == str.length) { // index来到终止位置的时候 结束的时候  已经没有地方放灯了
			/*
			* 这个for循环验证 你所选择的方案 也就是lights这个set能否照亮所有居民点
			* */
			for (int i = 0; i < str.length; i++) {
				if (str[i] != 'X') { //TODO  如果String的某一个字符不是'X' 意味着当前位置是点的话 这个点的位置是i
					//TODO 检查 i—1 i i+1 这三个2位置是否已有lights
					if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
						//TODO 没有的话 返回 这个数字 表示无效界
						return Integer.MAX_VALUE;
					}
				}
			}
			return lights.size();//返回set的size意味着返回放了几盏灯
		} else { //TODO str还没结束 那还能做选择 选择后续要放几盏灯
			//TODO 位置i不管是 X 还是 . 都有一个选择就是 这个点不放灯
			//TODO 没有把位置i放入到lights这个set里面 也就是当前位置不放灯 让i+1的位置做选择要不要放灯
			int no = process(str, index + 1, lights);
			int yes = Integer.MAX_VALUE;
			if (str[index] == '.') {//TODO ，的话 有放灯的可能性
				lights.add(index);//TODO 也就是当前位置i 放灯 把index放入到set里面
				//TODO 放完灯 后 继续往下做决定 也就是index+1
				yes = process(str, index + 1, lights);
				/*
				*TODO 把当前位置移除 恢复现场
				* eg 有三个点 。。。 0 1 2
				* 0有两个选择 不放灯 放灯 1 有两种选择 不放灯 放灯 2 也是
				* 不同的选择交错放在lights里面 eg： 有一个选择就是 0不放灯 1 放灯 lights里面有一个 1
				*  如果还想要1放灯的选择 那么就要把 0不放灯 1 放灯 删除 确保lights里面什么都没有 也就是恢复现场 才能放入1 仅lights
				 * */
				lights.remove(index);
			}
			//TODO 求最小值 不放灯和放灯之间哪个等最少
			return Math.min(no, yes);
		}
	}
	/*
	* 总是在当前这一步做出最优的决定
	* */
	public static int minLight2(String road) {
		char[] str = road.toCharArray();
		int i = 0;
		int light = 0;
		while (i < str.length) {//越界说明昨晚所有决定
			if (str[i] == 'X') {//如果是墙的话不用做决定
				i++;//让后面一个位置做决定
			} else {//意味着i位置是“.”
				light++;//只要是i 就一定加灯
				if (i + 1 == str.length) {//如果下一个位置没有字符 就结束
					break;
				} else { // 有i位置  i+ 1   X  .
					if (str[i + 1] == 'X') {//i+1位置是X i一定放灯（之前已经坐了light++） 再看i+2是否放灯
						i = i + 2;
					} else {//也就是i i+1 i+2 都是 "." 也就是i+1位置加灯（之前已经坐了light++） 再去看i+3是否放灯
						i = i + 3;
					}
				}
			}
		}
		return light;
	}

	// for test
	public static String randomString(int len) {
		char[] res = new char[(int) (Math.random() * len) + 1];
		for (int i = 0; i < res.length; i++) {
			res[i] = Math.random() < 0.5 ? 'X' : '.';
		}
		return String.valueOf(res);
	}

	public static void main(String[] args) {
		int len = 20;
		int testTime = 100000;
		for (int i = 0; i < testTime; i++) {
			String test = randomString(len);
			int ans1 = minLight1(test);
			int ans2 = minLight2(test);
			if (ans1 != ans2) {
				System.out.println("oops!");
			}
		}
		System.out.println("finish!");
	}
}
