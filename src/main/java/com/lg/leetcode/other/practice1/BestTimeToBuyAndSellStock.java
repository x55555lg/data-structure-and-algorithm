package com.lg.leetcode.other.practice1;

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

    public static void main(String[] args) {
    }
}
