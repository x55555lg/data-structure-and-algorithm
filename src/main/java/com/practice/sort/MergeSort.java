package com.practice.sort;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * O(N*logN)
 * 稳定
 *
 * @author Xulg
 * Created in 2021-04-29 12:31
 */
@SuppressWarnings("ManualArrayCopy")
class MergeSort {

    private static class MergeSortByRecurse {
        public static void sort(int[] arr) {
            if (arr == null || arr.length <= 1) {
                return;
            }
            recurse(arr, 0, arr.length - 1);
        }

        private static void recurse(int[] arr, int left, int right) {
            if (left == right) {
                return;
            }
            int mid = left + ((right - left) >> 1);
            recurse(arr, left, mid);
            recurse(arr, mid + 1, right);
            merge(arr, left, right, mid);
        }

        private static void merge(int[] arr, int left, int right, int mid) {
            if (left == right) {
                return;
            }
            int[] helper = new int[right - left + 1];
            int leftIdx = left;
            int rightIdx = mid + 1;
            int idx = 0;
            while (leftIdx <= mid && rightIdx <= right) {
                if (arr[leftIdx] <= arr[rightIdx]) {
                    helper[idx++] = arr[leftIdx++];
                } else {
                    helper[idx++] = arr[rightIdx++];
                }
            }
            while (leftIdx <= mid) {
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
            if (arr == null || arr.length <= 1) {
                return;
            }
            for (int mergeSize = 1; mergeSize < arr.length; ) {
                // 操作每一个子数组
                for (int left = 0; left < arr.length; ) {
                    int mid = left + mergeSize - 1;
                    if (mid >= arr.length) {
                        break;
                    }
                    int right = Math.min(arr.length - 1, mid + mergeSize);
                    merge(arr, left, right, mid);
                    // 下一个子数组
                    left = right + 1;
                }
                if (mergeSize > (arr.length >> 1)) {
                    // 提前退出
                    break;
                }
                // 翻倍
                mergeSize = mergeSize << 1;
            }
        }

        private static void merge(int[] arr, int left, int right, int mid) {
            if (left == right) {
                return;
            }
            int[] helper = new int[right - left + 1];
            int leftIdx = left;
            int rightIdx = mid + 1;
            int idx = 0;
            while (leftIdx <= mid && rightIdx <= right) {
                if (arr[leftIdx] <= arr[rightIdx]) {
                    helper[idx++] = arr[leftIdx++];
                } else {
                    helper[idx++] = arr[rightIdx++];
                }
            }
            while (leftIdx <= mid) {
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
            MergeSortByRecurse.sort(arr2);
            int[] arr3 = arr.clone();
            MergeSortByLoop.sort(arr3);
            if (!Arrays.equals(arr1, arr2) || !Arrays.equals(arr2, arr3)) {
                System.out.println("fucking......");
                return;
            }
        }
        System.out.println("success......");
        {
            int hash = 2132452345;
            System.out.println((hash & (4 - 1)) == (hash % 4));
        }
    }

}