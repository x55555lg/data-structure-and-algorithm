package com.lg.leetcode.topinterview.practice;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-02-22 10:28
 */
public class TwoSum {

    /*
     * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     * You can return the answer in any order.
     *
     * Example 1:
     * Input: nums = [2,7,11,15], target = 9
     * Output: [0,1]
     * Output: Because nums[0] + nums[1] == 9, we return [0, 1].
     *
     * Example 2:
     * Input: nums = [3,2,4], target = 6
     * Output: [1,2]
     *
     * Example 3:
     * Input: nums = [3,3], target = 6
     * Output: [0,1]
     *
     * Constraints:
     * 2 <= nums.length <= 103
     * -109 <= nums[i] <= 109
     * -109 <= target <= 109
     * Only one valid answer exists.
     */

    public static int[] twoSum(int[] nums, int target) {
        return bestSolution(nums, target);
    }

    private static int[] bestSolution(int[] arr, int target) {
        // key为数，value为数在arr中的位置
        HashMap<Integer, Integer> map = new HashMap<>(16);
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            if (map.containsKey(target - num)) {
                return new int[]{i, map.get(target - num)};
            } else {
                map.put(num, i);
            }
        }
        return new int[]{-1, -1};
    }

    private static int[] bruteForce(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

}
