package com.lg.algorithm.sort.a2.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * 选择排序
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 * 稳定性：不稳定
 *
 * @author Xulg
 * Created in 2021-01-18 10:57
 */
@SuppressWarnings("DuplicatedCode")
class SelectionSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            // 找到arr[i, n)上的最小值的位置
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            swap(arr, i, minIdx);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        {
            int times = 1000000;
            for (int time = 0; time < times; time++) {
                int length = RandomUtil.randomInt(0, 100);
                int[] arr = new int[length];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                }
                int[] arr1 = arr.clone();
                int[] arr2 = arr.clone();
                Arrays.sort(arr1);
                sort(arr2);
                if (!Arrays.equals(arr1, arr2)) {
                    System.out.println("Oops");
                }
            }
            System.out.println("finish!");
        }
    }
}
