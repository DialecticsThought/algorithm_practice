package heima_data_structure.test.datastructure.priorityqueue;

import heima_data_structure.java.src.main.java.com.itheima.datastructure.priorityqueue.Entry;
import heima_data_structure.java.src.main.java.com.itheima.datastructure.priorityqueue.Priority;
import heima_data_structure.java.src.main.java.com.itheima.datastructure.priorityqueue.PriorityQueue3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPriorityQueue3 {

    PriorityQueue3<Priority> getQueue() {
        PriorityQueue3<Priority> queue = new PriorityQueue3<>(10);
        queue.array = new Priority[]{
                new Entry(100),
                new Entry(19),
                new Entry(36),
                new Entry(17),
                new Entry(3),
                new Entry(15),
                new Entry(1),
                new Entry(2),
                new Entry(16),
                null
        };
        queue.size = 9;
        return queue;
    }

    @Test
    public void test() {
        PriorityQueue3<Priority> queue = getQueue();
        queue.poll();
        Assertions.assertArrayEquals(new Entry[]{
                new Entry(36),
                new Entry(19),
                new Entry(16),
                new Entry(17),
                new Entry(3),
                new Entry(15),
                new Entry(1),
                new Entry(2),
                null, null
        }, queue.array);

        queue.poll();
        Assertions.assertArrayEquals(new Entry[]{
                new Entry(19),
                new Entry(17),
                new Entry(16),
                new Entry(2),
                new Entry(3),
                new Entry(15),
                new Entry(1),
                null, null, null
        }, queue.array);
    }
}
