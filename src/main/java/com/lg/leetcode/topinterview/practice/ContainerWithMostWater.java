package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-03-01 11:30
 */
@SuppressWarnings("DuplicatedCode")
class ContainerWithMostWater {

    /*
     * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical
     * lines are drawn such that the two endpoints of the line i is at (i, ai) and (i, 0).
     * Find two lines, which, together with the x-axis forms a container, such that the container contains the most water.
     * Notice that you may not slant the container.
     *
     * Example 1:
     * Input: height = [1,8,6,2,5,4,8,3,7]
     * Output: 49
     * Explanation:
     *  The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case,
     *  the max area of water (blue section) the container can contain is 49.
     *
     * Example 2:
     * Input: height = [1,1]
     * Output: 1
     *
     * Example 3:
     * Input: height = [4,3,2,1,4]
     * Output: 16
     *
     * Example 4:
     * Input: height = [1,2,1]
     * Output: 2
     *
     * Constraints:
     *  n == height.length
     *  2 <= n <= 3 * 104
     *  0 <= height[i] <= 3 * 104
     */

    public static int maxArea(int[] height) {
        return DP.maxArea(height);
    }

    /**
     * 暴力解
     */
    private static class BruteForce {

        public static int maxArea(int[] height) {
            if (height == null || height.length == 0) {
                return 0;
            }
            return recurse(height, 0);
        }

        private static int recurse(int[] arr, int startIdx) {
            if (startIdx == arr.length) {
                return 0;
            }

            // 1    8    6    2    5    4    8    3    7
            // 0    1    2    3    4    5    6    7    8

            // startIdx点和后面的点组成的面积，求最大值
            int maxVal = Integer.MIN_VALUE;
            for (int idx = startIdx + 1; idx < arr.length; idx++) {
                // 计算[startIdx, idx]之间的面积
                int area = (idx - startIdx) * (Math.min(arr[startIdx], arr[idx]));
                maxVal = Math.max(maxVal, area);
            }
            // 看下一个点和后面的点的面积，求最大值
            int nextMaxArea = recurse(arr, startIdx + 1);
            // 取最大的
            return Math.max(maxVal, nextMaxArea);
        }

    }

    /**
     * 记忆化搜索
     */
    private static class MemorySearch {

        public static int maxArea(int[] height) {
            if (height == null || height.length == 0) {
                return 0;
            }
            int[] table = new int[height.length + 1];
            Arrays.fill(table, -1);
            return recurse(height, 0, table);
        }

        private static int recurse(int[] arr, int startIdx, int[] table) {
            if (table[startIdx] != -1) {
                // get value from cache
                return table[startIdx];
            }

            // base case
            if (startIdx == arr.length) {
                int r = 0;
                table[startIdx] = r;
                return r;
            }

            // 1    8    6    2    5    4    8    3    7
            // 0    1    2    3    4    5    6    7    8

            // startIdx点和后面的点组成的面积，求最大值
            int maxVal = Integer.MIN_VALUE;
            for (int idx = startIdx + 1; idx < arr.length; idx++) {
                // 计算[startIdx, idx]之间的面积
                int area = (idx - startIdx) * (Math.min(arr[startIdx], arr[idx]));
                maxVal = Math.max(maxVal, area);
            }
            // 看下一个点和后面的点的面积，求最大值
            int nextMaxArea = recurse(arr, startIdx + 1, table);

            // 取最大的
            int max = Math.max(maxVal, nextMaxArea);
            table[startIdx] = max;

            return max;
        }

    }

    /**
     * 动态规划表
     */
    private static class DP {

        public static int maxArea(int[] height) {
            if (height == null || height.length == 0) {
                return 0;
            }

            int[] table = new int[height.length + 1];
            // base case
            table[height.length] = 0;

            // 填表
            for (int startIdx = height.length - 1; startIdx >= 0; startIdx--) {
                // startIdx点和后面的点组成的面积，求最大值
                int maxVal = Integer.MIN_VALUE;
                for (int idx = startIdx + 1; idx < height.length; idx++) {
                    // 计算[startIdx, idx]之间的面积
                    int area = (idx - startIdx) * (Math.min(height[startIdx], height[idx]));
                    maxVal = Math.max(maxVal, area);
                }
                // 看下一个点和后面的点的面积，求最大值
                int nextMaxArea = table[startIdx + 1];
                // 取最大的
                table[startIdx] = Math.max(maxVal, nextMaxArea);
            }
            return table[0];
        }

    }

    /**
     * 动态规划表，优化版去掉中间的for循环
     */
    private static class DP2 {

        public static int maxArea(int[] height) {
            if (height == null || height.length == 0) {
                return 0;
            }

            int[] table = new int[height.length + 1];
            // base case
            table[height.length] = 0;

            // 填表
            for (int startIdx = height.length - 1; startIdx >= 0; startIdx--) {

                // TODO: 2021/3/1 优化掉这个for循环，画表找规律
                // startIdx点和后面的点组成的面积，求最大值
                int maxVal = Integer.MIN_VALUE;
                for (int idx = startIdx + 1; idx < height.length; idx++) {
                    // 计算[startIdx, idx]之间的面积
                    int area = (idx - startIdx) * (Math.min(height[startIdx], height[idx]));
                    maxVal = Math.max(maxVal, area);
                }

                // 看下一个点和后面的点的面积，求最大值
                int nextMaxArea = table[startIdx + 1];
                // 取最大的
                table[startIdx] = Math.max(maxVal, nextMaxArea);
            }
            return table[0];
        }

    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        int maxArea = MemorySearch.maxArea(arr);
        System.out.println(maxArea);

        arr = new int[]{4, 3, 2, 1, 4};
        maxArea = MemorySearch.maxArea(arr);
        System.out.println(maxArea);
    }

}
