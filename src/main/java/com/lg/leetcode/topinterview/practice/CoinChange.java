package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-25 16:35
 */
@SuppressWarnings("ConstantConditions")
class CoinChange {

    /*
     * You are given coins of different denominations and a total amount of money amount.
     * Write a function to compute the fewest number of coins that you need to make up that amount.
     * If that amount of money cannot be made up by any combination of the coins, return -1.
     *
     * You may assume that you have an infinite number of each kind of coin.
     *
     * Example 1:
     * Input: coins = [1,2,5], amount = 11
     * Output: 3
     * Explanation: 11 = 5 + 5 + 1
     *
     * Example 2:
     * Input: coins = [2], amount = 3
     * Output: -1
     *
     * Example 3:
     * Input: coins = [1], amount = 0
     * Output: 0
     *
     * Example 4:
     * Input: coins = [1], amount = 1
     * Output: 1
     *
     * Example 5:
     * Input: coins = [1], amount = 2
     * Output: 2
     *
     * Constraints:
     *  1 <= coins.length <= 12
     *  1 <= coins[i] <= 231 - 1
     *  0 <= amount <= 104
     */

    public static int coinChange(int[] coins, int amount) {
        return DP.coinChange(coins, amount);
    }

    private static class BruteForce {
        public static int coinChange(int[] coins, int amount) {
            if (coins == null || amount < 0) {
                return -1;
            }
            return recurse(coins, 0, amount);
        }

        private static int recurse(int[] coins, int coinIdx, int restAmount) {
            if (restAmount < 0) {
                return -1;
            }
            if (coinIdx == coins.length) {
                return restAmount == 0 ? 0 : -1;
            }
            if (restAmount == 0) {
                return 0;
            }

            // 使用coins[coinIdx]硬币
            int yes = recurse(coins, coinIdx, restAmount - coins[coinIdx]);
            if (yes != -1) {
                // 算上当前用了的硬币
                yes = yes + 1;
            }

            // 不用coins[coinIdx]硬币
            int no = recurse(coins, coinIdx + 1, restAmount);

            int r;
            if (yes == -1 && no == -1) {
                r = -1;
            } else if (yes == -1 && no != -1) {
                r = no;
            } else if (yes != -1 && no == -1) {
                r = yes;
            } else {
                r = Math.min(yes, no);
            }

            // final result
            return r;
        }
    }

    private static class MnemonicSearch {

        public static int coinChange(int[] coins, int amount) {
            if (coins == null || amount < 0) {
                return -1;
            }
            Integer[][] table = new Integer[coins.length + 1][amount + 1];
            return recurse(coins, 0, amount, table);
        }

        private static int recurse(int[] coins, int coinIdx, int restAmount, Integer[][] table) {
            if (restAmount < 0) {
                return -1;
            }
            if (table[coinIdx][restAmount] != null) {
                // cache
                return table[coinIdx][restAmount];
            }
            if (coinIdx == coins.length) {
                int r = restAmount == 0 ? 0 : -1;
                table[coinIdx][restAmount] = r;
                return r;
            }
            if (restAmount == 0) {
                int r = 0;
                table[coinIdx][restAmount] = r;
                return r;
            }

            // 使用coins[coinIdx]硬币
            int yes = recurse(coins, coinIdx, restAmount - coins[coinIdx], table);
            if (yes != -1) {
                // 算上当前用了的硬币
                yes = yes + 1;
            }

            // 不用coins[coinIdx]硬币
            int no = recurse(coins, coinIdx + 1, restAmount, table);

            int r;
            if (yes == -1 && no == -1) {
                r = -1;
            } else if (yes == -1 && no != -1) {
                r = no;
            } else if (yes != -1 && no == -1) {
                r = yes;
            } else {
                r = Math.min(yes, no);
            }
            table[coinIdx][restAmount] = r;

            // final result
            return r;
        }

    }

    private static class DP {

        public static int coinChange(int[] coins, int amount) {
            if (coins == null || amount < 0) {
                return -1;
            }
            int[][] table = new int[coins.length + 1][amount + 1];
            // base case
            for (int i = 0; i <= coins.length; i++) {
                table[i][0] = 0;
            }
            for (int i = 1; i <= amount; i++) {
                table[coins.length][i] = -1;
            }

            // coins--; amount++
            for (int coinIdx = coins.length - 1; coinIdx >= 0; coinIdx--) {
                for (int restAmount = 1; restAmount <= amount; restAmount++) {
                    // 使用coins[coinIdx]硬币
                    int yes = (restAmount - coins[coinIdx] < 0) ? -1 : table[coinIdx][restAmount - coins[coinIdx]];
                    if (yes != -1) {
                        // 算上当前用了的硬币
                        yes = yes + 1;
                    }

                    // 不用coins[coinIdx]硬币
                    int no = table[coinIdx + 1][restAmount];

                    int r;
                    if (yes == -1 && no == -1) {
                        r = -1;
                    } else if (yes == -1 && no != -1) {
                        r = no;
                    } else if (yes != -1 && no == -1) {
                        r = yes;
                    } else {
                        r = Math.min(yes, no);
                    }

                    // final result
                    table[coinIdx][restAmount] = r;
                }
            }
            return table[0][amount];
        }

    }

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println(BruteForce.coinChange(coins, amount));
        System.out.println(MnemonicSearch.coinChange(coins, amount));
        System.out.println(DP.coinChange(coins, amount));
    }
}
