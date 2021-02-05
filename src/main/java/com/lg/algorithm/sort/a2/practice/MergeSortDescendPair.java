package com.lg.algorithm.sort.a2.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * 使用归并排序求数组降序对个数
 *
 * @author Xulg
 * Created in 2020-10-26 14:08
 */
@SuppressWarnings({"DuplicatedCode", "SameParameterValue"})
class MergeSortDescendPair {

    /*
     * 在一个数组中，只要前面的数比后面的大，就叫做降序对，例如
     *  数组：{3, 1, 7, 0, 2}中，有这些降序对：
     *      3   1
     *      3   0
     *      3   2
     *      1   0
     *      7   0
     *      7   2
     */

    private static final boolean DEBUG = false;

    /**
     * 暴力解
     * O(n^2)
     */
    public static int countByViolence(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int count = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (i == 0) {
                continue;
            }
            // [0, i-1]上统计
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[i]) {
                    count++;
                    if (DEBUG) {
                        System.out.println(arr[j] + ", " + arr[i]);
                    }
                }
            }
        }
        return count;
    }

    /**
     * 使用归并排序解
     * O(N*logN)
     */
    public static int countByMergeSort(int[] arr) {
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
        int leftCount = mergeSort0(arr, left, mid);
        int rightCount = mergeSort0(arr, mid + 1, right);
        int currentCount = merge(arr, left, right, mid);
        return leftCount + rightCount + currentCount;
    }

    private static int mergeSort1(int[] arr, int left, int right) {
        int result = 0;
        int mergeSize = 1;
        while (mergeSize <= right) {
            int leftIdx = left;
            while (leftIdx <= right) {
                int midIdx = leftIdx + mergeSize - 1;
                if (midIdx > right) {
                    break;
                }
                int rightIdx = Math.min(midIdx + mergeSize, right);
                int count = merge(arr, leftIdx, rightIdx, midIdx);
                result += count;
                leftIdx = rightIdx + 1;
            }
            if (mergeSize > right / 2) {
                break;
            }
            mergeSize = mergeSize << 1;
        }
        return result;
    }

    private static int merge(int[] arr, int left, int right, int mid) {
        if (left == right) {
            return 0;
        }

        int count = 0;
        int[] helper = new int[right - left + 1];

        int idx = 0;
        int leftIdx = left;
        int rightIdx = mid + 1;
        while (leftIdx <= mid && rightIdx <= right) {
            if (arr[leftIdx] <= arr[rightIdx]) {
                helper[idx] = arr[leftIdx++];
            } else {
                // 左边大于右边，产生降序对
                // 在arr[leftIdx, mid]范围上有这么多个数是大于arr[rightIdx]的
                count += mid - leftIdx + 1;
                if (DEBUG) {
                    for (int i = leftIdx; i <= mid; i++) {
                        System.out.println(arr[i] + ", " + arr[rightIdx]);
                    }
                }
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

        //noinspection ManualArrayCopy
        for (int i = 0; i < helper.length; i++) {
            arr[left + i] = helper[i];
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countByViolence(new int[]{3, 1, 7, 0, 2}));
        System.out.println(countByViolence(new int[]{3, 3, 3}));

        int[] array = {3, 1, 7, 0, 2};
        System.out.println(countByMergeSort(array));
        System.out.println(Arrays.toString(array));

        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(0, 100);
            int[] arr = new int[length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(0, 100);
            }
            int[] arr1 = arr.clone();
            int[] arr2 = arr.clone();
            int r1 = countByViolence(arr1);
            int r2 = countByMergeSort(arr2);
            if (r1 != r2) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish!");
    }

}
