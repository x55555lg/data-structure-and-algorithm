package com.lg.datastructure.sort.heapsort;

import java.util.Arrays;

/**
 * 堆排序2.0
 * 比1.0版优化
 * 时间复杂度：O(n*log(n))
 *
 * @author Xulg
 * Created in 2020-10-14 17:01
 */
class HeapSort2 {

    public static void heapSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        // 将数组变成堆
        // 这里的时间复杂度是：O(n)
        for (int i = array.length - 1; i >= 0; i--) {
            heapify(array, i, array.length);
        }
        // 上面堆化之后，最大值已经在array[0]位置了

        /*
         * int n = array.length - 1;
         * [0, n]上面进行堆化，保证最大值是array[0]，array[0]和array[n]交换
         * [0, n-1]上面进行堆化，保证最大值是array[0]，array[0]和array[n-1]交换
         * [0, n-2]上面进行堆化，保证最大值是array[0]，array[0]和array[n-2]交换
         * ......
         * [0, 1]上面进行堆化，保证最大值是array[0]，array[0]和array[1]交换
         */

        // 堆的大小
        int heapSize = array.length;

        // 第一个数和最后一个数交换
        swap(array, 0, --heapSize);

        while (heapSize > 0) {
            heapify(array, 0, heapSize);
            // 第一个数和最后一个数交换
            swap(array, 0, --heapSize);
        }
    }

    /**
     * 堆化操作
     * 自上而下的进行堆化操作
     *
     * @param array    the array
     * @param maxPos   堆化开始的index，一般是堆顶
     * @param heapSize 堆的大小
     */
    @SuppressWarnings("SameParameterValue")
    private static void heapify(int[] array, int maxPos, int heapSize) {
        // maxPos是父节点
        while (true) {
            // 左子节点
            int leftIdx = 2 * maxPos + 1;
            // 右子节点
            int rightIdx = leftIdx + 1;

            // 左子节点索引越界了，右子节点肯定也越界了，直接break结束
            if (leftIdx >= heapSize) {
                break;
            }

            // largestSubIdx就是左右子节点中值最大的那个索引
            int largestSubIdx;
            if (rightIdx < heapSize) {
                // 右节点没有越界，这里说明左右节点都在
                // 左右节点都有，取左右节点中值大的和父节点比较
                largestSubIdx = array[rightIdx] > array[leftIdx] ? rightIdx : leftIdx;
            } else {
                // 只有左节点，那么左子节点就是最大的子节点
                largestSubIdx = leftIdx;
            }

            // 比较父节点值和子节点值的大小
            int oldMaxPos = maxPos;
            if (array[largestSubIdx] > array[maxPos]) {
                // 子节点值大于父节点值，交换父节点和子节点的位置
                swap(array, largestSubIdx, maxPos);
                // 将交换的这个子节点作为下一个父节点进行处理
                maxPos = largestSubIdx;
            }
            // 父节点没有变化，说明堆化完成了咯
            if (oldMaxPos == maxPos) {
                break;
            }
        }
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void main(String[] args) {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        System.out.println("merge sort before: " + Arrays.toString(array));
        heapSort(array);
        System.out.println("merge sort after:  " + Arrays.toString(array));
    }
}
