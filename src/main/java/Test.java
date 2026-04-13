


import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author veritas
 * @Data 2024/10/8 12:09
 */
public class Test {


    public void heapSort(int[] arr) {
        heapify(arr, arr.length);
        for (int right = arr.length - 1; right > 0; right--) {
            swap(arr, 0, right);
            down(arr, 0, right);
        }
    }

    public void heapify(int[] arr, int size) {
        for (int i = (size / 2) - 1; i >= 0; i--) {
            down(arr, i, size);
        }
    }


    public void down(int[] arr, int parent, int heapSize) {
        for (; ; ) {
            int left = parent * 2 + 1;
            int right = left + 1;
            int max = parent;
            if (left < heapSize && arr[left] > arr[max]) {
                max = left;
            }
            if (right < heapSize && arr[right] > arr[max]) {
                max = right;
            }
            if (max == parent) {
                break;
            }
            swap(arr, left, max);

            parent = max;
        }
    }


    public static int[] nethertLandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, R};
        }
        int less = L - 1;
        int more = R;
        int index = L;

        while (index < more) {
            if (arr[index] == arr[R]) {
                index = index + 1;
            }
            if (arr[index] < arr[R]) {
                swap(arr, index, less + 1);
                less = less + 1;
                index = index + 1;
            }
            if (arr[index] > arr[R]) {
                more = more - 1;
                swap(arr, index, more - 1);
            }
        }

        swap(arr, more, R);
        return new int[]{less + 1, more};
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public static void dfs(int count, int i, int j, int[] nums, int target, List<List<Integer>> results, LinkedList<Integer> stack) {
        if (count == 2) {
            twoSum(i, j, nums, target, results, stack);
            return;
        }
        for (int k = i; k < j; k++) {

            if (k > i && nums[k] == nums[k - 1]) {
                continue;
            }
            stack.push(nums[k]);

            int nextTarget = target - nums[k];
            dfs(count - 1, k + 1, j, nums, nextTarget, results, stack);

            stack.pop();
        }
    }

    public static void twoSum(int i, int j, int[] nums, int target, List<List<Integer>>
            results, LinkedList<Integer> stack) {
        while (i < j) {
            if (nums[i] + nums[j] > target) {
                j--;
            } else if (nums[i] + nums[j] < target) {
                i++;
            } else {
                ArrayList<Integer> result = new ArrayList<>(stack);
                result.add(nums[i]);
                result.add(nums[j]);
                results.add(result);
                i++;
                j--;
                while (i < j && nums[i] == nums[i + 1]) {
                    i++;
                }
                while (i < j && nums[j] == nums[j - 1]) {
                    j--;
                }
            }
        }
    }

    public void mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(nums, left, mid);
        mergeSort(nums, mid, right);

        // 对left~right 排序
        merge(nums, left, mid, right);
    }

    public void merge(int[] nums, int left, int mid, int right) {
        int[] tmp = new int[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                tmp[k] = nums[i];
                k++;
            } else {
                tmp[k] = nums[j];
                k++;
            }
        }
        while (i <= mid) {
            tmp[k] = nums[i];
            k++;
            i++;
        }
        while (j < right) {
            tmp[k] = nums[j];
            k++;
            j++;
        }
        for (int p = 0; i < tmp.length; i++) {
            nums[left + p] = tmp[p];
        }
    }
}
