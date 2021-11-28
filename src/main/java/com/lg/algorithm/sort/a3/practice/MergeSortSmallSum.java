package com.lg.algorithm.sort.a3.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Xulg
 * @since 2021-09-02 21:05
 * Description: 归并排序-求小和
 */
class MergeSortSmallSum {

    /*
     * 在一个数组中，一个数左边比它小的数的总和，叫做数的小和，所有数的小和累加起来，
     * 叫做数组小和。求一个给定数组的小和：
     * 例如：[1, 3, 4, 2, 5]
     *      1左边比1小的数：没有
     *      3左边比3小的数：1
     *      4左边比4小的数：1, 3
     *      2左边比2小的数：1
     *      5左边比5小的数：1, 3, 4, 2
     *     最小和：1 + 1 + 3 + 1 + 1 + 3 + 4 + 2
     */

    private static class BruteForce {
        public static int calcSmallSum(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            int smallSum = 0;
            for (int i = arr.length - 1; i >= 0; i--) {
                // 求[0, i-1]范围的比arr[i]小的数
                for (int j = 0; j < i; j++) {
                    if (arr[j] < arr[i]) {
                        smallSum += arr[j];
                    }
                }
                if (false) {
                    List<Integer> helper = new ArrayList<>();
                    for (int j = 0; j < i; j++) {
                        if (arr[j] < arr[i]) {
                            helper.add(arr[j]);
                        }
                    }
                    System.out.println(arr[i] + "左边小的数：" + Arrays.toString(helper.toArray()));
                }
            }
            return smallSum;
        }
    }

    private static class MergeSort {
        public static int calcSmallSum(int[] arr) {
            if (arr == null || arr.length < 2) {
                return 0;
            }
            return recurse(arr, 0, arr.length - 1);
        }

        private static int recurse(int[] arr, int left, int right) {
            if (left == right) {
                return 0;
            }
            int middle = ((right - left) >> 1) + left;
            int leftSmallSum = recurse(arr, left, middle);
            int rightSmallSum = recurse(arr, middle + 1, right);
            int curSmallSum = merge(arr, left, right, middle);
            return leftSmallSum + rightSmallSum + curSmallSum;
        }

        private static int merge(int[] arr, int left, int right, int middle) {
            if (left == right) {
                return 0;
            }
            int smallSum = 0;
            int[] helper = new int[right - left + 1];
            int idx = 0;
            int leftIdx = left;
            int rightIdx = middle + 1;
            while (leftIdx <= middle && rightIdx <= right) {
                if (arr[leftIdx] < arr[rightIdx]) {
                    // arr[rightIdx, right]范围上的数都比arr[leftIdx]大
                    smallSum += (right - rightIdx + 1) * arr[leftIdx];
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
            return smallSum;
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(0, 100);
            int[] arr = new int[length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(0, 100);
            }
            int[] arr1 = arr.clone();
            int[] arr2 = arr.clone();
            int r1 = BruteForce.calcSmallSum(arr1);
            int r2 = MergeSort.calcSmallSum(arr2);
            if (r1 != r2) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish!");
    }
}
