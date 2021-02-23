package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-22 9:52
 */
class MedianofTwoSortedArrays {

    /*
     * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
     * Follow up: The overall run time complexity should be O(log (m+n)).
     *
     * Example 1:
     * Input: nums1 = [1,3], nums2 = [2]
     * Output: 2.00000
     * Explanation: merged array = [1,2,3] and median is 2.
     *
     * Example 2:
     * Input: nums1 = [1,2], nums2 = [3,4]
     * Output: 2.50000
     * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
     *
     * Example 3:
     * Input: nums1 = [0,0], nums2 = [0,0]
     * Output: 0.00000
     *
     * Example 4:
     * Input: nums1 = [], nums2 = [1]
     * Output: 1.00000
     *
     * Example 5:
     * Input: nums1 = [2], nums2 = []
     * Output: 2.00000
     *
     * Constraints:
     * nums1.length == m
     * nums2.length == n
     * 0 <= m <= 1000
     * 0 <= n <= 1000
     * 1 <= m + n <= 2000
     * -106 <= nums1[i], nums2[i] <= 106
     *--------------------------------------------------------------------------------
     * 求取2个有序数组的中位数，如果是偶数个数就求两个中位数的平均值
     * 最优解：使用归并排序将num1和nums2合并，再求中位数
     */

    public static void main(String[] args) {
        double medianSortedArrays = findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4});
        System.out.println(medianSortedArrays);
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return solution(nums1, nums2);
    }

    private static double solution(int[] arr1, int[] arr2) {
        // use merge sort
        int[] arr = new int[arr1.length + arr2.length];
        int idx1 = 0, idx2 = 0, idx = 0;
        while (idx1 < arr1.length && idx2 < arr2.length) {
            if (arr1[idx1] <= arr2[idx2]) {
                arr[idx++] = arr1[idx1++];
            } else {
                arr[idx++] = arr2[idx2++];
            }
        }
        while (idx1 < arr1.length) {
            arr[idx++] = arr1[idx1++];
        }
        while (idx2 < arr2.length) {
            arr[idx++] = arr2[idx2++];
        }

        // get the median
        int middle = arr.length >> 1;
        if (arr.length % 2 == 0) {
            // 偶数个数取平均值
            return (double) (arr[middle - 1] + arr[middle]) / 2;
        } else {
            // 奇数个数取中间值
            return arr[middle];
        }
    }

}
