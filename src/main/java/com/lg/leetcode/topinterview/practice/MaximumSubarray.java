package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-03-05 14:55
 */
class MaximumSubarray {

    /*
     * Given an integer array nums, find the contiguous subarray (containing at least one number)
     * which has the largest sum and return its sum.
     *
     * Example 1:
     * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
     * Output: 6
     * Explanation: [4,-1,2,1] has the largest sum = 6.
     *
     * Example 2:
     * Input: nums = [1]
     * Output: 1
     *
     * Example 3:
     * Input: nums = [0]
     * Output: 0
     *
     * Example 4:
     * Input: nums = [-1]
     * Output: -1
     *
     * Example 5:
     * Input: nums = [-100000]
     * Output: -100000
     *
     * Constraints:
     *  1 <= nums.length <= 3 * 104
     *  -105 <= nums[i] <= 105
     *
     * Follow up:
     *  If you have figured out the O(n) solution, try coding another solution using
     *  the divide and conquer approach, which is more subtle.
     */

    public static int maxSubArray(int[] nums) {
        return 0;
    }

    /**
     * 暴力解，枚举每一个子数组
     */
    private static class BruteForce {

        public static int maxSubArray(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            int maxSum = Integer.MIN_VALUE;
            for (int i = 0; i < arr.length; i++) {
                for (int j = i; j < arr.length; j++) {
                    // sub array [i, j]
                    int sum = 0;
                    for (int k = i; k <= j; k++) {
                        sum += arr[k];
                    }
                    maxSum = Math.max(maxSum, sum);
                }
            }
            return maxSum;
        }

    }

    private static class Solution {

        public static int maxSubArray(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            int left = 0, right = 0;
            int maxSum = arr[0];
            for (int idx = 1; idx < arr.length; idx++) {
                // 左边界左移
            }
            return maxSum;
        }

    }

    public static void main(String[] args) {
        int[] arr = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(BruteForce.maxSubArray(arr));
    }

}
