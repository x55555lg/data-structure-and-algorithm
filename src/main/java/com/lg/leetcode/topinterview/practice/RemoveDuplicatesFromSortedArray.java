package com.lg.leetcode.topinterview.practice;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-03-01 14:45
 */
@SuppressWarnings("ManualArrayCopy")
class RemoveDuplicatesFromSortedArray {

    /*
     * Given a sorted array nums, remove the duplicates in-place such that each element appears only once and returns the new length.
     * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
     *
     * Clarification:
     *  Confused why the returned value is an integer but your answer is an array?
     *  Note that the input array is passed in by reference, which means a modification to
     *  the input array will be known to the caller as well.
     *
     * Internally you can think of this:
     *      // nums is passed in by reference. (i.e., without making a copy)
     *      int len = removeDuplicates(nums);
     *      // any modification to nums in your function would be known by the caller.
     *      // using the length returned by your function, it prints the first len elements.
     *      for (int i = 0; i < len; i++) {
     *          print(nums[i]);
     *      }
     *
     * Example 1:
     * Input: nums = [1,1,2]
     * Output: 2, nums = [1,2]
     * Explanation:
     *  Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
     *  It doesn't matter what you leave beyond the returned length.
     *
     * Example 2:
     * Input: nums = [0,0,1,1,1,2,2,3,3,4]
     * Output: 5, nums = [0,1,2,3,4]
     * Explanation:
     *  Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3,
     *  and 4 respectively. It doesn't matter what values are set beyond the returned length.
     *
     * Constraints:
     *  0 <= nums.length <= 3 * 104
     *  -104 <= nums[i] <= 104
     *  nums is sorted in ascending order.
     */

    public static int removeDuplicates(int[] nums) {
        return Solution.removeDuplicates(nums);
    }

    /**
     * 时间复杂度O(N*logN)
     * 空间复杂度O(1)
     */
    private static class Solution {

        public static int removeDuplicates(int[] nums) {
            return doRemoveDuplicates(nums);
        }

        private static int doRemoveDuplicates(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            /*
             * arr = { 0    0    1    1    1    2    2    3    3    4 }
             * 0    1    1    1    2    2    3    3    4
             * 0    1    2    2    3    3    4
             * 0    1    2    3    3    4
             * 0    1    2    3    4
             */
            int totalCount = arr.length;
            for (int idx = 0; idx < totalCount; idx++) {
                // 查找arr[idx]出现的第1个位置和最后1个位置
                // 第1个位置就是idx
                int lastIdx = binarySearchLastEquals(arr, idx, totalCount - 1, arr[idx]);

                // 减去重复的数量
                int duplicateCount = lastIdx - idx;

                // [lastIdx+1, n)往前移(lastIdx - firstIdx)个位置
                // 覆盖到[firstIdx+1, lastIdx]
                for (int index = lastIdx + 1; index < totalCount; index++) {
                    arr[index - duplicateCount] = arr[index];
                }

                // 从总数中扣除
                totalCount = totalCount - duplicateCount;
            }
            return totalCount;
        }

        private static int binarySearchLastEquals(int[] arr, int left, int right, int pivot) {
            if (left > right) {
                return -1;
            }
            if (left == right) {
                return arr[left] == pivot ? left : -1;
            }
            int low = left, high = right;
            while (low <= high) {
                // the middle position
                int middle = low + ((high - low) >> 1);
                if (arr[middle] > pivot) {
                    high = middle - 1;
                } else if (arr[middle] < pivot) {
                    low = middle + 1;
                } else {
                    assert arr[middle] == pivot;
                    // is the last one equals?
                    if (middle == right || arr[middle + 1] != pivot) {
                        // middle已经是n位置了
                        // arr[middle]和后一个位置的值不相等，middle就是最后一个
                        return middle;
                    } else {
                        // 还不是最后一个等于的，最后一个等于的数在右边
                        low = middle + 1;
                    }
                }
            }
            return -1;
        }

    }

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    private static class SolutionBest {

        public static int removeDuplicates(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            if (nums.length == 1) {
                // 数组只有1个数
                return nums.length;
            }

            /*
             * arr = { 0    0    1    1    1    2    2    3    3    4 }
             *---------------------------------------------------------
             * 0    1    1    1    2    2    3    3    4
             * 0    1    2    2    3    3    4
             * 0    1    2    3    3    4
             * 0    1    2    3    4
             */

            // 累计重复了多少个数
            int duplicateCount = 0;
            for (int idx = 1; idx < nums.length; idx++) {
                if (nums[idx - 1] == nums[idx]) {
                    // 当前的数和前一个重复了
                    duplicateCount++;
                }
                // 第一个可用来存放数据的不重复位置
                nums[idx - duplicateCount] = nums[idx];
            }
            // 返回去重后的数量
            return nums.length - duplicateCount;
        }

    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 1, 2};
        int duplicates = removeDuplicates(arr);
        int[] copyOf = Arrays.copyOf(arr, duplicates);
        System.out.println(Arrays.toString(copyOf));

        arr = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        duplicates = removeDuplicates(arr);
        copyOf = Arrays.copyOf(arr, duplicates);
        System.out.println(Arrays.toString(copyOf));

        arr = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        duplicates = SolutionBest.removeDuplicates(arr);
        copyOf = Arrays.copyOf(arr, duplicates);
        System.out.println(Arrays.toString(copyOf));
    }

}
