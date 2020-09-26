package com.lg.datastructure.sort;

import java.util.Arrays;

/**
 * 选择排序的实现
 * 复杂度：O(n²)
 *
 * @author Xulg
 * Created in 2020-09-18 8:26
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] array = {3, 1, 5, 2, 4, 7, 0, 9, 8};
        System.out.println("selection sort before: " + Arrays.toString(array));
        selectionSort(array);
        System.out.println("selection sort after: " + Arrays.toString(array));

        System.out.println(Arrays.toString(sort(new int[]{3, 1, 5, 2, 4, 7, 0, 9, 8})));
    }

    public static void selectionSort(int[] array) {
        // 值：   3   1   5   2   4   7   0   9   8
        // 下标： 0   1   2   3   4   5   6   7   8

        /*
         * 选择排序：最左边的值最小
         * 第一次：i = 0, minIndex = 0，从数组[1, 8]中获取比array[minIndex]处还要小的值的索引j，
         *         如果有，则覆盖minIndex为j，然后交换array[i]和array[minIndex]的值；
         *  变换后的数组：
         *      array = {0, 1, 5, 2, 4, 7, 3, 9, 8}
         *
         * 第二次：i = 1, minIndex = 1，从数组[2, 8]中获取比array[minIndex]处还要小的值的索引j，
         *         如果有，则覆盖minIndex为j，然后交换array[i]和array[minIndex]的值；
         *  变换后的数组：
         *      array = {0, 1, 5, 2, 4, 7, 3, 9, 8}
         *
         * 第三次：i = 2, minIndex = 2，从数组[3, 8]中获取比array[minIndex]处还要小的值的索引j，
         *         如果有，则覆盖minIndex为j，然后交换array[i]和array[minIndex]的值；
         *  变换后的数组：
         *      array = {0, 1, 2, 5, 4, 7, 3, 9, 8}
         *
         * 第四次：i = 3, minIndex = 3，从数组[4, 8]中获取比array[minIndex]处还要小的值的索引j，
         *         如果有，则覆盖minIndex为j，然后交换array[i]和array[minIndex]的值；
         *  变换后的数组：
         *      array = {0, 1, 2, 3, 4, 7, 5, 9, 8}
         *
         * 第五次：i = 4, minIndex = 4，从数组[5, 8]中获取比array[minIndex]处还要小的值的索引j，
         *         如果有，则覆盖minIndex为j，然后交换array[i]和array[minIndex]的值；
         *  变换后的数组：
         *      array = {0, 1, 2, 3, 4, 7, 5, 9, 8}
         *
         * 第六次：i = 5, minIndex = 5，从数组[6, 8]中获取比array[minIndex]处还要小的值的索引j，
         *         如果有，则覆盖minIndex为j，然后交换array[i]和array[minIndex]的值；
         *  变换后的数组：
         *      array = {0, 1, 2, 3, 4, 5, 7, 9, 8}
         *
         * 第七次：i = 6, minIndex = 6，从数组[7, 8]中获取比array[minIndex]处还要小的值的索引j，
         *         如果有，则覆盖minIndex为j，然后交换array[i]和array[minIndex]的值；
         *  变换后的数组：
         *      array = {0, 1, 2, 3, 4, 5, 7, 8, 9}
         *
         * 第八次：i = 7, minIndex = 7，从数组[8, 8]中获取比array[minIndex]处还要小的值的索引j，
         *         如果有，则覆盖minIndex为j，然后交换array[i]和array[minIndex]的值；
         *  变换后的数组：
         *      array = {0, 1, 2, 3, 4, 5, 7, 8, 9}
         */

        if (array == null || array.length < 2) {
            return;
        }

        for (int i = 0; i < array.length - 1; i++) {
            // minIndex为数组[i, length-1]范围中最小值的索引
            int minIndex = i;
            // 在数组[i, length-1]范围中查找最小值
            //int start = i + 1, end = array.length - 1;
            for (int start = i + 1; start <= array.length - 1; start++) {
                if (array[start] < array[minIndex]) {
                    minIndex = start;
                }
            }
            // 交换i和minIndex处的值
            swap(array, i, minIndex);
        }
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static int[] sort(int[] array) {
        if (array == null || array.length < 2) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            swap(array, i, minIndex);
        }
        return array;
    }
}
