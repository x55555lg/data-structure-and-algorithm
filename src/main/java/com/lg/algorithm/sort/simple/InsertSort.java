package com.lg.algorithm.sort.simple;

import java.util.Arrays;

/**
 * 插入排序
 * 复杂度：O(n²)
 *
 * @author Xulg
 * Created in 2020-09-18 9:17
 */
class InsertSort {
    public static void main(String[] args) {
        int[] array = {3, 1, 5, 2, 4, 7, 0, 9, 8};
        System.out.println("insert sort before: " + Arrays.toString(array));
        insertSort(array);
        System.out.println("insert sort after: " + Arrays.toString(array));

        System.out.println(Arrays.toString(sort(new int[]{3, 1, 5, 2, 4, 7, 0, 9, 8})));
    }

    @SuppressWarnings("ConstantConditions")
    private static void insertSort(int[] array) {
        // 值：   3   1   5   2   4   7   0   9   8
        // 下标： 0   1   2   3   4   5   6   7   8

        /*
         * 第1步：
         *      先在数组[0, 0]上面有序，这里天然有序
         *
         * 第2步：
         *      先在数组[0, 1]上面有序，从1开始往前面(数组左边)看，发现自己更小就交换
         *
         * 第3步：
         *      先在数组[0, 2]上面有序，从2开始往前面(数组左边)看，发现自己更小就交换
         * ......
         */

        if (array == null || array.length < 2) {
            return;
        }

        /*if (false) {
            //  我的正确的高效实现
            for (int i = 0; i < array.length; i++) {
                // array[i]往前(左)看，array[j]更小的话，就往前挪
                for (int j = i; j > 0; j--) {
                    if (array[j] < array[j - 1]) {
                        swap(array, j, j - 1);
                    }
                }
            }
        }*/

        if (true) {
            // 视频中的正确的高效实现
            for (int i = 0; i < array.length; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (array[j] > array[j + 1]) {
                        swap(array, j, j + 1);
                    }
                }
            }
        }
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    // 王峥书中的写的复杂了
    private static void insertSort2(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (array[j] > value) {
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            array[j + 1] = value;
        }
    }


    public static int[] sort(int[] array) {
        // 3    4   1   5   2   1   0   6
        // 0    1   2   3   4   5   6   7
        if (array == null || array.length < 2) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    swap(array, j, j - 1);
                }
            }
        }
        return array;
    }
}
