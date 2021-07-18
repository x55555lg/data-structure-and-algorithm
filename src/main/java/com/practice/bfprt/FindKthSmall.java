package com.practice.bfprt;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * 查第K小的数
 *
 * @author Xulg
 * Created in 2021-05-07 21:34
 */
class FindKthSmall {

    private static class BruteForce {
        public static int findKthSmall(int[] arr, int k) {
            assert arr != null && arr.length > 0 && k > 0;
            int[] another = arr.clone();
            Arrays.sort(another);
            return another[k - 1];
        }
    }

    private static class QuickSortIdea {

        public static int findKthSmall(int[] arr, int k) {
            assert arr != null && arr.length > 0 && k > 0;
            return recurse(arr.clone(), 0, arr.length - 1, k - 1);
        }

        private static int recurse(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert index == right;
                return arr[index];
            }
            int randomIdx = left + (int) (Math.random() * (right - left + 1));
            int pivot = arr[randomIdx];
            int[] partition = partition(arr, left, right, pivot);
            int start = partition[0], end = partition[1];
            if (index >= start && index <= end) {
                return arr[index];
            } else if (index < start) {
                return recurse(arr, left, start - 1, index);
            } else {
                assert index > end;
                return recurse(arr, end + 1, right, index);
            }
        }

        private static int[] partition(int[] arr, int left, int right, int pivot) {
            if (left == right) {
                return new int[]{left, right};
            }
            if (left > right) {
                return new int[]{-1, -1};
            }
            int lessIdx = left - 1;
            int moreIdx = right + 1;
            for (int idx = left; idx < moreIdx; ) {
                if (arr[idx] < pivot) {
                    swap(arr, idx++, ++lessIdx);
                } else if (arr[idx] > pivot) {
                    swap(arr, idx, --moreIdx);
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

    private static class BFPRT {
        public static int findKthSmall(int[] arr, int k) {
            assert arr != null && arr.length > 0 && k > 0;
            return bfprt(arr, 0, arr.length - 1, k - 1);
        }

        private static int bfprt(int[] arr, int left, int right, int index) {
            if (left == right) {
                assert index == right;
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
                // index > end
                return bfprt(arr, end + 1, right, index);
            }
        }

        private static int getMedianOfMedians(int[] arr, int left, int right) {
            int threshold = 5;
            int size = right - left + 1;
            int offset = (size % threshold == 0) ? 0 : 1;
            // median array
            int[] medians = new int[size / 5 + offset];
            for (int group = 0; group < medians.length; group++) {
                // sub array[start, end]
                int start = left + group * threshold;
                int end = Math.min(right, start + threshold - 1);
                // sort the sub array by insertion sort
                insertionSort(arr, start, end);
                // get the median of the sub array
                medians[group] = arr[(start + end) >> 1];
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

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        private static int[] partition(int[] arr, int left, int right, int pivot) {
            if (left == right) {
                return new int[]{left, right};
            }
            if (left > right) {
                return new int[]{-1, -1};
            }
            int lessIdx = left - 1, moreIdx = right + 1;
            for (int idx = left; idx < moreIdx; ) {
                if (arr[idx] < pivot) {
                    swap(arr, idx++, ++lessIdx);
                } else if (arr[idx] > pivot) {
                    swap(arr, idx, --moreIdx);
                } else {
                    idx++;
                }
            }
            if (lessIdx + 1 > moreIdx - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{lessIdx + 1, moreIdx - 1};
            }
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(1, 101)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(0, 100000);
            }
            int k = RandomUtil.randomInt(1, arr.length + 1);
            int r0 = BruteForce.findKthSmall(arr.clone(), k);
            int r1 = QuickSortIdea.findKthSmall(arr.clone(), k);
            int r2 = BFPRT.findKthSmall(arr.clone(), k);
            if (!(r0 == r1 && r1 == r2)) {
                System.out.println("fucking......");
            }
        }
        System.out.println("good job");
    }
}
