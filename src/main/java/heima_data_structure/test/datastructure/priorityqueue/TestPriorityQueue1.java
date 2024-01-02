package heima_data_structure.test.datastructure.priorityqueue;

import heima_data_structure.java.src.main.java.com.itheima.datastructure.priorityqueue.Entry;
import heima_data_structure.java.src.main.java.com.itheima.datastructure.priorityqueue.PriorityQueue1;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/*
    优先级队列       一端进, 另一端出       按优先级出队
    普通队列        一端进, 另一端出       FIFO
*/
public class TestPriorityQueue1 {

    @Test
    public void poll() {
        PriorityQueue1<Entry> queue = new PriorityQueue1<>(5);
        queue.offer(new Entry("task1", 4));
        queue.offer(new Entry("task2", 3));
        queue.offer(new Entry("task3", 2));
        queue.offer(new Entry("task4", 5));
        queue.offer(new Entry("task5", 1));
        assertFalse(queue.offer(new Entry("task6", 7)));

        System.out.println(Arrays.toString(queue.array));
        assertEquals(5, queue.poll().priority());
        System.out.println(Arrays.toString(queue.array));
        assertEquals(4, queue.poll().priority());
        assertEquals(3, queue.poll().priority());
        assertEquals(2, queue.poll().priority());
        assertEquals(1, queue.poll().priority());
    }
}
