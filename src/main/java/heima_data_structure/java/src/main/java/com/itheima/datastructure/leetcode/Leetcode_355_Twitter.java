package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.*;

public class Leetcode_355_Twitter {
    static class Twitter {

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

            Set<Integer> followees = new HashSet<>();
            Tweet head = new Tweet(-1, -1, null);
        }

        private final Map<Integer, User> userMap = new HashMap<>();
        private static int time;

        // 发布文章
        public void postTweet(int userId, int tweetId) {
            User user = userMap.computeIfAbsent(userId, User::new);
            user.head.next = new Tweet(tweetId, time++, user.head.next);
        }

        // 新增关注
        public void follow(int userId, int followeeId) {
            User user = userMap.computeIfAbsent(userId, User::new);
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

        // 获取最新10篇文章（包括自己和关注用户）
        public List<Integer> getNewsFeed(int userId) {
            User user = userMap.get(userId);
            if (user == null) {
                return List.of();
            }
            PriorityQueue<Tweet> queue
                    = new PriorityQueue<>(Comparator.comparingInt(Tweet::getTime).reversed());
            if(user.head.next != null) {
                queue.offer(user.head.next);
            }
            for (Integer id : user.followees) {
                User followee = userMap.get(id);
                if(followee.head.next != null) {
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
