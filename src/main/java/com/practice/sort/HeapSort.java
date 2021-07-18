package com.practice.sort;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * O(N*logN)
 * 不稳定
 *
 * @author Xulg
 * Created in 2021-04-29 14:51
 */
class HeapSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        // 堆化数组
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapify(arr, 0, i);
        }
    }

    private static void heapInsert(int[] arr, int index) {
        // 向上堆化
        while (index > 0) {
            int parentIdx = (index - 1) >> 1;
            // 保证大根堆
            if (arr[parentIdx] < arr[index]) {
                swap(arr, parentIdx, index);
            }
            index = parentIdx;
        }
    }

    private static void heapify(int[] arr, int index, int heapSize) {
        // 向下堆化
        int leftSubIdx = (index << 1) + 1;
        while (leftSubIdx < heapSize) {
            int rightSubIdx = leftSubIdx + 1;
            // 取子节点的最大那个
            int maxSubIdx = (rightSubIdx < heapSize) ? (arr[leftSubIdx] > arr[rightSubIdx] ? leftSubIdx : rightSubIdx) : leftSubIdx;
            // 取父节点，左节点，右节点的最大值
            int maxIdx = (arr[index] < arr[maxSubIdx]) ? maxSubIdx : index;
            if (index == maxIdx) {
                // 调整结束了
                break;
            }
            swap(arr, index, maxIdx);
            // 继续向下
            index = maxIdx;
            leftSubIdx = (maxIdx << 1) + 1;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[100];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt();
            }
            int[] arr1 = arr.clone();
            Arrays.sort(arr1);
            int[] arr2 = arr.clone();
            sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                System.out.println("fucking......");
                return;
            }
        }
        System.out.println("success......");
    }
}
