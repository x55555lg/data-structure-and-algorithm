package com.lg.algorithm.bfprt.a1;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * 在一个数组int[] arr中，查找第k小的数
 *
 * @author Xulg
 * Created in 2021-04-12 9:50
 */
class FindMinKth {

    public static int findMinKth(int[] arr, int k) {
        assert arr != null && arr.length > 0 && k > 0 && k <= arr.length;
        return QuickSortIdea.findMinKth(arr, k);
    }

    /**
     * 快排算法
     */
    private static class QuickSortIdea {

        public static int findMinKth(int[] arr, int k) {
            assert arr != null && arr.length > 0 && k > 0 && k <= arr.length;
            return recurse(arr, 0, arr.length - 1, k - 1);
        }

        // arr[left, right]上找index小的值
        private static int recurse(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return arr[index];
            }

            // left = 1; right = 5
            // [0, 1)
            // [0, 5)
            // [1, 6)
            int randomIdx = left + (int) (Math.random() * (right - left + 1));
            int pivot = arr[randomIdx];

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

        // arr[left, right]范围上根据pivot值对arr进行分区操作，pivot一定是arr[left, right]上的值
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
                    assert arr[idx] == pivot;
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

    /**
     * BFPRT算法
     */
    private static class BFPRT {

        public static int findMinKth(int[] arr, int k) {
            assert arr != null && arr.length > 0 && k > 0 && k <= arr.length;
            return bfprt(arr, 0, arr.length - 1, k - 1);
        }

        private static int bfprt(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert right == index;
                return arr[index];
            }
            int median = getMedianOfMedians(arr, left, right);
            int[] partition = partition(arr, left, right, median);
            int start = partition[0], end = partition[1];
            if (index >= start && index <= end) {
                return arr[index];
            } else if (index < start) {
                return bfprt(arr, left, start - 1, index);
            } else {
                return bfprt(arr, end + 1, right, index);
            }
        }

        private static int getMedianOfMedians(int[] arr, int left, int right) {
            int threshold = 5;
            int size = right - left + 1;
            int offset = size % threshold == 0 ? 0 : 1;
            int[] medians = new int[size / threshold + offset];
            for (int group = 0; group < medians.length; group++) {
                int start = left + group * threshold;
                int end = Math.min(start + threshold - 1, right);
                insertionSort(arr, start, end);
                // [start, end]范围上的中位数
                medians[group] = arr[(end + start) >> 1];
            }
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

        private static int[] partition(int[] arr, int left, int right, int pivot) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int lessIdx = left - 1, moreIdx = right + 1;
            for (int idx = left; idx < moreIdx; ) {
                if (arr[idx] > pivot) {
                    swap(arr, idx, --moreIdx);
                } else if (arr[idx] < pivot) {
                    swap(arr, idx++, ++lessIdx);
                } else {
                    assert arr[idx] == pivot;
                    idx++;
                }
            }
            if (lessIdx + 1 > moreIdx - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{lessIdx + 1, moreIdx - 1};
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
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

    public static int getMinKthForCompare(int[] arr, int k) {
        assert arr != null && arr.length > 0 : "what the hell?";
        assert k >= 1 && k <= arr.length : "what the hell?";
        Arrays.sort(arr);
        return arr[k - 1];
    }

    /* ******************************************************************************************************/

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(1, 11);
            int[] arr = generateIntArray(length);
            int k = RandomUtil.randomInt(1, arr.length + 1);
            int r = getMinKthForCompare(arr.clone(), k);
            int r1 = QuickSortIdea.findMinKth(arr.clone(), k);
            int r2 = BFPRT.findMinKth(arr.clone(), k);
            if (!(r == r1 && r1 == r2)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish!");
    }
}
