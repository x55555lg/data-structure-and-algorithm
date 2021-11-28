package com.practice2.dynamicprogramming;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Xulg
 * @since 2021-10-19 10:07
 * Description: 斐波那契数列
 */
class FibonacciSequence {

    /*
     * 暴力递归计算斐波那契数列
     * 1   1   2   3   5   8   13 ......
     * 公式：f(n) = f(n-1) + f(n-2);
     *       f(1) = 1;
     *       f(2) = 1;
     */

    private static class BruteForce {
        public static int calcFibonacciSequence(int n) {
            if (n <= 0) {
                return 0;
            }
            return recurse(n);
        }

        private static int recurse(int n) {
            if (n == 1) {
                return 1;
            }
            if (n == 2) {
                return 1;
            }
            return recurse(n - 1) + recurse(n - 2);
        }
    }

    private static class MemorySearch {
        public static int calcFibonacciSequence(int n) {
            if (n <= 0) {
                return 0;
            }
            int[] table = new int[n + 1];
            Arrays.fill(table, -1);
            return recurse(n, table);
        }

        private static int recurse(int n, int[] table) {
            if (table[n] != -1) {
                return table[n];
            }
            if (n == 1) {
                table[1] = 1;
                return 1;
            }
            if (n == 2) {
                table[2] = 1;
                return 1;
            }
            int val = recurse(n - 1, table) + recurse(n - 2, table);
            table[n] = val;
            return val;
        }
    }

    private static class DP {
        public static int calcFibonacciSequence(int n) {
            if (n <= 0) {
                return 0;
            }
            if (n == 1) {
                return 1;
            }
            if (n == 2) {
                return 1;
            }
            int[] table = new int[n + 1];
            table[1] = 1;
            table[2] = 1;
            for (int i = 3; i <= n; i++) {
                table[i] = table[i - 1] + table[i - 2];
            }
            return table[n];
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        HashSet<Integer> already = new HashSet<>();
        for (int time = 0; time < times; time++) {
            // n的范围：[0, 50]
            int n = RandomUtil.randomInt(0, 51);
            if (!already.contains(n)) {
                int r1 = BruteForce.calcFibonacciSequence(n);
                int r2 = MemorySearch.calcFibonacciSequence(n);
                int r3 = DP.calcFibonacciSequence(n);
                if (!(r1 == r2 && r2 == r3)) {
                    System.out.println("Oops----->>>" + n);
                }
                already.add(n);
            }
        }
        System.out.println("finish!");
    }
}
