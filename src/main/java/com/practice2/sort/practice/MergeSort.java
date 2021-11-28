package com.practice2.sort.practice;

import cn.hutool.core.util.RandomUtil;
import com.practice2.sort.CompareSort;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-10-30 16:21
 * Description: 归并排序
 */
class MergeSort {

    private static class MergeSortByRecurse {
        public static void sort(int[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }
            recurse(arr, 0, arr.length - 1);
        }

        private static void recurse(int[] arr, int left, int right) {
            if (left == right) {
                return;
            }
            int middleIdx = left + ((right - left) >> 1);
            recurse(arr, left, middleIdx);
            recurse(arr, middleIdx + 1, right);
            merge(arr, left, right, middleIdx);
        }

        private static void merge(int[] arr, int left, int right, int middleIdx) {
            if (left == right) {
                return;
            }
            int[] helper = new int[right - left + 1];
            int leftIdx = left;
            int rightIdx = middleIdx + 1;
            int idx = 0;
            while (leftIdx <= middleIdx && rightIdx <= right) {
                if (arr[leftIdx] <= arr[rightIdx]) {
                    helper[idx++] = arr[leftIdx++];
                } else {
                    helper[idx++] = arr[rightIdx++];
                }
            }
            while (leftIdx <= middleIdx) {
                helper[idx++] = arr[leftIdx++];
            }
            while (rightIdx <= right) {
                helper[idx++] = arr[rightIdx++];
            }
            for (int i = 0; i < helper.length; i++) {
                arr[left + i] = helper[i];
            }
        }
    }

    private static class MergeSortByLoop {
        public static void sort(int[] arr) {
            if (arr == null || arr.length == 0) {
                return;
            }
            // 子数组一半长度
            for (int mergeSize = 1; mergeSize < arr.length; ) {
                for (int left = 0; left < arr.length; ) {
                    int middleIdx = left + mergeSize - 1;
                    if (middleIdx >= arr.length) {
                        break;
                    }
                    int right = Math.min(middleIdx + mergeSize, arr.length - 1);
                    merge(arr, left, right, middleIdx);
                    left = right + 1;
                }
                if (mergeSize > arr.length / 2) {
                    // 提前退出
                    break;
                }
                mergeSize = mergeSize << 1;
            }
        }

        private static void merge(int[] arr, int left, int right, int middleIdx) {
            if (left == right) {
                return;
            }
            int[] helper = new int[right - left + 1];
            int leftIdx = left;
            int rightIdx = middleIdx + 1;
            int idx = 0;
            while (leftIdx <= middleIdx && rightIdx <= right) {
                if (arr[leftIdx] <= arr[rightIdx]) {
                    helper[idx++] = arr[leftIdx++];
                } else {
                    helper[idx++] = arr[rightIdx++];
                }
            }
            while (leftIdx <= middleIdx) {
                helper[idx++] = arr[leftIdx++];
            }
            while (rightIdx <= right) {
                helper[idx++] = arr[rightIdx++];
            }
            for (int i = 0; i < helper.length; i++) {
                arr[left + i] = helper[i];
            }
        }
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
            int[] arr3 = arr.clone();
            CompareSort.sort(arr1);
            MergeSortByRecurse.sort(arr2);
            MergeSortByLoop.sort(arr3);
            if (!Arrays.equals(arr1, arr2) || !Arrays.equals(arr2, arr3)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }
}
