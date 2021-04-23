package com.lg.leetcode.topinterview.practice;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-03-09 9:54
 */
class MajorityElement {

    /*
     * Given an array nums of size n, return the majority element.
     * The majority element is the element that appears more than ⌊n / 2⌋ times.
     * You may assume that the majority element always exists in the array.
     *
     * Example 1:
     * Input: nums = [3,2,3]
     * Output: 3
     *
     * Example 2:
     * Input: nums = [2,2,1,1,1,2,2]
     * Output: 2
     *
     * Constraints:
     *  n == nums.length
     *  1 <= n <= 5 * 104
     *  -2^31 <= nums[i] <= 2^31 - 1
     *-----------------------------------------------------------------------------
     * 给定一个大小为n的数组nums，返回多数元素。
     * 多数元素是出现超过⌊n/2⌋次的元素。您可以假设数组中始终存在多数元素。
     */

    public static int majorityElement(int[] nums) {
        return BruteForce.majorityElement(nums);
    }

    /**
     * Map计数器
     */
    private static class BruteForce {

        public static int majorityElement(int[] arr) {
            assert arr != null && arr.length > 0;
            int mid = arr.length >> 1;
            HashMap<Integer, Integer> countMap = new HashMap<>(
                    arr.length * 4 / 3 + 1);
            for (int val : arr) {
                if (countMap.containsKey(val)) {
                    countMap.put(val, countMap.get(val) + 1);
                } else {
                    countMap.put(val, 1);
                }
                if (countMap.get(val) > mid) {
                    return val;
                }
            }
            for (int val : arr) {
                if (countMap.get(val) > mid) {
                    return val;
                }
            }
            throw new RuntimeException();
        }

    }

    /**
     * BFPRT算法
     */
    private static class BFPRT {

        public static int majorityElement(int[] arr) {
            assert arr != null && arr.length > 0;
            int mid = arr.length >> 1;
            return bfprt(arr, 0, arr.length - 1, mid);
        }

        private static int bfprt(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return arr[index];
            }
            int median = getMedianOfMedians(arr, left, right);

            int[] equalsZone = partition(arr, left, right, median);
            int start = equalsZone[0], end = equalsZone[1];

            if (index >= start && index <= end) {
                return arr[index];
            } else if (index < start) {
                return bfprt(arr, left, start - 1, index);
            } else {
                // index > end
                return bfprt(arr, end + 1, right, index);
            }
        }

        private static int[] partition(int[] arr, int left, int right, int pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int lessThanIdx = left - 1;
            int moreThanIdx = right + 1;
            for (int idx = left; idx < moreThanIdx; ) {
                if (arr[idx] > pivot) {
                    swap(arr, idx, --moreThanIdx);
                } else if (arr[idx] < pivot) {
                    swap(arr, idx++, ++lessThanIdx);
                } else {
                    // arr[idx] == pivot
                    idx++;
                }
            }
            if (lessThanIdx + 1 > moreThanIdx - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{lessThanIdx + 1, moreThanIdx - 1};
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        private static int getMedianOfMedians(int[] arr, int left, int right) {
            int size = right - left + 1;
            int offset = size % 5 == 0 ? 0 : 1;
            int[] medians = new int[size / 5 + offset];
            for (int group = 0; group < medians.length; group++) {
                int start = left + group * 5;
                int end = Math.min(left + 4, right);
                insertionSort(arr, start, end);
                medians[group] = arr[(start + end) >> 1];
            }
            // 继续求中位数
            return bfprt(medians, 0, medians.length - 1, medians.length >> 1);
        }

        private static void insertionSort(int[] arr, int start, int end) {
            for (int i = start; i <= end; i++) {
                for (int j = i; j > start; j--) {
                    if (arr[j - 1] > arr[j]) {
                        swap(arr, j - 1, j);
                    }
                }
            }
        }
    }

    /**
     * 快排
     */
    private static class QuickSortIdea {

        public static int majorityElement(int[] arr) {
            assert arr != null && arr.length > 0;
            int mid = arr.length >> 1;
            return recurse(arr, 0, arr.length - 1, mid);
        }

        private static int recurse(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return arr[index];
            }
            int swapIdx = left + (int) (Math.random() * (right - left + 1));
            int pivot = arr[swapIdx];

            int[] partition = partition(arr, left, right, pivot);
            int start = partition[0], end = partition[1];

            if (index >= start && index <= end) {
                return arr[index];
            } else if (index < start) {
                return recurse(arr, left, start - 1, index);
            } else {
                // index > end
                return recurse(arr, end + 1, right, index);
            }
        }

        private static int[] partition(int[] arr, int left, int right, int pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int lessThanIdx = left - 1;
            int moreThanIdx = right + 1;
            for (int idx = left; idx < moreThanIdx; ) {
                if (arr[idx] > pivot) {
                    swap(arr, idx, --moreThanIdx);
                } else if (arr[idx] < pivot) {
                    swap(arr, idx++, ++lessThanIdx);
                } else {
                    // arr[idx] == pivot
                    idx++;
                }
            }
            if (lessThanIdx + 1 > moreThanIdx - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{lessThanIdx + 1, moreThanIdx - 1};
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 2, 3};
        System.out.println(BruteForce.majorityElement(arr));

        arr = new int[]{2, 2, 1, 1, 1, 2, 2};
        System.out.println(BruteForce.majorityElement(arr));

        arr = new int[]{3, 2, 3};
        System.out.println(BFPRT.majorityElement(arr));

        arr = new int[]{2, 2, 1, 1, 1, 2, 2};
        System.out.println(BFPRT.majorityElement(arr));
    }

}
