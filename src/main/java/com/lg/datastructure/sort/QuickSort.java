package com.lg.datastructure.sort;

/**
 * 快速排序
 * 时间复杂度：n*log(n)
 *
 * @author Xulg
 * Created in 2020-09-27 8:36
 */
class QuickSort {

    /*
     * 快排的前置问题：
     *      给定一个数组array，和一个整数num，请把小于等于num的数放在数组的左边，大于num的数放在数组的右边。
     *      要求额外的空间复杂度O(1)，时间复杂度O(n)
     * 设置一个小于等于区，起始index=-1，遍历array数组，
     *  如果array[i] > num，直接下一个(i++)
     *  如果array[i] <= num，array[i]和小于等于区的下一个值交换，即array[i]和array[-i+1]交换
     *  ...
     */

    public static void aVoid(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            // 遍历整个小于等于区
            for (int startIndex = 0; startIndex <= i; startIndex++) {
                if (array[startIndex] <= value) {

                }
            }
        }
        // TODO 2020/10/12 ......
    }

    private static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}
