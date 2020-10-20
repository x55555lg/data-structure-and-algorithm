package com.lg.datastructure.heap;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 堆相关的面试题
 *
 * @author Xulg
 * Created in 2020-10-19 16:04
 */
class HeapMianShiTi {

    /*
     *  已知有一个几乎有序的数组，几乎有序是指，如果把数组排好序的话，
     * 每个元素移动的距离一定不超过k，并且k相对于数组长度来说比较小。
     *      移动前：array[i]
     *      移动后：array[i']
     *      要求：i - i' < k
     * 请选择一种合适的排序策略，对数组进行排序
     *
     * 思路：
     *      1）建立一个大小为k的小根堆，并取堆顶元素保存到原数组中。
     *      2）依次加入数组中的后一个元素并对堆结构进行调整，始终维持大小为k的小根堆。
     *      3）采用普通堆排序的方法将最后k个元素排序并保存到原数组中。
     */

    // TODO 2020/10/20 解法不对

    @Test
    public void test1() {
        int[] array = {5, 3, 7, 2, 3, 4, 1};

        System.out.println("sort before: " + Arrays.toString(array));
        sortedArrDistanceLessK(array, 2);
        System.out.println("sort after:  " + Arrays.toString(array));
    }

    public static void sortedArrDistanceLessK(int[] arr, int k) {
        if (k == 0) {
            return;
        }
        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        // 0...K-1
        for (; index <= Math.min(arr.length - 1, k - 1); index++) {
            heap.add(arr[index]);
        }
        int i = 0;
        for (; index < arr.length; i++, index++) {
            heap.add(arr[index]);
            arr[i] = heap.poll();
        }
        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static void sortWithDistanceLimit(int[] array, int n, int k) {
        // 将数组的前k个数放入最小堆中
        PriorityQueue<Integer> heap = new PriorityQueue<>(k + 1);
        for (int i = 0; i < Math.min(array.length, k + 1); i++) {
            heap.add(array[i]);
        }
        for (int i = 0; i < n; i++) {
            array[i] = heap.poll();
            // 将后续的[k+1, array.length]加入堆中
            if (i + k + 1 < array.length) {
                heap.add(array[i + k + 1]);
            }
        }
    }

    /**
     * 时间复杂度O(N*log(k))
     */
    private static void sortWithDistanceLimit(int[] array, int k) {
        if (array == null || array.length < 2 || k == 0) {
            return;
        }

        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        // [0, k-1]
        for (; index <= Math.min(array.length - 1, k - 1); index++) {
            heap.add(array[index]);
        }

        int i = 0;
        for (; index < array.length; i++, index++) {
            heap.add(array[index]);
            array[i] = heap.poll();
        }

        while (!heap.isEmpty()) {
            array[i++] = heap.poll();
        }
    }

    /* ************************************************************************************************************** */

    public static class ScaleSort {
        public int[] sortElement(int[] A, int n, int k) {
            int[] heap = new int[k + 1];
            for (int i = 0; i < k + 1; i++) {
                insert(heap, i, A[i]);
            }
            for (int i = 0, count = k; i < n; i++) {
                A[i] = delete(heap, count--);
                if (i + k + 1 < n) {
                    insert(heap, ++count, A[i + k + 1]);
                }
            }
            return A;
        }

        void insert(int[] A, int count, int data) {
            int i, temp;
            i = count;
            A[i] = data;
            while (i != 0 && (i - 1) / 2 >= 0 && A[(i - 1) / 2] > data) {
                temp = A[i];
                A[i] = A[(i - 1) / 2];
                A[(i - 1) / 2] = temp;
                i = (i - 1) / 2;
            }
        }

        void down(int[] A, int count, int i) {
            int r, l;
            int maxson = -1;
            for (; ; ) {
                l = 2 * i + 1;
                r = 2 * i + 2;
                if (r <= count) {
                    if (A[l] < A[r]) {
                        maxson = l;
                    } else {
                        maxson = r;
                    }
                } else if (l <= count && r > count) {
                    maxson = l;
                } else if (l > count) {
                    break;
                }
                if (A[maxson] < A[i]) {
                    int temp = A[i];
                    A[i] = A[maxson];
                    A[maxson] = temp;
                    i = maxson;
                } else {
                    break;
                }
            }
        }

        int delete(int[] A, int count) {
            if (count == -1) {
                return -1;//当堆为空时，报错
            }
            int data = A[0];
            A[0] = A[count];
            A[count--] = 0;
            down(A, count, 0);
            return data;//返回删除的值
        }

        public static void main(String[] args) {
            int[] a = new int[]{3, 1, 2, 6, 5, 4, 7, 8, 10, 9};
            ScaleSort h = new ScaleSort();
            int[] r = h.sortElement(a, 10, 2);
            System.out.println(Arrays.toString(r));
        }

    }
}
