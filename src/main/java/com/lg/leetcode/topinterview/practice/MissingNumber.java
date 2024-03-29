package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-03-09 15:44
 */
class MissingNumber {

    /*
     * Given an array nums containing n distinct numbers in the range [0, n], return the only number
     * in the range that is missing from the array.
     * Follow up:
     *  Could you implement a solution using only O(1) extra space complexity and O(n) runtime complexity?
     *
     * Example 1:
     * Input: nums = [3,0,1]
     * Output: 2
     * Explanation:
     *  n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 2 is
     *  the missing number in the range since it does not appear in nums.
     *
     * Example 2:
     * Input: nums = [0,1]
     * Output: 2
     * Explanation: n = 2 since there are 2 numbers, so all numbers are in the range [0,2]. 2 is the missing number in the range since it does not appear in nums.
     *
     * Example 3:
     * Input: nums = [9,6,4,2,3,5,7,0,1]
     * Output: 8
     * Explanation:
     *  n = 9 since there are 9 numbers, so all numbers are in the range [0,9].
     *  8 is the missing number in the range since it does not appear in nums.
     *
     * Example 4:
     * Input: nums = [0]
     * Output: 1
     * Explanation:
     *  n = 1 since there is 1 number, so all numbers are in the range [0,1]. 1 is
     *  the missing number in the range since it does not appear in nums.
     *
     * Constraints:
     *  n == nums.length
     *  1 <= n <= 104
     *  0 <= nums[i] <= n
     *  All the numbers of nums are unique.
     */

    public static int missingNumber(int[] nums) {
        return Solution1.missingNumber(nums);
    }

    private static class Solution1 {

        public static int missingNumber(int[] arr) {
            // 0~n这些数字的累加和
            int totalSum = 0;
            for (int i = 0; i <= arr.length; i++) {
                totalSum = totalSum + i;
            }
            // arr中这些数字的累加和
            int sum = 0;
            for (int value : arr) {
                sum = sum + value;
            }
            // 差值即为缺失的数
            return totalSum - sum;
        }

    }
}
