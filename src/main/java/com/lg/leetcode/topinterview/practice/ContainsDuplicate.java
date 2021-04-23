package com.lg.leetcode.topinterview.practice;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-03-09 10:59
 */
class ContainsDuplicate {

    /*
     * Given an integer array nums, return true if any value appears at least twice in the array,
     * and return false if every element is distinct.
     *
     * Example 1:
     * Input: nums = [1,2,3,1]
     * Output: true
     *
     * Example 2:
     * Input: nums = [1,2,3,4]
     * Output: false
     *
     * Example 3:
     * Input: nums = [1,1,1,3,3,4,3,2,4,2]
     * Output: true
     *
     * Constraints:
     *  1 <= nums.length <= 105
     *  -109 <= nums[i] <= 109
     */

    public static boolean containsDuplicate(int[] nums) {
        return false;
    }

    /**
     * Map计数器
     */
    private static class BruteForce {

        public static boolean containsDuplicate(int[] arr) {
            if (arr == null || arr.length == 0) {
                return false;
            }
            HashMap<Integer, Integer> countMap = new HashMap<>(arr.length * 4 / 3 + 1);
            for (int val : arr) {
                if (countMap.containsKey(val)) {
                    countMap.put(val, countMap.get(val) + 1);
                } else {
                    countMap.put(val, 1);
                }
                if (countMap.get(val) > 1) {
                    return true;
                }
            }
            for (int val : arr) {
                if (countMap.get(val) > 1) {
                    return true;
                }
            }
            return false;
        }

    }

}
