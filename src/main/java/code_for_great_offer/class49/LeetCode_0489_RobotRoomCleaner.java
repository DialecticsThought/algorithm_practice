package code_for_great_offer.class49;

import java.util.HashSet;

public class LeetCode_0489_RobotRoomCleaner {

	//不要提交这个接口的内容
	interface Robot {
		/*
		*TODO
		* 调用该方法 会检测是否对应的方向有障碍
		* 有障碍 就直接返回false
		* 没有障碍 对应方向走一步 再返回true
		* */
		public boolean move();
		//TODO 向左转
		public void turnLeft();
		//TODO 向右转
		public void turnRight();
		//TODO 当前位置打扫垃圾
		public void clean();
	}

	//提交下面的内容
	public static void cleanRoom(Robot robot) {
		/*
		*TODO 机器人初始位置在哪里 哪里就是0行0列 原点
		* */
		clean(robot, 0, 0, 0, new HashSet<>());
	}

	private static final int[][] ds = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
	/*
	*TODO
	* 机器人robot，
	* 当前来到的位置(x,y)，且之前没来过 hashset记录
	* 机器人脸冲什么方向d，对应数组下标：0 1 2 3
	* visited里记录了机器人走过哪些位置
	* 函数的功能：不要重复走visited里面的位置，把剩下的位置，都打扫干净！
	* 而且要回去！
	*/
	public static void clean(Robot robot, int x, int y, int d, HashSet<String> visited) {
		//TODO 1.先打扫
		robot.clean();
		//TODO 2.把当前位置存下来
		visited.add(x + "_" + y);
		/*
		*TODO
		* 对于机器人，
		* 1.如果初始先去0方向，打扫后，还要回到当前位置
		* 再去1方向，打扫后，还要回到当前位置 也就是turnRight()
		* 再去2方向，打扫后，还要回到当前位置 也就是turnRight()
		* 再去3方向，打扫后，还要回到当前位置 也就是turnRight()
		* 2.如果初始先去1方向，打扫后，还要回到当前位置
		* 再去2方向，打扫后，还要回到当前位置
		* 再去3方向，打扫后，还要回到当前位置
		* 再去0方向，打扫后，还要回到当前位置
		* 3.如果初始先去2方向，打扫后，还要回到当前位置
		* 再去3方向，打扫后，还要回到当前位置
		* 再去0方向，打扫后，还要回到当前位置
		* 再去1方向，打扫后，还要回到当前位置
		* 3.如果初始先去3方向，打扫后，还要回到当前位置
		* 再去0方向，打扫后，还要回到当前位置
		* 再去1方向，打扫后，还要回到当前位置
		* 再去2方向，打扫后，还要回到当前位置
		* */
		for (int i = 0; i < 4; i++) {
			/*
			*TODO
			* d = 0 :  0 1 2 3
			* d = 1 :  1 2 3 0
			* d = 2 :  2 3 0 1
			* d = 3 :  3 0 1 2
			* 下一步的方向！
			*/
			int nextDirection = (i + d) % 4;
			//TODO 当下一步的方向定了！下一步的位置在哪？(nextX, nextY)
			int nextX = ds[nextDirection][0] + x;
			int nextY = ds[nextDirection][1] + y;
			//TODO 如果下一步没有走过 并且真的来到了下一步位置了
			if (!visited.contains(nextX + "_" + nextY) && robot.move()) {
				/*
				*TODO 举例
				* 假设 机器人向下面的图像 移动
				* ↑ → → → → → →
				* ↑           ↓
				* ← ← ←   ← ← ←
				*     ↑   ↓
				*     ↑   ↑
				* 机器人从某位置出发之后 最终还要回到那个位置
				* 不仅回到那个位置，回到该位置之后 机器人的方向要和最初时 在该位置的方向相同
				*TODO
				* 下面clean函数 用 nextX, nextY还是作为形参的原因
				* 上游的函数 调用 子函数 去 某一个位置即（nextX, nextY）时，已经到达（nextX, nextY）了
				* 但是没有记录到visited，所以子函数 需要visited.add(x + "_" + y);
				* 所以不是子函数到达（nextX, nextY）
				* 但是从（nextX, nextY）回到原始位置 是利用子函数
				* */
				clean(robot, nextX, nextY, nextDirection, visited);
			}
			//TODO 机器人回到 原始位置之后 向右转 向左转也可以
			robot.turnRight();
		}
		/*
		*TODO
		* 负责回去：之前的位置，怎么到你的！你要回去，而且方向和到你之前，要一致！
		* 假设eg:
		* 最开始的方向是：X --> Y
		* X是原始位置 Y指的是X的右侧 机器人初始指向的是右侧Y
		* 如果我想让机器人 在外面走了一群圈之后重新回到该位置X 并且 方向和初始相同的话
		* 就是旋转180°方向 即 X <-- Y，再走回到X位置
		* 回到X位置之后，为了让机器人和原始的方向相同
		* 需要再旋转180°方向
		 * */
		robot.turnRight();
		robot.turnRight();
		robot.move();
		robot.turnRight();
		robot.turnRight();
	}

}
