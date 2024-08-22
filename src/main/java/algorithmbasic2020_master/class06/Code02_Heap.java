package algorithmbasic2020_master.class06;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code02_Heap {

	public static class MyMaxHeap {
		private int[] heap;
		private final int limit;
		//即表示堆的大小 也表示 新来的节点一开始放在哪里
		private int heapSize;

		public MyMaxHeap(int limit) {
			heap = new int[limit];
			this.limit = limit;
			heapSize = 0;
		}

		public boolean isEmpty() {
			return heapSize == 0;
		}

		public boolean isFull() {
			return heapSize == limit;
		}

		public void push(int value) {
			if (heapSize == limit) {
				throw new RuntimeException("heap is full");
			}
			//一开始新来的节点放在heapSize位置
			heap[heapSize] = value;
			// value  heapSize
			heapInsert(heap, heapSize++);
		}

		/*
		* 用户此时，让你返回最大值，并且在大根堆中，把最大值删掉
		* 剩下的数，依然保持大根堆组织
		* eg: [9,8,7,6,3] 返回9这个头节点
		* 让最后一个节点替换掉头节点 得到 [3,8,7,6,3] => [3,8,7,6]
		* 然后调整 整个树变成大根堆的模样
		* */
		public int pop() {
			int ans = heap[0];
			/*
			* --heapSize 先减1 再赋值
			* */
			swap(heap, 0, --heapSize);
			heapify(heap, 0, heapSize);
			return ans;
		}
		/*
		* 新加进来的数，现在停在了index位置，请依次往上移动，
		* 移动到0位置，或者干不掉自己的父亲了，停！
		* */
		private void heapInsert(int[] arr, int index) {
			/*
			*TODO
			* [index]    [index-1]/2
			* index == 0
			* 判断当前节点和父节点的大小
			* 当前节点大于父节点 的话 当前节点与父节点的位置交换
			* 再判断当前节点与新的父节点的大小 如果当前节点大于父节点 的话 执行上面相同的操作
			* 直到当前节点达到了最顶端
			* */
			while (arr[index] > arr[(index - 1) / 2]) {
				swap(arr, index, (index - 1) / 2);
				index = (index - 1) / 2;
			}
		}

		/*
		* 从index位置，往下看，不断的下沉
		* 停：较大的孩子都不再比index位置的数大；已经没孩子了
		* eg：[3,8,7,6] 头节点3的左右子节点分别是 8 7
		* 头节点和 左右子节点中最大的节点比较 如果 头节点 < 左右子节点中最大的节点 则 交换
		* 得到 [8,3,7,6] 3这个节点 和左右子节点的最大值比较 交换
		* [8,6,7,3]  heapSize发现3这个节点没有右子节点 越界了
		 * */
		private void heapify(int[] arr, int index, int heapSize) {
			int left = index * 2 + 1;//TODO 得到当前节点的左子节点下标
			while (left < heapSize) { //TODO 如果有左孩子，有没有右孩子，可能有可能没有！
				//int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
				/*
				*TODO
				*  把较大孩子的下标，给largest
				* 有右子节点 且 右子节点 > 左子节点 当前节点和右子节点交换
				* 有左子节点 且 左子节点 > 右子节点 当前节点和左子节点交换
				* 有无 左右子节点 查看 heapSize和right/left关系
				* */
				int largest;
				int right = left + 1;//当前节点的右子节点下标
				//TODO right < heapSize  防止计算出的right下标溢出
				if(right < heapSize && arr[right] > arr[left]){
					largest = right;
				}else {
					largest = left;
				}
				/*
				*TODO
				* 选出了 左右子节点中的最大子节点的下标
				* 接下来 该下标的元素 和 父节点(当前节点)元素比较
				* 哪个大 把哪一个元素的下标赋值给largest
				* */
				largest = arr[largest] > arr[index] ? largest : index;
				if (largest == index) {//TODO 该下标的元素 和 父节点(当前节点)元素比较 父节点大 就结束
					break;
				}
				// index和较大孩子，要互换
				swap(arr, largest, index);
				index = largest;
				left = index * 2 + 1;
			}
		}

		private void swap(int[] arr, int i, int j) {
			int tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}

	}

	public static class RightMaxHeap {
		private int[] arr;
		private final int limit;
		private int size;

		public RightMaxHeap(int limit) {
			arr = new int[limit];
			this.limit = limit;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public boolean isFull() {
			return size == limit;
		}

		public void push(int value) {
			if (size == limit) {
				throw new RuntimeException("heap is full");
			}
			arr[size++] = value;
		}

		public int pop() {
			int maxIndex = 0;
			for (int i = 1; i < size; i++) {
				if (arr[i] > arr[maxIndex]) {
					maxIndex = i;
				}
			}
			int ans = arr[maxIndex];
			arr[maxIndex] = arr[--size];
			return ans;
		}

	}


	public static class MyComparator implements Comparator<Integer>{

		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}

	}

	public static void main(String[] args) {
		// TODO 默认小根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComparator());
		heap.add(5);
		heap.add(5);
		heap.add(5);
		heap.add(3);
		//  5 , 3
		System.out.println(heap.peek());
		heap.add(7);
		heap.add(0);
		heap.add(7);
		heap.add(0);
		heap.add(7);
		heap.add(0);
		System.out.println(heap.peek());
		while(!heap.isEmpty()) {
			System.out.println(heap.poll());
		}



		int value = 1000;
		int limit = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			int curLimit = (int) (Math.random() * limit) + 1;
			MyMaxHeap my = new MyMaxHeap(curLimit);
			RightMaxHeap test = new RightMaxHeap(curLimit);
			int curOpTimes = (int) (Math.random() * limit);
			for (int j = 0; j < curOpTimes; j++) {
				if (my.isEmpty() != test.isEmpty()) {
					System.out.println("Oops!");
				}
				if (my.isFull() != test.isFull()) {
					System.out.println("Oops!");
				}
				if (my.isEmpty()) {
					int curValue = (int) (Math.random() * value);
					my.push(curValue);
					test.push(curValue);
				} else if (my.isFull()) {
					if (my.pop() != test.pop()) {
						System.out.println("Oops!");
					}
				} else {
					if (Math.random() < 0.5) {
						int curValue = (int) (Math.random() * value);
						my.push(curValue);
						test.push(curValue);
					} else {
						if (my.pop() != test.pop()) {
							System.out.println("Oops!");
						}
					}
				}
			}
		}
		System.out.println("finish!");

	}

}
