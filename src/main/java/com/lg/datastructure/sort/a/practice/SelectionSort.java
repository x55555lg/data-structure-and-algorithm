package com.lg.datastructure.sort.a.practice;

import java.util.Arrays;

/**
 * 选择排序
 *
 * @author Xulg
 * Created in 2020-10-23 15:39
 */
class SelectionSort {

    /*
     * 时间复杂度：O(N^2)
     * 空间复杂度：O(1)
     * 不稳定
     */


    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            // 找出[i, array.length-1]的最小值索引
            int minIdx = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            swap(array, i, minIdx);
        }
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static void main(String[] args) {
        int[] array = {9, 5, 3, 2, 0, 6, 1, 4};
        sort(array);
        System.out.println("after sort: " + Arrays.toString(array));
    }
}
