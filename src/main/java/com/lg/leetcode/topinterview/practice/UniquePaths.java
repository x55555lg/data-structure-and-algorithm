package com.lg.leetcode.topinterview.practice;

import cn.hutool.core.lang.Assert;

/**
 * @author Xulg
 * Created in 2021-03-03 16:33
 */
@SuppressWarnings({"ExplicitArrayFilling", "AlibabaClassNamingShouldBeCamel"})
class UniquePaths {

    /*
     * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
     * The robot can only move either down or right at any point in time. The robot is trying to reach
     * the bottom-right corner of the grid (marked 'Finish' in the diagram below).
     * How many possible unique paths are there?
     *
     * Example 1:
     * Input: m = 3, n = 7
     * Output: 28
     *
     * Example 2:
     * Input: m = 3, n = 2
     * Output: 3
     * Explanation:
     *  From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
     *      1. Right -> Down -> Down
     *      2. Down -> Down -> Right
     *      3. Down -> Right -> Down
     *
     * Example 3:
     * Input: m = 7, n = 3
     * Output: 28
     *
     * Example 4:
     * Input: m = 3, n = 3
     * Output: 6
     *
     * Constraints:
     *  1 <= m, n <= 100
     *  It's guaranteed that the answer will be less than or equal to 2 * 109.
     *-------------------------------------------------------------------------------------------------
     * 机器人在一个m*n的网格的最左上角(开始位置(0, n-1))，机器人在任意位置只能向下或向右移动，问机器人想走到网格
     * 的最右下角(m-1, 0)，有几种走法？
     */

    public static int uniquePaths(int m, int n) {
        return 0;
    }

    private static class BruteForce {

        public static int uniquePaths(int m, int n) {
            if (m <= 0 || n <= 0) {
                return 0;
            }
            return recurse(m - 1, n - 1, 0, n - 1);
        }

        private static int recurse(int positionM, int positionN, int curM, int curN) {
            /*
             * 网格：m = 5, n = 4
             *      start (0, 3)
             *      dest  (4, 0)
             * 3
             *
             * 2
             *
             * 1
             *
             * 0    1   2   3   4
             */

            if (curM == positionM && curN == 0) {
                // 到达目的地，是1种有效走法
                return 1;
            }
            if (curM < 0 || curN < 0 || curM > positionM || curN > positionN) {
                // 走出网格了
                return 0;
            }
            // 向下走
            int down = recurse(positionM, positionN, curM, curN - 1);
            // 向右走
            int right = recurse(positionM, positionN, curM + 1, curN);
            return down + right;
        }

    }

    private static class MemorySearch {

        public static int uniquePaths(int m, int n) {
            if (m <= 0 || n <= 0) {
                return 0;
            }
            int[][] table = new int[m][n];
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++) {
                    table[i][j] = -1;
                }
            }
            return recurse(m - 1, n - 1, 0, n - 1, table);
        }

        private static int recurse(int positionM, int positionN, int curM, int curN, int[][] table) {
            /*
             * 网格：m = 5, n = 4
             *      start (0, 3)
             *      dest  (4, 0)
             * 3
             *
             * 2
             *
             * 1
             *
             * 0    1   2   3   4
             */
            if (curM < 0 || curN < 0 || curM > positionM || curN > positionN) {
                // 走出网格了
                return 0;
            }
            if (table[curM][curN] != -1) {
                return table[curM][curN];
            }
            if (curM == positionM && curN == 0) {
                // 到达目的地，是1种有效走法
                table[curM][curN] = 1;
                return 1;
            }
            // 向下走
            int down = recurse(positionM, positionN, curM, curN - 1, table);
            // 向右走
            int right = recurse(positionM, positionN, curM + 1, curN, table);
            int r = down + right;
            table[curM][curN] = r;
            return r;
        }

    }

    private static class DP {

        public static int uniquePaths(int m, int n) {
            if (m <= 0 || n <= 0) {
                return 0;
            }
            int positionM = m - 1;
            int positionN = n - 1;
            int[][] table = new int[m][n];
            // base case 到达目的地了，是1种有效路径
            table[positionM][0] = 1;
            table[0][positionN] = 0;

            /*
             * 网格：m = 5, n = 4
             *      start (0, 3)
             *      dest  (4, 0)
             *      0    1   2   3   4
             * 0    0                1
             *
             * 1    0                0
             *
             * 2    0                0
             *
             * 3    0                0
             */

            for (int curM = positionM - 1; curM >= 0; curM--) {
                for (int curN = 0; curN <= positionN; curN++) {
                    // 向下走
                    int down = 0;
                    if (curN - 1 >= 0) {
                        down = table[curM][curN - 1];
                    }
                    // 向右走
                    int right = table[curM + 1][curN];
                    table[curM][curN] = down + right;
                }
            }
            // TODO: 2021/3/3 error

            return table[0][n - 1];
        }

    }

    public static void main(String[] args) {
        Assert.isTrue(BruteForce.uniquePaths(3, 7) == 28);
        Assert.isTrue(BruteForce.uniquePaths(3, 2) == 3);
        Assert.isTrue(BruteForce.uniquePaths(7, 3) == 28);
        Assert.isTrue(BruteForce.uniquePaths(3, 3) == 6);
        Assert.isTrue(BruteForce.uniquePaths(5, 4) == 35);

        Assert.isTrue(MemorySearch.uniquePaths(3, 7) == 28);
        Assert.isTrue(MemorySearch.uniquePaths(3, 2) == 3);
        Assert.isTrue(MemorySearch.uniquePaths(7, 3) == 28);
        Assert.isTrue(MemorySearch.uniquePaths(3, 3) == 6);
        Assert.isTrue(MemorySearch.uniquePaths(5, 4) == 35);

        System.out.println(DP.uniquePaths(5, 4));
        Assert.isTrue(DP.uniquePaths(5, 4) == 35);
        Assert.isTrue(DP.uniquePaths(3, 7) == 28);
        Assert.isTrue(DP.uniquePaths(3, 2) == 3);
        Assert.isTrue(DP.uniquePaths(7, 3) == 28);
        Assert.isTrue(DP.uniquePaths(3, 3) == 6);

    }

}
