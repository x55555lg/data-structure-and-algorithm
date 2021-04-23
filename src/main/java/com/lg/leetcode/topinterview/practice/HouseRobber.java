package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-02-26 16:53
 */
class HouseRobber {

    /*
     *  You are a professional robber planning to rob houses along a street. Each house has a certain amount
     * of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses
     * have security system connected and it will automatically contact the police if two adjacent houses were
     * broken into on the same night.
     *  Given a list of non-negative integers representing the amount of money of each house, determine the maximum
     * amount of money you can rob tonight without alerting the police.
     *
     * Example 1:
     * Input: nums = [1,2,3,1]
     * Output: 4
     * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
     *              Total amount you can rob = 1 + 3 = 4.
     *
     * Example 2:
     * Input: nums = [2,7,9,3,1]
     * Output: 12
     * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
     *              Total amount you can rob = 2 + 9 + 1 = 12.
     *
     * Constraints:
     *  0 <= nums.length <= 100
     *  0 <= nums[i] <= 400
     *------------------------------------------------------------------------------------------
     * 你是一个职业劫匪，打算抢劫沿街的房屋。每间房子都有一定数量的钱，阻止你抢劫每间房子的唯一限
     * 制是相邻的房子都有连接的安全系统，如果两间相邻的房子在同一天晚上被闯入，它会自动联系警察。
     * 给出一个非负整数列表，代表每间房子的钱数，确定你今晚在不报警的情况下可以抢劫的最大金额。
     */

    public static int rob(int[] nums) {
        return DP.rob(nums);
    }

    /**
     * 暴力递归
     */
    private static class BruteForce {

        public static int rob(int[] nums) {
            return recurse(nums, 0);
        }

        private static int recurse(int[] houses, int curIdx) {
            if (curIdx >= houses.length) {
                // 房子抢完了
                return 0;
            }

            // 当前房子抢了
            int yes = recurse(houses, curIdx + 2);
            yes = yes + houses[curIdx];

            // 当前房子不抢
            int no = recurse(houses, curIdx + 1);

            return Math.max(yes, no);
        }

    }

    /**
     * 记忆化搜索
     */
    private static class MnemonicSearch {

        public static int rob(int[] nums) {
            int[] table = new int[nums.length];
            Arrays.fill(table, -1);
            return recurse(nums, 0, table);
        }

        private static int recurse(int[] houses, int curIdx, int[] table) {
            if (curIdx >= houses.length) {
                // 房子抢完了
                return 0;
            }

            if (table[curIdx] != -1) {
                return table[curIdx];
            }

            // 当前房子抢了
            int yes = recurse(houses, curIdx + 2, table);
            yes = yes + houses[curIdx];

            // 当前房子不抢
            int no = recurse(houses, curIdx + 1, table);

            // 取最大值
            int max = Math.max(yes, no);

            table[curIdx] = max;
            return max;
        }

    }

    /**
     * 动态规划表
     */
    private static class DP {

        public static int rob(int[] houses) {
            int[] table = new int[houses.length + 1];
            // base case
            table[houses.length] = 0;
            for (int curIdx = houses.length - 1; curIdx >= 0; curIdx--) {
                // 当前房子抢了
                int yes = (curIdx + 2 >= houses.length) ? 0 : table[curIdx + 2];
                yes = yes + +houses[curIdx];
                // 当前房子不抢
                int no = table[curIdx + 1];
                // 取最大值
                table[curIdx] = Math.max(yes, no);
            }
            return table[0];
        }

    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1};
        int rob = BruteForce.rob(nums);
        System.out.println(rob);

        nums = new int[]{2, 7, 9, 3, 1};
        rob = MnemonicSearch.rob(nums);
        System.out.println(rob);

        rob = DP.rob(nums);
        System.out.println(rob);
    }

}
