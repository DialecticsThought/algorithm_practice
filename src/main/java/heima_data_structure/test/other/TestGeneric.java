package heima_data_structure.test.other;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TestGeneric {
    static class Animal{

    }

    static class Plant {

    }

    static class Dog extends Animal {

    }

    static class Cat extends Animal {

    }

    static class C<T> {
        T t;
        T get() { return t; }
        void set(T t){ this.t = t; }
    }

    public static void test(C<? extends Animal> c){
        Animal animal = c.get();
    }

    public static <T> T test(List<T> list) {
        return null;
    }

    public static void main(String[] args) {
        Dog test = test(new ArrayList<Dog>());
//        test(new C<Object>());

        Consumer<Integer> c1 = System.out::println;
        Consumer<Number> c2 = System.out::println;
        Consumer<Object> c3 = System.out::println;

        ArrayList<Number> list = new ArrayList<>(new ArrayList<Integer>());
        list.add(1);
        list.add(2);
        list.add(3);
//        list.forEach(c1);
    }
}
