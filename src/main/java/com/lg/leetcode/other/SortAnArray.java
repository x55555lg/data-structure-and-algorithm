package com.lg.leetcode.other;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * @author Xulg
 * Description: leetcode_912
 * Created in 2021-05-26 22:44
 */
class SortAnArray {

    /*
     * Given an array of integers nums, sort the array in ascending order.
     *
     * Example 1:
     * Input: nums = [5,2,3,1]
     * Output: [1,2,3,5]
     *
     * Example 2:
     * Input: nums = [5,1,1,2,0,0]
     * Output: [0,0,1,1,2,5]
     *
     * Constraints:
     * 1 <= nums.length <= 5 * 104
     * -5 * 104 <= nums[i] <= 5 * 104
     */

    public static int[] sortArray(int[] nums) {
        Arrays.sort(nums);
        return nums;
    }

    private static class QuickSort {
        public static int[] sortArray(int[] nums) {
            if (nums == null || nums.length < 2) {
                return nums;
            }
            quickSort(nums, 0, nums.length - 1);
            return nums;
        }

        private static void quickSort(int[] arr, int left, int right) {
            if (left >= right) {
                return;
            }
            int pivot = arr[left + (int) (Math.random() * (right - left + 1))];
            int[] partition = partition(arr, left, right, pivot);
            int equalsStart = partition[0], equalsEnd = partition[1];
            quickSort(arr, left, equalsStart - 1);
            quickSort(arr, equalsEnd + 1, right);
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
                if (arr[idx] < pivot) {
                    swap(arr, idx++, ++less);
                } else if (arr[idx] > pivot) {
                    swap(arr, idx, --more);
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

    private static class MergeSort {
        public static int[] sortArray(int[] nums) {
            if (nums == null || nums.length < 2) {
                return nums;
            }
            mergeSort(nums, 0, nums.length - 1);
            return nums;
        }

        private static void mergeSort(int[] arr, int left, int right) {
            if (left == right) {
                return;
            }
            int mid = left + ((right - left) >> 1);
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, right, mid);
        }

        private static void merge(int[] arr, int left, int right, int mid) {
            int[] helper = new int[right - left + 1];
            int idx = 0, leftIdx = left, rightIdx = mid + 1;
            while (leftIdx <= mid && rightIdx <= right) {
                if (arr[leftIdx] <= arr[rightIdx]) {
                    helper[idx++] = arr[leftIdx++];
                } else {
                    helper[idx++] = arr[rightIdx++];
                }
            }
            while (leftIdx <= mid) {
                helper[idx++] = arr[leftIdx++];
            }
            while (rightIdx <= right) {
                helper[idx++] = arr[rightIdx++];
            }
            for (int i = 0; i < helper.length; i++) {
                arr[left + i] = helper[i];
            }
        }
    }

    private static class HeapSort {
        public static int[] sortArray(int[] nums) {
            if (nums == null || nums.length < 2) {
                return nums;
            }
            heapSort(nums);
            return nums;
        }

        private static void heapSort(int[] arr) {
            for (int idx = arr.length - 1; idx >= 0; idx--) {
                heapify(arr, idx, arr.length);
            }
            for (int idx = arr.length - 1; idx >= 0; idx--) {
                swap(arr, 0, idx);
                heapify(arr, 0, idx);
            }
        }

        /**
         * 向下大根堆化
         */
        private static void heapify(int[] arr, int index, int heapSize) {
            int leftSubIdx = (index << 1) + 1;
            while (leftSubIdx < heapSize) {
                int rightSubIdx = leftSubIdx + 1;
                // 取左右子节点中较大者
                int maxSubIdx = rightSubIdx >= heapSize ? leftSubIdx
                        : (arr[leftSubIdx] > arr[rightSubIdx] ? leftSubIdx : rightSubIdx);
                // 取父节点，max(左, 右)中的最大值
                int maxIdx = arr[index] > arr[maxSubIdx] ? index : maxSubIdx;
                if (maxIdx == index) {
                    // 还是父节点大，结束了
                    break;
                }
                // 交换父节点，max(左, 右)
                swap(arr, index, maxIdx);
                // next
                index = maxIdx;
                leftSubIdx = (index << 1) + 1;
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static class BubbleSort {
        public static int[] sortArray(int[] nums) {
            if (nums == null || nums.length < 2) {
                return nums;
            }
            bubbleSort(nums, 0, nums.length - 1);
            return nums;
        }

        private static void bubbleSort(int[] arr, int left, int right) {
            for (int i = left; i <= right; i++) {
                for (int j = i + 1; j <= right; j++) {
                    if (arr[i] > arr[j]) {
                        swap(arr, i, j);
                    }
                }
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static class SelectionSort {
        public static int[] sortArray(int[] nums) {
            if (nums == null || nums.length < 2) {
                return nums;
            }
            selectionSort(nums, 0, nums.length - 1);
            return nums;
        }

        private static void selectionSort(int[] arr, int left, int right) {
            for (int i = left; i < right; i++) {
                int minIdx = i;
                for (int j = i + 1; j <= right; j++) {
                    if (arr[j] < arr[minIdx]) {
                        minIdx = j;
                    }
                }
                swap(arr, i, minIdx);
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static class InsertionSort {
        public static int[] sortArray(int[] nums) {
            if (nums == null || nums.length < 2) {
                return nums;
            }
            insertionSort(nums, 0, nums.length - 1);
            return nums;
        }

        private static void insertionSort(int[] arr, int left, int right) {
            for (int i = left; i <= right; i++) {
                for (int j = i; j > left && arr[j - 1] > arr[j]; j--) {
                    swap(arr, j - 1, j);
                }
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static class Compare {
        public static int[] sortArray(int[] nums) {
            Arrays.sort(nums);
            return nums;
        }
    }

    public static void main(String[] args) {
        //String regex = "/[W|M]=[1-9][0-9]*/";
        String regex = "^([WM])-[1-9]\\d*$";
        System.out.println("W-0".matches(regex));
        System.out.println("W-10".matches(regex));
        System.out.println("W-21".matches(regex));
        System.out.println("W-1232131".matches(regex));
        System.out.println("W--1232131".matches(regex));
        System.out.println("M-0".matches(regex));
        System.out.println("M-10".matches(regex));
        System.out.println("M-21".matches(regex));
        System.out.println("M--1232131".matches(regex));
        System.out.println("dadada".matches(regex));

        System.out.println(String.format("%06d", 1));
        System.out.println(String.format("%06d", 12));
        System.out.println(String.format("%06d", 123));
        System.out.println(String.format("%06d", 1234));
        System.out.println(String.format("%06d", 12345));
        System.out.println(String.format("%06d", 123456));
        System.out.println(String.format("%06d", 1234567));

        System.out.println("H" + String.format("%06d", 126) + DateTime.now().toString("yyyyMMddHHmmssSSS") + RandomUtil.randomInt(10000, 100000));

        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(0, 100)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt();
            }
            int[] r0 = Compare.sortArray(arr.clone());
            int[] r1 = QuickSort.sortArray(arr.clone());
            int[] r2 = MergeSort.sortArray(arr.clone());
            int[] r3 = HeapSort.sortArray(arr.clone());
            int[] r4 = BubbleSort.sortArray(arr.clone());
            int[] r5 = SelectionSort.sortArray(arr.clone());
            int[] r6 = InsertionSort.sortArray(arr.clone());
            if (!Arrays.equals(r0, r1) || !Arrays.equals(r1, r2) || !Arrays.equals(r2, r3)
                    || !Arrays.equals(r3, r4) || !Arrays.equals(r4, r5) || !Arrays.equals(r5, r6)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }

}
