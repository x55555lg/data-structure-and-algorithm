package com.practice.dynamicprogramming;

import cn.hutool.core.util.RandomUtil;

import java.util.HashSet;

/**
 * @author Xulg
 * Description: 斐波那契数列
 * Created in 2021-05-19 10:19
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
            if (n == 1) {
                return 1;
            }
            if (n == 2) {
                return 1;
            }
            return calcFibonacciSequence(n - 1) + calcFibonacciSequence(n - 2);
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
            table[0] = 0;
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
                int r2 = DP.calcFibonacciSequence(n);
                if (!(r1 == r2)) {
                    System.out.println("Oops----->>>" + n);
                }
                already.add(n);
            }
        }
        System.out.println("finish!");
    }
}
