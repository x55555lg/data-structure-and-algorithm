package com.lg.algorithm.sort.a3.practice;

import cn.hutool.core.util.RandomUtil;
import com.lg.algorithm.sort.a3.ComparatorSort;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-09-02 21:03
 * Description: 归并排序
 */
@SuppressWarnings("ManualArrayCopy")
class MergeSort {

    /**
     * 递归写法
     */
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
            // 中间位置
            int middle = ((right - left) >> 1) + left;
            // 向左merge
            recurse(arr, left, middle);
            // 向右merge
            recurse(arr, middle + 1, right);
            // 归并操作
            merge(arr, left, right, middle);
        }

        private static void merge(int[] arr, int left, int right, int middle) {
            if (left == right) {
                return;
            }
            int[] helper = new int[right - left + 1];
            int idx = 0;
            int leftIdx = left;
            int rightIdx = middle + 1;
            while (leftIdx <= middle && rightIdx <= right) {
                if (arr[leftIdx] <= arr[rightIdx]) {
                    helper[idx++] = arr[leftIdx++];
                } else {
                    helper[idx++] = arr[rightIdx++];
                }
            }
            while (leftIdx <= middle) {
                helper[idx++] = arr[leftIdx++];
            }
            while (rightIdx <= right) {
                helper[idx++] = arr[rightIdx++];
            }
            // 写回原数组
            for (int i = 0; i < helper.length; i++) {
                arr[left + i] = helper[i];
            }
        }
    }

    /**
     * 非递归写法
     */
    private static class MergeSortByTraverse {
        public static void sort(int[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }

            for (int mergeSize = 1; mergeSize < arr.length; ) {
                for (int left = 0; left < arr.length; ) {
                    // 子数组的中间位置
                    int middle = left + mergeSize - 1;
                    if (middle >= arr.length) {
                        // 子数组的中间位置越界了
                        break;
                    }
                    // 子数组的右边界，不能超过整个数组的右边界
                    int right = Math.min(arr.length - 1, middle + mergeSize);
                    // 归并操作
                    merge(arr, left, right, middle);
                    // 下一个子数组的开始位置
                    left = right + 1;
                }
                if (mergeSize > (arr.length >> 1)) {
                    // mergeSize已经是数组的一半了，下一个循环肯定超过数组的长度
                    // 为了防止mergeSize值溢出，提前退出
                    break;
                }
                // 翻倍子数组的尺寸
                mergeSize = mergeSize << 1;
            }
        }

        private static void merge(int[] arr, int left, int right, int middle) {
            if (left == right) {
                return;
            }
            int[] helper = new int[right - left + 1];
            int idx = 0;
            int leftIdx = left;
            int rightIdx = middle + 1;
            while (leftIdx <= middle && rightIdx <= right) {
                if (arr[leftIdx] <= arr[rightIdx]) {
                    helper[idx++] = arr[leftIdx++];
                } else {
                    helper[idx++] = arr[rightIdx++];
                }
            }
            while (leftIdx <= middle) {
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
            ComparatorSort.sort(arr1);
            MergeSortByRecurse.sort(arr2);
            MergeSortByTraverse.sort(arr3);
            if (!Arrays.equals(arr1, arr2) || !Arrays.equals(arr2, arr3)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }

}
