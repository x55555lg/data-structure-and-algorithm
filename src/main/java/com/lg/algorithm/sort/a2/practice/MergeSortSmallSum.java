package com.lg.algorithm.sort.a2.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 使用归并排序求数组小和
 *
 * @author Xulg
 * Created in 2020-10-26 14:08
 */
@SuppressWarnings({"ManualArrayCopy", "DuplicatedCode", "SameParameterValue"})
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

    private static final boolean DEBUG = false;

    /**
     * 暴力解求数组的小和
     * O(n^2)
     */
    public static int calcSmallSumOfArrayByViolence(int[] arr) {
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
            if (DEBUG) {
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

    /**
     * 归并排序解求数组的小和
     * O(N*logN)
     */
    public static int calcSmallSumOfArrayByMergeSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return mergeSort1(arr, 0, arr.length - 1);
    }

    private static int mergeSort0(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        int mid = (right - left) / 2 + left;
        int leftSmallSum = mergeSort0(arr, left, mid);
        int rightSmallSum = mergeSort0(arr, mid + 1, right);
        int currentSmallSum = merge(arr, left, right, mid);
        return currentSmallSum + leftSmallSum + rightSmallSum;
    }

    private static int mergeSort1(int[] arr, int left, int right) {
        int sum = 0;
        int mergeSize = 1;
        while (mergeSize <= right) {
            int leftIdx = left;
            while (leftIdx <= right) {
                int midIdx = leftIdx + mergeSize - 1;
                if (midIdx > right) {
                    break;
                }
                int rightIdx = Math.min(midIdx + mergeSize, right);
                sum += merge(arr, leftIdx, rightIdx, midIdx);
                leftIdx = rightIdx + 1;
            }
            if (mergeSize > right / 2) {
                break;
            }
            mergeSize = mergeSize * 2;
        }
        return sum;
    }

    private static int merge(int[] arr, int left, int right, int mid) {
        if (left == right) {
            return 0;
        }
        int smallSum = 0;
        int[] helper = new int[right - left + 1];
        int idx = 0;
        int leftIdx = left;
        int rightIdx = mid + 1;
        while (leftIdx <= mid && rightIdx <= right) {
            if (arr[leftIdx] < arr[rightIdx]) {
                // 左边的数小于右边
                // 在arr[rightIdx, right]范围上的数都是大于arr[leftIdx]的
                // 在右边数组有(right - rightIdx + 1)个数比arr[leftIdx]大
                smallSum += (right - rightIdx + 1) * arr[leftIdx];
                helper[idx] = arr[leftIdx++];
            } else {
                helper[idx] = arr[rightIdx++];
            }
            idx++;
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
        return smallSum;
    }

    public static void main(String[] args) {
        System.out.println(calcSmallSumOfArrayByViolence(new int[]{1, 3, 4, 2, 5}));
        System.out.println(calcSmallSumOfArrayByMergeSort(new int[]{1, 3, 4, 2, 5}));

        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(0, 100);
            int[] arr = new int[length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(0, 100);
            }
            int[] arr1 = arr.clone();
            int[] arr2 = arr.clone();
            int r1 = calcSmallSumOfArrayByViolence(arr1);
            int r2 = calcSmallSumOfArrayByMergeSort(arr2);
            if (r1 != r2) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish!");
    }

}
