package heima_data_structure.test.other;

/**
 * <b>小顶堆</b>
 */
public class MinHeap {

    /*
              min
        1->4->5->null
        1->3->4->null
        2->6->null

        小顶堆
            1 2 4
        新链表
            s->1
     */

    BufferedIntReader[] array;
    int size;

    public MinHeap(int capacity) {
        array = new BufferedIntReader[capacity];
    }

    public boolean offer(BufferedIntReader offered) {
        if (isFull()) {
            return false;
        }
        int child = size++;
        int parent = (child - 1) / 2;
        while (child > 0 && offered.current < array[parent].current) {
            array[child] = array[parent];
            child = parent;
            parent = (child - 1) / 2;
        }
        array[child] = offered;
        return true;
    }

    public BufferedIntReader poll() {
        if (isEmpty()) {
            return null;
        }
        swap(0, size - 1);
        size--;
        BufferedIntReader e = array[size];
        array[size] = null; // help GC

        // 下潜
        down(0);

        return e;
    }

    private void down(int parent) {
        int left = 2 * parent + 1;
        int right = left + 1;
        int min = parent; // 假设父元素最小
        if (left < size && array[left].current < array[min].current) {
            min = left;
        }
        if (right < size && array[right].current < array[min].current) {
            min = right;
        }
        if (min != parent) { // 有孩子比父亲小
            swap(min, parent);
            down(min);
        }
    }

    private void swap(int i, int j) {
        BufferedIntReader t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == array.length;
    }
}
