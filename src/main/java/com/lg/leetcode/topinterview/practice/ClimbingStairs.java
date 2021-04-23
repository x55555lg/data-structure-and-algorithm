package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-03-03 12:12
 */
@SuppressWarnings({"unused", "AlibabaClassNamingShouldBeCamel", "AlibabaUndefineMagicConstant"})
class ClimbingStairs {

    /*
     * You are climbing a staircase. It takes n steps to reach the top.
     * Each time you can either climb 1 or 2 steps.
     * In how many distinct ways can you climb to the top?
     *
     * Example 1:
     * Input: n = 2
     * Output: 2
     * Explanation: There are two ways to climb to the top.
     * 1. 1 step + 1 step
     * 2. 2 steps
     *
     * Example 2:
     * Input: n = 3
     * Output: 3
     * Explanation: There are three ways to climb to the top.
     * 1. 1 step + 1 step + 1 step
     * 2. 1 step + 2 steps
     * 3. 2 steps + 1 step
     *
     *
     * Constraints:
     *  1 <= n <= 45
     */

    public static int climbStairs(int n) {
        return BruteForce.climbStairs(n);
    }

    private static class BruteForce {

        public static int climbStairs(int n) {
            return recurse(n);
        }

        private static int recurse(int restStep) {
            if (restStep == 0) {
                return 0;
            }
            if (restStep == 1) {
                return 1;
            }
            if (restStep == 2) {
                return 2;
            }
            return recurse(restStep - 1) + recurse(restStep - 2);
        }

    }

    private static class MemorySearch {

        public static int climbStairs(int n) {
            int[] table = new int[n + 1];
            Arrays.fill(table, -1);
            return recurse(n, table);
        }

        private static int recurse(int restStep, int[] table) {
            if (restStep == 0) {
                return 0;
            }
            if (table[restStep] != -1) {
                return table[restStep];
            }
            if (restStep == 1) {
                table[restStep] = 1;
                return 1;
            }
            if (restStep == 2) {
                table[restStep] = 2;
                return 2;
            }
            int r = recurse(restStep - 1, table) + recurse(restStep - 2, table);
            table[restStep] = r;
            return r;
        }

    }

    private static class DP {

        public static int climbStairs(int n) {
            if (n <= 0) {
                return 0;
            }
            if (n == 1) {
                return 1;
            }
            if (n == 2) {
                return 2;
            }
            int[] table = new int[n + 1];
            // base case
            table[0] = 0;
            table[1] = 1;
            table[2] = 2;
            for (int restStep = 3; restStep <= n; restStep++) {
                table[restStep] = table[restStep - 1] + table[restStep - 2];
            }
            return table[n];
        }

    }

    public static void main(String[] args) {
        System.out.println(DP.climbStairs(1));
        System.out.println(DP.climbStairs(2));
        System.out.println(DP.climbStairs(3));
    }

}
