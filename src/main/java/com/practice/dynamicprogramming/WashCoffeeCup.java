package com.practice.dynamicprogramming;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Xulg
 * Created in 2021-05-17 9:34
 */
class WashCoffeeCup {

    /*
     * 给定一个数组，代表每个人喝完咖啡准备刷杯子的时间
     * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯
     * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发
     * 返回让所有咖啡杯变干净的最早完成时间
     * 三个参数：int[] arr、int a、int b
     */

    /* ****************************************************************************************************************/

    private static class BruteForce {
        public static int washCupEarliestFinishTime(int[] arr, int a, int b) {
            if (arr == null || arr.length == 0 || a < 0 || b < 0) {
                return 0;
            }
            if (a >= b) {
                // 全部自动挥发，取最后完成的那个
                int answer = Integer.MIN_VALUE;
                for (int time : arr) {
                    answer = Math.max(answer, time + b);
                }
                return answer;
            }
            return recurse(0, 0, arr, a, b);
        }

        /**
         * [0, curCupIdx)已经洗完，[curCupIdx, N)洗完需要的最早时间
         */
        private static int recurse(int curCupIdx, int nextMachineWashTime,
                                   int[] coffeeCups, int machineWashConsumeTime, int autoCleanConsumeTime) {
            // base case
            if (curCupIdx == coffeeCups.length - 1) {
                // 最后一个杯子，看是挥发快还是机器洗快
                int a1 = coffeeCups[curCupIdx] + autoCleanConsumeTime;
                int a2 = Math.max(nextMachineWashTime, coffeeCups[curCupIdx]) + machineWashConsumeTime;
                return Math.min(a1, a2);
            }

            // 当前咖啡杯使用机器洗
            int currentFinishTime1 = Math.max(nextMachineWashTime, coffeeCups[curCupIdx]) + machineWashConsumeTime;
            // 后面杯子的最早完成时间
            int nextEarliestFinishTime1 = recurse(curCupIdx + 1, currentFinishTime1, coffeeCups, machineWashConsumeTime, autoCleanConsumeTime);
            // 取最大值，因为要等到最后一个杯子也洗干净
            int answer1 = Math.max(currentFinishTime1, nextEarliestFinishTime1);

            // 当前杯子使用自然挥发洗
            int currentFinishTime2 = coffeeCups[curCupIdx] + autoCleanConsumeTime;
            // 后面杯子的最早完成时间
            int nextEarliestFinishTime2 = recurse(curCupIdx + 1, nextMachineWashTime, coffeeCups, machineWashConsumeTime, autoCleanConsumeTime);
            // 取最大值，因为要等到最后一个杯子也洗干净
            int answer2 = Math.max(currentFinishTime2, nextEarliestFinishTime2);

            return Math.min(answer1, answer2);
        }
    }

    private static class MemorySearch {
        public static int washCupEarliestFinishTime(int[] arr, int a, int b) {
            if (arr == null || arr.length == 0 || a < 0 || b < 0) {
                return 0;
            }
            // 全部都机洗，求出下一次机器可洗的最大值
            int maxNextMachineWashTime = 0;
            for (int beginTime : arr) {
                maxNextMachineWashTime = a + Math.max(maxNextMachineWashTime, beginTime);
            }
            int[][] table = new int[arr.length][maxNextMachineWashTime + 1];
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < maxNextMachineWashTime; j++) {
                    table[i][j] = -1;
                }
            }
            return recurse(0, 0, arr, a, b, table);
        }

        private static int recurse(int curCupIdx, int nextMachineWashTime, int[] coffeeCups, int machineWashTime, int autoCleanTime, int[][] table) {
            // go cache
            if (table[curCupIdx][nextMachineWashTime] != -1) {
                return table[curCupIdx][nextMachineWashTime];
            }

            // base case
            if (curCupIdx == coffeeCups.length - 1) {
                int a1 = coffeeCups[curCupIdx] + autoCleanTime;
                int a2 = Math.max(coffeeCups[curCupIdx], nextMachineWashTime) + machineWashTime;
                int r = Math.min(a1, a2);
                table[curCupIdx][nextMachineWashTime] = r;
                return r;
            }

            // case1
            int current1 = Math.max(coffeeCups[curCupIdx], nextMachineWashTime) + machineWashTime;
            int next1 = recurse(curCupIdx + 1, current1, coffeeCups, machineWashTime, autoCleanTime, table);
            int answer1 = Math.max(current1, next1);

            // case2
            int current2 = coffeeCups[curCupIdx] + autoCleanTime;
            int next2 = recurse(curCupIdx + 1, nextMachineWashTime, coffeeCups, machineWashTime, autoCleanTime, table);
            int answer2 = Math.max(current2, next2);

            int r = Math.min(answer1, answer2);
            table[curCupIdx][nextMachineWashTime] = r;
            return r;
        }
    }

    private static class DP {
        public static int washCupEarliestFinishTime(int[] arr, int a, int b) {
            if (arr == null || arr.length == 0 || a < 0 || b < 0) {
                return 0;
            }
            // 全部都机洗，求出下一次机器可洗的最大值
            int maxNextMachineWashTime = 0;
            for (int beginTime : arr) {
                maxNextMachineWashTime = a + Math.max(maxNextMachineWashTime, beginTime);
            }

            // dp table
            int[][] table = new int[arr.length][maxNextMachineWashTime + 1];

            // base case 最后一个杯子，看是挥发快还是机器洗快
            for (int nextMachineWashTime = 0; nextMachineWashTime < table[0].length; nextMachineWashTime++) {
                int a1 = arr[arr.length - 1] + b;
                int a2 = Math.max(nextMachineWashTime, arr[arr.length - 1]) + a;
                table[arr.length - 1][nextMachineWashTime] = Math.min(a1, a2);
            }

            // fill table
            for (int curCupIdx = arr.length - 2; curCupIdx >= 0; curCupIdx--) {
                for (int nextMachineWashTime = 0; nextMachineWashTime <= maxNextMachineWashTime; nextMachineWashTime++) {
                    // case1
                    int current1 = Math.max(arr[curCupIdx], nextMachineWashTime) + a;
                    int answer1 = Integer.MAX_VALUE;
                    if (current1 <= maxNextMachineWashTime) {
                        int next1 = table[curCupIdx + 1][current1];
                        answer1 = Math.max(current1, next1);
                    }

                    // case2
                    int current2 = arr[curCupIdx] + b;
                    int next2 = table[curCupIdx + 1][nextMachineWashTime];
                    int answer2 = Math.max(current2, next2);

                    table[curCupIdx][nextMachineWashTime] = Math.min(answer1, answer2);
                }
            }
            return table[0][0];
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int[] test = {1, 1, 5, 5, 7, 10, 12, 12, 12, 12, 12, 12, 15};
        int a1 = 3;
        int b1 = 10;
        System.out.println(BruteForce.washCupEarliestFinishTime(test, a1, b1));
        System.out.println(MemorySearch.washCupEarliestFinishTime(test, a1, b1));
        System.out.println(DP.washCupEarliestFinishTime(test, a1, b1));
        System.out.println(dp(test, a1, b1));
        a1 = 10;
        b1 = 3;
        System.out.println(BruteForce.washCupEarliestFinishTime(test, a1, b1));
        System.out.println(MemorySearch.washCupEarliestFinishTime(test, a1, b1));
        System.out.println(DP.washCupEarliestFinishTime(test, a1, b1));
        System.out.println(dp(test, a1, b1));

        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int a = RandomUtil.randomInt(1, 5);
            int b = RandomUtil.randomInt(1, 10);
            int[] arr = new int[6];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(1, 11);
            }
            if (a >= b) {
                int[] clone = arr.clone();
                Arrays.sort(clone);
                int r = dp(clone, a, b);
                int r1 = BruteForce.washCupEarliestFinishTime(arr, a, b);
                int r2 = MemorySearch.washCupEarliestFinishTime(arr, a, b);
                int r3 = DP.washCupEarliestFinishTime(arr, a, b);
                if (!(r == r1 && r1 == r2 && r2 == r3)) {
                    System.out.println("Oops--->arr=" + Arrays.toString(arr) + " a=" + a + " b=" + b + " r=" + r + " r1=" + r1);
                }
            } else {
                int r = dp(arr, a, b);
                int r1 = BruteForce.washCupEarliestFinishTime(arr, a, b);
                int r2 = MemorySearch.washCupEarliestFinishTime(arr, a, b);
                int r3 = DP.washCupEarliestFinishTime(arr, a, b);
                if (!(r == r1 && r1 == r2 && r2 == r3)) {
                    System.out.println("Oops--->arr=" + Arrays.toString(arr) + " a=" + a + " b=" + b + " r=" + r + " r1=" + r1);
                }
            }
        }
        System.out.println("good job!");
    }

    /* ****************************************************************************************************************/

    /* 对数器方法 */

    public static int dp(int[] drinks, int a, int b) {
        if (a >= b) {
            return drinks[drinks.length - 1] + b;
        }
        // a < b
        int N = drinks.length;
        int limit = 0; // 咖啡机什么时候可用
        for (int i = 0; i < N; i++) {
            limit = Math.max(limit, drinks[i]) + a;
        }
        int[][] dp = new int[N][limit + 1];
        // N-1行，所有的值
        for (int washLine = 0; washLine <= limit; washLine++) {
            dp[N - 1][washLine] = Math.min(Math.max(washLine, drinks[N - 1]) + a, drinks[N - 1] + b);
        }
        for (int index = N - 2; index >= 0; index--) {
            for (int washLine = 0; washLine <= limit; washLine++) {
                int p1 = Integer.MAX_VALUE;
                int wash = Math.max(washLine, drinks[index]) + a;
                if (wash <= limit) {
                    p1 = Math.max(wash, dp[index + 1][wash]);
                }
                int p2 = Math.max(drinks[index] + b, dp[index + 1][washLine]);
                dp[index][washLine] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

    // 方法二：稍微好一点的解法
    private static class Machine {
        int timePoint;
        int workTime;

        Machine(int t, int w) {
            timePoint = t;
            workTime = w;
        }
    }

    private static class MachineComparator implements Comparator<Machine> {
        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }
    }

    // 方法二，每个人暴力尝试用每一个咖啡机给自己做咖啡，优化成贪心
    public static int minTime2(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return process(drinks, a, b, 0, 0);
    }

    // 方法二，洗咖啡杯的方式和原来一样，只是这个暴力版本减少了一个可变参数

    // process(drinks, 3, 10, 0,0)
    // a 洗一杯的时间 固定变量
    // b 自己挥发干净的时间 固定变量
    // drinks 每一个员工喝完的时间 固定变量
    // drinks[0..index-1]都已经干净了，不用你操心了
    // drinks[index...]都想变干净，这是我操心的，washLine表示洗的机器何时可用
    // drinks[index...]变干净，最少的时间点返回
    public static int process(int[] drinks, int a, int b, int index, int washLine) {
        if (index == drinks.length - 1) {
            return Math.min(Math.max(washLine, drinks[index]) + a, drinks[index] + b);
        }
        // 剩不止一杯咖啡
        // wash是我当前的咖啡杯，洗完的时间
        int wash = Math.max(washLine, drinks[index]) + a;// 洗，index一杯，结束的时间点
        // index+1...变干净的最早时间
        int next1 = process(drinks, a, b, index + 1, wash);
        // index....
        int p1 = Math.max(wash, next1);
        int dry = drinks[index] + b; // 挥发，index一杯，结束的时间点
        int next2 = process(drinks, a, b, index + 1, washLine);
        int p2 = Math.max(dry, next2);
        return Math.min(p1, p2);
    }

    /* ****************************************************************************************************************/

}
