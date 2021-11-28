package com.lg.leetcode.other.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Description: leetcode_700
 * Created in 2021-06-01 10:56
 */
class LongestIncreasingSubsequence {
    /*
     *  Given an integer array nums, return the length of the longest strictly increasing subsequence.
     *  A subsequence is a sequence that can be derived from an array by deleting some or no elements
     * without changing the order of the remaining elements. For example, [3,6,2,7] is a subsequence
     * of the array [0,3,1,6,2,2,7].
     *
     * Example 1:
     * Input: nums = [10,9,2,5,3,7,101,18]
     * Output: 4
     * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
     *
     * Example 2:
     * Input: nums = [0,1,0,3,2,3]
     * Output: 4
     *
     * Example 3:
     * Input: nums = [7,7,7,7,7,7,7]
     * Output: 1
     *
     * Constraints:
     * 1 <= nums.length <= 2500
     * -104 <= nums[i] <= 104
     */

    public static int lengthOfLIS(int[] nums) {
        return 0;
    }

    /**
     * // TODO 2021/6/1 BUG
     */
    private static class BruteForce {
        public static int lengthOfLIS(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            if (arr.length == 1) {
                return 1;
            }
            return recurse(arr.length - 1, arr);
        }

        private static int recurse(int idx, int[] arr) {
            // base case
            if (idx == 0) {
                return arr[0] < arr[1] ? 1 : 0;
            }
            // base case
            if (idx == arr.length - 1) {
                // 最长递增子序列以arr[n-1]结尾
                int r1 = 1 + recurse(idx - 1, arr);
                int r2 = recurse(idx - 1, arr);
                return Math.max(r1, r2);
            }
            // 最长递增子序列以arr[idx]结尾
            int r1 = arr[idx] < arr[idx + 1] ? 1 + recurse(idx - 1, arr) : 0;
            // 最长递增子序列不以arr[idx]结尾
            int r2 = recurse(idx - 1, arr);
            return Math.max(r1, r2);
        }
    }

    private static class MemorySearch {
        public static int lengthOfLIS(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            if (arr.length == 1) {
                return 1;
            }
            int[] table = new int[arr.length];
            Arrays.fill(table, -1);
            return recurse(arr.length - 1, arr, table);
        }

        private static int recurse(int idx, int[] arr, int[] table) {
            if (table[idx] != -1) {
                return table[idx];
            }
            // base case
            if (idx == 0) {
                int r = arr[0] < arr[1] ? 1 : 0;
                table[0] = r;
                return r;
            }
            // base case
            if (idx == arr.length - 1) {
                // 最长递增子序列以arr[n-1]结尾
                int r = 1 + recurse(idx - 1, arr, table);
                table[idx] = r;
                return r;
            }
            // 最长递增子序列以arr[idx]结尾
            int r1 = arr[idx] < arr[idx + 1] ? 1 + recurse(idx - 1, arr, table) : 0;
            // 最长递增子序列不以arr[idx]结尾
            int r2 = recurse(idx - 1, arr, table);
            int r = Math.max(r1, r2);
            table[idx] = r;
            return r;
        }
    }

    public static void main(String[] args) {
        System.out.println(BruteForce.lengthOfLIS(new int[]{4, 10, 4, 3, 8, 9}));
    }
}
