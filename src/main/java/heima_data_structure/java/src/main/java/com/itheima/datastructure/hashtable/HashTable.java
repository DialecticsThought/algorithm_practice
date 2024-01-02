package heima_data_structure.java.src.main.java.com.itheima.datastructure.hashtable;

import com.google.common.collect.Table;
import com.google.common.hash.Hashing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <h2>哈希表</h2>
 * <p>给每份数据分配一个编号，放入表格（数组）。</p>
 * <p>建立编号与表格索引的关系，将来就可以通过编号快速查找数据</p>
 * <ol>
 *  <li>理想情况编号当唯一，数组能容纳所有数据</li>
 *  <li>现实是不能说为了容纳所有数据造一个超大数组，编号也有可能重复</li>
 * </ol>
 * <p>
 * 解决
 * <ol>
 *     <li>有限长度的数组，以【拉链】方式存储数据</li>
 *     <li>允许编号适当重复，通过数据自身来进行区分</li>
 * </ol>
 */
public class HashTable {

    // 摘要算法
    // 散列算法

    // 节点类
    public static class Entry {
        public int hash; // 哈希码
        public Object key; // 键
        public Object value; // 值
        public Entry next;

        public Entry(int hash, Object key, Object value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
    }

    public Entry[] table = new Entry[16];
    public int size = 0; // 元素个数
    public float loadFactor = 0.75f; // 12 阈值
    public int threshold = (int) (loadFactor * table.length);

    /* 求模运算替换为位运算
        - 前提：数组长度是 2 的 n 次方
        - hash % 数组长度 等价于 hash & (数组长度-1)
     */

    // 根据 hash 码获取 value
    public Object get(int hash, Object key) {
        int idx = hash & (table.length - 1);
        if (table[idx] == null) {
            return null;
        }
        Entry p = table[idx];
        while (p != null) {
            if (p.key.equals(key)) {
                return p.value;
            }
            p = p.next;
        }
        return null;
    }

    // 向 hash 表存入新 key value，如果 key 重复，则更新 value
    public void put(int hash, Object key, Object value) {
        int idx = hash & (table.length - 1);
        if (table[idx] == null) {
            // 1. idx 处有空位, 直接新增
            table[idx] = new Entry(hash, key, value);
        } else {
            // 2. idx 处无空位, 沿链表查找 有重复key更新，否则新增
            Entry p = table[idx];
            while (true) {
                if (p.key.equals(key)) {
                    p.value = value; // 更新
                    return;
                }
                if (p.next == null) {
                    break;
                }
                p = p.next;
            }
            p.next = new Entry(hash, key, value); // 新增
        }
        size++;
        if (size > threshold) {
            resize();
        }
    }

    private void resize() {
        Entry[] newTable = new Entry[table.length << 1];
        for (int i = 0; i < table.length; i++) {
            Entry p = table[i]; // 拿到每个链表头
            if (p != null) {
            /*
                拆分链表，移动到新数组，拆分规律
                * 一个链表最多拆成两个
                * hash & table.length == 0 的一组
                * hash & table.length != 0 的一组
                                          p
                0->8->16->24->32->40->48->null
                            a
                0->16->32->48->null
                        b
                8->24->40->null
             */
                Entry a = null;
                Entry b = null;
                Entry aHead = null;
                Entry bHead = null;
                while (p != null) {
                    if ((p.hash & table.length) == 0) {
                        if (a != null) {
                            a.next = p;
                        } else {
                            aHead = p;
                        }
                        a = p; // 分配到a
                    } else {
                        if (b != null) {
                            b.next = p;
                        } else {
                            bHead = p;
                        }
                        b = p; // 分配到b
                    }
                    p = p.next;
                }
                // 规律： a 链表保持索引位置不变，b 链表索引位置+table.length
                if (a != null) {
                    a.next = null;
                    newTable[i] = aHead;
                }
                if (b != null) {
                    b.next = null;
                    newTable[i + table.length] = bHead;
                }
            }
        }
        table = newTable;
        threshold = (int) (loadFactor * table.length);
    }

    // 根据 hash 码删除，返回删除的 value
    public Object remove(int hash, Object key) {
        int idx = hash & (table.length - 1);
        if (table[idx] == null) {
            return null;
        }
        Entry p = table[idx];
        Entry prev = null;
        while (p != null) {
            if (p.key.equals(key)) {
                // 找到了, 删除
                if (prev == null) { // 链表头
                    table[idx] = p.next;
                } else { // 非链表头
                    prev.next = p.next;
                }
                size--;
                return p.value;
            }
            prev = p;
            p = p.next;
        }
        return null;
    }

    public Object get(Object key) {
        int hash = hash(key);
        return get(hash, key);
    }

    public void put(Object key, Object value) {
        int hash = hash(key);
        put(hash, key, value);
    }

    public Object remove(Object key) {
        int hash = hash(key);
        return remove(hash, key);
    }

    private static int hash(Object key) {
        if (key instanceof String k) {
            return Hashing.murmur3_32().hashString(k, StandardCharsets.UTF_8).asInt();
        }
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

    public void print() {
        int[] sums = new int[table.length];
        for (int i = 0; i < table.length; i++) {
            Entry p = table[i];
            while (p != null) {
                sums[i]++;
                p = p.next;
            }
        }
//        System.out.println(Arrays.toString(sums));

        Map<Integer, Long> collect = Arrays.stream(sums).boxed().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        System.out.println(collect);
    }

    public static void main(String[] args) throws IOException {
        HashTable table = new HashTable();
        /*for (int i = 0; i < 200000; i++) {
            Object obj = new Object();
            table.put(obj, obj);
        }*/
        /*List<String> strings = Files.readAllLines(Path.of("words"));
        for (String string : strings) {
            table.put(string, string);
        }*/

        table.put(2, 2);
        table.put(524290, 2);

        table.print();
    }
/*
    为什么计算索引位置用式子：
        【hash & (数组长度-1)】 等价于 【hash % 数组长度】
        10进制中去除以 10，100，1000时，余数就是被除数的后1，2，3 位
                    10^1 10^2 10^3
        2进制中去除以 10，100，1000时，余数也是被除数的后1，2，3 位
                    2^1 2^2 2^3 2^4
        因此求余数就是求二进制的后几位，而保留二进制后几位可以通过与
            1，3，7，11 ... 等数字按位与来实现，这些数字恰巧是数组长度-1

    为什么旧链表会拆分成两条，一条 hash & 旧数组长度==0 另一条!=0
        旧数组长度换算成二进制后，其中的 1 就是我们要检查的倒数第几位
            旧数组长度 8 二进制 => 1000 检查倒数第4位
            旧数组长度 16 二进制 => 10000 检查倒数第5位
        hash & 旧数组长度 就是用来检查扩容前后索引位置（余数）会不会变
    为什么拆分后的两条链表，一个原索引不变，另一个是原索引+旧数组长度

    它们都有个共同的前提：数组长度是 2 的 n 次方


 */

    /*
            1. 按位与
            2. 拆分链表
            3. 高低位异或

            11 质数
     */
}
