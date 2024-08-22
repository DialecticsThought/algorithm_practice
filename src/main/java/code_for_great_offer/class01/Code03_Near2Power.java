package code_for_great_offer.class01;

public class Code03_Near2Power {

    // 已知n是正数
    // 返回大于等于，且最接近n的，2的某次方的值
    public static final int tableSizeFor(int n) {
        n--;//TODO 实现最低位是0
        //TODO 下面的操作实现了某个数的二进制从最高位的1开始后面所有位都是1
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;//TODO 只算到16 是因为 Int是32位
        return (n < 0) ? 1 : n + 1;
    }

    public static void main(String[] args) {
        int cap = 120;
        System.out.println(tableSizeFor(cap));
    }

}
