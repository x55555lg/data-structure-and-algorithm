package com.lg.algorithm.sort.a1.practice;

import java.util.Arrays;

/**
 * 插入排序
 *
 * @author Xulg
 * Created in 2020-10-23 15:57
 */
class InsertSort {

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
            // 从i位置往左边看，前面比我大就交换
            for (int j = i; j > 0; j--) {
                if (array[j - 1] > array[j]) {
                    swap(array, j - 1, j);
                }
            }
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
