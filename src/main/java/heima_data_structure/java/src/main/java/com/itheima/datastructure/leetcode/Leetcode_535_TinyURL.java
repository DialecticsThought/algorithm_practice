package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Leetcode_535_TinyURL {

    public static void main(String[] args) {
        CodecHashCode codec = new CodecHashCode();
        String encoded = codec.encode("https://leetcode.cn/problems/1");
        System.out.println(encoded);

        encoded = codec.encode("https://leetcode.cn/problems/2");
        System.out.println(encoded);
    }


    /**
     * 要让【长】【短】网址一一对应
     * <p>
     * 1. 用【随机数】作为短网址标识
     * 2. 用【hash码】作为短网址标识
     * 3. 用【递增数】作为短网址标识
     * <p>
     * 长网址： https://leetcode.cn/problems/encode-and-decode-tinyurl/description/
     * 对应的短网址： http://tinyurl.com/4e9iAk
     */

    static class CodecHashCode {
        private final Map<String, String> longToShort = new HashMap<>();
        private final Map<String, String> shortToLong = new HashMap<>();
        private static final String SHORT_PREFIX = "http://tinyurl.com/";

        /**
         * 人话： 根据长网址 快速找到 短网址
         * @param longUrl
         * @return
         */
        public String encode(String longUrl) {
            String shortUrl = longToShort.get(longUrl);
            // 说明之前已经生成过了
            if (shortUrl != null) {
                return shortUrl;
            }
            // 生成短网址 用hashcode当做url的路径一部分
            int id = longUrl.hashCode();
            while (true) {
                // 变成真正的网址
                shortUrl = SHORT_PREFIX + id;
                // !shortToLong.containsKey(shortUrl)是防止hashcode是相同的，也就是hash碰撞
                if (!shortToLong.containsKey(shortUrl)) {
                    longToShort.put(longUrl, shortUrl);
                    shortToLong.put(shortUrl, longUrl);
                    break;
                }
                // 走到这说明 hashcode冲突了 那么让 hashcode++  继续尝试
                id++;
            }
            return shortUrl;
        }

        /**
         * 人话： 根据短网址 快速找到长网址
         * @param shortUrl
         * @return
         */
        public String decode(String shortUrl) {
            return shortToLong.get(shortUrl);
        }
    }

    static class CodecRandom {
        private final Map<String, String> longToShort = new HashMap<>();
        private final Map<String, String> shortToLong = new HashMap<>();
        private static final String SHORT_PREFIX = "http://tinyurl.com/";

        public String encode(String longUrl) {
            String shortUrl = longToShort.get(longUrl);
            // 说明之前已经生成过了
            if (shortUrl != null) {
                return shortUrl;
            }
            // 生成短网址
            while (true) {
                int id = ThreadLocalRandom.current().nextInt();// 1
                shortUrl = SHORT_PREFIX + id;
                // !shortToLong.containsKey(shortUrl)是防止随机数相同
                if (!shortToLong.containsKey(shortUrl)) {
                    longToShort.put(longUrl, shortUrl);
                    shortToLong.put(shortUrl, longUrl);
                    break;
                }
            }
            return shortUrl;
        }

        public String decode(String shortUrl) {
            return shortToLong.get(shortUrl);
        }
    }
}
