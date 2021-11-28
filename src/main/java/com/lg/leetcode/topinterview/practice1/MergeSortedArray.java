package com.lg.leetcode.topinterview.practice1;

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
