package com.practice2.dynamicprogramming;

import cn.hutool.core.util.RandomUtil;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-10-18 9:42
 * Description: TODO 请加入类描述信息
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
        public static int calcRobotWalkWays(int m, int k, int n, int p) {
            // assert n >= 2 && m > 0 && m <= n && k >= 0 && p > 0 && p <= n;
            if (m < 1 || n < 2 || k < 1 || p < 1 || m > n || p > n) {
                return 0;
            }
            return recurse(m, k, n, p);
        }

        private static int recurse(int currentPosition, int restSteps, int totalPosition, int destPosition) {
            if (currentPosition < 0 || currentPosition > totalPosition) {
                // 越界了
                return 0;
            }
            // base case
            if (restSteps == 0) {
                // 步数用完，看有没有到目的地
                return currentPosition == destPosition ? 1 : 0;
            }
            if (currentPosition == 1) {
                // 如果机器人来到1位置，那么下一步只能往右来到2位置；
                return recurse(2, restSteps - 1, totalPosition, destPosition);
            } else if (currentPosition == totalPosition) {
                // 如果机器人来到N位置，那么下一步只能往左来到N-1位置
                return recurse(totalPosition - 1, restSteps - 1, totalPosition, destPosition);
            } else {
                // 如果机器人来到中间位置，那么下一步可以往左走或者往右走
                int left = recurse(currentPosition - 1, restSteps - 1, totalPosition, destPosition);
                int right = recurse(currentPosition + 1, restSteps - 1, totalPosition, destPosition);
                return left + right;
            }
        }
    }

    private static class MemorySearch {
        public static int calcRobotWalkWays(int m, int k, int n, int p) {
            // assert n >= 2 && m > 0 && m <= n && k >= 0 && p > 0 && p <= n;
            if (m < 1 || n < 2 || k < 1 || p < 1 || m > n || p > n) {
                return 0;
            }
            int[][] table = new int[n + 1][k + 1];
            for (int i = 0; i < table.length; i++) {
                Arrays.fill(table[i], -1);
            }
            return recurse(m, k, n, p, table);
        }

        private static int recurse(int currentPosition, int restSteps, int totalPosition, int destPosition, int[][] table) {
            if (currentPosition < 0 || currentPosition > totalPosition) {
                // 越界了
                return 0;
            }
            if (table[currentPosition][restSteps] != -1) {
                return table[currentPosition][restSteps];
            }
            // base case
            if (restSteps == 0) {
                // 步数用完，看有没有到目的地
                table[currentPosition][restSteps] = currentPosition == destPosition ? 1 : 0;
                return table[currentPosition][restSteps];
            }
            if (currentPosition == 1) {
                // 如果机器人来到1位置，那么下一步只能往右来到2位置；
                table[currentPosition][restSteps] = recurse(2, restSteps - 1, totalPosition, destPosition, table);
                return table[currentPosition][restSteps];
            } else if (currentPosition == totalPosition) {
                // 如果机器人来到N位置，那么下一步只能往左来到N-1位置
                table[currentPosition][restSteps] = recurse(totalPosition - 1, restSteps - 1, totalPosition, destPosition, table);
                return table[currentPosition][restSteps];
            } else {
                // 如果机器人来到中间位置，那么下一步可以往左走或者往右走
                int left = recurse(currentPosition - 1, restSteps - 1, totalPosition, destPosition, table);
                int right = recurse(currentPosition + 1, restSteps - 1, totalPosition, destPosition, table);
                table[currentPosition][restSteps] = left + right;
                return table[currentPosition][restSteps];
            }
        }
    }

    private static class DP {
        public static int calcRobotWalkWays(int m, int k, int n, int p) {
            // assert n >= 2 && m > 0 && m <= n && k >= 0 && p > 0 && p <= n;
            if (m < 1 || n < 2 || k < 1 || p < 1 || m > n || p > n) {
                return 0;
            }
            int[][] table = new int[n + 1][k + 1];
            // base case 步数用完且到达目的地
            table[p][0] = 1;
            for (int restSteps = 1; restSteps <= k; restSteps++) {
                for (int currentPosition = 1; currentPosition <= n; currentPosition++) {
                    if (currentPosition == 1) {
                        // 如果机器人来到1位置，那么下一步只能往右来到2位置；
                        table[currentPosition][restSteps] = table[2][restSteps - 1];
                    } else if (currentPosition == n) {
                        // 如果机器人来到N位置，那么下一步只能往左来到N-1位置
                        table[currentPosition][restSteps] = table[n - 1][restSteps - 1];
                    } else {
                        // 如果机器人来到中间位置，那么下一步可以往左走或者往右走
                        int left = table[currentPosition - 1][restSteps - 1];
                        int right = table[currentPosition + 1][restSteps - 1];
                        table[currentPosition][restSteps] = left + right;
                    }
                }
            }
            return table[m][k];
        }
    }

    public static void main(String[] args) {
        System.out.println(BruteForce.calcRobotWalkWays(4, 9, 7, 5));
        System.out.println(MemorySearch.calcRobotWalkWays(4, 9, 7, 5));
        System.out.println(DP.calcRobotWalkWays(4, 9, 7, 5));
        int times = 1000000;
        int maxN = 20;
        int maxK = 20;
        for (int time = 0; time < times; time++) {
            int n = RandomUtil.randomInt(0, maxN);
            int m = RandomUtil.randomInt(0, n + 1);
            int p = RandomUtil.randomInt(0, n + 1);
            int k = RandomUtil.randomInt(0, maxK);
            int r0 = BruteForce.calcRobotWalkWays(m, k, n, p);
            int r1 = MemorySearch.calcRobotWalkWays(m, k, n, p);
            int r2 = DP.calcRobotWalkWays(m, k, n, p);
            if (!(r0 == r1 && r1 == r2)) {
                System.out.println(MessageFormat.format("fucking---m={0}, k={1}, n={2}, p={3}", m, k, n, p));
            }
        }
        System.out.println("good job");
    }

}
