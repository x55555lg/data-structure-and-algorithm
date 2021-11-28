package com.lg.leetcode.topinterview.practice1;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-02-23 10:58
 */
class FindFirstAndLastPositionOfElementInSortedArray {

    /*
     * Given an array of integers nums sorted in ascending order, find the starting
     * and ending position of a given target value.
     * If target is not found in the array, return [-1, -1].
     * Follow up: Could you write an algorithm with O(log n) runtime complexity?
     *
     * Example 1:
     * Input: nums = [5,7,7,8,8,10], target = 8
     * Output: [3,4]
     *
     * Example 2:
     * Input: nums = [5,7,7,8,8,10], target = 6
     * Output: [-1,-1]
     *
     * Example 3:
     * Input: nums = [], target = 0
     * Output: [-1,-1]
     *
     * Constraints:
     * 0 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     * nums is a non-decreasing array.
     * -109 <= target <= 109
     *--------------------------------------------------------------------------------------
     */

    public static int[] searchRange(int[] nums, int target) {
        return null;
    }

    public static void main(String[] args) {
        int[] range = searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println(Arrays.toString(range));

        range = searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6);
        System.out.println(Arrays.toString(range));

        range = searchRange(new int[]{5, 7, 7, 8, 8, 10}, 5);
        System.out.println(Arrays.toString(range));
    }

}
