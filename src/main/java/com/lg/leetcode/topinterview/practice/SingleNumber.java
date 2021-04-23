package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-26 14:55
 */
class SingleNumber {

    /*
     * Given a non-empty array of integers nums, every element appears twice except for one.
     * Find that single one.
     * Follow up:
     *  Could you implement a solution with a linear runtime complexity and without using extra memory?
     *
     * Example 1:
     * Input: nums = [2,2,1]
     * Output: 1
     *
     * Example 2:
     * Input: nums = [4,1,2,1,2]
     * Output: 4
     *
     * Example 3:
     * Input: nums = [1]
     * Output: 1
     *
     * Constraints:
     *  1 <= nums.length <= 3 * 104
     *  -3 * 104 <= nums[i] <= 3 * 104
     *  Each element in the array appears twice except for one element which appears only once.
     */

    public static int singleNumber(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int num = 0;
        for (int i = 1; i < nums.length; i++) {
            num = num ^ nums[i];
        }
        return num;
    }

    public static void main(String[] args) {
        int[] arr = {4, 1, 2, 1, 2};
        System.out.println(singleNumber(arr));
    }
}
