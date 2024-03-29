package com.lg.leetcode.other.practice1;

/**
 * @author Xulg
 * Description: leetcode_122
 * Created in 2021-05-25 16:28
 */
class BestTimeToBuyAndSellStockII {

    /*
     *  You are given an array prices where prices[i] is the price of a given stock on the ith day.
     *
     *  Find the maximum profit you can achieve. You may complete as many transactions as you like
     * (i.e., buy one and sell one share of the stock multiple times).
     *
     *  Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the
     * stock before you buy again).
     *
     * Example 1:
     * Input: prices = [7,1,5,3,6,4]
     * Output: 7
     * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
     * Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
     *
     * Example 2:
     * Input: prices = [1,2,3,4,5]
     * Output: 4
     * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
     * Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are engaging
     * multiple transactions at the same time. You must sell before buying again.
     *
     * Example 3:
     * Input: prices = [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transaction is done, i.e., max profit = 0.
     *
     * Constraints:
     * 1 <= prices.length <= 3 * 104
     * 0 <= prices[i] <= 104
     *------------------------------------------------------------------------------------------------------------------
     * 1、初始化收益为0
     * 2、从左到右遍历，发现当前元素比刚遍历的元素大，即可取得收益，累加结果即为最大收益
     *
     *  这道题由于可以无限次买入和卖出。我们都知道炒股想挣钱当然是低价买入高价抛出，
     * 那么这里我们只需要从第二天开始，如果当前价格比之前价格高，则把差值加入利润中，
     * 因为我们可以昨天买入，今日卖出，若明日价更高的话，还可以今日买入，明日再抛出。
     * 以此类推，遍历完整个数组后即可求得最大利润。
     */

    public static int maxProfit(int[] prices) {
        return 0;
    }
}
