package heima_data_structure.java.src.main.java.com.itheima.datastructure.hashtable;

/**
 * @Description
 * @Author veritas
 * @Data 2025/2/8 12:33
 */
public class HashTableTest {

    /**
    为什么计算索引位置用式子：
        【hash & (数组长度-1)】 等价于 【hash % 数组长度】
        10进制中去除以 10,100,1000时，余数 分别就是被除数的后1,2,3 位
                    10^1 10^2 10^3
        2进制中去除以 10，100，1000时，余数也是被除数的后1，2，3 位
                    2^1 2^2 2^3 2^4
     eg：
     436728 % 10 = 8 看436728 这个10进制数的最后1位
     436728 % 100 = 28 看436728 这个10进制数的最后2位
     436728 % 1000 = 728 看436728 这个10进制数的最后3位
     eg:
     10进制取模     2进制取模 除数是2的n次方
     30 % 2 = 0, 0011110 % 0000010 = 0000000  看0011110 这个2进制数的最后1位
     30 % 4 = 2, 0011110 % 0000100 = 0000010  看0011110 这个2进制数的最后2位
     30 % 8 = 6, 0011110 % 0001000 = 00000110 看0011110 这个2进制数的最后3位
     30 % 16 = 14, 0011110 % 0010000 = 00001110 看0011110 这个2进制数的最后4位
     30 % 32 = 30, 0011110 % 0100000 = 00111110 看0011110 这个2进制数的最后5位

     既然是看最后n位，本质就是 保留 最后n位
     保留二进制后几位可以通过与 1，3，7，11 ... 等数字按位与来实现，这些数字恰巧是数组长度-1
     (1)D = (1)B  (3)D = (11)B   (7)D = (111)B  (7)D = (1111)B

     假设原始hash表的旧数组长度=8 链表的元素是1~16 变成2进制之后 查看最后3位 得到每个数的索引位置
     新的数组被扩容到数组长度=16 查看最后4位 得到每个数的新的索引位置
     十进制   8位二进制    最后3位   最后4位
     -----------------------------------------
     1        00000001      001      0001    索引不变
     2        00000010      010      0010    索引不变
     3        00000011      011      0011    索引不变
     4        00000100      100      0100    索引不变
     5        00000101      101      0101    索引不变
     6        00000110      110      0110    索引不变
     7        00000111      111      0111    索引不变
     8        00001000      000      1000    新的索引是原始数组索引+ 1000 也就是旧数组长度8
     9        00001001      001      1001    新的索引是原始数组索引+ 1000 也就是旧数组长度8
     10       00001010      010      1010    新的索引是原始数组索引+ 1000 也就是旧数组长度8
     11       00001011      011      1011    新的索引是原始数组索引+ 1000 也就是旧数组长度8
     12       00001100      100      1100    新的索引是原始数组索引+ 1000 也就是旧数组长度8
     13       00001101      101      1101    新的索引是原始数组索引+ 1000 也就是旧数组长度8
     14       00001110      110      1110    新的索引是原始数组索引+ 1000 也就是旧数组长度8
     15       00001111      111      1111    新的索引是原始数组索引+ 1000 也就是旧数组长度8
     16       00010000      000      0000    索引不变


     为什么旧链表会拆分成两条，一条 hash & 旧数组长度==0 另一条!=0
        旧数组长度换算成二进制后，其中的 1 就是我们要检查的倒数第几位
            旧数组长度 8 二进制 => 1000 检查倒数第4位
            旧数组长度 16 二进制 => 10000 检查倒数第5位
        hash & 旧数组长度 就是用来检查扩容前后索引位置（余数）会不会变
    为什么拆分后的两条链表，一个原索引不变，另一个是原索引+旧数组长度

    它们都有个共同的前提：数组长度是 2 的 n 次方


 */
    public static Integer HASH_TABLE_INITIAL_SIZE = 1 << 8 ;

    static class Entry {
        /**
         * 和 hashtable的数组下标对应
         */
        int hash;

        Object key;

        Object value;

        Entry next;

        public Entry(int hash, Object key, Object value, Entry next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    // hash表的数组
    Entry[] hashtable = new Entry[HASH_TABLE_INITIAL_SIZE];
    // 元素的总个数
    int size = 0;
    // 负载因子 元素总个数 / 数组长度 <= 0.75f
    float loadFactor = 0.75f;
    // 阈值 初始值
    Integer threshold = (int) (loadFactor * HASH_TABLE_INITIAL_SIZE);


    /**
     * 取模运算替换成位运算
     * 利用hashcode算出 <k,v>所在hashtable数组的那一个位置 通常会利用取模运算
     * 但是我们可以利用二进制的位运算做加速
     * 前提： 数组的长度是 2^n
     * 十进制: hash mod 数组长度
     * 二进制: hash & (数组长度 - 1)
     *
     * @param hash
     * @param key
     * @return
     */
    Object get(int hash, Object key) {
        // 得到 hash对应的索引
        int index = hash & (hashtable.length - 1);

        if (hashtable[index] == null) {
            return null;
        }
        // 找到对应的链表 开始遍历
        Entry entry = hashtable[index];

        while (entry != null) {
            // 找到的情况
            if (entry.key.equals(key)) {
                return entry.value;
            }
            // 没有找到的情况
            entry = entry.next;
        }
        // 之心报道稿治理说明 整个链表遍历完成 但是没有找到
        return null;
    }

    /**
     * 如果 key 不存在，那么就是新增
     * 如果 key 存在， 那么就是覆盖
     *
     * @param hash
     * @param key
     * @param value
     */
    void put(int hash, Object key, Object value) {
        // 得到 hash对应的索引
        int index = hash & (hashtable.length - 1);
        // 索引对应的单元 没有使用
        if (hashtable[index] == null) {
            // 新增
            hashtable[index] = new Entry(hash, key, value, null);

            size++;
        }
        // 找到对应的链表 开始遍历
        Entry entry = hashtable[index];
        // 不断循环 退出循环条件 在里面
        while (true) {
            //  如果 key 存在， 那么就是覆盖
            if (entry.key.equals(key)) {
                entry.value = value;
                // 结束
                return;
            }
            if (entry.next == null) {
                break;// 跳出循环
            }
            //链表下移
            entry = entry.next;
        }
        // 执行到这里 说明 key 不存在，而且已经链表遍历到底了
        // 新增
        entry.next = new Entry(hash, key, value, null);

        size++;

        //判断是否需要扩容
        if(size > threshold) {

        }
    }


    public void resize(){
        Entry[] newHashTable = new Entry[hashtable.length<<1];
        // 遍历 hash表
        for(int i = 0; i < hashtable.length; i++){
            // 得到每一个链表的头
            Entry p = hashtable[i];

            if(p != null){// 拆分链表
                /**
                 * 拆分链表的规律
                 * 一恶搞链表最多拆分成两个链表
                 * table.length = 2 ^ n 说明是偶数
                 * hash & table.length == 0 的一组
                 * hash & table.length != 0 的一组
                 * <pre>
                 * eg:
                 * hashTable.length = 8;
                 * 1.
                 * p
                 * 0 -> 8 -> 16 -> 24 -> 32 -> 40 -> 48 -> null
                 * 链表1：
                 *  a
                 * null
                 * 链表2：
                 *  b
                 * null
                 * 2.
                 * 0 & 8 = 0;
                 *      p
                 * 0 -> 8 -> 16 -> 24 -> 32 -> 40 -> 48 -> null
                 * 链表1：
                 *  a
                 *  0
                 * 链表2：
                 *  b
                 * null
                 * 3.
                 * 8 & 8 = 8;
                 *            p
                 * 0 -> 8 -> 16 -> 24 -> 32 -> 40 -> 48 -> null
                 * 链表1：
                 *  a
                 *  0
                 * 链表2：
                 *  b
                 *  8
                 * 4.
                 * 16 & 8 = 0;
                 *                 p
                 * 0 -> 8 -> 16 -> 24 -> 32 -> 40 -> 48 -> null
                 * 链表1：
                 *        a
                 *  0 -> 16
                 * 链表2：
                 *  b
                 *  8
                 * 5.
                 * 24 & 8 = 8;
                 *                        p
                 * 0 -> 8 -> 16 -> 24 -> 32 -> 40 -> 48 -> null
                 * 链表1：
                 *        a
                 *  0 -> 16
                 * 链表2：
                 *        b
                 *  8 -> 24
                 * 6.
                 * 32 & 8 = 0;
                 *                              p
                 * 0 -> 8 -> 16 -> 24 -> 32 -> 40 -> 48 -> null
                 * 链表1：
                 *              a
                 *  0 -> 16 -> 32
                 * 链表2：
                 *       b
                 *  8 -> 24
                 * 7.
                 * 40 & 8 = 8;
                 *                                    p
                 * 0 -> 8 -> 16 -> 24 -> 32 -> 40 -> 48 -> null
                 * 链表1：
                 *              a
                 *  0 -> 16 -> 32
                 * 链表2：
                 *              b
                 *  8 -> 24 -> 40
                 * </pre>
                 */
                Entry a =null;// 用来遍历链表1的指针
                Entry b = null;// 用来遍历链表2的指针
                Entry aHead = null;// 用来记录链表1的头结点的指针
                Entry bHead = null;// 用来记录链表2的头结点的指针
                while(p != null){
                    if((p.hash &hashtable.length) == 0){
                        // 把当前被遍历到的节点 分配给a
                        // 执行尾插法
                        if(a!=null){// 说明链表1 已经有元素了
                            a.next = p;
                        }else {// 这里说明 该链表 还没有节点
                            aHead = p;
                        }
                        a = p;
                    }else {
                        // 把当前被遍历到的节点 分配给b
                        // 执行尾插法
                        if(b!=null){// 说明链表2 已经有元素了
                            b.next = p;
                        }else {// 这里说明 该链表 还没有节点
                            bHead = p;
                        }
                        b = p;
                    }
                    // 原始链表的指针下移
                    p = p.next;
                }
                // 执行到这里 原有的链表已经遍历并拆分完成 做收尾工作
                // 接下来就是2个新链表放入新的数组
                // 规律：链表1在新hashTable的索引 和原始索引相同， 链表2在新hashTable的索引 =原始索引+ 旧的HashTable.length
                if(a!=null){
                    a.next = null;
                    newHashTable[i] = aHead;
                }
                if(b!=null){
                    b.next = null;
                    newHashTable[i+hashtable.length] = bHead;
                }
            }
        }
        Integer newThreshold = (int) (loadFactor * hashtable.length);
        threshold = newThreshold;
        // 扩容完 原有hashTable指向新的
        hashtable = newHashTable;
    }

    /**
     * 返回被删除的key对应的value
     *
     * @param hash
     * @param key
     * @return
     */
    Object remove(int hash, Object key) {
        // 得到 hash对应的索引
        int index = hash & (hashtable.length - 1);

        if (hashtable[index] == null) {
            return null;
        }
        // 找到对应的链表 开始遍历
        Entry entry = hashtable[index];

        // 需要删除链表节点 那么需要知道被删除节点的上一个节点
        Entry prev = null;
        while (entry != null) {
            // 找到 被删除的key
            if (entry.next.key.equals(key)) {
                // 这个判断说明 被删除的节点 是否 链表的头节点
                if (prev == null) {
                    hashtable[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                return entry.value;
            }

            prev = entry;
            entry = entry.next;
        }
        // 执行到这 说明 没有找到
        return null;
    }
}
