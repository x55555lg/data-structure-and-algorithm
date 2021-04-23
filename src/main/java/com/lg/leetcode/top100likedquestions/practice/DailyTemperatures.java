package com.lg.leetcode.top100likedquestions.practice;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-03-12 14:53
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
class DailyTemperatures {

    /*
     * Given a list of daily temperatures T, return a list such that, for each day in the input,
     * tells you how many days you would have to wait until a warmer temperature.
     * If there is no future day for which this is possible, put 0 instead.
     *
     * For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76, 73],
     * your output should be [1, 1, 4, 2, 1, 1, 0, 0].
     *
     * Note:
     * The length of temperatures will be in the range [1, 30000].
     * Each temperature will be an integer in the range [30, 100].
     */

    public static int[] dailyTemperatures(int[] T) {
        return BruteForce2.dailyTemperatures(T);
    }

    /**
     * AC但是很慢
     */
    private static class BruteForce1 {

        public static int[] dailyTemperatures(int[] temperatures) {
            if (temperatures == null || temperatures.length == 0) {
                return new int[0];
            }
            int[] result = new int[temperatures.length];
            result[result.length - 1] = 0;
            for (int idx = 0; idx < result.length; idx++) {
                int waitDays = 0;
                for (int t = idx + 1; t < temperatures.length; t++) {
                    if (temperatures[t] > temperatures[idx]) {
                        // 间隔天数
                        waitDays = t - idx;
                        break;
                    }
                }
                result[idx] = waitDays;
            }
            return result;
        }

    }

    /**
     * AC但是慢
     */
    private static class BruteForce2 {
        public static int[] dailyTemperatures(int[] temperatures) {
            if (temperatures == null || temperatures.length == 0) {
                return new int[0];
            }
            int[] result = new int[temperatures.length];
            result[result.length - 1] = 0;
            // [73, 74, 75, 71, 69, 72, 76, 73]
            //  0   1   2   3   4   5   6   7
            // [1,  1,  4,  2,  1,  1,  0,  0 ]
            for (int idx = result.length - 2; idx >= 0; idx--) {
                if (temperatures[idx] < temperatures[idx + 1]) {
                    // 这天的温度比下一天的低，那么这天只要等待一天就可以了
                    result[idx] = 1;
                } else {
                    // [idx+1...]上面第一个大于的位置
                    for (int t = idx + 1; t < temperatures.length; t++) {
                        if (temperatures[t] > temperatures[idx]) {
                            result[idx] = t - idx;
                            break;
                        }
                    }
                }
            }
            return result;
        }
    }

    /**
     * 没懂
     */
    private static class BestSolution {
        public static int[] dailyTemperatures(int[] temperatures) {
            if (temperatures == null || temperatures.length == 0) {
                return new int[0];
            }
            int[] result = new int[temperatures.length];
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < temperatures.length; i++) {
                while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                    int idx = stack.pop();
                    result[idx] = i - idx;
                }
                stack.push(i);
            }
            return result;
        }
    }

    public static void main(String[] args) {
        int[] temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};
        int[] dailyTemperatures = BruteForce1.dailyTemperatures(temperatures);
        System.out.println(Arrays.toString(dailyTemperatures));

        dailyTemperatures = BruteForce2.dailyTemperatures(temperatures);
        System.out.println(Arrays.toString(dailyTemperatures));

        dailyTemperatures = BestSolution.dailyTemperatures(temperatures);
        System.out.println(Arrays.toString(dailyTemperatures));
    }
}
