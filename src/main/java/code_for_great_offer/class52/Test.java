package code_for_great_offer.class52;

/**
 * @Description
 * @Author veritas
 * @Data 2023/4/10 13:07
 */
public class Test {
    public static void main(String[] args) {
        Sub s = new Sub();
        System.out.println(s.count);//20
         Base b = s;//多态
        System.out.println(b == s);//对于引用类型的“==”,比较的是两个引用数据类型变量的地址是否相同（true，赋值赋的就是地址值）
        System.out.println(b.count);//10
        b.display();//20（虚拟方法调用,这里调用的是s所属类Sub的dispaly方法）
    }
}

class Base {
    int count = 10;

    public void display() {
        System.out.println(this.count);
    }
}

class Sub extends Base {
    //int count = 20;

    public void display() {
        System.out.println(this.count+10);
    }
}
