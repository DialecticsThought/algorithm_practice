/**
 * @Description
 * @Author veritas
 * @Data 2025/2/24 19:32
 */
public class Heap {
    private int[] array;

    private int capacity;

    private int realCapacity;

    public Heap(int  capacity) {
        this.array = new int[capacity];
       this.capacity = capacity;
       this.realCapacity = 0;
    }

    private int size(){
        return realCapacity;
    }

    private int getParent(int index){
        return (index - 1)/2;
    }

    private int getLeftChild(int index){
        return index*2+1;
    }

    private int getRightChild(int index){
        return index*2+2;
    }

    private void swap(int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void offer(int value) {
        heapifyUp(value);
    }

    public void heapifyUp(int num){
        if(capacity == realCapacity){
            return;
        }
        array[realCapacity] = num;
        // 放入数据 之后 数组含有的个数 ++
        realCapacity++;
        //初始当前的索引是数组最后一个元素的索引
        int index = realCapacity - 1;
        // 从最后一个元素开始，逐步向上调整
        while (index > 0) {
            int parent = getParent(index);
            // 如果父节点大于当前节点，则交换
            if (array[parent] > array[index]) {
                swap(parent, index);
                index = parent;  // 更新当前索引
            } else {
                break;  // 如果父节点不大于当前节点，停止调整
            }
        }
    }

    public int poll(){
        return heapifyDown();
    }

    public int heapifyDown(){
        if(realCapacity == 0) {
            return -1;  // 空堆返回一个无效的值
        }
        swap(0, size()-1);
        int result = array[size() - 1];  // 记录堆顶元素的值（将被移除）
        // 记录当前遍历到的索引 初始为0
        int current = 0;
        // 记录当前最小的元素的索引 初始为0
        int curMin = 0;

        while(current < size()){
            int leftChild = getLeftChild(current);

            int rightChild = getRightChild(current);
            // 如果左子节点存在且小于当前节点
            if (leftChild < size() && array[leftChild] < array[curMin]) {
                curMin = leftChild;
            }
            // 如果右子节点存在且小于当前节点
            if (rightChild < size() && array[rightChild] < array[curMin]) {
                curMin = rightChild;
            }
            // 如果当前节点已经是最小的，则无需交换，停止调整
            if (curMin == current) {
                break;
            }
            swap(current, curMin);

            current = curMin;
        }
        return result;
    }

    public static void main(String[] args) {
        Heap heap = new Heap(10);

        heap.offer(10);
        heap.offer(5);
        heap.offer(3);
        heap.offer(8);
        heap.offer(2);

        System.out.println(heap.poll());  // 输出：2（最小值）
        System.out.println(heap.poll());  // 输出：3
        System.out.println(heap.poll());  // 输出：5
    }

}
