package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-03-02 11:41
 */
class ProductOfArrayExceptSelf {

    /*
     * Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to
     * the product of all the elements of nums except nums[i].
     * Example:
     *  Input:  [1,2,3,4]
     *  Output: [24,12,8,6]
     *  Constraint: It's guaranteed that the product of the elements of any prefix or suffix of the array
     *              (including the whole array) fits in a 32 bit integer.
     *
     * Note: Please solve it without division and in O(n).
     * Follow up:
     * Could you solve it with constant space complexity? (The output array does not count as
     * extra space for the purpose of space complexity analysis.)
     */

    public static int[] productExceptSelf(int[] nums) {
        return Solution.productExceptSelf(nums);
    }

    private static class Solution {

        public static int[] productExceptSelf(int[] arr) {
            // 数组总的乘积
            int totalProduct = 1;
            for (int i = 0; i < arr.length; i++) {
                totalProduct *= arr[i];
            }

            // TODO: 2021/3/2 错误了，arr中有0就不对了

            int[] result = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                // 总乘积 / arr[i] = 除了arr[i]以外所有数的乘积
                result[i] = totalProduct / arr[i];
            }

            return result;
        }

    }

    public static void main(String[] args) {
        int[] productExceptSelf = Solution.productExceptSelf(new int[]{1, 2, 3, 4});
        System.out.println(Arrays.toString(productExceptSelf));
    }

}
