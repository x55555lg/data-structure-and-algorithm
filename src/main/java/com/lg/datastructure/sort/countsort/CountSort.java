package com.lg.datastructure.sort.countsort;

import java.util.Arrays;

/**
 * 计数排序
 * 时间复杂度：O(N)
 * 局限性：必须是非负整数(>=0)
 *
 * @author Xulg
 * Created in 2020-10-22 10:08
 */
class CountSort {

    /*
     * 计数排序：
     *  对于一个给定的int数组array[]，如果array中的取值均在0~200之间，使用计数排序。
     *  思路：
     *      1.找到数组array中的最大值max。
     *      2.申请一个数组helper[]，长度为max+1，index范围0~max个数，
     *      helper[index]来存放index出现的次数。
     *          例如：helper[3]=4，表示3这个数出现了4次
     *      3.遍历helper数组，将每个数按出现次数写回原数组array。
     */

    public static void countSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        // 找出最大值max
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }

        // 申请一个计数数组，helper数组的index表示数值，范围为[0, max]
        int[] helper = new int[max + 1];

        // 对array中各个值出现次数进行统计
        for (int value : array) {
            helper[value]++;
        }

        // 遍历helper数组，将每个数按出现次数写回原数组array
        int idx = 0;
        for (int i = 0; i < helper.length; i++) {
            for (int count = helper[i]; count > 0; count--) {
                array[idx++] = i;
            }
        }
    }

    public static void main(String[] args) {
        int[] array1 = {3, 1, 5, 2, 4, 7, 0, 9, 8};
        System.out.println("count sort before: " + Arrays.toString(array1));
        // 计数排序
        countSort(array1);
        System.out.println("count sort after:  " + Arrays.toString(array1));
    }
}
