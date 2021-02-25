package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Xulg
 * Created in 2021-02-25 10:03
 */
@SuppressWarnings("ManualArrayCopy")
class CountOfSmallerNumbersAfterSelf {

    /*
     * You are given an integer array nums and you have to return a new counts array. The counts array has the
     * property where counts[i] is the number of smaller elements to the right of nums[i].
     *
     * Example 1:
     * Input: nums = [5,2,6,1]
     * Output: [2,1,1,0]
     * Explanation:
     *  To the right of 5 there are 2 smaller elements (2 and 1).
     *  To the right of 2 there is only 1 smaller element (1).
     *  To the right of 6 there is 1 smaller element (1).
     *  To the right of 1 there is 0 smaller element.
     *
     * Example 2:
     * Input: nums = [-1]
     * Output: [0]
     *
     * Example 3:
     * Input: nums = [-1,-1]
     * Output: [0,0]
     *
     * Constraints:
     *  1 <= nums.length <= 105
     *  -104 <= nums[i] <= 104
     *--------------------------------------------------------------------------------
     * 求数组中的每个位置，在它的后面比它小的数有几个
     * 归并排序思想
     */

    public static List<Integer> countSmaller(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>(0);
        }
        Integer[] result = new Integer[nums.length];
        Arrays.fill(result, 0);
        mergeSort(nums, 0, nums.length - 1, result);
        return new ArrayList<>(Arrays.asList(result));
    }

    private static void mergeSort(int[] arr, int left, int right, Integer[] result) {
        if (left == right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        mergeSort(arr, left, mid, result);
        mergeSort(arr, mid + 1, right, result);
        merge(arr, left, right, mid, result);
    }

    private static void merge(int[] arr, int left, int right, int mid, Integer[] result) {
        if (left == right) {
            return;
        }
        int[] helper = new int[right - left + 1];
        int idx = 0;
        int leftIdx = left;
        int rightIdx = mid + 1;
        while (leftIdx <= mid && rightIdx <= right) {
            if (arr[leftIdx] > arr[rightIdx]) {
                // 在右数组上有(right - rightIdx + 1)个数是大于array[leftIdx]的
                helper[idx++] = arr[leftIdx++];
                result[leftIdx] = result[leftIdx] + (right - rightIdx + 1);
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

    /**
     * 暴力解
     * O(N^2)
     */
    private static List<Integer> bruteForce(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new ArrayList<>(0);
        }
        ArrayList<Integer> helper = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int count = 0;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    count++;
                }
            }
            helper.add(i, count);
        }
        return helper;
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 6, 1};
        List<Integer> list = bruteForce(arr);
        System.out.println(list);
        list = countSmaller(arr);
        System.out.println(list);
    }

}
