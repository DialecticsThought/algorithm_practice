package other;
/*
*
位运算的题目
之前介绍过一些，下面继续
给定两个有符号32位整数a和b，返回a和b中较大的。
【要求】
不用做任何比较判断。
* */
public class Code01_GetMax {
    /*
    * 请保证参数n 不是1就是0的情况下
    * 1 -> 0
    * 0 -> 1
    * */
    public static int flip(int n){
        return n ^ 1;//取反 按位异或运算符(^)是二元运算符
    }
    /*
    * n是非负数 返回1
    * n是负数 返回0
    * */
    public static int sign(int n){
        /*
        * n右移31位 那么n的符号位在最右侧 和 1 与运算 得到了结果 非负数是0 负数是1
        * 在对这个结果进行取反
        * */
        return flip( (n>>31)&1);
    }

    public static int getMax1(int a,int b){
        int c = a-b;//得到差值
        //得到差值的符号
        int scA=sign(c);//a-b为非负，scA为1; a-b为负，scA为0
        //得到差值的符号的取反
        int scB=flip(scA);// scA为8,scB为1; scA为1，scB为0
        //因为 scB和scA总有一个是0 +运算符总有一侧是0  if-else做成了互斥条件相加的判断
        return a*scA+b*scB;//scA为0,scb必为1; scA为1，scB必为0

    }
    /*
    * 用 + 来代替if-else
    * */
    public static int getMax2(int a, int b) {
        int c = a - b;
        int sa= sign(a);//得到a符号的状态
        int sb = sign(b);//得到b符号的状态
        int sc = sign(c);//得到差值符号的状态
        int difSab = sa ^ sb;// a和b的符号不一样，为1;一样，为0
        //sameSab和difSab互斥
        int sameSab = flip(difSab); // a和b的符号一样，为1;不一样，为0
        /*
        * 返回a
        * if(a和b符号相同 && a-b >= 0) =>sameSab * sc
        * if(a和b符号不相同 && a > 0) =>difSab * sa
        * +运算符两边条件互斥  +可以理解为或者
        * */
        int returnA = difSab * sa + sameSab * sc;
        int returnB = flip( returnA);//返回b的条件就是返回a的条件取反
        // +运算符两边条件互斥  +可以理解为或者
        return a * returnA + b * returnB;
    }

}
