package com.practice2.sort.practice;

import cn.hutool.core.util.RandomUtil;
import com.practice2.sort.CompareSort;

import java.util.Arrays;

/**
 * @author Xulg
 * @since 2021-10-30 16:22
 * Description: 堆排
 */
class HeapSort {

    /*
     * int p = 0;
     * int left = 2 * p + 1;
     * int right = 2 * p + 2;
     */

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int idx = arr.length - 1; idx >= 0; idx--) {
            heapify(arr, idx, arr.length);
        }
        for (int idx = arr.length - 1; idx >= 0; idx--) {
            swap(arr, 0, idx);
            heapify(arr, 0, idx);
        }
    }

    // 向下大根堆化
    private static void heapify(int[] arr, int parentIdx, int heapSize) {
        int leftIdx = 2 * parentIdx + 1;
        while (leftIdx < heapSize) {
            int rightIdx = leftIdx + 1;
            int maxSubIdx = rightIdx >= heapSize ? leftIdx
                    : (arr[leftIdx] > arr[rightIdx] ? leftIdx : rightIdx);
            int maxIdx = arr[maxSubIdx] > arr[parentIdx] ? maxSubIdx : parentIdx;
            if (maxIdx == parentIdx) {
                break;
            }
            swap(arr, parentIdx, maxIdx);
            parentIdx = maxIdx;
            leftIdx = 2 * parentIdx + 1;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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
            sort(arr1);
            CompareSort.sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                System.out.println("fucking");
            }
        }
        System.out.println("good job");
    }
}
