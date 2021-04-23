package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-03-04 11:10
 */
class TrappingRainWater {

    /*
     * Given n non-negative integers representing an elevation map where the width of each bar is 1,
     * compute how much water it can trap after raining.
     *
     * Example 1:
     * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     * Explanation:
     *  The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
     *  In this case, 6 units of rain water (blue section) are being trapped.
     *
     * Example 2:
     * Input: height = [4,2,0,3,2,5]
     * Output: 9
     *
     * Constraints:
     *  n == height.length
     *  0 <= n <= 3 * 104
     *  0 <= height[i] <= 105
     */

    public static int trap(int[] height) {
        return 0;
    }

    private static class BruteForce {

        public static int trap(int[] height) {
            if (height == null || height.length == 0) {
                return 0;
            }
            return recurse(height, 0, 2);
        }

        // [startIdx, endIdx]范围内可以存多少水
        private static int recurse(int[] height, int startIdx, int endIdx) {
            if (startIdx >= height.length || endIdx >= height.length) {
                return 0;
            }

            // 边界柱子高度不能为0,
            boolean canSaveWater = canSaveWater(height, startIdx, endIdx);

            if (true) {
                // 不能存水
                recurse(height, startIdx + 1, endIdx + 1);

                return 0;
            } else {
                // 下一个范围
                return recurse(height, endIdx, endIdx + 2);
            }
        }

        private static boolean canSaveWater(int[] height, int startIdx, int endIdx) {
            if (height[startIdx] == 0 || height[endIdx] == 0) {
                // 有一个柱子高度为0，不能存水
                return false;
            }
            // arr[startIdx, endIdx]内的柱子都低于边界
            int leftBarHeight = height[startIdx], rightBarHeight = height[endIdx];
            int minBarHeight = Math.min(leftBarHeight, rightBarHeight);
            for (int barIdx = startIdx + 1; barIdx < endIdx; barIdx++) {
                if (height[barIdx] < minBarHeight) {
                    return true;
                }
            }
            return false;
        }

    }

}
