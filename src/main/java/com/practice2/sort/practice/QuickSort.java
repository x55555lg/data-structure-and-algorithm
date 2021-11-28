package com.practice2.sort.practice;

import cn.hutool.core.util.RandomUtil;
import com.practice2.sort.CompareSort;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-10-30 16:22
 * Description: 随机快排
 */
class QuickSort {

    private static class RandomQuickSort {
        public static void sort(int[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }
            recurse(arr, 0, arr.length - 1);
        }

        private static void recurse(int[] arr, int left, int right) {
            if (left >= right) {
                return;
            }
            int randomIdx = left + (int) (Math.random() * (right - left + 1));
            int pivot = arr[randomIdx];
            int[] partition = partition(arr, left, right, pivot);
            int equalsStart = partition[0], equalsEnd = partition[1];
            recurse(arr, left, equalsStart - 1);
            recurse(arr, equalsEnd + 1, right);
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


    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 100_0000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(0, 100)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
            int[] arr1 = arr.clone();
            int[] arr2 = arr.clone();
            CompareSort.sort(arr1);
            RandomQuickSort.sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }
}
