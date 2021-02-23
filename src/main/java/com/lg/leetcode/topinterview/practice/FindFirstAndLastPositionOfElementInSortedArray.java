package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-02-23 10:58
 */
class FindFirstAndLastPositionOfElementInSortedArray {

    /*
     * Given an array of integers nums sorted in ascending order, find the starting
     * and ending position of a given target value.
     * If target is not found in the array, return [-1, -1].
     * Follow up: Could you write an algorithm with O(log n) runtime complexity?
     *
     * Example 1:
     * Input: nums = [5,7,7,8,8,10], target = 8
     * Output: [3,4]
     *
     * Example 2:
     * Input: nums = [5,7,7,8,8,10], target = 6
     * Output: [-1,-1]
     *
     * Example 3:
     * Input: nums = [], target = 0
     * Output: [-1,-1]
     *
     * Constraints:
     * 0 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     * nums is a non-decreasing array.
     * -109 <= target <= 109
     *--------------------------------------------------------------------------------------
     */

    public static int[] searchRange(int[] nums, int target) {
        return binarySearch(nums, target);
    }

    private static int[] binarySearch(int[] arr, int target) {
        // 找到第一个等于target的位置
        int start = binarySearchFirstOneEquals(arr, target);
        // 找到最后一个等于target位置
        int end = binarySearchLastOneEquals(arr, target);
        return new int[]{start, end};
    }

    private static int binarySearchFirstOneEquals(int[] array, int target) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] > target) {
                high = mid - 1;
            } else if (array[mid] < target) {
                low = mid + 1;
            } else {
                // 值相等，那么是不是第一个呢
                assert array[mid] == target;
                if (mid == 0 || array[mid - 1] != target) {
                    // 数组中第一个元素就是，或者中间位置的前一个元素不等于key
                    return mid;
                } else {
                    // 中间值的前一个元素也是等于key啊，所以第一个等于key的值还在左边
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    private static int binarySearchLastOneEquals(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] > target) {
                high = mid - 1;
            } else if (array[mid] < target) {
                low = mid + 1;
            } else {
                // 值相等，那么是不是最后一个呢
                assert array[mid] == target;
                // 数组中最后一个元素就是，或者中间位置的后一个元素不等于key
                if (mid == array.length - 1 || array[mid + 1] != target) {
                    return mid;
                } else {
                    // 中间值的后一个元素也是等于key啊，所以最后一个等于key的值还在右边
                    low = mid + 1;
                }
            }
        }
        return -1;
    }

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    private static int[] bruteForce(int[] arr, int target) {
        int start = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                start = i;
                break;
            }
        }
        if (start == -1) {
            // 数组中没有target元素
            return new int[]{-1, -1};
        }
        int end = -1;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == target) {
                end = i;
                break;
            }
        }
        return new int[]{start, end};
    }

    public static void main(String[] args) {
        int[] range = searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println(Arrays.toString(range));

        range = searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6);
        System.out.println(Arrays.toString(range));

        range = searchRange(new int[]{5, 7, 7, 8, 8, 10}, 5);
        System.out.println(Arrays.toString(range));
    }

}
