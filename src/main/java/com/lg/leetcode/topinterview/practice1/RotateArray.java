package com.lg.leetcode.topinterview.practice1;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-02-28 21:50
 */
@SuppressWarnings("ManualArrayCopy")
class RotateArray {

    /*
     * Given an array, rotate the array to the right by k steps, where k is non-negative.
     * Follow up:
     *   Try to come up as many solutions as you can, there are at least
     *  3 different ways to solve this problem.
     *   Could you do it in-place with O(1) extra space?
     *
     * Example 1:
     * Input: nums = [1,2,3,4,5,6,7], k = 3
     * Output: [5,6,7,1,2,3,4]
     * Explanation:
     * rotate 1 steps to the right: [7,1,2,3,4,5,6]
     * rotate 2 steps to the right: [6,7,1,2,3,4,5]
     * rotate 3 steps to the right: [5,6,7,1,2,3,4]
     *
     * Example 2:
     * Input: nums = [-1,-100,3,99], k = 2
     * Output: [3,99,-1,-100]
     * Explanation:
     * rotate 1 steps to the right: [99,-1,-100,3]
     * rotate 2 steps to the right: [3,99,-1,-100]
     *
     * Constraints:
     *  1 <= nums.length <= 2 * 104
     *  -231 <= nums[i] <= 231 - 1
     *  0 <= k <= 105
     */

    public static void main(String[] args) {
        /*
         * 2    3   4   5   6   7   1
         * 3    4   5   6   7   1   2
         * 4    5   6   7   1   2   3
         * 5    6   7   1   2   3   4
         * 6    7   1   2   3   4   5
         * 7    1   2   3   4   5   6
         * 1    2   3   4   5   6   7
         *
         * 2    3   4   5   6   7   1
         */
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        rotate(arr, 1);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotate(arr, 2);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotate(arr, 3);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotate(arr, 4);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotate(arr, 5);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotate(arr, 6);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotate(arr, 7);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{-1, -100, 3, 99};
        rotate(arr, 2);
        System.out.println(Arrays.toString(arr));
    }

    public static void rotate(int[] nums, int k) {
    }

}
