package com.lg.algorithm.dynamicprogramming.a2;

import cn.hutool.core.util.RandomUtil;

/**
 * @author Xulg
 * @since 2021-07-21 10:27
 * Description: 马走到目的地的走法数量
 */
class ChineseChessHorse {

    /*
     * 象棋的棋盘是9*10个格子(9条竖线10条横线)，马走k步从起始位置(0,0)走到终点位置(x,y)有
     * 多少种走法？
     */

    /* ****************************************************************************************************************/

    private static class BruteForce {
        public static int walk(int x, int y, int k) {
            if (x < 0 || x >= 9 || y < 0 || y >= 10 || k < 0) {
                return 0;
            }
            return recurse(0, 0, k, x, y);
        }

        /**
         * @param destX    the dest x
         * @param destY    the dest y
         * @param curX     the current x
         * @param curY     the current y
         * @param restStep rest steps
         * @return the walk ways
         */
        private static int recurse(int curX, int curY, int restStep, int destX, int destY) {
            if (curX < 0 || curX >= 9 || curY < 0 || curY >= 10) {
                // 已经越界
                return 0;
            }
            if (restStep == 0) {
                // 步数用完，是否到达目的地
                return curX == destX && curY == destY ? 1 : 0;
            }
            // 往8个方向走
            int r1 = recurse(curX - 1, curY + 2, restStep - 1, destX, destY);
            int r2 = recurse(curX + 1, curY + 2, restStep - 1, destX, destY);
            int r3 = recurse(curX - 2, curY + 1, restStep - 1, destX, destY);
            int r4 = recurse(curX + 2, curY + 1, restStep - 1, destX, destY);
            int r5 = recurse(curX - 1, curY - 2, restStep - 1, destX, destY);
            int r6 = recurse(curX + 1, curY - 2, restStep - 1, destX, destY);
            int r7 = recurse(curX - 2, curY - 1, restStep - 1, destX, destY);
            int r8 = recurse(curX + 2, curY - 1, restStep - 1, destX, destY);
            return r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8;
        }
    }

    private static class DP {
        public static int walk(int x, int y, int k) {
            if (x < 0 || x >= 9 || y < 0 || y >= 10 || k < 0) {
                return 0;
            }
            int[][][] table = new int[9][10][k + 1];
            // base case
            table[x][y][0] = 1;
            for (int restStep = 1; restStep <= k; restStep++) {
                for (int curX = 0; curX < 9; curX++) {
                    for (int curY = 0; curY < 10; curY++) {
                        // 往8个方向走
                        int r1 = getValue(curX - 1, curY + 2, restStep - 1, table);
                        int r2 = getValue(curX + 1, curY + 2, restStep - 1, table);
                        int r3 = getValue(curX - 2, curY + 1, restStep - 1, table);
                        int r4 = getValue(curX + 2, curY + 1, restStep - 1, table);
                        int r5 = getValue(curX - 1, curY - 2, restStep - 1, table);
                        int r6 = getValue(curX + 1, curY - 2, restStep - 1, table);
                        int r7 = getValue(curX - 2, curY - 1, restStep - 1, table);
                        int r8 = getValue(curX + 2, curY - 1, restStep - 1, table);
                        table[curX][curY][restStep] = r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8;
                    }
                }
            }
            return table[0][0][k];
        }

        private static int getValue(int curX, int curY, int restStep, int[][][] table) {
            if (curX < 0 || curX >= 9 || curY < 0 || curY >= 10) {
                // 已经越界
                return 0;
            }
            return table[curX][curY][restStep];
        }
    }

    /* ****************************************************************************************************************/

    /* 对数器方法 */

    private static class Compare {
        public static int ways(int x, int y, int k) {
            // 0~k
            int[][][] dp = new int[10][9][k + 1];
            // dp[..][..][0] = 0
            dp[0][0][0] = 1;
            for (int level = 1; level <= k; level++) {
                // level层，x y
                // x可能性
                for (int i = 0; i < 10; i++) {
                    // y的可能性
                    for (int j = 0; j < 9; j++) {
                        dp[i][j][level] = getValue(dp, i + 2, j - 1, level - 1)
                                + getValue(dp, i + 2, j + 1, level - 1)
                                + getValue(dp, i + 1, j + 2, level - 1)
                                + getValue(dp, i - 1, j + 2, level - 1)
                                + getValue(dp, i - 2, j + 1, level - 1)
                                + getValue(dp, i - 2, j - 1, level - 1)
                                + getValue(dp, i - 1, j - 2, level - 1)
                                + getValue(dp, i + 1, j - 2, level - 1);
                    }
                }
            }
            return dp[x][y][k];
        }

        private static int getValue(int[][][] dp, int x, int y, int k) {
            if (x < 0 || x > 9 || y < 0 || y > 8) {
                return 0;
            }
            return dp[x][y][k];
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            // x [0, 8]
            int x = RandomUtil.randomInt(0, 9);
            // y [0, 9]
            int y = RandomUtil.randomInt(0, 10);
            // k [0, 8]
            int k = RandomUtil.randomInt(0, 9);
            int r = Compare.ways(y, x, k);
            int r1 = BruteForce.walk(x, y, k);
            int r2 = DP.walk(x, y, k);
            if (!(r == r1 && r1 == r2)) {
                System.out.println("fucking");
            }
        }
        System.out.println("finished!");
    }
}
