package code_for_great_offer.class38;
/*
*TODO
* 给你一个用字符数组tasks 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。
* 任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。在任何一个单位时间，CPU 可以完成一个任务，或者处于待命状态。
* 然而，两个 相同种类 的任务之间必须有长度为整数 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
* 你需要计算完成所有任务所需要的 最短时间 。
* 示例 1：
* 输入：tasks = ["A","A","A","B","B","B"], n = 2
* 输出：8
* 解释：A -> B -> (待命) -> A -> B -> (待命) -> A -> B
*      在本示例中，两个相同类型任务之间必须间隔长度为 n = 2 的冷却时间，而执行一个任务只需要一个单位时间，所以中间出现了（待命）状态。
* 示例 2：
* 输入：tasks = ["A","A","A","B","B","B"], n = 0
* 输出：6
* 解释：在这种情况下，任何大小为 6 的排列都可以满足要求，因为 n = 0
* ["A","A","A","B","B","B"]
* ["A","B","A","B","A","B"]
* ["B","B","B","A","A","A"]
* ...
* 诸如此类
* 示例 3：
* 输入：tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
* 输出：16
* 解释：一种可能的解决方案是：
*      A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> (待命) -> (待命) -> A -> (待命) -> (待命) -> A
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/task-scheduler
*/
public class Problem_0621_TaskScheduler {

	/*
	*TODO
	* eg: [a b b a c b c c a d a d a e e e e] k=3
	* 找到出现次数最多的任务 是任务a 出现了5次 其他任务没有出现5次及以上的
	* ①.哪个任务的数量多 哪个先填写 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
	* 那么构造一个 任务执行链
	* a _ _ _ a  _ _ _ a _ _ _ a _ _ _ a
	* ②。这些空格的数量 等于 k ，让剩下的任务填满 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
	* 先填b任务 c有3个
	* a b _ _ a b _ _ a b _ _ a _ _ _ a
	* 再填c任务 c有3个
	* a b c _ a b c _ a b _ _ a c _ _ a
	* 再填d任务 d有2个
	* a b c _ a b c _ a b d _ a c d _ a
	* 最后是e任务
	* 直接放进去即可
	* eg2: <a,4> <b,1> <c,2> <d,3> k=3
	* 1.a _ _ _ a  _ _ _ a _ _ _ a
	* 2.a d _ _ a d _ _ a d _ _ a
	* 3.a d c _ a d c _ a d _ _ a
	* 4.a d c b a d c _ a d _ _ a
	*TODO 如果存在格子耗尽的情况
	* eg3: <a,4> <b,3> <c,3> <d,3> <e,3> <f,2> k=3
	* ③.相同的任务数量 顺序无所谓 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
	* 1.a _ _ _ a _ _ _ a _ _ _ a
	* 2.a b _ _ a b _ _ a b _ _ a
	* 3.a b c _ a b c _ a b c _ a
	* 4.a b c d a b c d a b c d a
	* ④.空格都消耗完之后  以 a b c d为一组 继续后面加任务
	* 5.a b c d e | a b c d e| a b c d e | a
	* 6.a b c d e f | a b c d e f | a b c d e | a
	*TODO 如果有多个 出现次数最多的任务
	* eg3:<a,5> <b,5> <c,5> k=3
	* 1.a b c _ _ _ a b c _ _ _ a b c _ _ _ a b c _ _ _ a b c
	 * */
	public static int leastInterval(char[] tasks, int free) {
		int[] count = new int[256];
		//TODO 先找到 出现最多次的任务x，到底是出现了几次
		int maxCount = 0;
		for (char task : tasks) {
			count[task]++;
			maxCount = Math.max(maxCount, count[task]);
		}
		//TODO 有多少种任务，都出现最多次
		int maxKinds = 0;
		for (int task = 0; task < 256; task++) {
			if (count[task] == maxCount) {
				maxKinds++;
			}
		}

		/*
		*
		*
		*TODO
		* maxKinds : 有多少种任务，都出现最多次
		* maxCount : 最多次，是几次？
		*  因为我们让之前的空格和非空格的位置填满
		* 最后一组剩余的任务数另外算
		* tasksExceptFinalTeam就是  砍掉最后一组剩余的任务数
		* */
		int tasksExceptFinalTeam = tasks.length - maxKinds;
		/*
		*TODO 假设任务a最多  <a,4>   k=3 总任务数量是N
		* a _ _ _ a _ _ _ a _ _ _ a
		* 排除最后一个任务a
		* a _ _ _ a _ _ _ a _ _  => maxCount - 1
		* 再把之前的所有a 看成"_"
		* _ _ _ _ | _ _ _ _ | _ _ _ _ => free + 1
		* 算出了总空格数量
		* 再 让 除了多余的最后一组任务（这里是只有a）的其他所有任务放在空格里面
		* eg: <a,4> <b,4> <c,4> <d,3> <e,3> k=3（free）
		* 这个例子中 最后一组 一定是 "a b c" 没有空格
		* 每一组 是 a b c _ _ _  如果把一组 前面3个字母抽掉
		* 那么就是 6个空格 _ _ _ _ _ _
		* 6 *（4-1） 4是指的是"a b c"的组的数量 = 18 个空格
		* 这18个空格 怼入（18-3）个任务数量 因为总共18个任务 -3是因为 最后一组的"a b c"被排除在外了
		* 那么剩下多个空格？ 剩下3个 这个情况是 空格数 > 排除掉最后一组任务的任务数量
		* eg：<a,3> <b,2> <c,1> k=4  一共6个任务
		* a _ _ _ _ | a _ _ _ _ | a
		* _ _ _ _ _ | _ _ _ _ _ | a
		* 此时预留了10个空格 排除掉最后一组的任务a 剩下5个任务
		* 把这6个任务 放到空格中 那还剩下5个空格
		* a b c _ _ | a b  _ _ _ | a
		* 那么答案就是 6+5
		* eg：<a,3> <b,2> <c,1> k=1  一共6个任务
		* a _ | a _ | a
		* _ _ | _ _ | a
		* 此时预留了4个空格 排除掉最后一组的任务a 剩下5个任务
		* a b | a b | a
		* 最后一个c =>  a b c | a b | a
		* 那么答案就是 6+0
		* */
		int spaces = (free + 1) * (maxCount - 1);
		//TODO 到底几个空格最终会留下！
		// 空格数 < 排除掉最后一组任务的任务数量 那么就是会有0个空格留下
		int restSpaces = Math.max(0, spaces - tasksExceptFinalTeam);
		//TODO 总共的任务+留下来的空格数 = ans
		return tasks.length + restSpaces;
		// return Math.max(tasks.length, ((n + 1) * (maxCount - 1) + maxKinds));
	}


}
