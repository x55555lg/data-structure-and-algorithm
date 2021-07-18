package com.practice.sort;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * O(n^2)
 * 不稳定
 *
 * @author Xulg
 * Created in 2021-04-29 10:55
 */
class SelectionSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
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
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[100];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt();
            }
            int[] arr1 = arr.clone();
            Arrays.sort(arr1);
            int[] arr2 = arr.clone();
            sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                System.out.println("fucking......");
                return;
            }
        }
        System.out.println("success......");
    }
}
