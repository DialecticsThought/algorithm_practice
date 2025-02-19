package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.*;

public class Leetcode_355_Twitter {
    /**
     * <pre>
     * 推特 用户 推文的关系
     *                   twitter
     * 包含       ↙           ↓            ↘
     *      user1           user2 --关注->   user3
     * 发布  ↙  ↘            ↙   ↘          ↙     ↘
     * tweet1  tweet2   tweet3  tweet4   tweet5  tweet6
     *
     * </pre>
     */
    static class Twitter {
        /**
         * 推文 （单链表的节点）
         */
        static class Tweet {
            int id;
            int time;
            Tweet next;

            public Tweet(int id, int time, Tweet next) {
                this.id = id;
                this.time = time;
                this.next = next;
            }

            public int getId() {
                return id;
            }

            public int getTime() {
                return time;
            }
        }

        static class User {
            int id;

            public User(int id) {
                this.id = id;
            }

            // 该用户关注了谁
            Set<Integer> followees = new HashSet<>();

            // 该用户的推文 是一个单链表  这里是链表的头
            // 哨兵节点
            Tweet head = new Tweet(-1, -1, null);
        }

        // key = 用户id, value = 用户对象
        private final Map<Integer, User> userMap = new HashMap<>();
        private static int time;

        /**
         * 发布文章
         *
         * @param userId
         * @param tweetId
         */
        public void postTweet(int userId, int tweetId) {
            // 找到用户
            // 找不到就创建新的用户
            User user = userMap.computeIfAbsent(userId, User::new);
            // 得到原始的第一篇文章 变成现在的第二篇文章
            // 新的文章变成哨兵节点的下一个文章
            user.head.next = new Tweet(tweetId, time++, user.head.next);
        }

        // 新增关注
        public void follow(int userId, int followeeId) {
            // 找到用户
            // 找不到就创建新的用户
            User user = userMap.computeIfAbsent(userId, User::new);
            // 找到用户
            // 找不到就创建新的用户
            User followee = userMap.computeIfAbsent(followeeId, User::new);
            user.followees.add(followee.id);
        }

        // 取消关注
        public void unfollow(int userId, int followeeId) {
            User user = userMap.get(userId);
            if (user != null) {
                user.followees.remove(followeeId);
            }
        }

        /**
         * 获取最新10篇文章（包括自己和关注用户）
         * 把当前用户 和所关注的用户的 推文链表 合并在一起
         * 而且 如果关注的用户 也有关注的用户，对应的推文链表也要合并 进来
         * 本质就是 传入userId，利用递归找到userId的user的followee列表 ，再遍历followee列表 ..... 直到找到所有的userid
         *
         * 把所有的有序列表合并 1.利用归并的思想 两两 合并 2.利用大顶堆合并
         * <pre>
         *   eg:
         *   下面有三个链表 每个数字表示时间点
         *   然后按层遍历
         *   初始情况
         *   链表：
         *      -1    -1    -1   <<= 哨兵节点
         *      10     9     3
         *       6     8     2
         *       5     7     1
         *       4
         *   把 要合并的链表的每一个第一个节点 放入大顶堆
         *   大顶堆： 10 9 3
         *   大顶堆 中 10最大 移除10，放入结果集合
         *   结果集合： 10
         *
         *   把10这个节点的后续节点6放入大顶堆
         *   大顶堆：9 6 3
         *   大顶堆 中 9最大 移除9，放入结果集合
         *   结果集合： 10 9
         *
         *   把9这个节点的后续节点8放入大顶堆
         *   大顶堆：8 6 3
         *   大顶堆 中 8最大 移除8，放入结果集合
         *   结果集合： 10 9 8
         *
         *   把8这个节点的后续节点7放入大顶堆
         *   大顶堆：7 6 3
         *   大顶堆 中 7最大 移除7，放入结果集合
         *   结果集合： 10 9 8  7
         *
         *   把7这个节点的后续节点放入大顶堆 ,没有后续节点不用放
         *   大顶堆：6 3
         *   大顶堆 中 6最大 移除6，放入结果集合
         *   结果集合： 10 9 8 7 6
         *
         *   把6这个节点的后续节点5放入大顶堆
         *   大顶堆：5 3
         *   大顶堆 中 5最大 移除5，放入结果集合
         *   结果集合： 10 9 8 7 6 5
         *
         *   把5这个节点的后续节点4放入大顶堆
         *   大顶堆：4 3
         *   大顶堆 中 4最大 移除4，放入结果集合
         *   结果集合： 10 9 8 7 6 5 4
         *
         *   把4这个节点的后续节点放入大顶堆 ,没有后续节点不用放
         *   大顶堆：3
         *   大顶堆 中 3最大 移除3，放入结果集合
         *   结果集合： 10 9 8 7 6 5 4 3
         * </pre>
         * @param userId
         * @return
         */
        public List<Integer> getNewsFeed(int userId) {
            User user = userMap.get(userId);
            if (user == null) {
                return List.of();
            }
            // 生成一个堆 大顶堆  jdk官方默认小顶堆
            PriorityQueue<Tweet> queue
                    = new PriorityQueue<>(Comparator.comparingInt(Tweet::getTime).reversed());
            if (user.head.next != null) {
                queue.offer(user.head.next);
            }
            for (Integer id : user.followees) {
                User followee = userMap.get(id);
                if (followee.head.next != null) {
                    queue.offer(followee.head.next);
                }
            }
            List<Integer> res = new ArrayList<>();
            int count = 0;
            while (!queue.isEmpty() && count < 10) {
                Tweet max = queue.poll();
                res.add(max.id);
                if (max.next != null) {
                    queue.offer(max.next);
                }
                count++;
            }
            return res;
        }
    }

    public static void main(String[] args) {
//        Twitter t = new Twitter();
//        t.postTweet(2, 5); //0
//        t.postTweet(1, 3); //1
//        t.postTweet(1, 101); //2
//        t.postTweet(2, 13); //3
//        t.postTweet(2, 10); //4
//        t.postTweet(1, 2); //5
//        t.postTweet(2, 94); //6
//        t.postTweet(2, 505); //7
//        t.postTweet(1, 333); //8
//        t.postTweet(1, 22); //9
//        System.out.println(t.getNewsFeed(2)); // -> 505 94 10 13 5
//        t.follow(2, 1);
//        System.out.println(t.getNewsFeed(2)); // -> 22 333 505 94 2 10 13 101 3 5


//        Twitter t = new Twitter();
//        t.postTweet(1, 11);
//        t.postTweet(2, 21);
//        t.postTweet(3, 31);
//        t.postTweet(4, 41);
//        t.postTweet(1, 12);
//        t.postTweet(2, 22);
//        t.postTweet(3, 32);
//        t.postTweet(3, 33);
//        t.postTweet(4, 42);
//        t.follow(1, 2);
//        t.follow(1, 3);
//        t.follow(1, 4);
//        System.out.println(t.getNewsFeed(1)); // -> 42, 33, 32, 22, 12, 41, 31, 21, 11

//        Twitter t = new Twitter();
//        for (int i = 0; i < 11; i++) {
//            t.postTweet(1, i);
//        }
//        System.out.println(t.getNewsFeed(1));

        Twitter t = new Twitter();
        t.postTweet(1, 1);
        System.out.println(t.getNewsFeed(1));
        t.follow(2, 1);
        System.out.println(t.getNewsFeed(2));
        t.unfollow(2, 1);
        System.out.println(t.getNewsFeed(2));

    }
}
