package heima_data_structure.test.other;

import java.io.*;

public class TestSerializable {

    static class Person implements Serializable{
        String name;

        public Person() {
            System.out.println("person 构造");
        }
    }


    static class Student  extends Person implements Serializable {
        int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
            System.out.println("student 构造");
        }
    }

    public static void main(String[] args) throws Exception {
        Student stu = new Student("aaa", 18);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(stu);

        System.out.println("------------------------------");
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bao.toByteArray()));
        Object o = ois.readObject();
        if (o instanceof Student s) {
            System.out.println(s.name);
            System.out.println(s.age);
        }
    }
}
