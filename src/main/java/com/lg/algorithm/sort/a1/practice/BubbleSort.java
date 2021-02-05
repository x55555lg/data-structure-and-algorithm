package com.lg.algorithm.sort.a1.practice;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * @author Xulg
 * Created in 2020-10-23 15:29
 */
class BubbleSort {

    /*
     * 时间复杂度：O(N^2)
     * 空间复杂度：O(1)
     * 稳定
     */

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    swap(array, i, j);
                }
            }
        }
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int[] array = {9, 5, 3, 2, 0, 6, 1, 4};
        sort(array);
        System.out.println("after sort: " + Arrays.toString(array));
    }
}
