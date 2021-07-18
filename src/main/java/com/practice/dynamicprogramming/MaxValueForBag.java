package com.practice.dynamicprogramming;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Description: 背包问题
 * Created in 2021-05-19 10:52
 */
class MaxValueForBag {

    /*
     * 背包问题
     *  这里能不能重复装同一个物品？假设不行吧
     * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表i号物品的重量和价值。
     * 给定一个正数bag，表示一个载重bag的袋子，你装的物品不能超过这个重量。返回你能装下最多的价值
     * 是多少?
     */

    /* ****************************************************************************************************************/

    private static class BruteForce {
        public static int getMaxValue(int[] weights, int[] values, int bag) {
            if (weights == null || weights.length == 0 || values == null || values.length == 0 || bag <= 0) {
                return 0;
            }
            assert weights.length == values.length;
            return recurse(0, bag, weights, values);
        }

        private static int recurse(int curIdx, int restBag, int[] weights, int[] values) {
            if (restBag < 0) {
                // 无效的选择
                return -1;
            }
            if (restBag == 0) {
                // 背包没空间了，能返回的最大价值为0
                return 0;
            }
            if (curIdx == weights.length) {
                // 物品选完了，能返回的最大价值为0
                return 0;
            }

            int nextMax = recurse(curIdx + 1, restBag - weights[curIdx], weights, values);
            int yes = (nextMax == -1) ? 0 : nextMax + values[curIdx];

            int no = recurse(curIdx + 1, restBag, weights, values);
            return Math.max(yes, no);
        }
    }

    private static class Compare {
        public static int getMaxValue(int[] w, int[] v, int bag) {
            int N = w.length;
            int[][] dp = new int[N + 1][bag + 1];
            for (int index = N - 1; index >= 0; index--) {
                for (int rest = 1; rest <= bag; rest++) {
                    dp[index][rest] = dp[index + 1][rest];
                    if (rest >= w[index]) {
                        dp[index][rest] = Math.max(dp[index][rest], v[index] + dp[index + 1][rest - w[index]]);
                    }
                }
            }
            return dp[0][bag];
        }
    }

    private static class MemorySearch {
        public static int getMaxValue(int[] weights, int[] values, int bag) {
            if (weights == null || weights.length == 0 || values == null || values.length == 0 || bag <= 0) {
                return 0;
            }
            assert weights.length == values.length;
            int[][] table = new int[values.length + 1][bag + 1];
            for (int i = 0; i <= values.length; i++) {
                for (int j = 0; j <= bag; j++) {
                    table[i][j] = -1;
                }
            }
            return recurse(0, bag, weights, values, table);
        }

        private static int recurse(int curIdx, int restBag, int[] weights, int[] values, int[][] table) {
            if (restBag < 0) {
                // 无效的选择
                return -1;
            }
            if (restBag == 0) {
                // 背包没空间了，能返回的最大价值为0
                return 0;
            }
            if (curIdx == weights.length) {
                // 物品选完了，能返回的最大价值为0
                return 0;
            }

            if (table[curIdx][restBag] != -1) {
                return table[curIdx][restBag];
            }

            int nextMax = recurse(curIdx + 1, restBag - weights[curIdx], weights, values, table);
            int yes = (nextMax == -1) ? 0 : nextMax + values[curIdx];

            int no = recurse(curIdx + 1, restBag, weights, values, table);

            int max = Math.max(yes, no);
            table[curIdx][restBag] = max;
            return max;
        }
    }

    private static class DP {
        public static int getMaxValue(int[] weights, int[] values, int bag) {
            if (weights == null || weights.length == 0 || values == null || values.length == 0 || bag <= 0) {
                return 0;
            }
            assert weights.length == values.length;
            int[][] table = new int[weights.length + 1][bag + 1];
            // base case
            for (int restBag = 0; restBag <= bag; restBag++) {
                table[weights.length][restBag] = 0;
            }
            // base case
            for (int curIdx = 0; curIdx <= weights.length; curIdx++) {
                table[curIdx][0] = 0;
            }
            for (int curIdx = weights.length - 1; curIdx >= 0; curIdx--) {
                for (int restBag = 1; restBag <= bag; restBag++) {
                    int yes = restBag - weights[curIdx] < 0 ? 0 : values[curIdx] + table[curIdx + 1][restBag - weights[curIdx]];
                    int no = table[curIdx + 1][restBag];
                    table[curIdx][restBag] = Math.max(yes, no);
                }
            }
            return table[0][bag];
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 1000000;
        int maxLength = 10;
        for (int time = 0; time < times; time++) {
            int bagMaxLoad = RandomUtil.randomInt(1, 200);
            int length = RandomUtil.randomInt(1, maxLength + 1);
            int[] weights = new int[length];
            int[] values = new int[length];
            for (int i = 0; i < length; i++) {
                int weight = RandomUtil.randomInt(1, 101);
                int value = RandomUtil.randomInt(1, 101);
                weights[i] = weight;
                values[i] = value;
            }
            int max1 = Compare.getMaxValue(weights, values, bagMaxLoad);
            int max2 = BruteForce.getMaxValue(weights, values, bagMaxLoad);
            int max3 = DP.getMaxValue(weights, values, bagMaxLoad);
            int max4 = MemorySearch.getMaxValue(weights, values, bagMaxLoad);
            if (!(max1 == max2 && max2 == max3 && max3 == max4)) {
                System.out.println("Oops!=======weights=" + Arrays.toString(weights)
                        + "----values=" + Arrays.toString(values) + "----bagMaxLoad=" + bagMaxLoad);
            }
        }
        System.out.println("finish!!!");
    }
}
