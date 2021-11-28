package com.lg.leetcode.other.practice;

/**
 * @author Xulg
 * Description: leetcode_704
 * Created in 2021-05-31 15:07
 */
class BinarySearch {

    /*
     *  Given an array of integers nums which is sorted in ascending order, and an integer
     * target, write a function to search target in nums. If target exists, then return its
     * index. Otherwise, return -1.
     *  You must write an algorithm with O(log n) runtime complexity.
     *
     * Example 1:
     * Input: nums = [-1,0,3,5,9,12], target = 9
     * Output: 4
     * Explanation: 9 exists in nums and its index is 4
     *
     * Example 2:
     * Input: nums = [-1,0,3,5,9,12], target = 2
     * Output: -1
     * Explanation: 2 does not exist in nums so return -1
     *
     * Constraints:
     * 1 <= nums.length <= 104
     * -9999 <= nums[i], target <= 9999
     * All the integers in nums are unique.
     * nums is sorted in an ascending order.
     */

    public static int search(int[] nums, int target) {
        return -1;
    }

    private static class Loop {
        public static int search(int[] arr, int target) {
            int left = 0, right = arr.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (arr[mid] > target) {
                    right = mid - 1;
                } else if (arr[mid] < target) {
                    left = mid + 1;
                } else {
                    assert arr[mid] == target;
                    return mid;
                }
            }
            return -1;
        }
    }

    private static class Recurse {
        public static int search(int[] arr, int target) {
            return recurse(arr, target, 0, arr.length - 1);
        }

        private static int recurse(int[] arr, int target, int left, int right) {
            if (left > right) {
                return -1;
            }
            int mid = left + ((right - left) >> 1);
            if (arr[mid] > target) {
                return recurse(arr, target, left, mid - 1);
            } else if (arr[mid] < target) {
                return recurse(arr, target, mid + 1, right);
            } else {
                assert arr[mid] == target;
                return mid;
            }
        }
    }

}
