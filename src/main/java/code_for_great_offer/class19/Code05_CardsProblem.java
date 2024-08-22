package code_for_great_offer.class19;

import java.util.LinkedList;

/**
 * TODO
 * 一张扑克有3个属性，每种属性有3种值（A、B、C）
 * 比如"AAA"，第一个属性值A，第二个属性值A，第三个属性值A
 * 比如"BCA"，第一个属性值B，第二个属性值C，第三个属性值A
 * 给定一个字符串类型的数组cards[]，每一个字符串代表一张扑克
 * 从中挑选三张扑克，一个属性达标的条件是：这个属性在三张扑克中全一样，或全不一样
 * 挑选的三张扑克达标的要求是：每种属性都满足上面的条件
 * 比如："ABC"、"CBC"、"BBC"
 * 第一张第一个属性为"A"、第二张第一个属性为"C"、第三张第一个属性为"B"，全不一样
 * 第一张第二个属性为"B"、第二张第二个属性为"B"、第三张第二个属性为"B"，全一样
 * 第一张第三个属性为"C"、第二张第三个属性为"C"、第三张第三个属性为"C"，全一样
 * 每种属性都满足在三张扑克中全一样，或全不一样，所以这三张扑克达标
 * 返回在cards[]中任意挑选三张扑克，达标的方法数
 * <p>
 * TODO
 * A视为0  B视为1  C视为2 理解为3进制
 * 如果牌是 ABC
 * A B C
 * 0 1 2
 * 也就是 3^2 * 0 + 3^1 * 1  + 3^0 * 2 = 5
 * 如果牌是 BBB
 * A B C
 * 1 1 1
 * 也就是 3^2 * 1 + 3^1 * 1  + 3^0 * 1 = 13
 * TODO
 * 方法1：
 * 假设有3种牌， AAA牌有100张,AAB牌有200张 ,AAC牌有150张
 * 根据题目要求
 * 可以直接在100张AAA牌拿个相同的C(3,100)
 * 可以直接在200张AAB牌拿个相同的C(3,200)
 * 可以直接在100张AAC牌拿个相同的C(3,150)
 * 方法2：
 * 从自己的一堆里面挑选出一张，剩下的2张从其他牌面挑选
 * eg:
 * 假设有3种牌， ABC牌有100张,BAB牌有200张 ,CCA牌有150张
 * 那么牌面组合数 = 100*200*150
 * TODO
 * 每个牌面 有3种属性，每个属性有3种可能性
 * 那么一个牌有27种可能性
 * 如果从27种牌里面，选出3种完全不同的牌
 * 假设  有100张ABC  有50张BAB  有60张CCA
 * 那么总共的组合数 是100*50*60
 * 因为根据题意，要选出完全不同的3种牌，现在每种牌都有多个，主要从每种牌里面随便取出一个即可
 * 这也说明有一个暴力的方法 枚举
 * f(cards,index,picks) 来到某一个牌card[index]做出要/不要的选择，直到选出3种不同的牌面
 *
 *TODO
 * 1)
 * 先收集所有牌面，收集每种牌面有几张
 * 一张牌中有三种属性，每种属性只有A、B、C三种可能性，一共有27种牌面。
 * 收集每种牌面一共有多少张。可以用map来做，也可以用桶计数来做
 * 用桶计数来做，那么桶的大小可以设置为27，然后 使用3进制，将牌面对应成一个数值：A对应0，B对应1，C对应2.那么：
 * AAA：000 -->0
 * AAB：001 -->1
 * AAC：002 -->2
 * ABA：010 -->3
 * ABB：011 ---> 0 * 3^2 + 1 * 3^1 + 1 * 3^0 = 0 + 3 + 1 = 4
 * ABC：012 ---> 0 * 3^2 + 1 * 3^1 + 2 * 3^0 = 0 + 3 + 2 = 5
 * .....
 * 2)
 *  1.先处理从自己那堆挑选的方法数量：直接根据公式计算  C(n,m)(n是上标，m是下标）
 *      eg：有100张AAA牌，那么C(3,100) ====> 从自己那堆里面挑选
 * 	2.然后处理从其他的那堆挑选的方法数量 ====> 从其他人的堆里面挑选
 * 		2.1.枚举27个牌面，每个牌面要跟不要，但是一旦超过3种，就停止(剪枝)
 * 		2.2.到最后正好三个牌面，如果发现每一位都一样或者不一样，那么就把它们的数量取出来，然后相乘即可
 */
public class Code05_CardsProblem {

    public static int ways1(String[] cards) {
        LinkedList<String> picks = new LinkedList<>();
        return process1(cards, 0, picks);
    }

    public static int process1(String[] cards, int index, LinkedList<String> picks) {
        if (picks.size() == 3) {//说明当前已经选了3张牌了 开始做检查
            return getWays1(picks);
        }
        if (index == cards.length) {
            return 0;
        }
        int ways = 0;
        //选择1： 不拿走当前cards[index] 去考虑cards[index+1]是否要拿走
        int case1 = process1(cards, index + 1, picks);
        ways += case1;
        //选择2： 拿走当前cards[index] 去考虑cards[index+1]是否要拿走
        //这个选择 把cards[index] 放入到答案中
        picks.addLast(cards[index]);
        int case2 = process1(cards, index + 1, picks);
        ways += case2;
        //这是dfs的特点 回溯
        picks.pollLast();
        return ways;
    }

    public static int getWays1(LinkedList<String> picks) {
        char[] s1 = picks.get(0).toCharArray();
        char[] s2 = picks.get(1).toCharArray();
        char[] s3 = picks.get(2).toCharArray();
        for (int i = 0; i < 3; i++) {
            if ((s1[i] != s2[i] && s1[i] != s3[i] && s2[i] != s3[i]) || (s1[i] == s2[i] && s1[i] == s3[i])) {
                continue;
            }
            return 0;
        }
        return 1;
    }

    /*
     *TODO
     * 牌面的数量一共也就是27种 因为一共3个属性 每个属性有3种情况 3* 3 * 3
     * 一共有两种挑选方法：
     * 	1.从自己的那一堆挑选，那么：
     * 		 C(n,m)(n是上标，m是下标）
     * 		如果AAA有100张，这100张里随意挑选3张都达标C（3,100）
     * 		如果AAB有200张，这200张里随意挑选3张都达标的有C（3,200）
     * 	2.从自己的那一堆挑选一张，剩下的两张从其他牌面中挑选
     * 		如果AAA有100张，AAB有50张，AAC有20张，那么共有100 * 50 * 20种方法
     * */
    public static int ways2(String[] cards) {
        /**
         *TODO
         *  1）先收集所有牌面，收集每种牌面有几张
         * 一张牌中有三种属性，每种属性只有A、B、C三种可能性，一共有27种牌面。
         * 收集每种牌面一共有多少张。可以用map来做，也可以用桶计数来做
         * 用桶计数来做，那么桶的大小可以设置为27，然后 使用3进制，将牌面对应成一个数值：A对应0，B对应1，C对应2.那么：
         * AAA：000 -->0
         * AAB：001 -->1
         * AAC：002 -->2
         * ABA：010 -->3
         * ABB：011 ---> 0 * 3^2 + 1 * 3^1 + 1 * 3^0 = 0 + 3 + 1 = 4
         * ABC：012 ---> 0 * 3^2 + 1 * 3^1 + 2 * 3^0 = 0 + 3 + 2 = 5
         * */
        int[] counts = new int[27];
        for (String s : cards) {
            char[] str = s.toCharArray();
            //TODO 把牌面变成数字 的话 统计数字 就相当于统计牌数
            counts[(str[0] - 'A') * 9 + (str[1] - 'A') * 3 + (str[2] - 'A') * 1]++;
        }
        int ways = 0;
        /**
         *TODO 2)
         *  1.先处理从自己那堆挑选的方法数量：直接根据公式计算  C(n,m)(n是上标，m是下标）
         *      eg：有100张AAA牌，那么C(3,100)
         * 	2.然后处理从其他的那堆挑选的方法数量
         * 		2.1.枚举27个牌面，每个牌面要跟不要，但是一旦超过3种，就停止(剪枝)
         * 		2.2.到最后正好三个牌面，如果发现每一位都一样或者不一样，那么就把它们的数量取出来，然后相乘即可
         * */
        for (int status = 0; status < 27; status++) {
            int n = counts[status];
            if (n > 2) {
                //C(3,n) = (n * (n - 1) * (n - 2) / 6)
                ways += n == 3 ? 1 : (n * (n - 1) * (n - 2) / 6);
            }
        }

        LinkedList<Integer> path = new LinkedList<>();

        for (int i = 0; i < 27; i++) {
            /**
             * 下面的操作表示
             * 当前来到i，拿走i这张牌，
             * 下次选择从i往后开始选择，因为不能拿之前的牌了，防止重复
             */
            if (counts[i] != 0) {
                path.addLast(i);//拿走一张牌
                //跑后续
                ways += process2(counts, i, path);
                //把当前拿走的牌 还回去 跑其他分支  dfs的经典还原
                path.pollLast();
            }
        }
        return ways;
    }

    /**
     * 该函数的含义：
     * TODO
     * 重复的例子
     * eg:一开始先拿AAA，再拿BBB，最后拿CCC和一开始先拿BBB，再拿AAA，最后拿CCC 是一种情况
     * 所以 每次拿牌 一定 要 拿比pre大的牌面（牌数） 防止拿重复
     * 已知，把三个属性看成3进制
     * eg:
     * 有27种牌面
     * AAA = 3^2 * 0 + 3^1 * 0  + 3^0 * 0 = 0
     * AAB = 3^2 * 0 + 3^1 * 0  + 3^0 * 1 = 1
     * AAC = 3^2 * 0 + 3^1 * 0  + 3^0 * 2 = 2
     * ABA = ....
     * ABB = ....
     * 一开始 拿了AAA ，接下来又拿了AAC，
     * 那么之前AAB 就不能拿了，因为是 要拿 >2(AAC)的牌面
     * TODO
     * eg:
     * 之前的牌面，拿了一些。假设已经拿了 ABC  BBB 存到了path中 ...
     * 此时 pre = BBB  pre存的是之前拿的牌面中最大的
     * eg：
     * 之前拿了 ABC  ... 那么 pre  = ABC
     * eg:
     * 之前拿了 ABC BBB CAB 那么 pre = CAB
     * TODO
     * 牌面一定要依次变大，所有形成的有效牌面，把方法数返回
     *
     * @param counts
     * @param pre
     * @param path
     * @return
     */
    public static int process2(int[] counts, int pre, LinkedList<Integer> path) {
        if (path.size() == 3) {
            return getWays2(counts, path);
        }
        int ways = 0;
        //如果拿了 牌面是next 那么下一次从next之后的牌拿
        for (int next = pre + 1; next < 27; next++) {
            if (counts[next] != 0) {
                //当前牌面拿了next
                path.addLast(next);
                //走后续，从next+1开始往后挑选牌面，不看前面的了
                ways += process2(counts, next, path);
                //删掉 下一轮走其他后续  dfs还原现场
                path.pollLast();
            }
        }
        return ways;
    }

    public static int getWays2(int[] counts, LinkedList<Integer> path) {
        // 因为此时v1 v2 v3 已经是3进制的数了
        int v1 = path.get(0);//第1个牌面
        int v2 = path.get(1);//第2个牌面
        int v3 = path.get(2);//第3个牌面
        //TODO 下面的for就是判断 每个牌面的属性是否有效
        for (int i = 9; i > 0; i /= 3) {
            /**
             *TODO
             * 因为3进制 并且是3位 ，分别是 3^2,3^1,3^0
             * i = 9 的时候 v1 / 9 就能搞出第1个属性  也就是得到最高位
             * 同理， v2  v3也是如此
             * 都除完 之后,v1  v2  v3再取余，下次除3
             * ....
             */
            int cur1 = v1 / i;//第1个牌面的第1个属性 第2个属性 第3个属性
            int cur2 = v2 / i;//第2个牌面的第1个属性 第2个属性 第3个属性
            int cur3 = v3 / i;//第3个牌面的第1个属性 第2个属性 第3个属性
            v1 %= i;
            v2 %= i;
            v3 %= i;
            //检查是否当前的属性是否 全一样 或 全不一样
            if ((cur1 != cur2 && cur1 != cur3 && cur2 != cur3) || (cur1 == cur2 && cur1 == cur3)) {
                continue;
            }
            //不满足条件 才会返回0
            return 0;
        }
        // 来到这里的时候 说明 这3张牌 一定是满足条件的
        v1 = path.get(0);
        v2 = path.get(1);
        v3 = path.get(2);
        //3个牌面的牌数相乘
        return counts[v1] * counts[v2] * counts[v3];
    }

    // for test
    public static String[] generateCards(int size) {
        int n = (int) (Math.random() * size) + 3;
        String[] ans = new String[n];
        for (int i = 0; i < n; i++) {
            char cha0 = (char) ((int) (Math.random() * 3) + 'A');
            char cha1 = (char) ((int) (Math.random() * 3) + 'A');
            char cha2 = (char) ((int) (Math.random() * 3) + 'A');
            ans[i] = String.valueOf(cha0) + String.valueOf(cha1) + String.valueOf(cha2);
        }
        return ans;
    }

    // for test
    public static void main(String[] args) {
        int size = 20;
        int testTime = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String[] arr = generateCards(size);
            int ans1 = ways1(arr);
            int ans2 = ways2(arr);
            if (ans1 != ans2) {
                for (String str : arr) {
                    System.out.println(str);
                }
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test finish");

        long start = 0;
        long end = 0;
        String[] arr = generateCards(10000000);
        System.out.println("arr size : " + arr.length + " runtime test begin");
        start = System.currentTimeMillis();
        ways2(arr);
        end = System.currentTimeMillis();
        System.out.println("run time : " + (end - start) + " ms");
        System.out.println("runtime test end");
    }

}
