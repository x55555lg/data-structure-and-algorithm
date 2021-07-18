package com.lg.leetcode.topinterview.practice;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-03-01 17:32
 */
class LargestRectangleInHistogram {

    /*
     *  Given n non-negative integers representing the histogram's bar height where the width
     * of each bar is 1, find the area of largest rectangle in the histogram.
     *
     *  Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].
     * The largest rectangle is shown in the shaded area, which has area = 10 unit.
     *
     * Example:
     * Input: [2,1,5,6,2,3]
     * Output: 10
     *
     * Example 1:
     * Input: heights = [2,1,5,6,2,3]
     * Output: 10
     * Explanation: The above is a histogram where width of each bar is 1.
     * The largest rectangle is shown in the red area, which has an area = 10 units.
     *
     * Example 2:
     * Input: heights = [2,4]
     * Output: 4
     *
     * Constraints:
     *  1 <= heights.length <= 105
     *  0 <= heights[i] <= 104
     */

    public static int largestRectangleArea(int[] heights) {
        return 0;
    }

    /**
     * 暴力解，超时了
     */
    private static class BruteForce {

        public static int largestRectangleArea(int[] heights) {
            return recurse(heights, 0);
        }

        private static int recurse(int[] arr, int curIdx) {
            // base case
            if (curIdx == arr.length - 1) {
                // 最后一个柱子，柱子本身就是最大面积
                return arr[curIdx];
            }
            int curMax = arr[curIdx];
            int minHeight = arr[curIdx];
            for (int idx = curIdx + 1; idx < arr.length; idx++) {
                // [curIdx, idx]上最小height是多少
                //int minHeight = getMinHeight(arr, curIdx, idx);
                minHeight = Math.min(minHeight, arr[idx]);
                // [curIdx, idx]上的最大面积
                int rectangleArea = (idx - curIdx + 1) * minHeight;
                curMax = Math.max(rectangleArea, curMax);
            }
            int nextMaxRectangleArea = recurse(arr, curIdx + 1);
            return Math.max(curMax, nextMaxRectangleArea);
        }

        private static int getMinHeight(int[] arr, int left, int right) {
            int minHeight = Integer.MAX_VALUE;
            for (int i = left; i <= right; i++) {
                minHeight = Math.min(minHeight, arr[i]);
            }
            return minHeight;
        }
    }

    /**
     * 记忆化搜索，超时了
     */
    private static class MemorySearch {

        public static int largestRectangleArea(int[] heights) {
            int[] table = new int[heights.length + 1];
            Arrays.fill(table, -1);
            return recurse(heights, 0, table);
        }

        private static int recurse(int[] arr, int curIdx, int[] table) {
            if (table[curIdx] != -1) {
                // get the cache if exist
                return table[curIdx];
            }
            // base case
            if (curIdx == arr.length - 1) {
                table[curIdx] = arr[curIdx];
                return table[curIdx];
            }
            int curMax = arr[curIdx];
            int minHeight = arr[curIdx];
            for (int idx = curIdx + 1; idx < arr.length; idx++) {
                // [curIdx, idx]上最小height是多少
                //int minHeight = getMinHeight(arr, curIdx, idx);
                minHeight = Math.min(minHeight, arr[idx]);
                // [curIdx, idx]上的最大面积
                int rectangleArea = (idx - curIdx + 1) * minHeight;
                curMax = Math.max(rectangleArea, curMax);
            }
            int nextMaxRectangleArea = recurse(arr, curIdx + 1, table);
            int max = Math.max(curMax, nextMaxRectangleArea);
            table[curIdx] = max;
            return max;
        }

        private static int getMinHeight(int[] arr, int left, int right) {
            int minHeight = Integer.MAX_VALUE;
            for (int i = left; i <= right; i++) {
                minHeight = Math.min(minHeight, arr[i]);
            }
            return minHeight;
        }
    }

    /**
     * 动态规划表，超时了
     */
    private static class DP {

        public static int largestRectangleArea(int[] heights) {
            int[] table = new int[heights.length];
            // base case 最后一个柱子，柱子本身就是最大面积
            table[heights.length - 1] = heights[heights.length - 1];
            // 填表
            for (int curIdx = heights.length - 2; curIdx >= 0; curIdx--) {
                int curMax = heights[curIdx];
                int minHeight = heights[curIdx];
                for (int idx = curIdx + 1; idx < heights.length; idx++) {
                    // [curIdx, idx]上最小height是多少
                    //int minHeight = getMinHeight(heights, curIdx, idx);
                    minHeight = Math.min(minHeight, heights[idx]);
                    // [curIdx, idx]上的最大面积
                    int rectangleArea = (idx - curIdx + 1) * minHeight;
                    // 留最大的面积情况
                    curMax = Math.max(rectangleArea, curMax);
                }
                int nextMaxRectangleArea = table[curIdx + 1];
                table[curIdx] = Math.max(curMax, nextMaxRectangleArea);
            }
            return table[0];
        }

        private static int getMinHeight(int[] arr, int left, int right) {
            int minHeight = Integer.MAX_VALUE;
            for (int i = left; i <= right; i++) {
                minHeight = Math.min(minHeight, arr[i]);
            }
            return minHeight;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 1, 5, 6, 2, 3};
        int largestRectangleArea = MemorySearch.largestRectangleArea(arr);
        System.out.println(largestRectangleArea);

        largestRectangleArea = BruteForce.largestRectangleArea(arr);
        System.out.println(largestRectangleArea);

        largestRectangleArea = DP.largestRectangleArea(arr);
        System.out.println(largestRectangleArea);

        arr = new int[]{2, 4};
        largestRectangleArea = MemorySearch.largestRectangleArea(arr);
        System.out.println(largestRectangleArea);

        largestRectangleArea = BruteForce.largestRectangleArea(arr);
        System.out.println(largestRectangleArea);

        largestRectangleArea = DP.largestRectangleArea(arr);
        System.out.println(largestRectangleArea);

        largestRectangleArea = BruteForce.largestRectangleArea(new int[]{3});
        System.out.println(largestRectangleArea);

        arr = Arrays.stream(FileUtil.readUtf8String(new ClassPathResource("data.txt").getFile()).split(",")).mapToInt(Integer::valueOf).toArray();
        largestRectangleArea = DP.largestRectangleArea(arr);
        System.out.println(largestRectangleArea);
    }

}
