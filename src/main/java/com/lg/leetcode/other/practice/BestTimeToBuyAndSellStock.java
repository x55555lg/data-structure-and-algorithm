package com.lg.leetcode.other.practice;

/**
 * @author Xulg
 * Description: leetcode_121
 * Created in 2021-05-25 14:57
 */
class BestTimeToBuyAndSellStock {

    /*
     *  You are given an array prices where prices[i] is the price of a given stock on the ith day.
     * You want to maximize your profit by choosing a single day to buy one stock and choosing a
     * different day in the future to sell that stock.
     *
     *  Return the maximum profit you can achieve from this transaction. If you cannot achieve any
     * profit, return 0.
     *
     * Example 1:
     * Input: prices = [7,1,5,3,6,4]
     * Output: 5
     * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
     * Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
     *
     * Example 2:
     * Input: prices = [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transactions are done and the max profit = 0.
     *
     * Constraints:
     * 1 <= prices.length <= 105
     * 0 <= prices[i] <= 104
     *------------------------------------------------------------------------------------------------------
     *思路：
     * 任意位置idx，求arr[idx+1, N)范围上的最大值 减去 arr[idx]：
     *      int money = max[idx+1, N) - arr[idx]
     * 遍历所有的位置idx，求差值最大的那个
     */

    public static int maxProfit(int[] prices) {
        return 0;
    }

    /**
     * time O(N^2)
     * space O(1)
     */
    private static class BruteForce {
        public static int maxProfit(int[] prices) {
            if (prices == null || prices.length == 0) {
                return 0;
            }
            if (prices.length == 1) {
                return 0;
            }
            int max = 0;
            for (int day = 0; day < prices.length; day++) {
                max = Math.max(max, recurse(day, prices));
            }
            return max;
        }

        private static int recurse(int day, int[] arr) {
            if (day == arr.length) {
                return 0;
            }
            int cost = arr[day];
            int maxMoney = 0;
            for (int futureDay = day + 1; futureDay < arr.length; futureDay++) {
                maxMoney = Math.max(maxMoney, arr[futureDay] - cost);
            }
            return maxMoney;
        }
    }

    /**
     * time O(N)
     * space O(N)
     */
    private static class BruteForce2 {
        public static int maxProfit(int[] prices) {
            if (prices == null || prices.length < 2) {
                return 0;
            }

            /*
             * [ 7, 1, 5, 3, 6, 4 ]
             *   0  1  2  3  4  5
             *   7  6  6  6  6  6
             * max[5, 5]上的最大值：4
             * max[4, 5]上的最大值：max(arr[4], max[5, 5]) = 6
             * max[3, 5]上的最大值：max(arr[3], max[4, 5]) = 6
             * max[2, 5]上的最大值：max(arr[2], max[3, 5]) = 6
             * max[1, 5]上的最大值：max(arr[1], max[2, 5]) = 6
             * max[0, 5]上的最大值：max(arr[0], max[1, 5]) = 7
             *
             * table[i] = m 表示[i, N)上的最大值是m
             */
            int[] table = new int[prices.length];
            // base case
            table[prices.length - 1] = prices[prices.length - 1];
            for (int i = table.length - 2; i >= 0; i--) {
                table[i] = Math.max(prices[i], table[i + 1]);
            }

            int maxProfit = 0;
            for (int day = 0; day < prices.length - 1; day++) {
                int maxPrice = table[day + 1];
                maxProfit = Math.max(maxProfit, maxPrice - prices[day]);
            }
            return maxProfit;
        }
    }

    /**
     * time O(N)
     * space O(1)
     */
    private static class BruteForce3 {
        public static int maxProfit(int[] prices) {
            if (prices == null || prices.length < 2) {
                return 0;
            }
            // 最小价格
            int minPrice = Integer.MAX_VALUE;
            int maxProfit = 0;
            for (int price : prices) {
                // 不断更新最小价格
                minPrice = Math.min(minPrice, price);
                // 不断更新取最大收益
                maxProfit = Math.max(price - minPrice, maxProfit);
            }
            return maxProfit;
        }
    }

    public static void main(String[] args) {
        System.out.println(BruteForce.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println(BruteForce2.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println(BruteForce3.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }
}
