package com.lg.algorithm.sort.a3.practice;

import cn.hutool.core.util.RandomUtil;
import com.lg.algorithm.sort.a3.ComparatorSort;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-09-02 21:00
 * Description: 选择排序
 */
class SelectionSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            // arr[i, n)上的最小值的index
            int minValIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minValIdx]) {
                    minValIdx = j;
                }
            }
            swap(arr, i, minValIdx);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 100_0000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(0, 100)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
            int[] arr1 = arr.clone();
            int[] arr2 = arr.clone();
            sort(arr1);
            ComparatorSort.sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }

}
