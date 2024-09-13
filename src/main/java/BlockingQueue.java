import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author veritas
 * @Data 2024/9/10 17:41
 */
public class BlockingQueue {
    private Object[] elements;

    private int capacity;

    private int startIndex;

    private int endIndex;

    private Lock lock;

    private Condition notEmpty;

    private Condition notFull;

    public BlockingQueue(int capacity) {
        this.elements = new Object[capacity];

        this.capacity = capacity;

        lock = new ReentrantLock();

        notEmpty = lock.newCondition();

        notFull = lock.newCondition();
    }

    public void put(Object element) {
        try {
            // 进入区
            lock.lock();
            // 等待区
            while (elements.length == capacity) {
                notFull.wait();
            }
            // 临界区
            elements[endIndex] = element;
            endIndex++;
            capacity++;
            if (endIndex == elements.length) {
                endIndex = 0;
            }
            // 退出区
            notifyAll();
            // 剩余区 这里没有自己的事情
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }
    }

    public Object take() {
        try {
            // 进入区
            lock.lock();
            // 等待区
            while (elements.length == 0) {
                notEmpty.wait();
            }
            // 临界区
            Object element = elements[startIndex];
            startIndex++;
            capacity--;
            if (startIndex == elements.length) {
                startIndex = 0;
            }
            // 退出区
            notifyAll();
            // 剩余区 这里没有自己的事情
            return element;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }
        return null;
    }
}
