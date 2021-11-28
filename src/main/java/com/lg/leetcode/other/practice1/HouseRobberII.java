package com.lg.leetcode.other.practice1;

import cn.hutool.core.util.RandomUtil;

/**
 * @author Xulg
 * Description: leetcode_213
 * Created in 2021-05-25 9:27
 */
class HouseRobberII {
    /*
     *  You are a professional robber planning to rob houses along a street.
     * Each house has a certain amount of money stashed. All houses at this
     * place are arranged in a circle. That means the first house is the neighbor
     * of the last one. Meanwhile, adjacent houses have a security system connected,
     * and it will automatically contact the police if two adjacent houses were broken
     * into on the same night.
     *
     *  Given an integer array nums representing the amount of money of each house,
     * return the maximum amount of money you can rob tonight without alerting
     * the police.
     *
     * Example 1:
     * Input: nums = [2,3,2]
     * Output: 3
     * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.
     *
     * Example 2:
     * Input: nums = [1,2,3,1]
     * Output: 4
     * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
     * Total amount you can rob = 1 + 3 = 4.
     *
     * Example 3:
     * Input: nums = [0]
     * Output: 0
     *
     * Constraints:
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 1000
     *---------------------------------------------------------------------------------------------------
     * nums是一个循环数组，即首尾相连的，解题思路：
     *  nums是一个首尾相连的数组[0, n]
     *      n位置抢了，0位置就不能抢
     *      0位置抢了，n位置就不能抢
     *  所以可以分成2中情形：
     *      nums[0, n-1]范围上抢劫，即不抢n位置，求出最大值r1
     *      nums[1, n]范围上抢劫，即不抢0位置，求出最大值r2
     *     求出两种情况下的最大值
     *          r = max(r1, r2);
     */

    public static int rob(int[] nums) {
        return 0;
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(0, 21)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(0, 101);
            }
            int r0 = 0;
            int r1 = 0;
            int r2 = 0;
            int r3 = 0;
            if (!(r0 == r1 && r1 == r2 && r2 == r3)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }
}
