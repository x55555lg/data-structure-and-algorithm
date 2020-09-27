package com.lg.datastructure.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 复杂度：O(n²)
 *
 * @author Xulg
 * Created in 2020-09-18 9:02
 */
class BubbleSort {

    public static void main(String[] args) {
        int[] array = {3, 1, 5, 2, 4, 7, 0, 9, 8};
        System.out.println("bubble sort before: " + Arrays.toString(array));
        bubbleSort(array);
        System.out.println("bubble sort after: " + Arrays.toString(array));
    }

    public static void bubbleSort(int[] array) {
        // 值：   3   1   5   2   4   7   0   9   8
        // 下标： 0   1   2   3   4   5   6   7   8
        /*
         *当i = 0的时候
         * 第1次，比较array[0]和array[1]谁大，大的在后面，交换后：
         *      1   3   5   2   4   7   0   9   8
         *      0   1   2   3   4   5   6   7   8
         *
         * 第2次，比较array[0]和array[2]谁大，大的在后面，交换后：
         *      1   3   5   2   4   7   0   9   8
         *      0   1   2   3   4   5   6   7   8
         *
         * 第3次，比较array[0]和array[3]谁大，大的在后面，交换后：
         *      1   3   5   2   4   7   0   9   8
         *      0   1   2   3   4   5   6   7   8
         *
         * 第4次，比较array[0]和array[4]谁大，大的在后面，交换后：
         *      1   3   5   2   4   7   0   9   8
         *      0   1   2   3   4   5   6   7   8
         *
         * 第5次，比较array[0]和array[5]谁大，大的在后面，交换后：
         *      1   3   5   2   4   7   0   9   8
         *      0   1   2   3   4   5   6   7   8
         *
         * 第6次，比较array[0]和array[6]谁大，大的在后面，交换后：
         *      0   3   5   2   4   7   1   9   8
         *      0   1   2   3   4   5   6   7   8
         *
         * 第7次，比较array[0]和array[7]谁大，大的在后面，交换后：
         *      0   3   5   2   4   7   1   9   8
         *      0   1   2   3   4   5   6   7   8
         *
         * 第8次，比较array[0]和array[8]谁大，大的在后面，交换后：
         *      0   3   5   2   4   7   1   9   8
         *      0   1   2   3   4   5   6   7   8
         *当i = 1的时候
         *  ......
         */

        if (array == null || array.length < 2) {
            return;
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[i]) {
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
}
