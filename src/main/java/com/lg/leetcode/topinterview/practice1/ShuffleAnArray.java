package com.lg.leetcode.topinterview.practice1;

import java.util.Random;

/**
 * @author Xulg
 * Created in 2021-02-26 9:38
 */
class ShuffleAnArray {

    /*
     * Given an integer array nums, design an algorithm to randomly shuffle the array.
     *
     * Implement the Solution class:
     *  Solution(int[] nums) Initializes the object with the integer array nums.
     *  int[] reset() Resets the array to its original configuration and returns it.
     *  int[] shuffle() Returns a random shuffling of the array.
     *
     * Example 1:
     * Input
     * ["Solution", "shuffle", "reset", "shuffle"]
     * [[[1, 2, 3]], [], [], []]
     * Output
     * [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
     *
     * Explanation
     *  Solution solution = new Solution([1, 2, 3]);
     *  solution.shuffle();    // Shuffle the array [1,2,3] and return its result. Any permutation of [1,2,3] must be equally likely to be returned. Example: return [3, 1, 2]
     *  solution.reset();      // Resets the array back to its original configuration [1,2,3]. Return [1, 2, 3]
     *  solution.shuffle();    // Returns the random shuffling of array [1,2,3]. Example: return [1, 3, 2]
     *
     *
     *
     * Constraints:
     *  1 <= nums.length <= 200
     *  -106 <= nums[i] <= 106
     *  All the elements of nums are unique.
     *  At most 5 * 104 calls will be made to reset and shuffle.
     */

    private static class Solution {

        private final int[] originalArray;
        private final int[] cloneArr;
        private final Random rnd = new Random();

        public Solution(int[] nums) {
            this.originalArray = nums;
            cloneArr = originalArray.clone();
        }

        /**
         * Resets the array to its original configuration and return it.
         */
        public int[] reset() {
            return originalArray.clone();
        }

        /**
         * Returns a random shuffling of the array.
         */
        public int[] shuffle() {
            for (int i = cloneArr.length; i > 1; i--) {
                swap(cloneArr, i - 1, rnd.nextInt(i));
            }
            return cloneArr;
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

}
