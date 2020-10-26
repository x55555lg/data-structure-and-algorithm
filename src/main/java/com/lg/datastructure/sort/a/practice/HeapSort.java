package com.lg.datastructure.sort.a.practice;

import java.util.Arrays;

/**
 * 堆排序
 * 按照大根堆计算
 *
 * @author Xulg
 * Created in 2020-10-26 9:02
 */
@SuppressWarnings("DuplicatedCode")
class HeapSort {

    /*
     * 时间复杂度：O(N*logN)
     * 空间复杂度：O(1)
     * 不稳定
     */

    @SuppressWarnings("ConstantConditions")
    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        if (true) {
            // 将数组变成最大堆
            // 这里的时间复杂度是：O(N)
            for (int idx = array.length - 1; idx >= 0; idx--) {
                heapify(array, idx, array.length);
            }
        }
        if (false) {
            // 将数组变成最大堆
            // 这里的时间复杂度是：O(N*LogN)
            for (int idx = 0; idx < array.length; idx++) {
                heapInsert(array, idx);
            }
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

        // 交换第一个和最后一个数，将最大值挪到最后去
        swap(array, 0, array.length - 1);

        // 堆的大小，最后一个已经是最大值了，不需要再参与了，所以要去掉
        int heapSize = array.length - 1;

        // 这里总的时间复杂度是: O(N*LogN)
        while (heapSize > 0) {
            // 堆化操作
            // 这里的时间复杂度是: O(LogN)
            heapify(array, 0, heapSize);
            // 第一个数和最后一个交换
            // 这里的时间复杂度是: O(1)
            swap(array, 0, heapSize - 1);
            heapSize--;
        }
    }

    /**
     * 从下到上的堆化操作
     *
     * @param array the array
     * @param index the start index of the heapify
     */
    @SuppressWarnings("ConditionalBreakInInfiniteLoop")
    private static void heapInsert(int[] array, int index) {
        while (true) {
            if (index == 0) {
                // 到根节点就不用看了
                break;
            }
            // 计算节点的父节点位置p=(i -1)/2;
            int parentIdx = (index - 1) >> 1;
            // 父节点的值大于子节点就交换，保证堆是一个小根堆
            if (array[parentIdx] > array[index]) {
                swap(array, parentIdx, index);
            }
            // 看看父节点是什么情况
            index = parentIdx;
        }
    }

    /**
     * 从上到下的堆化操作
     *
     * @param array    the array
     * @param index    the start index of the heapify
     * @param heapSize the heap size
     */
    private static void heapify(int[] array, int index, int heapSize) {
        while (true) {
            // 计算左子节点的位置left=2*p+1;
            int leftSubIdx = index * 2 + 1;
            // 计算右子节点的位置left=2*p+2;
            int rightSubIdx = leftSubIdx + 1;

            // 左子节点越界了，直接退出了，因为右子节点肯定也越界了
            if (leftSubIdx >= heapSize) {
                break;
            }

            // 获取左右子节点中最大值的那个索引位置
            int maxSubIdx;
            if (rightSubIdx < heapSize) {
                // 右子节点没有越界，比较左右子节点谁最大
                //maxSubIdx = array[rightSubIdx] < array[leftSubIdx] ? rightSubIdx : leftSubIdx;
                maxSubIdx = array[rightSubIdx] > array[leftSubIdx] ? rightSubIdx : leftSubIdx;
            } else {
                // 右子节点越界了，左子节点就是最大的
                maxSubIdx = leftSubIdx;
            }

            // 父节点和最大子节点比较，取最大的那个的索引
            //int maxIdx = array[index] < array[maxSubIdx] ? index : maxSubIdx;
            int maxIdx = array[index] > array[maxSubIdx] ? index : maxSubIdx;

            // 如果父左右3个节点中最大值还是父节点，堆化完成可以结束了
            if (maxIdx == index) {
                break;
            } else {
                // 子节点比父节点大，进行交换，满足最大堆的要求
                swap(array, index, maxIdx);
                // 查看下一个节点
                index = maxIdx;
            }
        }
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 2, 8, 7, 5, 6, 4};
        sort(array);
        System.out.println("after sort: " + Arrays.toString(array));
    }
}
