package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class TinyURLLeetcode535 {

    public static void main(String[] args) {
        CodecHashCode codec = new CodecHashCode();
        String encoded = codec.encode("https://leetcode.cn/problems/1");
        System.out.println(encoded);

        encoded = codec.encode("https://leetcode.cn/problems/2");
        System.out.println(encoded);
    }


    /*
        要让【长】【短】网址一一对应

            1. 用【随机数】作为短网址标识
            2. 用【hash码】作为短网址标识
            3. 用【递增数】作为短网址标识

        长网址： https://leetcode.cn/problems/encode-and-decode-tinyurl/description/
        对应的短网址： http://tinyurl.com/4e9iAk
     */

    static class CodecHashCode {
        private final Map<String, String> longToShort = new HashMap<>();
        private final Map<String, String> shortToLong = new HashMap<>();
        private static final String SHORT_PREFIX = "http://tinyurl.com/";

        public String encode(String longUrl) {
            String shortUrl = longToShort.get(longUrl);
            if (shortUrl != null) {
                return shortUrl;
            }
            // 生成短网址
            int id = longUrl.hashCode();
            while (true) {
                shortUrl = SHORT_PREFIX + id;
                if (!shortToLong.containsKey(shortUrl)) {
                    longToShort.put(longUrl, shortUrl);
                    shortToLong.put(shortUrl, longUrl);
                    break;
                }
                id++;
            }
            return shortUrl;
        }

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
            if (shortUrl != null) {
                return shortUrl;
            }
            // 生成短网址
            while (true) {
                int id = ThreadLocalRandom.current().nextInt();// 1
                shortUrl = SHORT_PREFIX + id;
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
