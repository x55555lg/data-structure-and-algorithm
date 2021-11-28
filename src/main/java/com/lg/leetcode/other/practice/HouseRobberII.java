package com.lg.leetcode.other.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

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
        return DP.rob(nums);
    }

    /**
     * 垃圾暴力解法
     */
    private static class BruteForce {
        public static int rob(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            if (arr.length == 1) {
                return arr[0];
            }
            return recurse(0, arr, new int[arr.length]);
        }

        /**
         * [0, curIdx)已抢，求[curIdx, N)可以抢的最大金额
         *
         * @param curIdx     当前到达的屋子
         * @param arr        屋子数组
         * @param robRecords 记录哪些屋子抢了，哪些没抢
         * @return 最大抢劫金额
         */
        private static int recurse(int curIdx, int[] arr, int[] robRecords) {
            // base case
            if (curIdx >= arr.length) {
                return 0;
            }
            if (curIdx == arr.length - 1) {
                // 看最后一个屋能不能抢
                return robRecords[0] == 1 || (curIdx - 1 > 0 && robRecords[curIdx - 1] == 1) ? 0 : arr[curIdx];
            }

            // 抢了当前屋子，去下下个屋子
            robRecords[curIdx] = 1;
            int yes = arr[curIdx] + recurse(curIdx + 2, arr, robRecords);
            robRecords[curIdx] = 0;

            // 不抢当前屋子去下一个位置的屋子
            int no = recurse(curIdx + 1, arr, robRecords);

            // 最大值
            return Math.max(yes, no);
        }
    }

    /**
     * 可动态规划的暴力解法
     */
    private static class BruteForce2 {
        public static int rob(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            if (arr.length == 1) {
                return arr[0];
            }
            // [0, n-2)
            int r1 = recurse(0, arr.length - 1, arr);
            // [1, n-1)
            int r2 = recurse(1, arr.length, arr);
            return Math.max(r1, r2);
        }

        private static int recurse(int curIdx, int limit, int[] arr) {
            if (curIdx >= limit) {
                // 房子抢完了
                return 0;
            }
            // 抢了当前屋子，去下下个屋子
            int yes = arr[curIdx] + recurse(curIdx + 2, limit, arr);
            // 不抢当前屋子去下一个位置的屋子
            int no = recurse(curIdx + 1, limit, arr);
            // 最大值
            return Math.max(yes, no);
        }
    }

    private static class MemorySearch {
        public static int rob(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            if (arr.length == 1) {
                return arr[0];
            }
            int[] table = new int[arr.length];
            // [0, n-2)
            Arrays.fill(table, -1);
            int r1 = recurse(0, arr.length - 1, arr, table);
            // [1, n-1)
            Arrays.fill(table, -1);
            int r2 = recurse(1, arr.length, arr, table);
            return Math.max(r1, r2);
        }

        private static int recurse(int curIdx, int limit, int[] arr, int[] table) {
            if (curIdx >= limit) {
                // 房子抢完了
                return 0;
            }
            if (table[curIdx] != -1) {
                return table[curIdx];
            }
            // 抢了当前屋子，去下下个屋子
            int yes = arr[curIdx] + recurse(curIdx + 2, limit, arr, table);
            // 不抢当前屋子去下一个位置的屋子
            int no = recurse(curIdx + 1, limit, arr, table);
            // 最大值
            int r = Math.max(yes, no);
            table[curIdx] = r;
            return r;
        }
    }

    private static class DP {
        public static int rob(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            if (arr.length == 1) {
                return arr[0];
            }
            int r1 = dp(0, arr.length - 1, arr);
            int r2 = dp(1, arr.length, arr);
            return Math.max(r1, r2);
        }

        private static int dp(int startIdx, int limit, int[] arr) {
            int[] table = new int[arr.length];
            for (int curIdx = limit - 1; curIdx >= startIdx; curIdx--) {
                // 抢了当前屋子，去下下个屋子
                int yes = arr[curIdx];
                if (curIdx + 2 < limit) {
                    yes = yes + table[curIdx + 2];
                }
                // 不抢当前屋子去下一个位置的屋子
                int no = 0;
                if (curIdx + 1 < limit) {
                    no = no + table[curIdx + 1];
                }
                table[curIdx] = Math.max(yes, no);
            }
            return table[startIdx];
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(0, 21)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(0, 101);
            }
            int r0 = BruteForce.rob(arr.clone());
            int r1 = BruteForce2.rob(arr.clone());
            int r2 = MemorySearch.rob(arr.clone());
            int r3 = DP.rob(arr.clone());
            if (!(r0 == r1 && r1 == r2 && r2 == r3)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }
}
