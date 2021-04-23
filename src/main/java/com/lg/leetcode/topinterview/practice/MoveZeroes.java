package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-03-04 17:41
 */
class MoveZeroes {

    /*
     * Given an array nums, write a function to move all 0's to the end of it while maintaining
     * the relative order of the non-zero elements.
     *
     * Example:
     *  Input: [0,1,0,3,12]
     *  Output: [1,3,12,0,0]
     *
     * Note:
     *  You must do this in-place without making a copy of the array.
     *  Minimize the total number of operations.
     */

    public static void moveZeroes(int[] nums) {
    }

    /**
     * O(N^2)
     */
    private static class BruteForce {

        public static void moveZeroes(int[] arr) {
            if (arr == null || arr.length == 0) {
                return;
            }
            // 0    1   0   3   12
            // 1    0   0   3   12
            // 1    3   0   0   12
            // 1    3   12  0   0
            for (int idx = 0; idx < arr.length; ) {
                int val = arr[idx];
                if (val == 0) {
                    // 找到后面第一个非0的位置
                    int swapIdx = -1;
                    for (int i = idx + 1; i < arr.length; i++) {
                        if (arr[i] != 0) {
                            swapIdx = i;
                            break;
                        }
                    }
                    // 好不到说明已经交换完了
                    if (swapIdx == -1) {
                        break;
                    }
                    swap(arr, idx, swapIdx);
                } else {
                    idx++;
                }
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

    }

    /**
     * O(N)
     */
    private static class Solution {

        public static void moveZeroes(int[] arr) {
            if (arr == null || arr.length == 0) {
                return;
            }
            /*
             * 0    1   0   3   12
             * position = 0
             *--------------------
             *--------------------
             * 1    0   0   3   12
             * position = 1
             *
             * 1    3   0   0   12
             * position = 2
             *
             * 1    3   12  0   0
             * position = 3
             */
            int position = 0;
            for (int idx = 0; idx < arr.length; idx++) {
                if (arr[idx] != 0) {
                    // 非0的数前移
                    arr[position] = arr[idx];
                    // 前移后，该位置的数置为0
                    // 此处需要判断同一位置就不能操作
                    if (idx != position) {
                        arr[idx] = 0;
                    }
                    position++;
                }
            }
        }

    }

    public static void main(String[] args) {
        int[] arr = {0, 1, 0, 3, 12};
        Solution.moveZeroes(arr);
        System.out.println(Arrays.toString(arr));

        arr = new int[]{1};
        Solution.moveZeroes(arr);
        System.out.println(Arrays.toString(arr));
    }

}
