package com.practice.sort;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-04-29 16:16
 */
class CountSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        for (int value : arr) {
            assert value >= 0;
        }
        int maxVal = arr[0];
        for (int i = 1; i < arr.length; i++) {
            maxVal = Math.max(maxVal, arr[i]);
        }
        int[] helper = new int[maxVal + 1];
        for (int i = 0; i < arr.length; i++) {
            helper[arr[i]]++;
        }
        int idx = 0;
        for (int i = 0; i < helper.length; i++) {
            if (helper[i] > 0) {
                for (int c = 0; c < helper[i]; c++) {
                    arr[idx++] = i;
                }
            }
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
                arr[i] = RandomUtil.randomInt(0, 99);
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
