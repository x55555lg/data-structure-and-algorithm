package com.practice2.dynamicprogramming;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-10-18 14:55
 */
class ComposeCurrencyValue {

    /*
     * 给定数组arr，arr中所有的值都为正数且不重复
     * 每个值代表一种面值的货币，每种面值的货币可以使用任意张
     * 再给定一个整数aim，代表要找的钱数
     * 求组成aim的方法数
     */

    private static class BruteForce1 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            return recurse(0, aim, arr);
        }

        private static int recurse(int curIdx, int restAim, int[] coins) {
            if (curIdx == coins.length) {
                // 货币用完了
                return restAim == 0 ? 1 : 0;
            }
            if (restAim < 0) {
                return 0;
            }
            // base case
            if (restAim == 0) {
                // 凑钱成功
                return 1;
            }
            int count = 0;
            int curVal = coins[curIdx];
            // 当前货币选1张，2张，3张。。。
            for (int zhang = 0; zhang * curVal <= restAim; zhang++) {
                count += recurse(curIdx + 1, restAim - zhang * curVal, coins);
            }
            return count;
        }
    }

    private static class MemorySearch1 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            int[][] table = new int[arr.length + 1][aim + 1];
            for (int[] temp : table) {
                Arrays.fill(temp, -1);
            }
            return recurse(0, aim, arr, table);
        }

        private static int recurse(int curIdx, int restAim, int[] coins, int[][] table) {
            if (table[curIdx][restAim] != -1) {
                return table[curIdx][restAim];
            }
            // base case
            if (curIdx == coins.length) {
                // 货币用完了
                table[curIdx][restAim] = restAim == 0 ? 1 : 0;
                return table[curIdx][restAim];
            }
            int count = 0;
            int curVal = coins[curIdx];
            // 当前货币选1张，2张，3张。。。
            for (int zhang = 0; zhang * curVal <= restAim; zhang++) {
                count += recurse(curIdx + 1, restAim - zhang * curVal, coins, table);
            }
            table[curIdx][restAim] = count;
            return table[curIdx][restAim];
        }
    }

    private static class DP1 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            int[][] table = new int[arr.length + 1][aim + 1];
            // base case
            for (int curIdx = 0; curIdx <= arr.length; curIdx++) {
                table[curIdx][0] = 1;
            }
            for (int curIdx = arr.length - 1; curIdx >= 0; curIdx--) {
                for (int restAim = 1; restAim <= aim; restAim++) {
                    int count = 0;
                    int curVal = arr[curIdx];
                    // 当前货币选1张，2张，3张。。。
                    for (int zhang = 0; zhang * curVal <= restAim; zhang++) {
                        count += table[curIdx + 1][restAim - zhang * curVal];
                    }
                    table[curIdx][restAim] = count;
                }
            }
            return table[0][aim];
        }
    }

    private static class BruteForce2 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            return recurse(0, aim, arr);
        }

        private static int recurse(int curIdx, int restAim, int[] coins) {
            if (restAim < 0) {
                return 0;
            }
            if (curIdx == coins.length) {
                // 货币用完了
                return restAim == 0 ? 1 : 0;
            }
            // base case
            if (restAim == 0) {
                // 凑钱成功
                return 1;
            }
            // 继续选当前货币
            int yes = 0;
            if (restAim - coins[curIdx] >= 0) {
                yes = recurse(curIdx, restAim - coins[curIdx], coins);
            }
            // 选下一个货币
            int no = recurse(curIdx + 1, restAim, coins);
            return yes + no;
        }
    }

    private static class MemorySearch2 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            int[][] table = new int[arr.length + 1][aim + 1];
            for (int[] temp : table) {
                Arrays.fill(temp, -1);
            }
            return recurse(0, aim, arr, table);
        }

        private static int recurse(int curIdx, int restAim, int[] coins, int[][] table) {
            if (restAim < 0) {
                return 0;
            }
            // base case
            if (curIdx == coins.length) {
                // 货币用完了
                table[curIdx][restAim] = restAim == 0 ? 1 : 0;
                return table[curIdx][restAim];
            }
            // base case
            if (restAim == 0) {
                // 凑钱成功
                return 1;
            }
            // 继续选当前货币
            int yes = 0;
            if (restAim - coins[curIdx] >= 0) {
                yes = recurse(curIdx, restAim - coins[curIdx], coins, table);
            }
            // 选下一个货币
            int no = recurse(curIdx + 1, restAim, coins, table);
            table[curIdx][restAim] = yes + no;
            return table[curIdx][restAim];
        }
    }

    private static class DP2 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            int[][] table = new int[arr.length + 1][aim + 1];
            // base case
            for (int curIdx = 0; curIdx <= arr.length; curIdx++) {
                table[curIdx][0] = 1;
            }
            for (int curIdx = arr.length - 1; curIdx >= 0; curIdx--) {
                for (int restAim = 1; restAim <= aim; restAim++) {
                    // 继续选当前货币
                    int yes = 0;
                    if (restAim - arr[curIdx] >= 0) {
                        yes = table[curIdx][restAim - arr[curIdx]];
                    }
                    // 选下一个货币
                    int no = table[curIdx + 1][restAim];
                    table[curIdx][restAim] = yes + no;
                }
            }
            return table[0][aim];
        }
    }

    /* ****************************************************************************************************************/

    /* 对数器 */

    public static int ways(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process1(arr, 0, aim);
    }

    private static int process1(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            ways += process1(arr, index + 1, rest - (zhang * arr[index]));
        }
        return ways;
    }

    public static int ways4(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;// dp[N][1...aim] = 0;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                int faceVal = arr[index];
                if (rest - faceVal >= 0) {
                    dp[index][rest] += dp[index][rest - faceVal];
                }
            }
        }
        return dp[0][aim];
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 100000;
        for (int time = 0; time < times; time++) {
            int aim = RandomUtil.randomInt(1, 100);
            int[] array = new int[10];
            for (int i = 0; i < array.length; i++) {
                int val = RandomUtil.randomInt(1, 31);
                while (ArrayUtil.contains(array, val)) {
                    val = RandomUtil.randomInt(1, 101);
                }
                array[i] = val;
            }
            int count0 = ways(array, aim);
            int count1 = BruteForce1.compose(array, aim);
            int count2 = BruteForce2.compose(array, aim);
            int count3 = MemorySearch1.compose(array, aim);
            int count4 = MemorySearch2.compose(array, aim);
            int count5 = DP1.compose(array, aim);
            int count6 = DP2.compose(array, aim);
            int count7 = ways4(array, aim);
            if (!(count0 == count1 && count1 == count2
                    && count2 == count3 && count3 == count4
                    && count4 == count5 && count5 == count6
                    && count6 == count7)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finished!");
    }
}
