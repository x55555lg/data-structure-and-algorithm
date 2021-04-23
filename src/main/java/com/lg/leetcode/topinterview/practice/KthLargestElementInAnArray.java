package com.lg.leetcode.topinterview.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2021-02-23 17:00
 */
class KthLargestElementInAnArray {

    /*
     * Find the kth largest element in an unsorted array.
     * Note that it is the kth largest element in the sorted order,
     * not the kth distinct element.
     *
     * Example 1:
     * Input: [3,2,1,5,6,4] and k = 2
     * Output: 5
     *
     * Example 2:
     * Input: [3,2,3,1,2,4,5,5,6] and k = 4
     * Output: 4
     *
     * Note: You may assume k is always valid, 1 ≤ k ≤ array's length.
     *------------------------------------------------------------------
     * 在数组中找第k大的数(类似于在数组中找到第k小的数)
     */

    /**
     * 快排思想
     */
    private static class QuickSortIdea {
        public static int findKthLargest(int[] nums, int k) {
            return recurse(nums, 0, nums.length - 1, k - 1);
        }

        private static int recurse(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return arr[index];
            }

            // random position
            int randomIdx = left + (int) (Math.random() * (right - left + 1));
            int pivot = arr[randomIdx];

            // partition
            int[] partition = partition(arr, left, right, pivot);

            if (index >= partition[0] && index <= partition[1]) {
                return arr[index];
            } else if (index < partition[0]) {
                return recurse(arr, left, partition[0] - 1, index);
            } else {
                // index > partition[1]
                return recurse(arr, partition[1] + 1, right, index);
            }
        }

        /**
         * 分区操作
         * arr: 大于区 等于区间 小于区
         */
        private static int[] partition(int[] arr, int left, int right, int pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            int more = left - 1;
            int less = right + 1;
            for (int idx = left; idx < less; ) {
                if (arr[idx] > pivot) {
                    swap(arr, ++more, idx++);
                } else if (arr[idx] < pivot) {
                    swap(arr, --less, idx);
                } else {
                    // arr[idx] == pivot
                    idx++;
                }
            }
            if (more + 1 > less - 1) {
                // not exist equals zone
                return new int[]{-1, -1};
            } else {
                return new int[]{more + 1, less - 1};
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    /**
     * BFPRT算法
     */
    @SuppressWarnings("AlibabaClassNamingShouldBeCamel")
    private static class BFPRT {

        public static int findKthLargest(int[] nums, int k) {
            return bfprt(nums, 0, nums.length - 1, k - 1);
        }

        private static int bfprt(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return arr[index];
            }

            // 选择中位数
            int median = getMedianOfMedians(arr, left, right);

            // partition
            int[] partition = partition(arr, left, right, median);

            if (index >= partition[0] && index <= partition[1]) {
                return arr[index];
            } else if (index < partition[0]) {
                return bfprt(arr, left, partition[0] - 1, index);
            } else {
                // index > partition[1]
                return bfprt(arr, partition[1] + 1, right, index);
            }
        }

        private static int getMedianOfMedians(int[] arr, int left, int right) {
            int size = right - left + 1;
            int offset = size % 5 == 0 ? 0 : 1;
            int[] medians = new int[size / 5 + offset];
            for (int group = 0; group < medians.length; group++) {
                int start = left + 5 * group;
                int end = Math.min(right, start + 4);
                insertSort(arr, start, end);
                medians[group] = arr[(start + end) >> 1];
            }
            // 继续求中位数
            return bfprt(medians, 0, medians.length - 1, medians.length >> 1);
        }

        private static void insertSort(int[] arr, int start, int end) {
            for (int i = start; i <= end; i++) {
                for (int j = i; j > start; j--) {
                    if (arr[j - 1] > arr[j]) {
                        swap(arr, j - 1, j);
                    }
                }
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        /**
         * 分区操作
         * arr: 大于区 等于区间 小于区
         */
        private static int[] partition(int[] arr, int left, int right, int pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            int more = left - 1;
            int less = right + 1;
            for (int idx = left; idx < less; ) {
                if (arr[idx] > pivot) {
                    swap(arr, ++more, idx++);
                } else if (arr[idx] < pivot) {
                    swap(arr, --less, idx);
                } else {
                    // arr[idx] == pivot
                    idx++;
                }
            }
            if (more + 1 > less - 1) {
                // not exist equals zone
                return new int[]{-1, -1};
            } else {
                return new int[]{more + 1, less - 1};
            }
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(1, 11);
            int[] arr = generateIntArray(length);
            int k = RandomUtil.randomInt(1, arr.length + 1);
            int r = getMaxKthForCompare(arr.clone(), k);
            int r1 = QuickSortIdea.findKthLargest(arr.clone(), k);
            int r2 = BFPRT.findKthLargest(arr.clone(), k);
            if (!(r == r1 && r1 == r2)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish!");
    }

    /* ******************************************************************************************************/

    /* 对数器 */

    public static int[] generateIntArray(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = RandomUtil.randomInt(-10, 11);
        }
        return arr;
    }

    public static int getMaxKthForCompare(int[] arr, int k) {
        assert arr != null && arr.length > 0 : "what the hell?";
        assert k >= 1 && k <= arr.length : "what the hell?";
        Arrays.sort(arr);
        return arr[arr.length - k];
    }

    /* ******************************************************************************************************/

}
