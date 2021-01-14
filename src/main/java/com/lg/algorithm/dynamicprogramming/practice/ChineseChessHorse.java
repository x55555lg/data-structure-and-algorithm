package com.lg.algorithm.dynamicprogramming.practice;

import cn.hutool.core.util.RandomUtil;

/**
 * 象棋中马从(0,0)位置走k步到达(x,y)位置有多少种走法
 *
 * @author Xulg
 * Created in 2021-01-13 13:03
 */
class ChineseChessHorse {

    /*
     * 象棋的棋盘是9*10个格子(9条竖线10条横线)，马走k步从起始位置(0,0)走到终点位置(x,y)有
     * 多少种走法？
     */

    /* ****************************************************************************************************************/

    /**
     * 暴力递归
     *
     * @param x 马的起始位置x坐标
     * @param y 马的起始位置y坐标
     * @param k 马可以走k步
     * @return 有多少种走法
     */
    public static int walkWays1(int x, int y, int k) {
        if (x < 0 || x > 8 || y < 0 || y > 9 || k < 0) {
            return 0;
        }
        return walk(x, y, 0, 0, k);
    }

    /**
     * 暴力递归尝试
     * (x,y)为终点位置，(curX,curY)为马当前所在位置，k为剩余的可走步数
     *
     * @param x    终点位置x坐标
     * @param y    终点位置y左边
     * @param curX 马当前所在位置x坐标
     * @param curY 马当前所在位置y坐标
     * @param k    马剩余多少步可以走
     * @return 有多少种走法
     */
    private static int walk(int x, int y, int curX, int curY, int k) {
        // 马走出棋盘的范围了，不是有效的走法
        if (curX < 0 || curX > 8 || curY < 0 || curY > 9) {
            return 0;
        }

        // base case
        if (k == 0) {
            // 剩余步数为0,如果马的当前位置来到了终点(x,y)位置
            // 就是一种有效的走法，否则就不是
            return (curX == x && curY == y) ? 1 : 0;
        }

        // 在棋盘范围内，在任意位置马可以有8种走法(往8个方向走日字)
        int r1 = walk(x, y, curX - 2, curY + 1, k - 1);
        int r2 = walk(x, y, curX - 1, curY + 2, k - 1);
        int r3 = walk(x, y, curX + 1, curY + 2, k - 1);
        int r4 = walk(x, y, curX + 2, curY + 1, k - 1);
        int r5 = walk(x, y, curX - 2, curY - 1, k - 1);
        int r6 = walk(x, y, curX - 1, curY - 2, k - 1);
        int r7 = walk(x, y, curX + 1, curY - 2, k - 1);
        int r8 = walk(x, y, curX + 2, curY - 1, k - 1);

        // 总共走法
        return r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8;
    }

    /**
     * 动态规划-记忆化搜索
     *
     * @param x 马的起始位置x坐标
     * @param y 马的起始位置y坐标
     * @param k 马可以走k步
     * @return 有多少种走法
     */
    public static int walkWays2(int x, int y, int k) {
        if (x < 0 || x > 8 || y < 0 || y > 9 || k < 0) {
            return 0;
        }
        // 缓存表
        Integer[][][] dp = new Integer[9][10][k + 1];
        return walk(x, y, 0, 0, k, dp);
    }

    /**
     * 暴力递归尝试
     * (x,y)为终点位置，(curX,curY)为马当前所在位置，k为剩余的可走步数
     *
     * @param x    终点位置x坐标
     * @param y    终点位置y左边
     * @param curX 马当前所在位置x坐标
     * @param curY 马当前所在位置y坐标
     * @param k    马剩余多少步可以走
     * @param dp   缓存dp表
     * @return 有多少种走法
     */
    private static int walk(int x, int y, int curX, int curY, int k, Integer[][][] dp) {
        // 马走出棋盘的范围了，不是有效的走法
        if (curX < 0 || curX > 8 || curY < 0 || curY > 9) {
            return 0;
        }

        // 走缓存
        if (dp[curX][curY][k] != null) {
            return dp[curX][curY][k];
        }

        // base case
        if (k == 0) {
            // 剩余步数为0,如果马的当前位置来到了终点(x,y)位置
            // 就是一种有效的走法，否则就不是
            int r = curX == x && curY == y ? 1 : 0;
            dp[curX][curY][k] = r;
            return r;
        }

        // 在棋盘范围内，在任意位置马可以有8种走法(往8个方向走日字)
        int r1 = walk(x, y, curX - 2, curY + 1, k - 1, dp);
        int r2 = walk(x, y, curX - 1, curY + 2, k - 1, dp);
        int r3 = walk(x, y, curX + 1, curY + 2, k - 1, dp);
        int r4 = walk(x, y, curX + 2, curY + 1, k - 1, dp);
        int r5 = walk(x, y, curX - 2, curY - 1, k - 1, dp);
        int r6 = walk(x, y, curX - 1, curY - 2, k - 1, dp);
        int r7 = walk(x, y, curX + 1, curY - 2, k - 1, dp);
        int r8 = walk(x, y, curX + 2, curY - 1, k - 1, dp);

        // 总共走法
        int r = r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8;
        dp[curX][curY][k] = r;
        return r;
    }

    /**
     * 动态规划-dp表
     *
     * @param x 马的起始位置x坐标
     * @param y 马的起始位置y坐标
     * @param k 马可以走k步
     * @return 有多少种走法
     */
    public static int walkWays3(int x, int y, int k) {
        if (x < 0 || x > 8 || y < 0 || y > 9 || k < 0) {
            // 出了棋盘边界 非法的步数k
            return 0;
        }

        // 缓存表
        int[][][] dp = new int[9][10][k + 1];
        // base case 马到达了终点位置(x,y)且剩余步数为0步，这是一种有效的走法
        // dp[...][...][0]其余位置的值都是0
        dp[x][y][0] = 1;

        // 当前层的数据都是依赖下一层的，而下一层的数据是已经得到的，所以填表需要从下层往上层填充
        // 填表 restK为剩余步数
        for (int restK = 1; restK <= k; restK++) {
            // 当前x坐标位置
            for (int curX = 0; curX < 9; curX++) {
                // 当前y坐标位置
                for (int curY = 0; curY < 10; curY++) {
                    // 在棋盘范围内，在任意位置马可以有8种走法(往8个方向走日字)
                    int r1 = getValueFromDp(dp, curX - 2, curY + 1, restK - 1);
                    int r2 = getValueFromDp(dp, curX - 1, curY + 2, restK - 1);
                    int r3 = getValueFromDp(dp, curX + 1, curY + 2, restK - 1);
                    int r4 = getValueFromDp(dp, curX + 2, curY + 1, restK - 1);
                    int r5 = getValueFromDp(dp, curX - 2, curY - 1, restK - 1);
                    int r6 = getValueFromDp(dp, curX - 1, curY - 2, restK - 1);
                    int r7 = getValueFromDp(dp, curX + 1, curY - 2, restK - 1);
                    int r8 = getValueFromDp(dp, curX + 2, curY - 1, restK - 1);
                    dp[curX][curY][restK] = r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8;
                }
            }
        }
        // 最终结果
        return dp[0][0][k];
    }

    private static int getValueFromDp(int[][][] dp, int curX, int curY, int restK) {
        if (curX < 0 || curX > 8 || curY < 0 || curY > 9) {
            return 0;
        }
        return dp[curX][curY][restK];
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
        System.out.println(walkWays1(3, 2, 3));
        System.out.println(walkWays2(3, 2, 3));
        System.out.println(walkWays3(3, 2, 3));
        System.out.println(ways(2, 3, 3));

        System.out.println(walkWays1(0, 0, 0));
        System.out.println(walkWays2(0, 0, 0));
        System.out.println(walkWays3(0, 0, 0));
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
            int r1 = walkWays1(x, y, k);
            int r2 = walkWays2(x, y, k);
            int r3 = walkWays3(x, y, k);
            if (!(r == r1 && r1 == r2 && r2 == r3)) {
                System.out.println("Oops fucking!-------x=" + x + " y=" + y + " k=" + k
                        + " r=" + r + " r1=" + r1 + " r2=" + r2 + " r3=" + r3);
            }
        }
        System.out.println("finished!");
    }

}
