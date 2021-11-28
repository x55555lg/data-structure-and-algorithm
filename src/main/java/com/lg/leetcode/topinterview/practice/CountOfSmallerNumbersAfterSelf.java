package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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

    private static class MergeSort2 {
        static int[] count;
        static int[] index;

        public static List<Integer> calcSmallSum(int[] arr) {
            if (arr == null || arr.length < 2) {
                return new ArrayList<>(0);
            }
            // count[i]表示i位置右边比我小的个数
            count = new int[arr.length];
            index = new int[arr.length];
            for (int i = 0; i < index.length; i++) {
                index[i] = i;
            }

            recurse(arr, 0, arr.length - 1);

            List<Integer> list = new LinkedList<>();
            for (int i = 0; i < count.length; i++) {
                list.add(count[i]);
            }
            return list;
        }

        private static void recurse(int[] arr, int left, int right) {
            if (left == right) {
                return;
            }
            int middle = ((right - left) >> 1) + left;
            recurse(arr, left, middle);
            recurse(arr, middle + 1, right);
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
                if (arr[leftIdx] < arr[rightIdx]) {
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

        private static void merge2(int[] nums, int left, int right, int middle) {
            int[] arr = new int[right - left + 1];
            for (int i = left; i <= right; i++) {
                arr[i - left] = nums[i];
            }
            int[] temp = new int[right - left + 1];
            for (int i = left; i <= right; i++) {
                temp[i - left] = index[i];
            }

            int p = left;
            int leftIdx = left;
            int rightIdx = middle + 1;
            int cnt = 0;
            while (leftIdx <= middle && rightIdx <= right) {
                if (arr[leftIdx - left] > arr[rightIdx - left]) {
                    cnt++;
                    index[p] = temp[rightIdx - left];
                    nums[p] = arr[rightIdx - left];
                    p++;
                    rightIdx++;
                } else {
                    index[p] = temp[leftIdx - left];
                    nums[p] = arr[leftIdx - left];
                    count[index[p]] += cnt;
                    p++;
                    leftIdx++;
                }
            }

            while (rightIdx <= right) {
                index[p] = temp[rightIdx - left];
                nums[p] = arr[rightIdx - left];
                p++;
                rightIdx++;
            }

            while (leftIdx <= middle) {
                index[p] = temp[leftIdx - left];
                nums[p] = arr[leftIdx - left];
                count[index[p]] += cnt;
                p++;
                leftIdx++;
            }
        }
    }

    public static void main(String[] args) {
        // TODO 2021/9/16 有问题的
        int[] arr = {5, 2, 6, 1};
        List<Integer> list = bruteForce(arr);
        System.out.println(list);
        list = countSmaller(arr);
        System.out.println(list);
    }

}
