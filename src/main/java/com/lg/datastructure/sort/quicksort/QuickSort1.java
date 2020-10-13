package com.lg.datastructure.sort.quicksort;

import java.util.Arrays;

/**
 * 快排实现1.0
 * 时间复杂度:
 * 最差：O(n^2)
 *
 * @author Xulg
 * Created in 2020-10-13 16:20
 */
class QuickSort1 {

    /*
     * 快排的实现原理1.0：
     *  在整个数组array[]中，取数组的最后一个值作为比较值，然后进行区间划分
     *      ...   1   2   3   4   5   6   ...   7    X
     *            L                                  R
     *            在[L, R-1]上划分区间：小于等于区、大于区
     *              array[i] <= array[R]    放在区间的左边
     *              array[i] > array[R]     放在区间的右边
     *              将array[R]插入到小于等于区的后面一个位置，保证array[R]是小于等于区的最后一个元素
     *            此时数组的区间划分：K就是小于等于区的右边界索引
     *              [L, K]      小于等于区
     *              [K+1, R]    大于区
     *            在[L, K-1]进行区域划分。。。
     *            在[K+1, R]进行区域划分。。。
     */

    public static void quickSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        doQuickSort(array, 0, array.length - 1);
    }

    private static void doQuickSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        /*
         * k就是小于等于区的右边界lessThanAreaRightIndex
         * [left, k]       小于等于区
         * [k+1, right]    大于区
         */
        // 小于等于区的右边界
        int lessThanAreaRightIndex = partition(array, left, right);
        // 排序小于等于区，array[k]不需要参与进去了
        doQuickSort(array, left, lessThanAreaRightIndex - 1);
        // 排序大于区
        doQuickSort(array, lessThanAreaRightIndex + 1, right);
    }

    /**
     * 区间划分
     * array[i] <= array[right] 放到[left, right-1]的左边
     * array[i] > array[right]  放到[left, right-1]的右边
     * array[right]放到小于等于区的下一个位置
     *
     * @param array the array
     * @param left  the left index of array
     * @param right the right index of array
     * @return the right index of the less than area
     */
    private static int partition(int[] array, int left, int right) {
        if (left > right) {
            return -1;
        }
        if (left == right) {
            return left;
        }
        // 小于等于区的起始索引
        int lessThanIndex = left - 1;
        for (int i = left; i < right; i++) {
            if (array[i] <= array[right]) {
                // 小于等于区的下一个位置和当前的数进行交换
                swap(array, lessThanIndex + 1, i);
                // 小于等于区的索引右移
                lessThanIndex++;
            }
        }
        // 将array[right]插入到小于等于区的后面一个位置，
        // 保证array[right]是小于等于区的最后一个元素
        swap(array, lessThanIndex + 1, right);
        // 返回小于等于区的右边界索引
        return lessThanIndex + 1;
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void main(String[] args) {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        System.out.println("merge sort before: " + Arrays.toString(array));
        quickSort(array);
        System.out.println("merge sort after:  " + Arrays.toString(array));
    }
}
