package com.practice.dynamicprogramming;

import cn.hutool.core.util.RandomUtil;

/**
 * 象棋中马从(0,0)位置走k步到达(x,y)位置有多少种走法
 *
 * @author Xulg
 * Created in 2021-05-12 22:25
 */
class ChineseChessHorse {

    /*
     * 象棋的棋盘是9*10个格子(9条竖线10条横线)，马走k步从起始位置(0,0)走到终点位置(x,y)有
     * 多少种走法？
     */

    /* ****************************************************************************************************************/

    private static class BruteForce {
        public static int walkWays(int destX, int destY, int k) {
            if (destX < 0 || destX > 8 || destY < 0 || destY > 9 || k < 0) {
                return 0;
            }
            return recurse(0, 0, k, destX, destY);
        }

        private static int recurse(int curX, int curY, int restK, int destX, int destY) {
            if (curX < 0 || curX > 8 || curY < 0 || curY > 9) {
                // 走出棋盘了，0种走法
                return 0;
            }

            // base case
            if (restK == 0) {
                // 步数走完了，到了目的地就是1种有效走法，否则不是
                return curX == destX && curY == destY ? 1 : 0;
            }

            // 在棋盘范围内，在任意位置马可以有8种走法(往8个方向走日字)
            int r1 = recurse(curX + 2, curY - 1, restK - 1, destX, destY);
            int r2 = recurse(curX + 1, curY - 2, restK - 1, destX, destY);
            int r3 = recurse(curX - 1, curY - 2, restK - 1, destX, destY);
            int r4 = recurse(curX - 2, curY - 1, restK - 1, destX, destY);
            int r5 = recurse(curX + 2, curY + 1, restK - 1, destX, destY);
            int r6 = recurse(curX + 1, curY + 2, restK - 1, destX, destY);
            int r7 = recurse(curX - 1, curY + 2, restK - 1, destX, destY);
            int r8 = recurse(curX - 2, curY + 1, restK - 1, destX, destY);

            // 返回总走法
            return r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8;
        }
    }

    private static class MemorySearch {
        public static int walkWays(int destX, int destY, int k) {
            if (destX < 0 || destX > 8 || destY < 0 || destY > 9 || k < 0) {
                return 0;
            }
            int[][][] table = new int[9][10][k + 1];
            // fill initial value
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 10; j++) {
                    for (int c = 0; c <= k; c++) {
                        table[i][j][c] = -1;
                    }
                }
            }
            return recurse(0, 0, k, destX, destY, table);
        }

        private static int recurse(int curX, int curY, int restSteps, int destX, int destY, int[][][] table) {
            if (curX < 0 || curX > 8 || curY < 0 || curY > 9) {
                // 走出棋盘了，0种走法
                return 0;
            }

            // use cache first if exist
            if (table[curX][curY][restSteps] != -1) {
                return table[curX][curY][restSteps];
            }

            // base case
            if (restSteps == 0) {
                // 步数走完了，到了目的地就是1种有效走法，否则不是
                int r = curX == destX && curY == destY ? 1 : 0;
                table[curX][curY][restSteps] = r;
                return r;
            }

            // 在棋盘范围内，在任意位置马可以有8种走法(往8个方向走日字)
            int r1 = recurse(curX + 2, curY - 1, restSteps - 1, destX, destY, table);
            int r2 = recurse(curX + 1, curY - 2, restSteps - 1, destX, destY, table);
            int r3 = recurse(curX - 1, curY - 2, restSteps - 1, destX, destY, table);
            int r4 = recurse(curX - 2, curY - 1, restSteps - 1, destX, destY, table);
            int r5 = recurse(curX + 2, curY + 1, restSteps - 1, destX, destY, table);
            int r6 = recurse(curX + 1, curY + 2, restSteps - 1, destX, destY, table);
            int r7 = recurse(curX - 1, curY + 2, restSteps - 1, destX, destY, table);
            int r8 = recurse(curX - 2, curY + 1, restSteps - 1, destX, destY, table);

            // 返回总走法
            int r = r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8;
            table[curX][curY][restSteps] = r;
            return r;
        }
    }

    private static class DP {
        public static int walkWays(int destX, int destY, int k) {
            if (destX < 0 || destX > 8 || destY < 0 || destY > 9 || k < 0) {
                return 0;
            }
            int[][][] table = new int[9][10][k + 1];
            // base case
            table[destX][destY][0] = 1;
            // 填表
            for (int restStep = 1; restStep <= k; restStep++) {
                for (int curX = 0; curX < 9; curX++) {
                    for (int curY = 0; curY < 10; curY++) {
                        // 在棋盘范围内，在任意位置马可以有8种走法(往8个方向走日字)
                        int r1 = getValueFromTable(curX + 2, curY - 1, restStep - 1, table);
                        int r2 = getValueFromTable(curX + 1, curY - 2, restStep - 1, table);
                        int r3 = getValueFromTable(curX - 1, curY - 2, restStep - 1, table);
                        int r4 = getValueFromTable(curX - 2, curY - 1, restStep - 1, table);
                        int r5 = getValueFromTable(curX + 2, curY + 1, restStep - 1, table);
                        int r6 = getValueFromTable(curX + 1, curY + 2, restStep - 1, table);
                        int r7 = getValueFromTable(curX - 1, curY + 2, restStep - 1, table);
                        int r8 = getValueFromTable(curX - 2, curY + 1, restStep - 1, table);
                        table[curX][curY][restStep] = r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8;
                    }
                }
            }
            return table[0][0][k];
        }

        private static int getValueFromTable(int x, int y, int k, int[][][] table) {
            if (x < 0 || y < 0 || x > 8 || y > 9) {
                return 0;
            }
            return table[x][y][k];
        }
    }

    /* ****************************************************************************************************************/

    /* 对数器方法 */

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

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        System.out.println(BruteForce.walkWays(3, 2, 3));
        System.out.println(MemorySearch.walkWays(3, 2, 3));
        System.out.println(DP.walkWays(3, 2, 3));
        System.out.println(ways(2, 3, 3));

        System.out.println(BruteForce.walkWays(0, 0, 0));
        System.out.println(MemorySearch.walkWays(0, 0, 0));
        System.out.println(DP.walkWays(0, 0, 0));
        System.out.println(ways(0, 0, 0));

        int times = 1000000;
        for (int time = 0; time < times; time++) {
            // x [0, 8]
            int x = RandomUtil.randomInt(0, 9);
            // y [0, 9]
            int y = RandomUtil.randomInt(0, 10);
            // k [0, 8]
            int k = RandomUtil.randomInt(0, 9);
            //noinspection SuspiciousNameCombination
            int r = ways(y, x, k);
            int r1 = BruteForce.walkWays(x, y, k);
            int r2 = MemorySearch.walkWays(x, y, k);
            int r3 = DP.walkWays(x, y, k);
            if (!(r == r1 && r1 == r2 && r2 == r3)) {
                System.out.println("Oops fucking!-------x=" + x + " y=" + y + " k=" + k
                        + " r=" + r + " r1=" + r1 + " r2=" + r2 + " r3=" + r3);
            }
        }
        System.out.println("finished!");
    }

}
