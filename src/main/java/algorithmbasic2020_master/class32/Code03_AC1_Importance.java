package algorithmbasic2020_master.class32;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://www.bilibili.com/video/BV1Ag41117YU/
 * fail针对某个分支匹配失败之后，重新指向关联的其他分支上
 * <p>
 * 在扫描字符串的时候，是顺着trie数
 * 但不是每次匹配都能找到我们的敏感词，为了不中断这个查找的链路
 * 代表着在这个节点上无法再继续深入的时候，跳转到哪一个位置能继续查找，不中断
 * <p>
 * 每个节点都有可能无法继续向下进行匹配，每个节点都要去构建
 * 敏感词：[say,she,shr,he,her]
 * TODO
 * 首先我们要创建的是根节点
 * root这个根节点表示Trie树的起点
 * 这个节点不代表任何单词
 * <pre>
 *         R
 * </pre>
 * 然后我们从这个敏感词库中取到第一个单词say
 * 拆分后就是三个字母S A Y 放到Trie树中,依次向下延伸
 * 因为这个Y 它是一个单词的结束字母
 * 所以我们得给这个节点做一个标记标记,这个位置是敏感词的截止位置
 * 说明走到这个节点,已经成功匹配到了一个敏感词
 * 同时这个节点,它还记录了另外一个信息就是这个敏感词的长度
 * 我们这里是单词say,所以长度的话是3
 * 数字的这个值是多少 => 我们就回溯多少个节点
 * <pre>
 *
 *         R
 *  	  ↙
 * 	   s
 * 	  ↙
 * 	 a
 *  ↙
 * y
 * [3]
 * </pre>
 * 然后我们取第二个敏感词进行拆分
 * 拆分之后呢是字母S H E
 * 注意我们同一层次的节点,是不会存放重复字母的
 * 因为root节点呢已经有了S节点,所以我们就不用重复创建节点了
 * <pre>
 *
 *         R
 *  	  ↙ ↘
 * 	   s
 * 	  ↙ ↘
 * 	 a   h
 *  ↙   ↙
 *  y    e
 * [3]  [3]
 * </pre>
 * 以此类推，最终得到
 * <pre>
 *
 *         R
 *  	  ↙ ↘
 * 	     s	  h
 * 	    ↙ ↘	   ↘
 * 	  a    h 	 e[2]
 *   ↙    ↙  ↘	   ↘
 *  y    e     r	 r
 * [3]  [3]   [3]   [3]
 * </pre>
 * Trie树构建完成后还有最重要的一步就是这个FAIL指针的构建
 * <p>
 * FAIL指针是指在某个分支匹配失败之后重新指向关联的其他分支上
 * 这句话要怎么理解呢？
 * 就是说我们在扫描字符串的时候,是顺着这个Trie树逐渐深入查找的
 * 但是不是每次匹配都能找到我们的敏感词，
 * 为了不中断这个查找的链路，我们就需要在每个节点上去做一个指向
 * 代表着在这个节点上无法再继续深入的时候，该跳转到哪个位置进行继续查找，以保证这个查找不会被中断
 * <p>
 * 为什么是每个节点?
 * 因为每一个节点,它都有可能是无法继续向下进行匹配,所以我们我们要为每一个节点去进行FAIL指针的构建
 * 有三个原则
 * 一、构建fail指针的遍历为层次遍历。
 * 二、root节点的fail指针指向自己本身。
 * 三、如果当前节点父节点的fail指针指向的节点下存在与当前节点一样的子节点，则当前节点的fail指针指向该子节点。否则指向root节点。
 * 因为层次遍历 从root出发
 * <pre>
 *                ↓ ← ←
 *  	 		  R → ↑
 * 	 	 		 / \
 * 	 	       /    \
 *            s	     h
 *   		 / \       \
 *   	    /   \       \
 * 	       a     h 	 	e[2]
 * 	      /     / \       \
 * 	 	 /     /   \       \
 * 	    y    e      r	   r
 * 	   [3]   [3]    [3]    [3]
 * </pre>
 * 再下来我们看S节点
 * 试着按照这个原则3去构建一下
 * 所以这个root下面是不会有一个S节点
 * 所以我们就是这个S节点的fAIL指针指向这个root节点
 * <pre>
 *                ↓ ← ←
 *  ↑	 		  R → ↑
 * 	↑	 		 / \
 * 	↑	       /    \
 *  ↑  ← ← ←  s	     h
 *   		 / \       \
 *   	    /   \       \
 * 	       a     h 	 	e[2]
 * 	      /     / \       \
 * 	 	 /     /   \       \
 * 	    y    e      r	   r
 * 	   [3]   [3]    [3]    [3]
 * </pre>
 * 再看这个H节点
 * H节点的父节点的fAIL指针还是他root
 * root下面同样也不会再有一个
 * 跟当前节点一样的H节点
 * 所以呢我们这个H节点的标指针还是指向root
 * <pre>
 *       → → → → ↓ ← ← ← ← ← ← ←
 *  ↑	 		  R → ↑         ↑      1
 * 	↑	 		 / \            ↑
 * 	↑	       /    \           ↑
 *  ↑  ← ← ←  s	     h → → → →  ↑       2
 *   		 / \       \
 *   	    /   \       \
 * 	       a     h 	 	e[2]
 * 	      /     / \       \
 * 	 	 /     /   \       \
 * 	    y    e      r	   r
 * 	   [3]   [3]    [3]    [3]
 * </pre>
 * 然后再下来我们看A节点
 * 我们还是按照这边第三点去进行这个fail指针的构建
 * A节点的父节点fail指针
 * 下面是否存在着与A节点一样的子节点
 * 很显然我们这个root下面只有S和H，并没有存在一个A节点
 * 所以我们这个A节点的FaiI指针也是指向root节点
 * <pre>
 *       → → → → ↓ ← ← ← ← ← ← ←
 *  ↑	 		  R → ↑         ↑      1
 * 	↑	 		 / \            ↑
 * 	↑	       /    \           ↑
 *  ↑  ← ← ←  s	     h → → → →  ↑       2
 *  ↑		 / \       \
 *  ↑	    /   \       \
 * 	↑ ← ←  a     h 	 	e[2]
 * 	      /     / \       \
 * 	 	 /     /   \       \
 * 	    y    e      r	   r
 * 	   [3]   [3]    [3]    [3]
 * </pre>
 * 接下来我们再看这个H节点
 * 还是按照这个原则来进行去构建这个FAII指针
 * 如果当前节点的父节点也就是H的复节点是S的FAIL指针指向的节点是root
 * root是否有与这个H节点是一样的子节点
 * 很明显我们这里有一个H
 * 所以,我们这个H的fAIL指针就要指向到root的H节点
 * 它指向的就不再是root节点了
 * 也就意味着当我们这个匹配敏感词的时候走到了这个H节点适配之后
 * 就不需要重新回到这个root节点,再向下去查找
 * 只需要沿着这个标指针转移到另一个分支,然后就可以继续向下进行匹配查找
 * <pre>
 *       → → → → ↓ ← ← ← ← ← ← ←
 *  ↑	 		  R → ↑   ↑            1
 * 	↑	 		 / \      ↑
 * 	↑	       /    \     ↑
 *  ↑  ← ← ←  s	     h → →             2
 *  ↑		 / \   ↗  \
 *  ↑	    /   \ ↗    \
 * 	↑ ← ←  a     h 	 	e[2]           3
 * 	      /     / \       \
 * 	 	 /     /   \       \
 * 	    y    e      r	    r          4
 * 	   [3]   [3]    [3]    [3]
 * </pre>
 * 然后到e节点,e节点的父节点是H,H的FAIL指针指向的是root
 * root下面呢=没有跟e节点一样的子节点了
 * 所以呢我们这e节点的这个FAIL指针指向的也是root
 * <pre>
 *       → → → → ↓ ← ← ← ← ← ← ←
 *  ↑	 		  R → ↑   ↑     ↑       1
 * 	↑	 		 / \      ↑     ↑
 * 	↑	       /    \     ↑     ↑
 *  ↑  ← ← ←  s	     h → →      ↑       2
 *  ↑		 / \   ↗  \         ↑
 *  ↑	    /   \ ↗    \        ↑
 * 	↑ ← ←  a     h 	 	e[2] →→ ↑       3
 * 	      /     / \       \
 * 	 	 /     /   \       \
 * 	    y    e      r	    r           4
 * 	   [3]   [3]    [3]    [3]
 * </pre>
 * 到我们这个Y节点
 * Y节点的父节点是A
 * A的FAII指针是指向root
 * root下面也没有Y节点
 * 所以我们这个Y节点的FAII指针指向的也是root节点
 * <pre>
 *       → → → → ↓ ← ← ← ← ← ← ←
 *  ↑	 		  R → ↑   ↑     ↑       1
 * 	↑	 		 / \      ↑     ↑
 * 	↑	       /    \     ↑     ↑
 *  ↑  ← ← ←  s	     h → →      ↑       2
 *  ↑		 / \   ↗  \         ↑
 *  ↑	    /   \ ↗    \        ↑
 * 	↑ ← ←  a     h 	 	e[2] →→ ↑       3
 * 	↑     /     / \       \
 * 	↑	 /     /   \       \
 * 	↑ ← y    e      r	    r           4
 * 	   [3]   [3]    [3]    [3]
 * </pre>
 * 接下来我们看E节点
 * E节点的父节点是H
 * H的FAIL指针是指向另一个分支的H节点
 * 这个H节点下面有1个子节点E,刚好跟我们这个当前的E节点是一样的
 * 所以我们这E节点的FAIL指针指向的是H节点的子节点E节点
 * <p>
 * 被指向的E节点已经有存储敏感词的长度了
 * 也就是说走到这个位置成功匹配到了敏感词
 * 所以这里我们需要把这个FAII指针指向的E节点下面存储的单词长度2 拷贝一份
 * 到我们当前这个节点E来
 * 我们当前这个节点E就有了两个单词长度信息
 * <p>
 * 也就意味着当我们走到这E节点
 * 已经匹配到了两个敏感词
 * 我们需要在这个节点进行回溯两次
 * 长度为三的是SHE
 * 长度为二的是HE
 * 因为我们SHE跟HE都是敏感词
 * 这也是我们构建FAIL指针比较关键的一个点
 * 就是如果当前节点的FAIL指针指向的节点是另一个单词的结束节点
 * 那么我们就要将这个节点下面的单词长度信息拷贝一份到我们这个当前节点下来
 * <pre>
 *       → → → → ↓ ← ← ← ← ← ← ←
 *  ↑	 		  R → ↑   ↑     ↑       1
 * 	↑	 		 / \      ↑     ↑
 * 	↑	       /    \     ↑     ↑
 *  ↑  ← ← ←  s	     h → →      ↑       2
 *  ↑		 / \   ↗  \         ↑
 *  ↑	    /   \ ↗    \        ↑
 * 	↑ ← ←  a     h 	 	e[2] →  ↑       3
 * 	↑     /     / \     ↑ \
 * 	↑	 /     /   \    ↑  \
 * 	↑ ← y    e      r	↑   r           4
 * 	  [3]   [3,2]  [3]  ↑  [3]
 * 	         ↓          ↑
 *           →  → → → → →
 * </pre>
 * R节点的父节点是H
 * H的FAIL指针指向的是另一个分子的H,这个H下面没有R节点
 * 所以我们这个R节点的FAIL指针指向的是root节点
 * <pre>
 *       → → → → ↓ ← ← ← ← ← ← ← ← ← ← ← ← ←
 *  ↑	 		  R → ↑   ↑     ↑           ↑       1
 * 	↑	 		 / \      ↑     ↑           ↑
 * 	↑	       /    \     ↑     ↑           ↑
 *  ↑  ← ← ←  s	     h → →      ↑           ↑       2
 *  ↑		 / \   ↗  \         ↑           ↑
 *  ↑	    /   \ ↗    \        ↑           ↑
 * 	↑ ← ←  a     h 	 	e[2] →  ↑           ↑       3
 * 	↑     /     / \     ↑ \                 ↑
 * 	↑	 /     /   \    ↑  \                ↑
 * 	↑ ← y    e      r	↑   r               ↑       4
 * 	  [3]   [3,2]  [3]  ↑  [3]              ↑
 * 	         ↓      ↓   ↑                   ↑
 *           →  → → ↓ → →                   ↑
 *                  ↓                       ↑
 *                  → → → → → → → → → → → → ↑
 * </pre>
 * 最后我们看这个R节点
 * R节点的父节点是E,E的FAIl指针指向的是root
 * root下面并没有R节点
 * 所以我们这个R节点指向的FAIl指针指向的也是root
 * <pre>
 *       → → → → ↓ ← ← ← ← ← ← ← ← ← ← ← ← ←
 *  ↑	 		  R → ↑   ↑     ↑      ↑    ↑       1
 * 	↑	 		 / \      ↑     ↑      ↑    ↑
 * 	↑	       /    \     ↑     ↑      ↑    ↑
 *  ↑  ← ← ←  s	     h → →      ↑      ↑    ↑       2
 *  ↑		 / \   ↗  \         ↑      ↑    ↑
 *  ↑	    /   \ ↗    \        ↑      ↑    ↑
 * 	↑ ← ←  a     h 	 	e[2] →  ↑      ↑    ↑       3
 * 	↑     /     / \     ↑ \            ↑    ↑
 * 	↑	 /     /   \    ↑  \           ↑    ↑
 * 	↑ ← y    e      r	↑   r  → → → → ↑    ↑       4
 * 	  [3]   [3,2]  [3]  ↑  [3]              ↑
 * 	         ↓      ↓   ↑                   ↑
 *           →  → → ↓ → →                   ↑
 *                  ↓                       ↑
 *                  → → → → → → → → → → → → ↑
 * </pre>
 * 对于第1层的root指向自己
 * 对于第2层的所有节点都是指向root
 * 对于第4层的y节点而言，其父是a,a的fail == root, root无y的分支
 * 对于第3层的h节点而言，其父是s,s的fail == root, root有h的分支
 * 对于第4层的e节点而言，其父是h,h的fail == h, h有e的分支
 */
public class Code03_AC1_Importance {

    public static class Node {
        public int end; // 有多少个字符串以该节点结尾
        public Node fail;
        public Node[] nexts;

        public Node() {
            end = 0;
            fail = null;
            nexts = new Node[26];
        }
    }

    public static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        // 你有多少个匹配串，就调用多少次insert
        public void insert(String s) {
            char[] str = s.toCharArray();
            Node cur = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (cur.nexts[index] == null) {
                    Node next = new Node();
                    cur.nexts[index] = next;
                }
                cur = cur.nexts[index];
            }
            cur.end++;
        }

        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node cfail = null;
            while (!queue.isEmpty()) {
                cur = queue.poll(); // 父
                for (int i = 0; i < 26; i++) { // 下级所有的路
                    if (cur.nexts[i] != null) { // 该路下有子节点
                        cur.nexts[i].fail = root; // 初始时先设置一个值
                        cfail = cur.fail;
                        while (cfail != null) { // cur不是头节点
                            if (cfail.nexts[i] != null) {
                                cur.nexts[i].fail = cfail.nexts[i];
                                break;
                            }
                            cfail = cfail.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        public int containNum(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            int ans = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                follow = cur;
                while (follow != root) {
                    if (follow.end == -1) {
                        break;
                    }
                    { // 不同的需求，在这一段{ }之间修改
                        ans += follow.end;
                        follow.end = -1;
                    } // 不同的需求，在这一段{ }之间修改
                    follow = follow.fail;
                }
            }
            return ans;
        }

    }

    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("c");
        ac.build();
        System.out.println(ac.containNum("cdhe"));
    }

}
