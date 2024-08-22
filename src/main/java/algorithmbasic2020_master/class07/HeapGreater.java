package algorithmbasic2020_master.class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/*
 * TODO T一定要是非基础类型，有基础类型需求包一层
 */
public class HeapGreater<T> {

	private ArrayList<T> heap;
	/*
	*TODO
	*  indexMap： 任何给的样本 记录这个样本在堆上的位置
	*  根据com 来进行比较
	* */
	private HashMap<T, Integer> indexMap;
	private int heapSize;//TODO 堆的大小
	private Comparator<? super T> comp;//TODO 对象比较的比较器

	public HeapGreater(Comparator<T> c) {
		heap = new ArrayList<>();
		indexMap = new HashMap<>();
		heapSize = 0;
		comp = c;
	}

	public boolean isEmpty() {
		return heapSize == 0;
	}

	public int size() {
		return heapSize;
	}
	/*
	* TODO 是否 收过这个数据 不需要遍历 直接查询到 O(1）
	 */
	public boolean contains(T obj) {
		return indexMap.containsKey(obj);
	}

	public T peek() {
		return heap.get(0);
	}
	/*
	* 记录了当前obj在heapSize这个位置上
	* 并heapInsert
	* */
	public void push(T obj) {
		heap.add(obj);
		indexMap.put(obj, heapSize);
		heapInsert(heapSize++);
	}
	/*
	*TODO
	* 0位置的东西一定是要返回的
	* 第一个位置的元素 和最有一个位置的元素 互换 ==> 就是弹出原来的第一个元素
	* heap查出掉最有一个位置 不是删除样本
	* indexMap同步于heap
	* 因为最有一个位置的元素现在在第一个 所以要heapfiy
	* */
	public T pop() {
		T ans = heap.get(0);
		swap(0, heapSize - 1);
		indexMap.remove(ans);
		heap.remove(--heapSize);
		heapify(0);
		return ans;
	}
	//TODO  原有的堆只能删除堆顶 因为不知道堆的内部结构 而这个方法 可以直接删除
	public void remove(T obj) {
		//TODO 找到最后一个元素
		T replace = heap.get(heapSize - 1);
		//TODO 找到 指定元素的位置
		int index = indexMap.get(obj);
		//TODO 删除指定元素 indexMap和heap一起删除
		indexMap.remove(obj);
		heap.remove(--heapSize);
		if (obj != replace) {
			//TODO 被删除的元素的位置 现在是 最后一个元素
			heap.set(index, replace);
			indexMap.put(replace, index);
			//TODO 做堆的调整
			resign(replace);
		}
	}
	//TODO 做堆的调整
	public void resign(T obj) {
		int valueIndex = indexMap.get(obj);
		/*
		* TODO
		*  某一个节点的值被修改了 这个节点的位置首先找出来 再重新排序
		*  下面两个操作只会有一个
		* */
		heapInsert(valueIndex);
		heapify(valueIndex);
	}

	// 请返回堆上的所有元素
	public List<T> getAllElements() {
		List<T> ans = new ArrayList<>();
		for (T c : heap) {
			ans.add(c);
		}
		return ans;
	}
	/*
	* TODO
	*   比较当前节点和当前节点的父节点
	*  负数的话 当前节点和父节点交换
	* */
	private void heapInsert(int index) {
		//TODO 通过比较器来比较大小 而比较器可以自定义
		while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
			swap(index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}
	/*
	* TODO 下沉方法
	* */
	private void heapify(int index) {
		int left = index * 2 + 1;
		while (left < heapSize) {
			int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
			best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
			if (best == index) {
				break;
			}
			swap(best, index);
			index = best;
			left = index * 2 + 1;
		}
	}
	/*
	* TODO
	*  1.heap中有两个元素交换
	*  2.indexMap中的两个记录也要有变化
	* */
	private void swap(int i, int j) {
		T o1 = heap.get(i);
		T o2 = heap.get(j);
		heap.set(i, o2);
		heap.set(j, o1);
		indexMap.put(o2, i);
		indexMap.put(o1, j);
	}

}
