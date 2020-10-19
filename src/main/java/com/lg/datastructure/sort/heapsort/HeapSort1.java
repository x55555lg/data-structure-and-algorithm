package com.lg.datastructure.sort.heapsort;

import java.util.Arrays;

/**
 * 堆排序1.0
 * 时间复杂度：O(n*log(n))
 *
 * @author Xulg
 * Created in 2020-10-14 17:01
 */
class HeapSort1 {

    public static void heapSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        /*
         * 原数组：5, 3, 7, 2, 3, 4, 1
         */

        // 将数组变成堆
        // 这里的时间复杂度是：O(n*log(n))，
        // 这里可以优化成O(n)，代码见com.lg.datastructure.sort.heapsort.HeapSort2
        for (int i = 0; i < array.length; i++) {
            heapInsert(array, i);
        }

        /*
         * 数组堆化后：7, 5, 4, 2, 3, 3, 1
         */

        // 堆的大小
        int heapSize = array.length;

        /*
         * 第一个数和最后一个数交换
         * 堆化之后最大值肯定是第一个元素，将第一个值和最后一个值交换，即将最大值挪到最后
         */
        swap(array, 0, --heapSize);

        /*
         * 数组变成：1, 5, 4, 2, 3, 3, 7
         *           0  1  2  3  4  5  6
         *           heapSize = 7
         * 不管最后一位，不断的在[0, heapSize-1]上进行堆化操作，交换第一个值和最后一个元素
         *      在[0, 5]上进行堆化操作，交换第一个值和最后一个元素
         *      在[0, 4]上进行堆化操作，交换第一个值和最后一个元素
         *      在[0, 3]上进行堆化操作，交换第一个值和最后一个元素
         *      在[0, 2]上进行堆化操作，交换第一个值和最后一个元素
         *      在[0, 1]上进行堆化操作，交换第一个值和最后一个元素
         *      在[0, 0]上进行堆化操作，交换第一个值和最后一个元素
         */

        // 循环不断的堆化，将最大值挪到最后
        while (heapSize > 0) {
            heapify(array, 0, heapSize);
            // 第一个数和最后一个数交换
            swap(array, 0, --heapSize);
        }
    }

    /**
     * 堆化操作
     * 自下而上的堆化操作
     *
     * @param array the array
     * @param index the index to start heapify with
     */
    private static void heapInsert(int[] array, int index) {
        while (index > 0) {
            int parentIdx = (index - 1) / 2;
            // 如果子节点大于它的父节点，就进行交换
            if (array[index] > array[parentIdx]) {
                swap(array, index, parentIdx);
            }
            // 看父节点的状况
            index = parentIdx;
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
