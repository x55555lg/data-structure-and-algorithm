package com.lg.algorithm.dynamicprogramming.practice;

/**
 * @author Xulg
 * Created in 2020-12-01 9:49
 */
class RobotWalk {

    /*
     *阿里面试题
     * 假设有排成一行的N个位置，记为1~N，N一定大于或等于2
     * 开始时机器人在其中的M位置上(M 一定是1~N中的一个)
     * 如果机器人来到1位置，那么下一步只能往右来到2位置；
     * 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
     * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
     * 规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
     * 给定四个参数 N、M、K、P，返回方法数。
     *--------------------------------------------------------------------------
     *分析：
     *      现有：N=7 M=3 P=2 K=3
     *       总共有[1, 7]个位置，初始位置在M=3处，终点位置是P=2处，总共可以走K=3步，
     *       问有多少种走法？
     *          初始状态：剩余步数K=3
     *          1   2   3   4   5   6   7
     *              P   M
     *          行走路径：
     *              3→4→3→2
     *              3→2→1→2
     *              3→2→3→2
     *--------------------------------------------------------------------------
     * 暴力递归解法：
     *      当前所在位置 currentPosition
     *      剩余步数     restSteps
     *      base case:   剩余步数为0，即restSteps == 0；如果当前所在位置就是目的地，那么这就是有效的走法
     *      在currentPosition位置可以向左走，也可以向右边走，
     *      将左右两边的走法累加就是所有的走法
     *--------------------------------------------------------------------------
     * 动态规划解法-记忆化搜索：
     *      在暴力递归的基础上，增加二维缓存表，对所有的计算结果缓存起来，提高效率
     *      行表示当前所在位置currentPosition，列表示剩余的步数，存放的数据就是有几种走法
     *          int[][] dbTable = new int[m + 1][k + 1];
     *              例如：dbTable[2][3] = 2   表示当前位置在2，剩余3步可以走，有2中走法
     *--------------------------------------------------------------------------
     * 动态规划解法-经典：
     *      直接通过二维缓存表推出所有的结果
     */

    /**
     * 暴力递归解法
     */
    public static int calcWalkWays1(int n, int m, int p, int k) {
        // 参数无效直接返回0
        if (n <= 1 || m <= 0 || p <= 0 || k <= 0 || m > n || p > n) {
            return 0;
        }
        // 总共N个位置，从M点出发，还剩K步，返回最终能达到P的方法数
        return walk1(n, m, k, p);
    }

    /**
     * 该函数的含义：
     * 只能在[1, totalPositions]这些位置上移动，当前机器人在currentPosition位置，
     * 走完restSteps步之后，停在destPosition位置的方法数作为返回值返回
     *
     * @param totalPositions  总共有多少个位置，不变参数
     * @param currentPosition 当前机器人所在的位置
     * @param restSteps       剩余机器人可走的步数
     * @param destPosition    机器人要去的终点位置，不变参数
     * @return 有多少走法
     */
    private static int walk1(int totalPositions, int currentPosition, int restSteps, int destPosition) {
        // base case
        if (restSteps == 0) {
            // 如果没有剩余步数了，当前的currentPosition位置就是最后的位置
            // 如果最后的位置停在destPosition上，那么之前做的移动是有效的
            // 如果最后的位置没在destPosition上，那么之前做的移动是无效的
            return currentPosition == destPosition ? 1 : 0;
        }

        // 如果还有restSteps步要走，而当前的currentPosition位置在1位置上，那么当前这步只能从1走向2
        // 后续的过程就是，来到2位置上，还剩restSteps-1步要走
        if (currentPosition == 1) {
            return walk1(totalPositions, currentPosition + 1,
                    restSteps - 1, destPosition);
        }

        // 如果还有restSteps步要走，而当前的currentPosition位置在totalPositions位置上，
        // 那么当前这步只能从totalPositions走向totalPositions-1
        // 后续的过程就是，来到totalPositions-1位置上，还剩restSteps-1步要走
        if (currentPosition == totalPositions) {
            return walk1(totalPositions, currentPosition - 1,
                    restSteps - 1, destPosition);
        }

        // 如果还有restSteps步要走，而当前的currentPosition位置在中间位置上，那么当前这步可以走向左，也可以走向右
        // 走向左之后，后续的过程就是，来到currentPosition-1位置上，还剩restSteps-1步要走
        // 走向右之后，后续的过程就是，来到currentPosition+1位置上，还剩restSteps-1步要走
        // 走向左、走向右是截然不同的方法，所以总方法数要都算上
        int left = walk1(totalPositions, currentPosition - 1, restSteps - 1, destPosition);
        int right = walk1(totalPositions, currentPosition + 1, restSteps - 1, destPosition);
        // 两边走法的总和
        return left + right;
    }

    /* ****************************************************************************************************************/

    /**
     * 动态规划解法-记忆化搜索
     */
    @SuppressWarnings("ExplicitArrayFilling")
    public static int calcWalkWays2(int n, int m, int p, int k) {
        // 参数无效直接返回0
        if (n <= 1 || m <= 0 || p <= 0 || k <= 0 || m > n || p > n) {
            return 0;
        }

        /*
         * 缓存表，行表示currentPosition，列表示restSteps
         * dbTable[currentPosition][restSteps]表示有多少的走法
         * 0行不使用，因为currentPosition不可能为0
         * 初始化状态下，-1表示机器人在currentPosition位置，剩余restSteps步，没有走法
         *这里其实也可以用hash表来当缓存，只是用数组更优
         */
        int[][] dbTable = new int[n + 1][k + 1];
        for (int row = 0; row < dbTable.length; row++) {
            for (int column = 0; column < dbTable[row].length; column++) {
                dbTable[row][column] = -1;
            }
        }

        // 总共N个位置，从M点出发，还剩K步，返回最终能达到P的方法数
        return walk2(n, m, k, p, dbTable);
    }

    /**
     * 该函数的含义：
     * 只能在[1, totalPositions]这些位置上移动，当前机器人在currentPosition位置，
     * 走完restSteps步之后，停在destPosition位置的方法数作为返回值返回
     *
     * @param totalPositions  总共有多少个位置，不变参数
     * @param currentPosition 当前机器人所在的位置
     * @param restSteps       剩余机器人可走的步数
     * @param destPosition    机器人要去的终点位置，不变参数
     * @param dpTable         缓存表，行表示currentPosition，列表示restSteps，
     *                        dbTable[currentPosition][restSteps]表示有多少的走法
     * @return 有多少走法
     */
    private static int walk2(int totalPositions, int currentPosition, int restSteps,
                             int destPosition, int[][] dpTable) {
        // 优先从缓存表中拿走法
        if (dpTable[currentPosition][restSteps] != -1) {
            return dpTable[currentPosition][restSteps];
        }

        // base case
        if (restSteps == 0) {
            // 如果没有剩余步数了，当前的currentPosition位置就是最后的位置
            // 如果最后的位置停在destPosition上，那么之前做的移动是有效的
            // 如果最后的位置没在destPosition上，那么之前做的移动是无效的
            int result = currentPosition == destPosition ? 1 : 0;
            // 缓存结果
            dpTable[currentPosition][restSteps] = result;
            return result;
        }

        // 如果还有restSteps步要走，而当前的currentPosition位置在1位置上，那么当前这步只能从1走向2
        // 后续的过程就是，来到2位置上，还剩restSteps-1步要走
        if (currentPosition == 1) {
            int result = walk2(totalPositions, currentPosition + 1,
                    restSteps - 1, destPosition, dpTable);
            // 缓存结果
            dpTable[currentPosition][restSteps] = result;
            return result;
        }

        // 如果还有restSteps步要走，而当前的currentPosition位置在totalPositions位置上，
        // 那么当前这步只能从totalPositions走向totalPositions-1
        // 后续的过程就是，来到totalPositions-1位置上，还剩restSteps-1步要走
        if (currentPosition == totalPositions) {
            int result = walk2(totalPositions, currentPosition - 1,
                    restSteps - 1, destPosition, dpTable);
            // 缓存结果
            dpTable[currentPosition][restSteps] = result;
            return result;
        }

        // 如果还有restSteps步要走，而当前的currentPosition位置在中间位置上，那么当前这步可以走向左，也可以走向右
        // 走向左之后，后续的过程就是，来到currentPosition-1位置上，还剩restSteps-1步要走
        // 走向右之后，后续的过程就是，来到currentPosition+1位置上，还剩restSteps-1步要走
        // 走向左、走向右是截然不同的方法，所以总方法数要都算上
        int left = walk2(totalPositions, currentPosition - 1,
                restSteps - 1, destPosition, dpTable);
        int right = walk2(totalPositions, currentPosition + 1,
                restSteps - 1, destPosition, dpTable);
        // 两边走法的总和
        int result = left + right;

        // 缓存结果
        dpTable[currentPosition][restSteps] = result;
        return result;
    }

    /* ****************************************************************************************************************/

    /**
     * 动态规划解法-经典解法
     */
    public static int calcWalkWays3(int n, int m, int p, int k) {
        /*
         * 行：剩余可走步数rest
         * 列：机器人当前所在的位置current
         * dbTable[i][j]表示当前有i步，位置在j处，有多少种走法
         * 0列不使用，因为位置不可能为0
         * 0行说明剩余步数为0了，如果current位置等于p位置，就是有效走法，否则就是无效走法
         *  dbTable[0][1] = 0;
         *  dbTable[0][2] = 0;
         *  dbTable[0][3] = 0;
         *  dbTable[0][4] = 0;
         *  dbTable[0][p] = 1;  剩余步数为0，且来到了目标位置，是1种有效的走法
         *  dbTable[0][n] = 0;
         */
        int[][] dbTable = new int[k + 1][n + 1];
        // base case
        dbTable[0][p] = 1;

        // 遍历每一步，第1步 第2步 第3步 第k步
        for (int rest = 1; rest <= k; rest++) {
            // 遍历每次来到的位置
            for (int current = 1; current <= n; current++) {
                if (current == 1) {
                    // 在1位置，只能往右边的2位置走
                    dbTable[rest][current] = dbTable[rest - 1][2];
                } else if (current == n) {
                    // 在N位置，只能往左边的N-1位置走
                    dbTable[rest][current] = dbTable[rest - 1][n - 1];
                } else {
                    // 在(1, N)之间的某个中间位置，可以往左边走也可以往右边走
                    // 往左边走
                    int left = dbTable[rest - 1][current - 1];
                    // 往右边走
                    int right = dbTable[rest - 1][current + 1];
                    dbTable[rest][current] = left + right;
                }
            }
        }
        // 返回位置为m，剩余步数为k的走法数量
        return dbTable[k][m];
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        System.out.println(calcWalkWays1(7, 4, 5, 9));
        System.out.println(calcWalkWays2(7, 4, 5, 9));
        System.out.println(calcWalkWays3(7, 4, 5, 9));
    }

}
