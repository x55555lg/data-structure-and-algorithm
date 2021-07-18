package com.practice.dynamicprogramming;

import cn.hutool.core.util.RandomUtil;

import java.text.MessageFormat;

/**
 * @author Xulg
 * Created in 2021-05-13 9:44
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
     */

    private static class BruteForce {
        public static int walkWays(int m, int k, int n, int p) {
            if (m < 1 || n < 2 || k < 1 || p < 1 || m > n || p > n) {
                return 0;
            }
            return recurse(m, k, n, p);
        }

        private static int recurse(int current, int rest, int total, int dest) {
            if (current <= 0 || current > total) {
                // 越界了
                return 0;
            }
            if (rest == 0) {
                // 步数用完了，看到没到目的地
                return current == dest ? 1 : 0;
            }
            if (current == 1) {
                // 如果机器人来到1位置，那么下一步只能往右来到2位置
                return recurse(2, rest - 1, total, dest);
            }
            if (current == total) {
                // 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置
                return recurse(total - 1, rest - 1, total, dest);
            }
            // 可以往左走，可以往右走
            int left = recurse(current - 1, rest - 1, total, dest);
            int right = recurse(current + 1, rest - 1, total, dest);
            // 返回总走法
            return left + right;
        }
    }

    private static class MemorySearch {
        public static int walkWays(int m, int k, int n, int p) {
            if (m < 1 || n < 2 || k < 1 || p < 1 || m > n || p > n) {
                return 0;
            }
            int[][] table = new int[n + 1][k + 1];
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= k; j++) {
                    table[i][j] = -1;
                }
            }
            return recurse(m, k, n, p, table);
        }

        private static int recurse(int current, int rest, int total, int dest, int[][] table) {
            if (current <= 0 || current > total) {
                // 越界了
                return 0;
            }

            if (table[current][rest] != -1) {
                // go cache
                return table[current][rest];
            }

            // 步数用完了，看到没到目的地
            if (rest == 0) {
                int r = current == dest ? 1 : 0;
                table[current][rest] = r;
                return r;
            }

            // 如果机器人来到1位置，那么下一步只能往右来到2位置
            if (current == 1) {
                int r = recurse(2, rest - 1, total, dest, table);
                table[current][rest] = r;
                return r;
            }

            // 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置
            if (current == total) {
                int r = recurse(total - 1, rest - 1, total, dest, table);
                table[current][rest] = r;
                return r;
            }

            // 可以往左走，可以往右走
            int left = recurse(current - 1, rest - 1, total, dest, table);
            int right = recurse(current + 1, rest - 1, total, dest, table);
            int r = left + right;
            table[current][rest] = r;
            return r;
        }
    }

    private static class DP {
        public static int walkWays(int m, int k, int n, int p) {
            if (m < 1 || n < 2 || k < 1 || p < 1 || m > n || p > n) {
                return 0;
            }
            int[][] table = new int[n + 1][k + 1];
            // base case 剩余0步，到达目的地
            table[p][0] = 1;
            for (int rest = 1; rest <= k; rest++) {
                for (int current = 1; current <= n; current++) {
                    if (current == 1) {
                        // 如果机器人来到1位置，那么下一步只能往右来到2位置
                        table[current][rest] = table[2][rest - 1];
                    } else if (current == n) {
                        // 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置
                        table[current][rest] = table[n - 1][rest - 1];
                    } else {
                        // 可以往左走，可以往右走
                        int left = table[current - 1][rest - 1];
                        int right = table[current + 1][rest - 1];
                        table[current][rest] = left + right;
                    }
                }
            }
            return table[m][k];
        }
    }

    public static void main(String[] args) {
        System.out.println(BruteForce.walkWays(4, 9, 7, 5));
        System.out.println(MemorySearch.walkWays(4, 9, 7, 5));
        System.out.println(DP.walkWays(4, 9, 7, 5));
        int times = 1000000;
        int maxN = 20;
        int maxK = 20;
        for (int time = 0; time < times; time++) {
            int n = RandomUtil.randomInt(0, maxN);
            int m = RandomUtil.randomInt(0, n + 1);
            int p = RandomUtil.randomInt(0, n + 1);
            int k = RandomUtil.randomInt(0, maxK);
            int r0 = BruteForce.walkWays(m, k, n, p);
            int r1 = MemorySearch.walkWays(m, k, n, p);
            int r2 = DP.walkWays(m, k, n, p);
            if (!(r0 == r1 && r1 == r2)) {
                System.out.println(MessageFormat.format("fucking---m={0}, k={1}, n={2}, p={3}", m, k, n, p));
            }
        }
        System.out.println("good job");
    }

}
