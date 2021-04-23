package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-03-04 16:23
 */
@SuppressWarnings({"ManualArrayCopy", "SameParameterValue"})
class MergeSortedArray {
    /*
     * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
     * The number of elements initialized in nums1 and nums2 are m and n respectively. You may assume
     * that nums1 has a size equal to m + n such that it has enough space to hold additional elements
     * from nums2.
     *
     * Example 1:
     * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * Output: [1,2,2,3,5,6]
     *
     * Example 2:
     * Input: nums1 = [1], m = 1, nums2 = [], n = 0
     * Output: [1]
     *
     * Constraints:
     *  nums1.length == m + n
     *  nums2.length == n
     *  0 <= m, n <= 200
     *  1 <= m + n <= 200
     *  -109 <= nums1[i], nums2[i] <= 109
     */

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums1 == null || nums1.length == 0) {
            return;
        }
        if (m == 0) {
            for (int i = 0; i < nums1.length; i++) {
                nums1[i] = nums2[i];
            }
            return;
        }
        doMergeSort(nums1, 0, m - 1, nums2, 0, n - 1);
    }

    private static void doMergeSort(int[] arr1, int left1, int right1,
                                    int[] arr2, int left2, int right2) {
        int[] helper = new int[right1 - left1 + 1 + right2 - left2 + 1];
        int idx = 0;
        int leftIdx = left1;
        int rightIdx = left2;
        while (leftIdx <= right1 && rightIdx <= right2) {
            if (arr1[leftIdx] <= arr2[rightIdx]) {
                helper[idx++] = arr1[leftIdx++];
            } else {
                helper[idx++] = arr2[rightIdx++];
            }
        }
        while (leftIdx <= right1) {
            helper[idx++] = arr1[leftIdx++];
        }
        while (rightIdx <= right2) {
            helper[idx++] = arr2[rightIdx++];
        }
        for (int i = 0; i < helper.length; i++) {
            arr1[i] = helper[i];
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3;
        int[] nums2 = {2, 5, 6};
        int n = 3;
        merge(nums1, m, nums2, n);
        System.out.println(Arrays.toString(nums1));

        nums1 = new int[]{1};
        m = 1;
        nums2 = new int[]{};
        n = 0;
        merge(nums1, m, nums2, n);
        System.out.println(Arrays.toString(nums1));

        nums1 = new int[]{0};
        m = 0;
        nums2 = new int[]{1};
        n = 1;
        merge(nums1, m, nums2, n);
        System.out.println(Arrays.toString(nums1));

        nums1 = new int[]{4, 0, 0, 0, 0, 0};
        m = 1;
        nums2 = new int[]{1, 2, 3, 5, 6};
        n = 5;
        merge(nums1, m, nums2, n);
        System.out.println(Arrays.toString(nums1));
    }

}
