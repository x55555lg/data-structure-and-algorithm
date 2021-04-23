package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-03-01 14:08
 */
class SortColors {

    /*
     * Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the
     * same color are adjacent, with the colors in the order red, white, and blue.
     *
     * We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
     *
     * Example 1:
     * Input: nums = [2,0,2,1,1,0]
     * Output: [0,0,1,1,2,2]
     *
     * Example 2:
     * Input: nums = [2,0,1]
     * Output: [0,1,2]
     *
     * Example 3:
     * Input: nums = [0]
     * Output: [0]
     *
     * Example 4:
     * Input: nums = [1]
     * Output: [1]
     *
     * Constraints:
     *  n == nums.length
     *  1 <= n <= 300
     *  nums[i] is 0, 1, or 2.
     *
     * Follow up:
     *  Could you solve this problem without using the library's sort function?
     *  Could you come up with a one-pass algorithm using only O(1) constant space?
     *-----------------------------------------------------------------------------
     * 给颜色排序
     *  0   红
     *  1   白
     *  2   蓝
     */

    public static void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        partition(nums, 0, nums.length - 1);
    }

    /**
     * 荷兰旗分区
     * 0全在arr左边，1在arr中间，2在arr右边
     */
    private static void partition(int[] arr, int left, int right) {
        int less = left - 1;
        int more = right + 1;
        for (int idx = left; idx < more; ) {
            if (arr[idx] == 0) {
                swap(arr, idx++, ++less);
            } else if (arr[idx] == 2) {
                swap(arr, idx, --more);
            } else {
                // arr[idx] == 1
                idx++;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 0, 2, 1, 1, 0};
        sortColors(arr);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{2, 0, 1};
        sortColors(arr);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1};
        sortColors(arr);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{0};
        sortColors(arr);
        System.out.println(Arrays.toString(arr));
    }

}
