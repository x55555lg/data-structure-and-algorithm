package com.lg.algorithm.dynamicprogramming.a1;

/**
 * @author Xulg
 * Created in 2021-02-17 20:18
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
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
     */

    /**
     * 暴力解法
     */
    public static int walkByViolence(int n, int m, int k, int p) {
        if (n < 2 || m <= 0 || k <= 0 || p <= 0 || m > n || p > n) {
            return 0;
        }
        return recurse(n, p, m, k);
    }

    /**
     * @param n             N个可走的位置(常量)
     * @param p             目的地位置(常量)
     * @param curPosition   当前机器人所在位置(变量)
     * @param remainderStep 剩余可走步数(变量)
     * @return 几种走法
     */
    private static int recurse(int n, int p, int curPosition, int remainderStep) {
        // base case
        if (remainderStep == 0) {
            // 剩余步骤为0，而且机器人到达目的地，是一种走法
            return curPosition == p ? 1 : 0;
        }
        if (curPosition == 1) {
            // 如果机器人来到1位置，那么下一步只能往右来到2位置
            return recurse(n, p, 2, remainderStep - 1);
        }
        if (curPosition == n) {
            // 如果机器人来到N位置，那么下一步只能往左来到N-1位置
            return recurse(n, p, n - 1, remainderStep - 1);
        }

        // 左边走
        int left = recurse(n, p, curPosition - 1, remainderStep - 1);
        // 右边走
        int right = recurse(n, p, curPosition + 1, remainderStep - 1);

        // 总的走法
        return left + right;
    }

    /**
     * 动态规划解法
     */
    public static int walkByDP(int n, int m, int k, int p) {
        if (n <= 1 || m <= 0 || k <= 0 || p <= 0 || m > n || p > n) {
            return 0;
        }

        // row表示机器人所在的位置[1,N]
        // column表示剩余步数[0,k]
        int[][] table = new int[n + 1][k + 1];

        // column为0的时候，如果机器人所在位置就是p，那么就是一种有效的走法
        table[p][0] = 1;

        // 填表
        for (int curPosition = 1; curPosition <= n; curPosition++) {
            for (int remainderStep = 1; remainderStep <= k; remainderStep++) {
                if (curPosition == 1) {
                    // 如果机器人来到1位置，那么下一步只能往右来到2位置
                    table[curPosition][remainderStep] = table[2][remainderStep - 1];
                } else if (curPosition == n) {
                    // 如果机器人来到N位置，那么下一步只能往左来到N-1位置
                    table[curPosition][remainderStep] = table[n - 1][remainderStep - 1];
                } else {
                    // 左边走
                    int left = table[curPosition - 1][remainderStep - 1];
                    // 右边走
                    int right = table[curPosition + 1][remainderStep - 1];
                    // 总的走法
                    table[curPosition][remainderStep] = left + right;
                }
            }
        }
        return table[m][k];
    }

}
