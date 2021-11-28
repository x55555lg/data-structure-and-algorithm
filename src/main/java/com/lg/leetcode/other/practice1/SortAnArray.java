package com.lg.leetcode.other.practice1;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Description: leetcode_912
 * Created in 2021-05-26 22:44
 */
class SortAnArray {

    /*
     * Given an array of integers nums, sort the array in ascending order.
     *
     * Example 1:
     * Input: nums = [5,2,3,1]
     * Output: [1,2,3,5]
     *
     * Example 2:
     * Input: nums = [5,1,1,2,0,0]
     * Output: [0,0,1,1,2,5]
     *
     * Constraints:
     * 1 <= nums.length <= 5 * 104
     * -5 * 104 <= nums[i] <= 5 * 104
     */

    public static int[] sortArray(int[] nums) {
        Arrays.sort(nums);
        return nums;
    }

    private static class Compare {
        public static int[] sortArray(int[] nums) {
            Arrays.sort(nums);
            return nums;
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(0, 100)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt();
            }
            int[] r0 = Compare.sortArray(arr.clone());
            int[] r1 = null;
            int[] r2 = null;
            int[] r3 = null;
            int[] r4 = null;
            int[] r5 = null;
            int[] r6 = null;
            if (!Arrays.equals(r0, r1) || !Arrays.equals(r1, r2) || !Arrays.equals(r2, r3)
                    || !Arrays.equals(r3, r4) || !Arrays.equals(r4, r5) || !Arrays.equals(r5, r6)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }

}
