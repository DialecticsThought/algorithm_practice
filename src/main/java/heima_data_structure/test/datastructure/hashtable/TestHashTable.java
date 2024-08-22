package heima_data_structure.test.datastructure.hashtable;
import heima_data_structure.java.src.main.java.com.itheima.datastructure.hashtable.HashTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestHashTable {

    @Test
    public void get() {
        HashTable table = new HashTable();
        HashTable.Entry e0 = new HashTable.Entry(0, "沙", "沙僧");
        e0.next = new HashTable.Entry(16, "于", "于谦");
        HashTable.Entry e1 = new HashTable.Entry(1, "空", "空见");

        table.table[0] = e0;
        table.table[1] = e1;

        assertEquals("沙僧", table.get(0, "沙"));
        assertEquals("于谦", table.get(16, "于"));
        assertNull(table.get(0, "空"));
        assertEquals("空见", table.get(1, "空"));
        assertNull(table.get(3, "空"));
    }


    @Test
    void put() {
        HashTable table = new HashTable();
        table.put(1, "zhang", "张三"); // 1 % 16 == 1
        table.put(17, "li", "李四");   // 17 % 16 == 1
        table.put(2, "wang", "王五");  // 2

        assertEquals(3, table.size);
        assertEquals("张三", table.table[1].value);
        assertEquals("李四", table.table[1].next.value);
        assertEquals("王五", table.table[2].value);

        table.put(1, "zhang", "张4");
        table.put(17, "li", "李5");
        assertEquals("张4", table.table[1].value);
        assertEquals("李5", table.table[1].next.value);
    }

    @Test
    void remove1() {
        HashTable table = new HashTable();
        table.put(1, "zhang", "张三");  // 1
        table.put(17, "li", "李四");    // 1
        table.put(2, "wang", "王五");

        table.remove(1, "zhang");
        assertEquals(2, table.size);
        assertEquals("李四", table.table[1].value);
        assertNull(table.table[1].next);
    }

    @Test
    void remove2() {
        HashTable table = new HashTable();
        table.put(1, "zhang", "张三"); // 1
        table.put(17, "li", "李四");   // 1
        table.put(2, "wang", "王五");

        table.remove(17, "li");
        assertEquals(2, table.size);
        assertEquals("张三", table.table[1].value);
        assertNull(table.table[1].next);
    }

    @Test
    void resize() {
        HashTable table = new HashTable();
        for (int i = 2; i < 11; i++) {
            table.put(i, new Object(), new Object());
        }
        table.put(1, "zhang", "张三"); // 原位不动
        table.put(17, "li", "李四");
        table.put(33, "wang", "王五"); // 原位不动
        assertEquals("张三", table.table[1].value);
        assertEquals("李四", table.table[1].next.value);
        assertEquals("王五", table.table[1].next.next.value);
        table.put(49, "zhao", "赵六");
        assertEquals(13, table.size);
        assertEquals(32, table.table.length);
        assertEquals(24, table.threshold);
        assertEquals("张三", table.table[1].value);
        assertEquals("王五", table.table[1].next.value);
        assertEquals("李四", table.table[17].value);
        assertEquals("赵六", table.table[17].next.value);
    }
}