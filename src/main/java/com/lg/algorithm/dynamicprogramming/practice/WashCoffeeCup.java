package com.lg.algorithm.dynamicprogramming.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Xulg
 * Created in 2020-12-15 14:13
 */
@SuppressWarnings("DuplicatedCode")
class WashCoffeeCup {

    /*
     * 文件中有5亿个取值范围为[0,10亿]的整数
     * 给2MB内存空间文件不能修改，只能读取找
     * 出一个文件中不存在的数
     */

    /*
     * 给定一个数组，代表每个人喝完咖啡准备刷杯子的时间
     * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯
     * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发
     * 返回让所有咖啡杯变干净的最早完成时间
     * 三个参数：int[] arr、int a、int b
     */

    /* ****************************************************************************************************************/

    /**
     * 暴力尝试
     *
     * @param arr 每个人喝完咖啡准备开始刷杯子的时间
     * @param a   咖啡机洗一个杯子耗费时间
     * @param b   咖啡杯自己挥发干净耗费时间
     * @return 所有咖啡杯变干净的最早完成时间
     */
    public static int getEarliestFinishTimeWashCoffeeCups(int[] arr, int a, int b) {
        if (arr == null || arr.length == 0 || a < 0 || b < 0) {
            return 0;
        }

        // 用咖啡机洗的时间比自然挥发还要慢啊，杯子都使用自然挥发的方式是最快的
        if (a >= b) {
            // 所有杯子都使用自然挥发的方式，最早完成时间就是最晚开始洗的咖啡杯时间加上自然挥发时间
            // earliestFinishTime = max(arr) + b;
            int earliestFinishTime = 0;
            for (int beginWashTime : arr) {
                earliestFinishTime = Math.max(earliestFinishTime, beginWashTime + b);
            }
            return earliestFinishTime;
        }

        // 递归进行各种尝试
        return recurse(arr, a, b, 0, 0);
    }

    /**
     * 暴力递归
     * cups[0...curCupIdx-1]都已经干净了，不用你操心了
     * cups[curCupIdx...]都想变干净，这是我操心的，nextCoffeeMachineCanWashTime表示洗的机器何时可用
     * cups[curCupIdx...]变干净，最少的时间点返回
     *
     * @param cups                         每个人喝完咖啡准备开始刷杯子的时间
     * @param washTime                     咖啡机洗一个杯子耗费时间
     * @param volatilizeTime               咖啡杯自己挥发干净耗费时间
     * @param curCupIdx                    当前正准备清洗哪个咖啡杯，变量
     * @param nextCoffeeMachineCanWashTime 下一次咖啡机可用的时间点，变量
     * @return cups[curCupIdx...]咖啡杯变干净的最早的时间点
     */
    private static int recurse(int[] cups, int washTime, int volatilizeTime, int curCupIdx, int nextCoffeeMachineCanWashTime) {
        // base case
        if (curCupIdx == cups.length - 1) {
            /*最后一个杯子了，看是用咖啡机快还是用自然挥发快*/
            int useCoffeeMachineTime = Math.max(cups[curCupIdx], nextCoffeeMachineCanWashTime) + washTime;
            int useVolatilizeTime = cups[curCupIdx] + volatilizeTime;
            // 看是哪种早
            return Math.min(useCoffeeMachineTime, useVolatilizeTime);
        }

        // 当前杯子开始清洗的时间
        int beginWashTime = cups[curCupIdx];

        // 当前咖啡杯用咖啡机洗完的时间 咖啡机下一次使用时间就得等到当前杯子洗完了
        int currentCupsUseCoffeeMachineFinishWashTime = Math.max(beginWashTime, nextCoffeeMachineCanWashTime) + washTime;
        // cups[curCupIdx+1...]咖啡杯变干净的最早时间
        int nextCupsFinishWashTime1 = recurse(cups, washTime, volatilizeTime, curCupIdx + 1, currentCupsUseCoffeeMachineFinishWashTime);
        // 这里要取咖啡杯最晚变干净的时间点
        int p1 = Math.max(currentCupsUseCoffeeMachineFinishWashTime, nextCupsFinishWashTime1);

        // 当前咖啡杯自然挥发干净的时间
        int currentCupsUseVolatilizationFinishWashTime = beginWashTime + volatilizeTime;
        // cups[curCupIdx+1...]咖啡杯变干净的最早时间
        int nextCupsFinishWashTime2 = recurse(cups, washTime, volatilizeTime, curCupIdx + 1, nextCoffeeMachineCanWashTime);
        // 这里要取咖啡杯最晚变干净的时间点
        int p2 = Math.max(currentCupsUseVolatilizationFinishWashTime, nextCupsFinishWashTime2);

        return Math.min(p1, p2);
    }

    /* ****************************************************************************************************************/

    /**
     * 动态规划
     *
     * @param arr 每个人喝完咖啡准备开始刷杯子的时间
     * @param a   咖啡机洗一个杯子耗费时间
     * @param b   咖啡杯自己挥发干净耗费时间
     * @return 所有咖啡杯变干净的最早完成时间
     */
    public static int getEarliestFinishTimeWashCoffeeCupsByDp1(int[] arr, int a, int b) {
        if (arr == null || arr.length == 0 || a < 0 || b < 0) {
            return 0;
        }

        // 用咖啡机洗的时间比自然挥发还要慢啊，杯子都使用自然挥发的方式是最快的
        if (a >= b) {
            // 所有杯子都使用自然挥发的方式，最早完成时间就是最晚开始洗的咖啡杯时间加上自然挥发时间
            // earliestFinishTime = max(arr) + b;
            int earliestFinishTime = 0;
            for (int beginWashTime : arr) {
                earliestFinishTime = Math.max(earliestFinishTime, beginWashTime + b);
            }
            return earliestFinishTime;
        }

        // 计算如果所有杯子都用咖啡机洗干净后，咖啡机什么时候可以再用
        int limit = 0;
        for (int beginWashTime : arr) {
            limit = Math.max(beginWashTime, limit) + a;
        }

        // 创建动态规划表，行表示咖啡杯的index位置，列表示咖啡机可使用的时间点
        // dpTable[row][column]的值表示洗完[0...row]咖啡杯的最早时间点
        int[][] dpTable = new int[arr.length][limit + 1];

        // 最后一行，表示最后一个杯子了，看是用咖啡机快还是用自然挥发快
        int lastRow = arr.length - 1;
        for (int column = 0; column <= limit; column++) {
            int beginWashTime = arr[lastRow];
            // 使用咖啡机洗完的时间点
            int useMachineFinishTime = Math.max(beginWashTime, column) + a;
            // 自然挥发洗完的时间点
            int volatilizationFinishTime = beginWashTime + b;
            dpTable[lastRow][column] = Math.min(useMachineFinishTime, volatilizationFinishTime);
        }

        // 从倒数第二行开始填表
        for (int curCupIdx = lastRow - 1; curCupIdx >= 0; curCupIdx--) {
            for (int nextMachineCanWashTime = 0; nextMachineCanWashTime <= limit; nextMachineCanWashTime++) {
                // 当前杯子开始清洗的时间
                int beginWashTime = arr[curCupIdx];

                int p1;
                // 当前咖啡杯用咖啡机洗完的时间 咖啡机下一次使用时间就得等到当前杯子洗完了
                int currentCupsUseCoffeeMachineFinishWashTime = Math.max(beginWashTime, nextMachineCanWashTime) + a;
                if (currentCupsUseCoffeeMachineFinishWashTime <= limit) {
                    // cups[curCupIdx+1...]咖啡杯变干净的最早时间
                    int nextCupsFinishWashTime1 = dpTable[curCupIdx + 1][currentCupsUseCoffeeMachineFinishWashTime];
                    // 这里要取咖啡杯最晚变干净的时间点
                    p1 = Math.max(currentCupsUseCoffeeMachineFinishWashTime, nextCupsFinishWashTime1);
                } else {
                    // 用咖啡机洗了之后，时间超限，这种方案无效
                    p1 = Integer.MAX_VALUE;
                }

                // 当前咖啡杯自然挥发干净的时间
                int currentCupsUseVolatilizationFinishWashTime = beginWashTime + b;
                // cups[curCupIdx+1...]咖啡杯变干净的最早时间
                int nextCupsFinishWashTime2 = dpTable[curCupIdx + 1][nextMachineCanWashTime];
                // 这里要取咖啡杯最晚变干净的时间点
                int p2 = Math.max(currentCupsUseVolatilizationFinishWashTime, nextCupsFinishWashTime2);

                dpTable[curCupIdx][nextMachineCanWashTime] = Math.min(p1, p2);
            }
        }
        return dpTable[0][0];
    }

    /* ****************************************************************************************************************/

    /* 对数器方法 */

    @SuppressWarnings("all")
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
    @SuppressWarnings("all")
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

    public static void main(String[] args) {
        int[] test = {1, 1, 5, 5, 7, 10, 12, 12, 12, 12, 12, 12, 15};
        int a1 = 3;
        int b1 = 10;
        System.out.println(recurse(test, a1, b1, 0, 0));
        System.out.println(getEarliestFinishTimeWashCoffeeCups(test, a1, b1));
        System.out.println(getEarliestFinishTimeWashCoffeeCupsByDp1(test, a1, b1));
        System.out.println(dp(test, a1, b1));
        a1 = 10;
        b1 = 3;
        System.out.println(recurse(test, a1, b1, 0, 0));
        System.out.println(getEarliestFinishTimeWashCoffeeCups(test, a1, b1));
        System.out.println(getEarliestFinishTimeWashCoffeeCupsByDp1(test, a1, b1));
        System.out.println(dp(test, a1, b1));

        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int a = RandomUtil.randomInt(1, 5);
            int b = RandomUtil.randomInt(1, 10);
            int[] arr = new int[6];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(1, 11);
            }
            //int r1 = minTime2(arr, arr.length, a, b);
            if (a >= b) {
                int[] clone = arr.clone();
                Arrays.sort(clone);
                int r = dp(clone, a, b);
                int r1 = getEarliestFinishTimeWashCoffeeCups(arr, a, b);
                int r2 = getEarliestFinishTimeWashCoffeeCupsByDp1(arr, a, b);
                if (!(r == r1 && r1 == r2)) {
                    System.out.println("Oops--->arr=" + Arrays.toString(arr) + " a=" + a + " b=" + b + " r=" + r + " r1=" + r1);
                }
            } else {
                int r = dp(arr, a, b);
                int r1 = getEarliestFinishTimeWashCoffeeCups(arr, a, b);
                int r2 = getEarliestFinishTimeWashCoffeeCupsByDp1(arr, a, b);
                if (!(r == r1 && r1 == r2)) {
                    System.out.println("Oops--->arr=" + Arrays.toString(arr) + " a=" + a + " b=" + b + " r=" + r + " r1=" + r1);
                }
            }
        }
    }

}
