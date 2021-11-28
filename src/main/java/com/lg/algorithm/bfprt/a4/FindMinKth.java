package com.lg.algorithm.bfprt.a4;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-07-21 9:40
 * Description: 获取第K小的数
 */
class FindMinKth {

    private static class BruteForce {
        public static int findMinKth(int[] arr, int k) {
            assert arr != null && arr.length > 0 && k > 0;
            Arrays.sort(arr);
            return arr[k - 1];
        }
    }

    private static class QuickSortIdea {
        public static int findMinKth(int[] arr, int k) {
            assert arr != null && arr.length > 0 && k > 0;
            return recurse(arr, 0, arr.length - 1, k - 1);
        }

        private static int recurse(int[] arr, int left, int right, int index) {
            // base case
            if (left == right) {
                assert right == index;
                return arr[index];
            }
            int randomIdx = left + (int) (Math.random() * (right - left + 1));
            int[] partition = partition(arr, left, right, arr[randomIdx]);
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
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            int less = left - 1, more = right + 1;
            for (int idx = left; idx < more; ) {
                if (arr[idx] > pivot) {
                    swap(arr, idx, --more);
                } else if (arr[idx] < pivot) {
                    swap(arr, idx++, ++less);
                } else {
                    idx++;
                }
            }
            if (less + 1 > more - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{less + 1, more - 1};
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static class BFPRT {
        public static int findMinKth(int[] arr, int k) {
            assert arr != null && arr.length > 0 && k > 0;
            return recurse(arr, 0, arr.length - 1, k - 1);
        }

        private static int recurse(int[] arr, int left, int right, int index) {
            // base case
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
                return recurse(arr, left, start - 1, index);
            } else {
                assert index > end;
                return recurse(arr, end + 1, right, index);
            }
        }

        private static int getMedianOfMedians(int[] arr, int left, int right) {
            int size = right - left + 1;
            int offset = size % 5 == 0 ? 0 : 1;
            int[] medians = new int[size / 5 + offset];
            for (int group = 0; group < medians.length; group++) {
                int start = left + group * 5;
                int end = Math.min(start + 4, right);
                insertionSort(arr, start, end);
                medians[group] = arr[(start + end) >> 1];
            }
            return recurse(medians, 0, medians.length - 1, medians.length >> 1);
        }

        private static void insertionSort(int[] arr, int start, int end) {
            for (int i = start; i <= end; i++) {
                for (int j = i; j > start && arr[j - 1] > arr[j]; j--) {
                    swap(arr, j - 1, j);
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
            int less = left - 1, more = right + 1;
            for (int idx = left; idx < more; ) {
                if (arr[idx] > pivot) {
                    swap(arr, idx, --more);
                } else if (arr[idx] < pivot) {
                    swap(arr, idx++, ++less);
                } else {
                    idx++;
                }
            }
            if (less + 1 > more - 1) {
                return new int[]{-1, -1};
            } else {
                return new int[]{less + 1, more - 1};
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

    /* ******************************************************************************************************/

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(1, 11);
            int[] arr = generateIntArray(length);
            int k = RandomUtil.randomInt(1, arr.length + 1);
            int r = BruteForce.findMinKth(arr.clone(), k);
            int r1 = QuickSortIdea.findMinKth(arr.clone(), k);
            int r2 = BFPRT.findMinKth(arr.clone(), k);
            if (!(r == r1 && r1 == r2)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish!");
    }
}
