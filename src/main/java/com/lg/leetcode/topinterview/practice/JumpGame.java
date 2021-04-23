package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-03-01 13:12
 */
class JumpGame {

    /*
     * Given an array of non-negative integers nums, you are initially positioned at the first index of the array.
     * Each element in the array represents your maximum jump length at that position.
     * Determine if you are able to reach the last index.
     *
     * Example 1:
     * Input: nums = [2,3,1,1,4]
     * Output: true
     * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
     *
     * Example 2:
     * Input: nums = [3,2,1,0,4]
     * Output: false
     * Explanation:
     *  You will always arrive at index 3 no matter what. Its maximum jump length is 0,
     *  which makes it impossible to reach the last index.
     *
     * Constraints:
     *  1 <= nums.length <= 3 * 104
     *  0 <= nums[i] <= 105
     */

    public static boolean canJump(int[] nums) {
        return BruteForce.canJump(nums);
    }

    /**
     * 暴力解
     */
    private static class BruteForce {

        public static boolean canJump(int[] arr) {
            if (arr == null || arr.length == 0) {
                return false;
            }
            if (arr.length == 1) {
                return true;
            }
            return recurse2(arr, 0);
            // 从1位置开始
            //return recurse(arr, arr.length - 1, 1, 0);
        }

        private static boolean recurse3(int[] arr, int curIdx, int destIdx) {
            if (curIdx >= arr.length) {
                return false;
            }
            int remainderSteps = arr[curIdx];
            if (remainderSteps <= 0) {
                // 当前没有步数可走
                return false;
            }

            return false;
        }

        private static boolean recurse2(int[] arr, int curIdx) {
            if (curIdx >= arr.length - 1) {
                // 越过目的地了
                return true;
            }
            if (arr[curIdx] <= 0) {
                // 没有步数了
                return false;
            }
            // 跳转到下一个位置
            return recurse2(arr, curIdx + arr[curIdx]);
        }

        private static boolean recurse(int[] arr, int destIdx, int curIdx, int remainderSteps) {
            if (curIdx >= destIdx) {
                // 越过目的地了
                return true;
            }
            // 加上当前位置的步数，剩余几步
            remainderSteps = remainderSteps + arr[curIdx];
            if (remainderSteps <= 0) {
                // 没有步数了
                return false;
            }
            // 走了一步，加上当前位置的步数，还剩余多少步可以走
            return recurse(arr, destIdx, curIdx + 1, remainderSteps - 1);
        }

        private static boolean recurse(int[] arr, int curIdx, int destIdx) {
            if (curIdx >= arr.length) {
                // 越界了咯
                return true;
            }
            if (curIdx == destIdx) {
                // 跳到终点了
                return true;
            }
            // 可以跳几步
            int jumpSteps = arr[curIdx];
            if (jumpSteps <= 0) {
                // 没有步数可以跳了啊
                return false;
            }
            return recurse(arr, curIdx + jumpSteps, destIdx);
        }
    }

    /**
     * 贪心算法
     */
    private static class Greedy {

        public static boolean canJump(int[] arr) {
            if (arr == null || arr.length == 0) {
                return false;
            }
            if (arr.length == 1) {
                return true;
            }
            int reach = arr[0];
            for (int i = 1; i < arr.length && reach >= i; i++) {
                if (i + arr[i] > reach) {
                    reach = i + arr[i];
                }
            }
            return reach >= (arr.length - 1);
        }

    }

    public static int jump(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 跳了多少步
        int step = 0;
        // step步内，右边界
        int cur = 0;
        // step+1步内，右边界
        int next = 0;
        for (int i = 0; i < arr.length; i++) {
            if (cur < i) {
                step++;
                cur = next;
            }
            next = Math.max(next, i + arr[i]);
        }
        return step;
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 1, 4};
        // except true
        System.out.println(BruteForce.canJump(arr));

        arr = new int[]{3, 2, 1, 0, 4};
        // except false
        System.out.println(BruteForce.canJump(arr));

        arr = new int[]{2, 0};
        // except true
        System.out.println(BruteForce.canJump(arr));

        arr = new int[]{2, 5, 0, 0};
        // except true
        System.out.println(BruteForce.canJump(arr));

        arr = new int[]{1, 2, 0, 1};
        // except true
        System.out.println(BruteForce.canJump(arr));

        arr = new int[]{2, 0, 0};
        // except true
        System.out.println(BruteForce.canJump(arr));
    }

}
