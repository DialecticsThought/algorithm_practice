package algorithmbasic2020_master.class19;

import java.util.HashMap;

/**
 *TODO
 * 本题测试链接：https://leetcode.com/problems/stickers-to-spell-word
 * 题目的本质： 用贴纸把str里面的字符包含完全
 * str = "abc"
 * sticker[] = ["ac","ka"]
 * |         abc
 * |     用ac↙     ↘ 用ka
 * |     b          bc
 * | 用ac↙↘ 用ka  用ac ↙ ↘ 用ka
 * | X       X       b      X
 * |            用ac ↙ ↘ 用ka
 * |              X     X
 * str = "babca" "bba" "ccc" "aaa"
 * 先用贴纸 "bba" => b和b和a
 * 现在缺c和a
 * 用贴纸 "ccc" 中的一个c
 * 要a的话 用贴纸 "bba" "aaa"
 * eg: "babbac" 被认为是 "aabbbc" 因为顺序无所谓
 * 有贴纸 ab bba  ac  cc
 * 每个节点都有4个分支 因为有4个贴纸  也就是通过枚举来解决 也就是for循环
 * |            "aabbbc"
 * |   ab ↙   bba ↙    ac  ↘    cc ↘
 * |   abbc     abc       abb      aabbb
 * |   .......
 *
 */

public class Code_691_StickersToSpellWord {
    /**
     * 给定一个字符串str，给定一个字符串类型的数组arr。
     * arr里的每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来。
     * 返回需要至少多少张贴纸可以完成这个任务。例子: str= "babac", arr = {"ba","c","abcd"}
     * 至少需要两张贴纸"ba"和"abcd"，因为使用这两张贴纸，把每一个字符单独剪开，含有2个a、2个b、1个c。
     * 是可以拼出str的。所以返回2。
     */
    public static int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 所有贴纸stickers，每一种贴纸都有无穷张
    // target
    // 最少张数
    public static int process1(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        //初始化 因为要求出最少使用贴纸的数量 那么先定义下来
        int min = Integer.MAX_VALUE;
        /*
         *TODO
         * eg： "babbac" 还有一些贴纸{"ab","bba","ac","cc"} 把这个字符串认为是 "aabbbc"
         * 这类似于一个决策树，从"aabbbc"开始到结束之间每一个节点都会有4种分支（4种贴纸）
         * 具体来讲 "aabbbc"这个头结点有4中分支
         * "aabbbc" 通过使用贴纸"ab" -> "abbc"  通过使用贴纸"ab" --> "bc"  ...
         * 								    通过使用贴纸"ac" --> "bb"  ...
         * 								    通过使用贴纸"bba"--> "c"   ...
         * 									通过使用贴纸"cc" --> "abb" ...
         * 		   通过使用贴纸"bba" -> "abc"  通过使用贴纸"ab"  ...
         * 								    通过使用贴纸"ac"  ...
         * 								    通过使用贴纸"bba" ...
         * 									通过使用贴纸"cc"  ...
         * 		   通过使用贴纸"ac" -> "abb"   ...
         * 		   通过使用贴纸"cc" -> "aabbb" ...
         * */
        for (String sticker : stickers) {//TODO 对剩余字符串遍历sticker里面的所有贴纸 假设每张贴纸都成为第一张的情况下 做遍历
            //TODO 也就是说 每个剩余字符串（树的所有节点除了叶子结点）
            //TODO rest减掉sticker上面的字符变成下一Rest
            String rest = minus(target, sticker);
            if (rest.length() != target.length()) {
                //TODO 定义一个变量 来表示当前决策所使用的的最小数量
                int cur = process1(stickers, rest);
                min = Math.min(min, cur);//不断更新最小数量
            }
        }
        //return min + (min == Integer.MAX_VALUE ? 0 : 1);
        if (min == Integer.MAX_VALUE) {
            return min;
        }
        return min + 1;
    }

    public static String minus(String s1, String s2) {
        /*
         *TODO 变成字符数组
         * eg： 字符串是"babbac"  但是贴纸数组里面有"xyz" 和字符串的每一个字符都没关系
         * 所以把这个贴纸数组里面的"xyz" 删除
         * */
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] count = new int[26];
        for (char cha : str1) {
            count[cha - 'a']++;
        }
        for (char cha : str2) {
            count[cha - 'a']--;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    builder.append((char) (i + 'a'));
                }
            }
        }
        return builder.toString();
    }

    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        // 关键优化(用词频表替代贴纸数组)
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }
        int ans = process2(counts, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    //TODO stickers[i] 数组，当初i号贴纸的字符统计 int[][] stickers -> 所有的贴纸
    // 每一种贴纸都有无穷张
    // 返回搞定target的最少张数
    // 最少张数
    public static int process2(int[][] stickers, String t) {
        if (t.length() == 0) {
            return 0;
        }
        // target做出词频统计
        // target  aabbc  2 2 1..
        //                0 1 2..
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            // 尝试第一张贴纸是谁
            int[] sticker = stickers[i];
            // 最关键的优化(重要的剪枝!这一步也是贪心!)
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int nums = tcounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    public static int minStickers3(String[] stickers, String target) {
        int N = stickers.length;
        /**
         *TODO 这个counts就代表了stickers所有贴纸
         * eg:"baabk" "aaccc" "afffd"
         * 这个表格就是N个行（N是stickers的数组长度） 26是字母表
         * "baabk" 就是 2,2,0,0,0....1,0,0,...,0
         *  这个表的某一行第0列就是a出现的次数 第2列就是b出现的次数（出现了2次） 第10列的1表示k出现1次
         * */
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            /**
             * 遍历数组里面的字符串元素
             * 每一个字符串元素 转成 字符的数组
             * 转成 字符的数组之后
             * 对字符的数组进行遍历
             * 每个贴纸的每一个字符 执行cha - 'a' ASCII码加减得到
             * eg ’b‘-'a'=1 进行 counts[i][cha - 'a']++ =counts[i][1]++
             * 表示b出现次数+1
             * */
            char[] str = stickers[i].toCharArray();
            /*
             *
             * */
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);//在空字符的时候返回0张贴纸
        //这个函数的唯一可变参数就是target
        int ans = process3(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * target剩余字符串
     * t的子过程如果被算过 就会存在dp里面
     * 返回值是-1 map中的贴纸 怎么都无法搞定rest
     */
    public static int process3(int[][] stickers, String rest, HashMap<String, Integer> dp) {
        /**
         * dp 有一个key 等于rest 剩余字符串 意思： 之前已经算过了
         * 直接从dp里面拿出计算过的结果value
         * */
        if (dp.containsKey(rest)) {
            return dp.get(rest);
        }
        //下面的代码就是正式的递归调用
        /**
         * 把rest变成字符数组
         * eg “bbbaa” = 230000000..00  一个词频数组 每一个数字都是字母出现的次数
         * tcounts 替代了rest剩余字符串
         * 接下来就是用counts表格来解决target
         * */
        char[] target = rest.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int N = stickers.length;//得到一共有几种贴纸
        //搞定rest 使用的贴纸数量的最小值 初始值Integer.MAX_VALUE
        int min = Integer.MAX_VALUE;
        /**
         * 枚举当前第一张贴纸是谁
         * */
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {//TODO 让所有贴纸含有target第1个字符的贴纸 去做判断 贪心
                StringBuilder builder = new StringBuilder();
                /*
                 *TODO 判断贴纸里面的字符是否完全和rest里面的字符无关
                 * eg "abc" "xyz"
                 * */
                int count = 0;
                for (int k = 0; k < stickers[i].length; k++) {
                    /**
                     * 明i号贴纸和rest都有某一个字符stickers[i][k]>0 && tcounts[k]>0
                     * */
                    if (stickers[i][k] > 0 && tcounts[k] > 0) {
                        count++;
                    }
                }
                if (count == 0) {//说明i号贴纸和rest没有一个字符是有关的
                    break;
                }
                /**
                 * i号贴纸的循环里面 j枚举（循环）a~z字符
                 * tcounts是由rest转变过来的
                 * eg：rest的tcounts [17,13,10] 表示 17个a 13个b 10个c
                 * 贴纸表格里面的某一行 有 [10,7,19] 表示10个a 7个b 19个c 代表了某一个贴纸 有这些字母
                 * rest和贴纸抵消 后 看看是否有剩余 (对应if (tcounts[j] > 0))
                 * 发现剩余7个a 6个b -9个c
                 * -9 < 0那么 对应k < Math.max(0, tcounts[j] - stickers[i][j]) 取0
                 * builder.append((char) ('a' + j)) 指的是7个a 6个b 在builder变量里面
                 * 添加“aaaaaaabbbbbb” 因为-9个c 取0 那么就是添加0个c
                 * 这个“aaaaaaabbbbbb”就是剩下待解决的字符串也就是rest2
                 * */
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
						/*int nums = tcounts[j] - sticker[j];
						for (int k = 0; k < nums; k++) {
							builder.append((char) (j + 'a'));
						}*/
                        for (int k = 0; k < Math.max(0, tcounts[j] - stickers[i][j]); k++) {
                            builder.append((char) ('a' + j));
                        }
                    }
                }
                String rest2 = builder.toString();
                /**
                 *剩下待解决的字符串也就是rest2丢到函数中去
                 * stickers和dp参数不变
                 * */
                int tmp = process3(stickers, rest2, dp);
                if (tmp != -1) {
                    //min = Math.min(min,tmp);
                    //当你使用了i号贴纸作为第一张贴纸的时候 1+tmp个贴纸数就是你此时的解决方案
                    min = Math.min(min, tmp + 1);
                }

            }
        }
        //int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
		/*if(min == Integer.MAX_VALUE){
			ans = -1;
		}else {
			ans = min;
		}*/
        int ans = (min == Integer.MAX_VALUE ? -1 : min);
        dp.put(rest, ans);
        return ans;
    }

    /*
     * "aaaabbbbcc" 先用"aa"  再用"bb"
     * 先用"bb" 再用"aa" 是相同的结果
     * 说明有重复
     * */
    public static void main(String[] args) {
        String[] arr = {"aaaa", "bbaa", "ccddd"};
        String[] arr1 = {"aaaa"};
        String str = "acccccccccccccccccccccddddddd";
        System.out.println(minStickers1(arr, str));
        System.out.println(minStickers2(arr, str));
        System.out.println(minStickers3(arr1, str));
    }
}
