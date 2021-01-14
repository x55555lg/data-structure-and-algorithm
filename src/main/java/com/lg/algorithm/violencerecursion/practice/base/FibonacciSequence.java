package com.lg.algorithm.violencerecursion.practice.base;

import cn.hutool.core.util.RandomUtil;

import java.util.HashSet;

/**
 * 斐波那契数列
 *
 * @author Xulg
 * Created in 2020-12-01 9:44
 */
@SuppressWarnings("DuplicatedCode")
class FibonacciSequence {

    /*
     * 暴力递归计算斐波那契数列
     * 1   1   2   3   5   8   13 ......
     * 公式：f(n) = f(n-1) + f(n-2);
     *       f(1) = 1;
     *       f(2) = 1;
     */

    /*
     * 矩阵幂解法
     * 时间复杂度O(logN)
     * 矩阵计算斐波那契数列公式：
     *  F(n)F(n-1) = |F(2)F(1)| * |1 1|^(n-2)
     *                            |1 0|
     */

    /**
     * 暴力递归解法
     * 时间复杂度：O(2^n)
     */
    public static int calcByViolence(int n) {
        assert n >= 0;
        // base case
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return calcByViolence(n - 1) + calcByViolence(n - 2);
    }

    /**
     * 动态规划-记忆化搜索
     * 时间复杂度：O(n)
     */
    public static int calcByDp1(int n) {
        assert n >= 0;
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[] dpTable = new int[n + 1];
        // base case
        dpTable[0] = 0;
        dpTable[1] = 1;
        dpTable[2] = 1;
        // 其余位置初始值都是-1表示还没有求取结果
        for (int i = 3; i <= n; i++) {
            dpTable[i] = -1;
        }
        return recurse(n, dpTable);
    }

    private static int recurse(int n, int[] dpTable) {
        if (dpTable[n] != -1) {
            return dpTable[n];
        }
        int r = recurse(n - 1, dpTable) + recurse(n - 2, dpTable);
        dpTable[n] = r;
        return r;
    }

    /**
     * 动态规划-DP表
     * 时间复杂度：O(n)
     */
    public static int calcByDp2(int n) {
        assert n >= 0;
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[] dpTable = new int[n + 1];
        // base case
        dpTable[0] = 0;
        dpTable[1] = 1;
        dpTable[2] = 1;
        // 从3开始算
        for (int i = 3; i <= n; i++) {
            dpTable[i] = dpTable[i - 1] + dpTable[i - 2];
        }
        // 最终结果
        return dpTable[n];
    }

    public static int calcByMatrix(int n) {
        return 0;
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 1000000;
        HashSet<Integer> already = new HashSet<>();
        for (int time = 0; time < times; time++) {
            // n的范围：[0, 50]
            int n = RandomUtil.randomInt(0, 51);
            if (!already.contains(n)) {
                int r1 = calcByViolence(n);
                int r2 = calcByDp1(n);
                int r3 = calcByDp2(n);
                if (!(r1 == r2 && r2 == r3)) {
                    System.out.println("Oops----->>>" + n);
                }
                already.add(n);
            }
        }
        System.out.println("finish!");
    }

}
