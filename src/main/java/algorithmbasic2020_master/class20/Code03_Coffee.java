package algorithmbasic2020_master.class20;


import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
 * 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
 * 认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
 * 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
 * 洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
 * 四个参数：arr, n, a, b
 * 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。
 * TODO
 * 咖啡杯会发是可以并行的
 * 但是咖啡机只能串行洗杯子
 * 现在有一个小根堆 堆里面每一个元素是对象<目前咖啡机什么时候可以用，开飞机跑完一杯要多久>
 * 小根堆的比较：再可用的时间+泡一杯的时间 = 总时间   然后从小到大排序
 * 小根堆加入后再弹出的过程模拟了排队
 * 假设:arr=[3,1,7]
 * eg:初始小根堆如下
 * <0,1>  表示0号时间点可用 泡一杯要1时间单位
 * <0,3>  表示0号时间点可用 泡一杯要3时间单位
 * <0,7>
 * 1. 0号小人到来，从堆中得到<0,1>
 * 此时堆里 <0,3> <0,7> [从小到大]
 * 1时间跑完并喝完咖啡，咖啡机可用把<1,1>放入堆里
 * 此时堆里 <1,1> <0,3> <0,7> [从小到大]
 * 2. 1号小人到来，从堆中得到<1,1>
 * 此时堆里 <0,3> <0,7> [从小到大]
 * 2时间跑完并喝完咖啡，咖啡机可用把<2,1>放入堆里
 * 此时堆里 <2,1> <0,3> <0,7> [从小到大]     <2,1>和<0,3>的排序无所谓
 * 3. 2号小人到来，从堆中得到<2,1>
 * 此时堆里 <0,3> <0,7> [从小到大]
 * 3时间跑完并喝完咖啡，咖啡机可用把<3,1>放入堆里
 * 此时堆里 <0,3> <3,1>  <0,7> [从小到大]
 * 4. 3号小人到来，从堆中得到<0,3>
 * 此时堆里 <3,1> <0,7> [从小到大]
 * (0+3)时间跑完并喝完咖啡，咖啡机可用把<3,3>放入堆里
 * 此时堆里 <3,1> <3,3> <0,7> [从小到大]
 * note:
 * 看上去人是一个个来的，但是其实是一起来的
 * eg:
 * 现在每个人喝完的最优时间arr[1,4,7]
 * 现在洗杯子要3时间，挥发要8时间
 * 那么一开始f(0,0)表示 0号用户要洗杯子，洗杯子机0时间可用
 * 那么f(0,0)有2各分支 1.用咖啡机洗，2.挥发  取2个分支的min最小
 *                                       f(0,0)
 *                            洗↙                        ↘ 挥发
 *                      max{1+3,f(1,4)}                 max{1+8,f(1,4)}
 *              洗↙                          ↘ 挥发
 * max{max{4,arr[1]}+3,f(2,max{4,arr[1]+3})}  max{arr[1]+8,f(2,arr[1]+8)}
 *                      洗↙          ↘ 挥发
 *  ........................................
 * 1. max{1+3,f(1,4)} 表示0好用户要在1+3时间内变赶紧，f(1,4)表示现在要让1号用户做决定，但是咖啡机可以再用的时间点是4
 * 2. f(2,max{4,arr[1]+3})取max的原因：1号用户要等0号用户用完洗杯子机才能用
 */
public class Code03_Coffee {

    // 验证的方法
    // 彻底的暴力
    // 很慢但是绝对正确
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

    /*
     *TODO
     * 咖啡机洗杯子的时间 a  固定变量
     * 挥发的时间 b  固定变量
     * 每一个员工喝完的时间  固定变量
     * drink[0..index-1]都已经写完了 决定好了 只管 index后面
     * washline 表示咖啡机什么时候可以用
     * 返回drink[index...]变干净的最短时间
     * */
    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
		/*if (index == drinks.length) {
			return time;
		}*/
        if (index == drinks.length - 1) {
            /*
             *TODO
             * 处理最后一杯咖啡的时候 两种选择 挥发 放入咖啡机
             * 放入咖啡机
             * （咖啡机有空的时间点和喝完index咖啡的时间点）两个时间取最大值最大值 + 咖啡机洗杯子的时间a
             * eg ：
             * 咖啡机有空的时间点=3  喝完index咖啡的时间点=2 用咖啡机洗的话 必须在3时间点使用
             * 咖啡机有空的时间点=4  喝完index咖啡的时间点=20 用咖啡机洗的话 等必须在20时间点使用
             * 但是 挥发
             * 只要喝完咖啡就可以 没有其他限制条件
             * */
            return Math.min(Math.max(washLine, drinks[index]) + a, drinks[index] + b);
        }

        //TODO 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;//用咖啡机洗完当前index咖啡杯的时间点
        /**
         * TODO  next1是 让index+1到最后的所有咖啡杯变干净的最早时间
         * */
        int next1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        //TODO  选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int next2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(next1, next2);
    }

    //TODO  优良一点的暴力尝试的方法和minTime1相同 自己写的 ☆☆☆☆☆☆☆
    public static int minTime3(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return process(drinks, a, b, 0, 0);
    }

    /**
     * TODO drinks[]一开始是每个人喝完咖啡的最优时间
     *  a是洗一杯的事件 固定变量 （串行）
     *  b自己挥发干净的时间  固定变量 （并行）
     *    drinks每一个员工喝完的时间固定变量
     *  drinks[o ..index-1]都已经干净了，不用你操心了
     *  drinks[index.. .]都想变干净，这是我操心的,washLine表示洗的机器何时可用
     *  drinks[index...]变干净，最少的时间点返回
     */
    public static int process(int[] drinks, int a, int b, int index, int washLine) {
		/*if (index == drinks.length) {
			return time;
		}*/
        if (index == drinks.length - 1) {
            /**
             *TODO
             * 处理最后一杯咖啡的时候 两种选择 挥发 放入咖啡机
             * 放入咖啡机
             * （咖啡机有空的时间点和喝完index咖啡的时间点）两个时间取最大值最大值 + 咖啡机洗杯子的时间a
             * eg ：
             * 咖啡机有空的时间点=3  喝完index咖啡的时间点=2 用咖啡机洗的话 必须在3时间点使用
             * 咖啡机有空的时间点=4  喝完index咖啡的时间点=20 用咖啡机洗的话 等必须在20时间点使用
             * 但是 挥发
             * 只要喝完咖啡就可以 没有其他限制条件
             * */
            return Math.min(Math.max(washLine, drinks[index]) + a, drinks[index] + b);
        }

        //TODO 选择一（树的分支1）：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;//用咖啡机洗完当前index咖啡杯的时间点
        /**
         * next1是 用咖啡机让index+1到最后的所有咖啡杯变干净的最早时间
         * */
        int next1 = process(drinks, a, b, index + 1, wash);
        /**
         * 让 index开始到最后的所有杯子洗完的情况
         * 必须让index杯子洗完 还要让index+1到最后的所有杯子洗完
         * 那么 就看 哪个时间最久
         * eg：index杯子洗完 是时间点5  让index+1到最后的所有杯子洗完 是时间点10
         * 最终时间是 是时间点10  这才是第一种可能性得到的最早的时间
         * */
        int p1 = Math.max(wash, next1);
        //TODO 选择二（树的分支2）：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;//选择自然挥发洗完当前index咖啡杯的时间点
        //TODO next2是 让index+1到最后的所有咖啡杯变干净的最早时间
        int next2 = process(drinks, a, b, index + 1, washLine);
        /**
         *TODO
         * 让 index开始到最后的所有杯子洗完的情况
         * 必须让index杯子洗完 还要让index+1到最后的所有杯子洗完
         * 那么 就看 哪个时间最久
         * eg：index杯子洗完 是时间点5  让index+1到最后的所有杯子洗完 是时间点10
         * 最终时间是 是时间点10  这才是第一种可能性得到的最早的时间
         * */
        int p2 = Math.max(dry, next2);
        //TODO 返回两种情况（2中分支）的最小时间
        return Math.max(p1, p2);
    }

    // 以下为贪心+优良暴力
    public static class Machine {
        public int timePoint;
        public int workTime;

        public Machine(int t, int w) {
            timePoint = t;
            workTime = w;
        }
    }

    // TODO 小根堆   比较咖啡机什么时候可用 + 咖啡要泡多久
    public static class MachineComparator implements Comparator<Machine> {
        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }
    }

    // 优良一点的暴力尝试的方法
    public static int minTime1(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return bestTime(drinks, a, b, 0, 0);
    }


    /*
     TODO
       drinks 所有杯子可以开始洗的时间
       wash 单杯洗干净的时间（串行）
       air 挥发干净的时间(并行)
       free 洗的机器什么时候可用
       drinks[index.....]都变干净，最早的结束时间（返回）
    */
    public static int bestTime(int[] drinks, int wash, int air, int index, int free) {
        if (index == drinks.length) {//TODO 说明没有杯子要洗了
            return 0;
        }
        //TODO index号杯子 i号用户 杯子干净的时间 决定洗  Max（喝完的时间 和 洗杯子机器的可用时间）+洗杯子的时间
        int selfClean1 = Math.max(drinks[index], free) + wash;
        //TODO 剩下的杯子变干净的时间 有2个变量 一个是第几个人要洗杯子 另一个人是 洗杯子的机器什么时候有空
        int restClean1 = bestTime(drinks, wash, air, index + 1, selfClean1);
        int p1 = Math.max(selfClean1, restClean1);

        //TODO index号杯子 决定挥发
        int selfClean2 = drinks[index] + air;//TODO 喝完的时间+挥发的时间 = i号用户 杯子干净的时间
        int restClean2 = bestTime(drinks, wash, air, index + 1, free);
        int p2 = Math.max(selfClean2, restClean2);
        return Math.min(p1, p2);
    }

    // 贪心+优良尝试改成动态规划
    public static int minTime2(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return bestTimeDp(drinks, a, b);
    }

    public static int bestTimeDp(int[] drinks, int wash, int air) {
        int N = drinks.length;
        int maxFree = 0;
        for (int i = 0; i < drinks.length; i++) {
            maxFree = Math.max(maxFree, drinks[i]) + wash;
        }
        int[][] dp = new int[N + 1][maxFree + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int free = 0; free <= maxFree; free++) {
                int selfClean1 = Math.max(drinks[index], free) + wash;
                if (selfClean1 > maxFree) {
                    break; // 因为后面的也都不用填了
                }
                // index号杯子 决定洗
                int restClean1 = dp[index + 1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);
                // index号杯子 决定挥发
                int selfClean2 = drinks[index] + air;
                int restClean2 = dp[index + 1][free];
                int p2 = Math.max(selfClean2, restClean2);
                dp[index][free] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime1(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            int ans4 = minTime3(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3 + " , " + ans4);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }

    public void test01() {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        int[] arr = randomArray(len, max);
        int n = (int) (Math.random() * 7) + 1;
        int a = (int) (Math.random() * 7) + 1;
        int b = (int) (Math.random() * 10) + 1;
        int ans1 = minTime2(arr, n, a, b);
        int ans2 = minTime3(arr, n, a, b);
        System.out.println(ans1 + " , " + ans2 + " , " + (ans1 == ans2));
        System.out.println("测试结束");
    }

}
