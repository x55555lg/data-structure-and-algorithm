package com.lg.algorithm.dynamicprogramming.a1;

import cn.hutool.core.util.RandomUtil;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-02-17 20:17
 */
class ChineseChessHorse {

    /*
     * 象棋的棋盘是9*10个格子(9条竖线10条横线)，马走k步从起始位置(0,0)走到终点位置(x,y)有
     * 多少种走法？
     */

    /* ****************************************************************************************************************/

    /**
     * 暴力解法
     * 象棋的棋盘是9*10个格子(9条竖线10条横线)，马走k步从起始位置(0,0)走到终点位置(x,y)有多少种走法
     *
     * @param x the x
     * @param y the y
     * @param k k steps
     * @return the walk ways
     */
    public static int walkByBruteForce(int x, int y, int k) {
        if (x < 0 || x > 8 || y < 0 || y > 9 || k < 0) {
            return 0;
        }
        return recurse(x, y, 0, 0, k);
    }

    /**
     * @param destX    the dest x
     * @param destY    the dest y
     * @param curX     the current x
     * @param curY     the current y
     * @param restStep rest steps
     * @return the walk ways
     */
    private static int recurse(int destX, int destY, int curX, int curY, int restStep) {
        if (curX < 0 || curX > 8 || curY < 0 || curY > 9) {
            // 走出棋盘了
            return 0;
        }
        // base case
        if (restStep == 0) {
            // 剩余0步，是否达到了终点位置
            return curX == destX && curY == destY ? 1 : 0;
        }
        // 马可以往8个方向走法
        int p1 = recurse(destX, destY, curX - 2, curY + 1, restStep - 1);
        int p2 = recurse(destX, destY, curX - 1, curY + 2, restStep - 1);
        int p3 = recurse(destX, destY, curX + 1, curY + 2, restStep - 1);
        int p4 = recurse(destX, destY, curX + 2, curY + 1, restStep - 1);
        int p5 = recurse(destX, destY, curX - 2, curY - 1, restStep - 1);
        int p6 = recurse(destX, destY, curX - 1, curY - 2, restStep - 1);
        int p7 = recurse(destX, destY, curX + 1, curY - 2, restStep - 1);
        int p8 = recurse(destX, destY, curX + 2, curY - 1, restStep - 1);
        // total ways
        return p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
    }

    /**
     * 动态规划
     * 象棋的棋盘是9*10个格子(9条竖线10条横线)，马走k步从起始位置(0,0)走到终点位置(x,y)有多少种走法
     *
     * @param x the x
     * @param y the y
     * @param k k steps
     * @return the walk ways
     */
    @SuppressWarnings("DuplicatedCode")
    public static int walkByDP(int x, int y, int k) {
        if (x < 0 || x > 8 || y < 0 || y > 9 || k < 0) {
            return 0;
        }

        //棋盘X轴，棋盘Y轴，剩余步数
        int[][][] table = new int[9][10][k + 1];
        // base case 马走到了终点位置，剩余步数为0
        table[x][y][0] = 1;

        // 填表
        for (int restStep = 1; restStep <= k; restStep++) {
            for (int curX = 0; curX < 9; curX++) {
                for (int curY = 0; curY < 10; curY++) {
                    // 马可以往8个方向走法
                    int p1 = getValueFromTable(table, curX - 2, curY + 1, restStep - 1);
                    int p2 = getValueFromTable(table, curX - 1, curY + 2, restStep - 1);
                    int p3 = getValueFromTable(table, curX + 1, curY + 2, restStep - 1);
                    int p4 = getValueFromTable(table, curX + 2, curY + 1, restStep - 1);
                    int p5 = getValueFromTable(table, curX - 2, curY - 1, restStep - 1);
                    int p6 = getValueFromTable(table, curX - 1, curY - 2, restStep - 1);
                    int p7 = getValueFromTable(table, curX + 1, curY - 2, restStep - 1);
                    int p8 = getValueFromTable(table, curX + 2, curY - 1, restStep - 1);
                    // total ways
                    table[curX][curY][restStep] = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
                }
            }
        }
        return table[0][0][k];
    }

    private static int getValueFromTable(int[][][] table, int curX, int curY, int restStep) {
        if (curX < 0 || curX > 8 || curY < 0 || curY > 9) {
            // 走出棋盘了
            return 0;
        }
        return table[curX][curY][restStep];
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
        HashMap<Integer, Integer> map = new HashMap<>();
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
            int r1 = walkByBruteForce(x, y, k);
            int r2 = walkByDP(x, y, k);

            if (map.containsKey(r)) {
                map.put(r, map.get(r) + 1);
            } else {
                map.put(r, 1);
            }

            if (!(r == r1 && r1 == r2)) {
                System.out.println("Oops fucking!-------x=" + x + " y=" + y + " k=" + k
                        + " r=" + r + " r1=" + r1 + " r2=" + r2);
            }
        }
        System.out.println("finished!");
    }

}
